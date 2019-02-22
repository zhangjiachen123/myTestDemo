package com.yuminsoft.ams.system.controller.approve;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Callback
 * 回调一般是异步处理的一种技术。
 * 一个回调是被传递到并且执行完该方法。 这种方式只能异步回调，
 * 如果需要同步等待线程处理结果可以使用下面介绍的Futures
 *
 * @Author: zhangjiachen
 * @Date: 2019/2/19 16:15
 * @Description:
 */
public class TestLock {
    private static Lock lock = new ReentrantLock();

    private static ArrayList<Integer> arrayList = new ArrayList<>();

    public static void main(String[] args) {
        final TestLock test = new TestLock();

        new Thread() {
            @Override
            public void run() {
                test.insert();
            }
        }.start();

        new Thread() {
            @Override
            public void run() {
                test.insert();
            }
        }.start();
        try {
            Thread.sleep(8000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(arrayList);
    }

    public void insert() {
        if (lock.tryLock()) {
            try {
                System.out.println(Thread.currentThread().getName() + "得到了锁");
                for (int i = 0; i < 5; i++) {
                    arrayList.add(i);
                }
                Thread.sleep(2000);
            } catch (Exception e) {
                System.out.println("异常！");
            } finally {
                System.out.println(Thread.currentThread().getName() + "释放了锁");
                lock.unlock();
            }
        } else {
            System.out.println("获取锁失败!等待另外一个线程释放锁!");
            try {
                Thread.sleep(3000);
                insert();
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println("异常!");
            }
        }

    }
}
