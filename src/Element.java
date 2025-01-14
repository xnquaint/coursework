public abstract class Element {
    protected String name;
    protected double tCurrent; // Поточний час
    protected double tNext;    // Час наступної події
    protected int state;       // Стан (0 - вільний, 1 - зайнятий)
    protected int quantity;    // Кількість оброблених подій
    protected double delayMean; // Середнє значення затримки
    protected double delayDev;  // Відхилення затримки

    public Element(String name) {
        this.name = name;
        this.tCurrent = 0.0;
        this.tNext = Double.MAX_VALUE;
        this.state = 0;
        this.quantity = 0;
    }

    public abstract void inAct();

    public abstract void outAct();

    public double getDelay() {
        return FunRand.Unif(delayMean - delayDev, delayMean + delayDev);
    }

    public void setUnifDistribution(double delayMean, double delayDev) {
        this.delayMean = delayMean;
        this.delayDev = delayDev;
    }

    public String getName() {
        return name;
    }

    public double getTNext() {
        return tNext;
    }

    public void setTCurrent(double tCurrent) {
        this.tCurrent = tCurrent;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getQuantity() {
        return quantity;
    }

    public void printInfo() {
        System.out.println(name + " | Оброблено: " + quantity);
    }
}
