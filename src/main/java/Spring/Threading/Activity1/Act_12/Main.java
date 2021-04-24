package main.java.code.Threading.Activity1.Act_12;

public class Main {
    public static void main(String[] args) {
        MyThread m1 = new MyThread("ABA",3500);
        MyThread m2 = new MyThread("BCD",2500);
        m1.start();
        m2.start();
    }
}
