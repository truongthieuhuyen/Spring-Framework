package main.java.code.Threading.Activity1.Act_12;

public class MyThread extends Thread {
    private String name;
    private int sleep;

    public MyThread(String name, int sleep) {
        this.name = name;
        this.sleep = sleep;
//        this.setName(name);
    }

    @Override
    public void run() {
        while (true) {
            System.out.println(System.currentTimeMillis() + " " + name);
            try {
                Thread.sleep(sleep);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
