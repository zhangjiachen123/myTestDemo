package com.ymkj.ams.common.util;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 线程安全的hashMap
 *
 * @author:Zhang jc
 * @date: 2018/9/18 16:19
 */
public class LockTest {

    private static volatile Map data = new HashMap();
    private static ReadWriteLock lock = new ReentrantReadWriteLock(false);
    private static Lock readLock = LockTest.lock.readLock();
    private static Lock writeLock = LockTest.lock.writeLock();

    /**
     * @author:Zhang jc
     * @date: 2018/9/18 16:19
     * @description: 写入数据
     */
    public void setData(Object key, Object value) {
        //获取写入锁
        writeLock.lock();
        try {
            data.put(key, value);
        } catch (Exception e) {
            System.out.println("写入数据异常");
        } finally {
            writeLock.unlock();
        }
    }

    /**
     * @author:Zhang jc
     * @date: 2018/9/18 16:19
     * @description: 读取数据
     */
    public Object getData(Object key) {
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

}
