package Threading.Activity1.Act_14;

import java.util.*;

public class Main {
    public static Map<String, String> map_14 = new HashMap<>();
    public static String key = "";
    public static boolean flag ;

    public static void main(String[] args) {
        map_14.put("Monday ", " 星期一");
        map_14.put("Tuesday ", " 星期二");
        map_14.put("Wednesday ", " 星期三");
        map_14.put("Thursday ", " 星期四");
        map_14.put("Friday ", " 星期五");
        map_14.put("Saturday ", " 星期六");
        map_14.put("Sunday ", " 星期天");
//        map_14.putIfAbsent(null, " 根本没有人爱你");
//        System.out.println(map_14);

        PullDays pullDays = new PullDays();
        GetDays getDays = new GetDays();
        pullDays.start();
        getDays.start();

    }
}

class PullDays extends Thread {
    @Override
    public void run() {
        Random r = new Random();
        Object[] keyList = Main.map_14.keySet().toArray();
        while (true) {
            Main.key = keyList[r.nextInt(keyList.length)].toString();
            System.out.println(Thread.currentThread().getName() + " Key :" + Main.key);
            Main.flag = true;
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class GetDays extends Thread {
    @Override
    public void run() {
        while (true) {
            if (Main.flag == true) {
                String value = Main.map_14.get(Main.key);
                System.out.println(Thread.currentThread().getName() + " Value :" + value);
                Main.flag = false;
            }
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

