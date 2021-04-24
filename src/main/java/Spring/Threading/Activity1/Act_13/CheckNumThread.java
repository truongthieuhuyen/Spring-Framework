package main.java.code.Threading.Activity1.Act_13;

public class CheckNumThread extends Thread {

    @Override
    public void run() {
        while (true) {
            if (GenNumThread.flag = true) {
                if (GenNumThread.year % 4 == 0 & GenNumThread.year % 100 != 0) {
                    System.out.println(Thread.currentThread().getName() + ": " + GenNumThread.year + " is a leap year");
                } else {
                    System.out.println(Thread.currentThread().getName() + ": " + GenNumThread.year + " is not a leap year");
                }GenNumThread.flag = false;
            }
            try {
                Thread.sleep(GenNumThread.sleep);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
