package com.ymkj.ams.common.util;

import java.util.HashMap;

public class JvmTest extends Thread {
    public static final long starttime = System.currentTimeMillis();

    @Override
    public void run() {
        try {
            while (true) {
                long t = System.currentTimeMillis() - starttime;
                System.out.println("time:" + t);
                Thread.sleep(100);
            }
        } catch (Exception e) {
            System.out.println("thread exception");
        }
    }

    public static void main(String[] args) {
        Thread t = new Thread(new JvmTest());
        t.start();
    }

    static class MyThread extends Thread {
        HashMap<Long, byte[]> map = new HashMap<Long, byte[]>();

        @Override
        public void run() {
            try {
                while (true) {
                    //如果map消耗的内存消耗大于450时，那就清理内存
                    if (map.size() * 512 / 1024 / 1024 >= 450) {
                        System.out.println("=====准备清理=====:" + map.size());
                        map.clear();
                    }
                    for (int i = 0; i < 1024; i++) {
                        map.put(System.nanoTime(), new byte[512]);
                    }
                    Thread.sleep(1);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public static void main(String[] args) {
//            Thread t = new Thread(new MyThread());
//            t.start();
            //显示JVM总内存
            long totalMem = Runtime.getRuntime().totalMemory();
            System.out.println(totalMem);
            //显示JVM尝试使用的最大内存
            long maxMem = Runtime.getRuntime().maxMemory();
            System.out.println(maxMem);
            //空闲内存
            long freeMem = Runtime.getRuntime().freeMemory();
            System.out.println(freeMem);
        }
    }

}
