package demo.thread;

import com.sun.tools.javac.util.StringUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolDemo {

    public static void main(String[] args) {
        /*ExecutorService executorService = Executors.newFixedThreadPool(3);
        int[] resutl = new int[3];

        for(int i = 0; i < 10; i++) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println("running... " + Thread.currentThread());
                }
            });
        }
        System.out.println("shutdown pool...");
        executorService.shutdown();*/

//        int[] result = twoSum(new int[]{1,3,4,2}, 6);
//        for (int i = 0; i < result.length; i ++) {
//            System.out.println("==" + result[i]);
//        }
        boolean result = twoSum(-121);
        System.out.println("--" + result);
    }

    public String replaceStr(String s) {
        return s.replace(" ", "%20");
    }

    public static boolean twoSum(int x) {
        /*if (x < 0) {
            return false;
        }
        int temp = x;
        int[] a = new int[String.valueOf(x).length()];
        for (int i = 0; i < a.length; i ++) {
            a[i] = temp % 10;
            temp = (temp - a[i]) / 10;
        }
        String k = "";
        for (int i = 0; i < a.length; i ++) {
            k = k+a[i];
        }
        String r = "";
        for (int i = a.length-1; i >= 0; --i) {
            r = r+a[i];
        }
        System.out.println("===" + k);
        System.out.println("1===" + r);

        return k.equals(r);*/

        if(x < 0) return false ;
        int reverse = 0;
        int end = 0;
        int y = x;
        while(x > 0){
            end = x%10;
            x/=10;
            reverse = reverse * 10+end;
        }

        return reverse == y;

    }

}
