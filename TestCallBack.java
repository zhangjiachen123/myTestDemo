package com.yuminsoft.ams.system.controller.approve;

import java.util.concurrent.*;

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
public class TestCallBack {
    private static ExecutorService es = new ThreadPoolExecutor(2, 2,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>());

    public static void main(String[] args) {
        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() throws Exception {
                System.out.println("1");
//                Thread.sleep(2000);
                return "OK!";
            }
        };
        Callable<String> callable2 = callable;
        es.execute(new Thread());
        Future<String> future = es.submit(callable);
        Future<String> future2 = es.submit(callable2);
//        if (!future.isDone() && !future2.isDone()) {
//            System.out.println("线程1没有执行完，等待执行结果");
//        }
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (future.isDone() && future2.isDone()) {
            System.out.println("两个线程全部执行完毕!");
        }
        System.out.println("线程1" + future.isDone() + "线程2" + future2.isDone());

    }
}
