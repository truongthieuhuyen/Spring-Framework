package Threading.Activity1.Act_13;

import java.util.Random;

import static jdk.nashorn.internal.objects.NativeMath.random;

public class GenNumThread extends Thread {
    @Override
    public void run() {
        Random random = new Random();
        while (true) {
            int year = 1000;
            year = year + random.nextInt(8999);
            System.out.println(Thread.currentThread().getId() + ": generated year " + year);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

