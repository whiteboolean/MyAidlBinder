package com.example.myapplication;

import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class CountDownLatchTest {

    private static final CountDownLatch countDownLatch = new CountDownLatch(2);
    public static void main(String[] args) {
        final int[] i = {0};
        final int[] j = {0};
        System.out.println("main方法进来了，当前时间:"+ new Date(System.currentTimeMillis()));
        System.out.println("i:"+i[0]);
        System.out.println("j:"+j[0]);


        new Thread(){
            @Override
            public void run() {
                super.run();
                System.out.println("Thread1进来了");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                countDownLatch.countDown();
                System.out.println("三秒后Thread1跑完了，给i赋值");
                i[0] = 3;

            }
        }.start();

        new Thread(){
            @Override
            public void run() {
                System.out.println("Thread2进来了");
                try {
                    Thread.sleep(8000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                countDownLatch.countDown();
                System.out.println("五秒后Thread2跑完了，给i赋值");
                j[0] = 3;
            }
        }.start();

        try {
            boolean await = countDownLatch.await(2000, TimeUnit.SECONDS);
            if (await){
                System.out.println("泥马，超时了");
                System.out.println("两个方法都跑完了,现在的时间:"+new Date(System.currentTimeMillis()));
            }else{
                System.out.println("两个方法都跑完了,现在的时间:"+new Date(System.currentTimeMillis()));
                System.out.println("i:"+i[0]);
                System.out.println("j:"+j[0]);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



    }
}
