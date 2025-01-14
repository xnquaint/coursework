public class Process extends Element {
    private int maxQueue;       // Максимальний розмір черги
    private int queue;          // Поточна довжина черги
    private int failures;       // Кількість втрачених пакетів
    private Process satellite;  // Супутниковий канал
    private double totalWorkingTime; // Сумарний час роботи каналу

    public Process(String name, double delayMean, double delayDev) {
        super(name);
        setUnifDistribution(delayMean, delayDev);
        this.maxQueue = 1;
        this.queue = 0;
        this.failures = 0;
        this.totalWorkingTime = 0.0;
    }

    public void setSatellite(Process satellite) {
        this.satellite = satellite;
        System.out.println(name + " тепер має супутниковий канал: " + satellite.getName());
    }

    @Override
    public void inAct() {
        System.out.println(name + " отримав подію");
        if (state == 0) {
            // Якщо канал вільний, починаємо обробку
            state = 1;
            double delay = getDelay();
            tNext = tCurrent + delay;
            totalWorkingTime += delay; // Додаємо час роботи каналу
        } else if (queue < maxQueue) {
            // Якщо канал зайнятий, додаємо до черги
            queue++;
        } else if (satellite != null && satellite.getState() == 0) {
            // Якщо супутниковий канал вільний, передаємо подію
            satellite.inAct();
        } else {
            // Інакше фіксуємо втрату
            failures++;
        }
    }

    @Override
    public void outAct() {
        quantity++;
        if (queue > 0) {
            // Обробляємо наступну подію з черги
            queue--;
            tNext = tCurrent + getDelay();
        } else {
            // Звільняємо канал
            state = 0;
            tNext = Double.MAX_VALUE;
        }
    }


    public int getFailures() {
        return failures;
    }

    public double getTotalWorkingTime() {
        return totalWorkingTime;
    }

    public double getUtilization(double totalModelingTime) {
        return totalWorkingTime / totalModelingTime;
    }

    @Override
    public void printInfo() {
        System.out.println(name + " | Оброблено: " + quantity + ", Черга: " + queue + ", Втрати: " + failures);
    }
}
