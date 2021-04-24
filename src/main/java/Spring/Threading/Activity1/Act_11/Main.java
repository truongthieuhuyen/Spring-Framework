package Spring.Threading.Activity1.Act_11;

public class Main {
    public static void main(String[] args) {
        System.out.println("MyThread starts running");
        for (int i=1; i<=50;i++){
            MyThread myThread = new MyThread();
            myThread.start();
        }
    }
}
