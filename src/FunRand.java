public class FunRand {
    public static double Unif(double timeMin, double timeMax) {
        return timeMin + Math.random() * (timeMax - timeMin);
    }
}
