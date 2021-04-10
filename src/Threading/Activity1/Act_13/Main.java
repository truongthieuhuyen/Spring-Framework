package Threading.Activity1.Act_13;

public class Main {
    public static void main(String[] args) {
        GenNumThread genNum = new GenNumThread();
        genNum.start();

        CheckNumThread checkNum = new CheckNumThread();
        checkNum.start();
    }
}
