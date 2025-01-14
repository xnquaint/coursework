import java.util.ArrayList;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Create creatorAB = new Create("Creator AB");
        Create creatorBA = new Create("Creator BA");

        Process channelAB = new Process("Канал AB", 10, 0);
        Process channelBA = new Process("Канал BA", 10, 0);

        Process satellite = new Process("Супутниковий канал", 10, 5);

        channelAB.setSatellite(satellite);
        channelBA.setSatellite(satellite);

        creatorAB.setNextElement(channelAB);
        creatorBA.setNextElement(channelBA);

        ArrayList<Element> elements = new ArrayList<>();
        elements.add(creatorAB);
        elements.add(creatorBA);
        elements.add(channelAB);
        elements.add(channelBA);
        elements.add(satellite);

        Model model = new Model(elements);
        System.out.println("Початок моделювання...");
        model.simulate(100000.0);
    }
}

// verification
//public class Main {
//    public static void main(String[] args) {
//        int runs = 20;
//        double totalCallFreq = 0.0;
//        double totalSatelliteLoad = 0.0;
//        double totalMainChannelLoadAB = 0.0;
//        double totalMainChannelLoadBA = 0.0;
//        double totalModelingTime = 300000.0;
//        int totalFailures = 0;
//
//        for (int i = 0; i < runs; i++) {
//            Create creatorAB = new Create("Creator AB");
//            Create creatorBA = new Create("Creator BA");
//
//            Process channelAB = new Process("Канал AB", 10, 0);
//            Process channelBA = new Process("Канал BA", 10, 0);
//
//            Process satellite = new Process("Супутниковий канал", 10, 5);
//
//            channelAB.setSatellite(satellite);
//            channelBA.setSatellite(satellite);
//
//            creatorAB.setNextElement(channelAB);
//            creatorBA.setNextElement(channelBA);
//
//            ArrayList<Element> elements = new ArrayList<>();
//            elements.add(creatorAB);
//            elements.add(creatorBA);
//            elements.add(channelAB);
//            elements.add(channelBA);
//            elements.add(satellite);
//
//            // Запуск моделювання
//            Model model = new Model(elements);
//            model.simulate(300000.0);
//
//            int totalGenerated = creatorAB.getQuantity() + creatorBA.getQuantity();
//            double satelliteUtilization = satellite.getUtilization(totalModelingTime);
//
//            // Збір результатів для усереднення
//            totalCallFreq += totalGenerated > 0 ? (satelliteUtilization * totalModelingTime) / totalGenerated : 0;
//            totalSatelliteLoad += satelliteUtilization;
//            totalMainChannelLoadAB += channelAB.getUtilization(totalModelingTime);
//            totalMainChannelLoadBA += channelBA.getUtilization(totalModelingTime);
//            totalFailures += channelAB.getFailures() + channelBA.getFailures();
//        }
//
//        // Усереднення
//        System.out.println("\nУсереднені результати після " + runs + " прогонів:");
//        System.out.println("Частота викликів супутникового каналу (callFreq): " + (totalCallFreq / runs));
//        System.out.println("Завантаження супутникового каналу (satelliteLoad): " + (totalSatelliteLoad / runs));
//        System.out.println("Завантаження основного каналу AB (mainChannelLoad AB): " + (totalMainChannelLoadAB / runs));
//        System.out.println("Завантаження основного каналу BA (mainChannelLoad BA): " + (totalMainChannelLoadBA / runs));
//        System.out.println("Середня кількість втрат (failures): " + (totalFailures / runs));
//    }
//}

//experiments
//public class Main {
//    public static void main(String[] args) throws IOException {
//        Create creatorAB = new Create("Creator AB");
//        Create creatorBA = new Create("Creator BA");
//        Process channelAB = new Process("Канал AB", 10, 0);
//        Process channelBA = new Process("Канал BA", 10, 0);
//        Process satellite = new Process("Супутниковий канал", 10, 5);
//
//        channelAB.setSatellite(satellite);
//        channelBA.setSatellite(satellite);
//        creatorAB.setNextElement(channelAB);
//        creatorBA.setNextElement(channelBA);
//
//        ArrayList<Element> elements = new ArrayList<>();
//        elements.add(creatorAB);
//        elements.add(creatorBA);
//        elements.add(channelAB);
//        elements.add(channelBA);
//        elements.add(satellite);
//
//        Model model = new Model(elements);
//        model.simulate(40000.0);
//
//        double transitionPeriod = model.determineTransitionPeriod();
//        System.out.println("Тривалість перехідного періоду: " + transitionPeriod);
//
//        model.exportDataToCSV("simulation_results.csv");
//    }
//}

