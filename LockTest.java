package com.yuminsoft.ams.system.controller.approve;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * ReentrantReadWriteLock 测试
 * 读锁多线程  写锁单线程
 * 是可重入锁 但是不支持降级
 * new时候传的参数是否是公平锁 公平锁因为要排序，根据进入线程池队列的优先级排序，所以性能不如写锁 默认为非公平
 *
 * @Author: zhangjiachen
 * @Date: 2019/2/19 9:40
 * @Description:
 */
public class LockTest {
    private static volatile Map data = new HashMap();
    private static ReadWriteLock lock = new ReentrantReadWriteLock(true);
    private static Lock readLock = lock.readLock();
    private static Lock writeLock = lock.writeLock();

    /**
     * @author:Zhang jc
     * @date: 2018/9/18 16:19
     * @description: 写入数据
     */
    public static void setData(Object key, Object value) {
        //获取写入锁
        writeLock.lock();
        try {
            data.put(key, value);
        } catch (Exception e) {
            System.out.println("写入数据异常");
        } finally {
//            writeLock.unlock();
        }
    }

    /**
     * @author:Zhang jc
     * @date: 2018/9/18 16:19
     * @description: 读取数据
     */
    public static Object getData(Object key) {
        //获取读锁
        readLock.lock();
        Object result = null;
        try {
            result = data.get(key);
        } catch (Exception e) {
            System.out.println("读数据异常");
        } finally {
            readLock.unlock();
        }
        return result;
    }

    /**
     * 锁升级（读锁升级为写锁） 不支持
     *
     * @param
     * @return
     * @author ZhangJiaChen
     * @date 2019/2/19 10:33
     **/
    private static void upGradeLock() {
        try {
            ReentrantReadWriteLock rtl = new ReentrantReadWriteLock(false);
            rtl.readLock().lock();
            System.out.println("获取读锁");
            rtl.writeLock().lock();
            System.out.println("获取写锁");
        } catch (Exception e) {
            System.out.println("升级锁失败" + e.getMessage());
        }
    }

    /**
     * 锁降级(写锁降级为读锁)
     *
     * @param
     * @return
     * @author ZhangJiaChen
     * @date 2019/2/19 10:36
     **/
    private static ReentrantReadWriteLock downLock() {
        ReentrantReadWriteLock rtl = null;
        try {
            rtl = new ReentrantReadWriteLock(false);
            rtl.writeLock().lock();
            System.out.println("获取写锁");
            rtl.readLock().lock();
            System.out.println("获取读锁");
        } catch (Exception e) {
            System.out.println("降级锁失败" + e.getMessage());
        }
        return rtl;
    }

    public static void main(String[] args) {
        ReentrantReadWriteLock rtw = downLock();
        rtw.writeLock().lock();
        System.out.println("第二次");
    }
}
