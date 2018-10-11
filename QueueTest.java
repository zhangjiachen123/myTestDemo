package com.ymkj.ams.common.util;

import java.util.LinkedList;
import java.util.Random;

public class QueueTest {

    private volatile LinkedList<String> queue;

    private int maxSize;

    public QueueTest(LinkedList<String> queue, int maxSize) {
        this.queue = queue;
        this.maxSize = maxSize;
    }

    /**
     * @author:Zhang jc
     * @date: 2018/9/17 17:40
     * @description: 生产
     */
    public void proveider() {
        synchronized (queue) {
            try {
                while (queue.size() == maxSize) {
                    queue.wait(1000);
                }
                String random = String.valueOf(new Random(10).nextInt());
                queue.offer(random);
                System.out.println(Thread.currentThread().getName() + "生产一条记录:" + random);
                queue.notify();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    /**
     * @author:Zhang jc
     * @date: 2018/9/17 17:40
     * @description: 消费
     */
    public void consumer() {
        synchronized (queue) {
            try {
                while (queue.size() == 0) {
                    queue.wait(1000);
                }
                queue.remove();
                System.out.println(Thread.currentThread().getName() + "消费一条记录");
                queue.notify();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    /**
     * @author:Zhang jc
     * @date: 2018/9/17 18:17
     * @description: 提供者
     */
    static class Provider implements Runnable {

        private QueueTest queueTest;

        public Provider(QueueTest queueTest) {
            this.queueTest = queueTest;
        }

        @Override
        public void run() {
            while (true) {
                queueTest.proveider();
            }
        }
    }

    /**
     * @author:Zhang jc
     * @date: 2018/9/17 18:17
     * @description: 消费者
     */
    static class Consumer implements Runnable {
        private QueueTest queueTest;

        public Consumer(QueueTest queueTest) {
            this.queueTest = queueTest;
        }

        @Override
        public void run() {
            while (true) {
                queueTest.consumer();
            }
        }
    }

    public static void main(String[] args) {
        LinkedList<String> list = new LinkedList<>();
        QueueTest queueTest = new QueueTest(list, 10);
        Provider provider = new Provider(queueTest);
        Thread t1 = new Thread(provider);
        t1.start();
        Consumer consumer = new Consumer(queueTest);
        Thread t2 = new Thread(consumer);
        t2.start();
    }
}
