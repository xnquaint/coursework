public class Create extends Element {
    private Element nextElement;

    public Create(String name) {
        super(name);
        setUnifDistribution(10, 3);
        tNext = 0.0;
    }

    @Override
    public void inAct() {
        outAct();
    }

    @Override
    public void outAct() {
        quantity++;
        tNext = tCurrent + getDelay();
        if (nextElement != null) {
            nextElement.inAct();
        }
    }

    public void setNextElement(Element element) {
        this.nextElement = element;
        System.out.println(name + " тепер з'єднаний з " + element.getName());
    }
}
