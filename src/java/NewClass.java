/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Administrator
 */
public class NewClass extends Thread {

    private static int minute = 90;

    private static int second = 0;

    private int hour = 0;

    public void run() {
        if (minute >= 60) {
            minute -= 60;
            hour += 1;
        }
        while (hour * minute * second >= 0) {
            System.out.println(hour + ":" + minute + ":" + second);

            try {
                sleep(1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if (second != 0) {
                second--;

            } else {
                second = 59;
                if (minute > 0) {
                    minute--;

                } else {
                    minute = 59;
                    if (hour > 0) {
                        hour--;

                    } else {

                        return;
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        NewClass t = new NewClass();
        t.start();

    }

}
