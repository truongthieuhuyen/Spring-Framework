package Threading.Activity1.Act_11;

public class MyThread extends Thread {

    @Override
    public void run() {
        for (int i = 1; i <= 50; i++) {
            System.out.println("thread " + i);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("END");
    }
}
