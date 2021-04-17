package Threading.Activity1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

//Activity-16: Viết chương trình tìm số nguyên có số lượng ước lớn nhất trong dải từ 1 tới 100.000
public class Act_16 {
    public static Integer maxUoc = 1;
    public static List<Integer> result = new ArrayList<>();
    public static BlockingQueue<Integer> input = new LinkedBlockingQueue<>();// sử dụng blocking queue

    public static void main(String[] args) throws InterruptedException {
        for (int i = 1; i <= 100000; i++) {
            input.put(i);//đưa 100.000 số vào List 'result'
        }
        System.out.println( System.currentTimeMillis()+ ": Start" );   //in ra console "Start ..."
        int numThread = 100;//tạo ra sô lượng thread theo ý muốn
        for (int i = 0; i < numThread; i++) {
            Thread thread = new checkSingleNumber();
            thread.start();
        }
//        for (int i = 1; i <= 100000; i++) {
//            int soUoc = timSoUoc(i);
//    if (soUoc > maxUoc){
//        maxUoc = soUoc;
//        ressult.removeAll(ressult);
//        ressult.add(i);
//    }
//        }
    }
}

//tạo thread check từng số trong List 'result'
class checkSingleNumber extends Thread {
    @Override
    public void run() {
        while (true) {
            try {
                Integer num = Act_16.input.poll(10, TimeUnit.MILLISECONDS);// kéo trong List ra
                if (num != null) {
                    int soUoc = timSoUoc(num);// counter (đếm được ở hàm timSoUoc)
                    updateMax(num, soUoc);
                } else {
                    System.out.println(System.currentTimeMillis() + ": " + Act_16.result.toString() + " có số lượng ước là " + Act_16.maxUoc);
                    Thread.currentThread().stop();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // tìm số lượng ước của 1 số trong List 'result'
    private Integer timSoUoc(int number) {
        int counter = 0;// bộ đếm  = 0
        for (int i = 1; i <= number; i++) {
            if (number % i == 0) {
                counter++;
            }
        }
        return counter;

    }

    // update vào List 'result'
    private synchronized void updateMax(int number, int soUoc) {
        if (Act_16.maxUoc < soUoc) {
            Act_16.maxUoc = soUoc;
            Act_16.result.clear();// xoá List cũ xong add List mới
            Act_16.result.add(number);
        } else if (Act_16.maxUoc == soUoc) {
            Act_16.result.add(number);// chỉ cần add vào List
        }
    }
}