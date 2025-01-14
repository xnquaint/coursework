import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Model {
    private double tCurrent;
    private double tNext;
    private List<Element> elements;
    private List<Double> timePoints = new ArrayList<>();
    private List<Double> responseValues = new ArrayList<>();

    public Model(List<Element> elements) {
        this.elements = elements;
        this.tCurrent = 0.0;
        this.tNext = Double.MAX_VALUE;
    }

    public void simulate(double time) {
        while (tCurrent < time) {
            tNext = Double.MAX_VALUE;
            Element nextEvent = null;

            for (Element e : elements) {
                if (e.getTNext() < tNext) {
                    tNext = e.getTNext();
                    nextEvent = e;
                }
            }

            if (tNext == Double.MAX_VALUE) {
                System.out.println("Жодної події немає. tCurrent: " + tCurrent);
                break;
            }

            tCurrent = tNext;
            for (Element e : elements) {
                e.setTCurrent(tCurrent);
            }

            if (nextEvent != null) {
                nextEvent.outAct();
            }

            collectData(tCurrent, calculateResponse());

            for (Element e : elements) {
                e.printInfo();
            }
        }

        printResults();
    }

    private void collectData(double currentTime, double responseValue) {
        timePoints.add(currentTime);
        responseValues.add(responseValue);
    }

    private double calculateResponse() {
        int totalFailures = 0;
        for (Element e : elements) {
            if (e instanceof Process) {
                totalFailures += ((Process) e).getFailures();
            }
        }
        return totalFailures;
    }

    public double determineTransitionPeriod() {
        double epsilon = 0.01;
        for (int i = 1; i < responseValues.size(); i++) {
            double avgCurrent = responseValues.get(i);
            double avgPrevious = responseValues.get(i - 1);
            if (Math.abs(avgCurrent - avgPrevious) < epsilon) {
                return timePoints.get(i);
            }
        }
        return -1;
    }

    public void exportDataToCSV(String filename) throws IOException {
        try (PrintWriter writer = new PrintWriter(new File(filename))) {
            writer.println("Time,Response");
            for (int i = 0; i < timePoints.size(); i++) {
                writer.println(timePoints.get(i) + "," + responseValues.get(i));
            }
        }
    }

    public void printResults() {
        System.out.println("\nРезультати моделювання:");
        double totalModelingTime = tCurrent;
        int totalFailures = 0;
        double satelliteUtilization = 0.0;
        double mainChannelLoadAB = 0.0;
        double mainChannelLoadBA = 0.0;
        int totalGenerated = 0;

        for (Element e : elements) {
            e.printInfo();
            if (e instanceof Process) {
                Process process = (Process) e;
                totalFailures += process.getFailures();

                if (process.getName().equals("Супутниковий канал")) {
                    satelliteUtilization = process.getUtilization(totalModelingTime);
                } else if (process.getName().equals("Канал AB")) {
                    mainChannelLoadAB = process.getUtilization(totalModelingTime);
                } else if (process.getName().equals("Канал BA")) {
                    mainChannelLoadBA = process.getUtilization(totalModelingTime);
                }
            } else if (e instanceof Create) {
                totalGenerated += e.getQuantity();
            }
        }

        double callFreq = totalGenerated > 0 ? (satelliteUtilization * totalModelingTime) / totalGenerated : 0;

        System.out.println("\nВихідні характеристики:");
        System.out.println("Частота викликів супутникового каналу (callFreq): " + callFreq);
        System.out.println("Завантаження супутникового каналу (satelliteLoad): " + satelliteUtilization);
        System.out.println("Завантаження основного каналу AB (mainChannelLoad AB): " + mainChannelLoadAB);
        System.out.println("Завантаження основного каналу BA (mainChannelLoad BA): " + mainChannelLoadBA);
        System.out.println("Загальна кількість втрат (failures): " + totalFailures);
        System.out.println("Необхідний розмір буферів для роботи без втрат (bufferSizeNeeded): " + (totalFailures > 0 ? "Потребує збільшення" : "Достатній"));
    }
}
