package Threading.Activity1.Act_13;

import java.util.Random;

public class GenNumThread extends Thread {
    public static int sleep = 2000;
    public static boolean flag = false;
    public static int year;

    @Override
    public void run() {
        Random random = new Random();
        while (true) {
            year = 1000 + random.nextInt(8999);
            System.out.println(Thread.currentThread().getName() + ": generated year " + year);
            flag = true;
            try {
                Thread.sleep(sleep);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

