package Threading.Activity1.Act_11;

public class Main {
    public static void main(String[] args) {
        System.out.println("MyThread starts running");
        MyThread myThread = new MyThread();
        myThread.start();
    }
}
