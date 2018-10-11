package com.ymkj.ams.common.util;


import org.apache.commons.collections.CollectionUtils;

import java.util.*;

/**
 * 1 合并两个已排序的队列
 * 给定两个已经排序的队列，返回一个合并后的已排序队列
 * 注意事项：
 * 1）时间和空间复杂度尽可能低
 */
public class ConcurrentUtilsTest {
    private static List list1 = new LinkedList();
    private static List list2 = new LinkedList();

    public static void main(String[] args) {
        list1.add("1");
        list1.add("3");
        list2.add("2");
        list2.add("4");
        System.out.println(CollectionUtils.union(list1, list2));
        Set set = new HashSet<>();
        set.addAll(list1);
        set.addAll(list2);
        Iterator iterator = set.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
        System.out.println(set);
    }

    static class MapTest {
        public static void main(String[] args) {
            HashMap<Integer, Integer> data = new HashMap<Integer, Integer>(1);
            data.put(1, 1);
            data.put(2, 2);
            data.put(3, 3);
            for (Map.Entry<Integer, Integer> val : data.entrySet()) {
                System.out.println(val);
            }

            for (Integer val : data.keySet()) {
                System.out.println(val);
                System.out.println(data.get(val));
            }
        }
    }

    static class myTestOther extends CollectionUtils {

        public static void main(String[] args) {
            List list = new ArrayList();
            list.add("1");
            list.add("2");
            List list2 = new ArrayList();
            list2.add("3");
            list2.add("2");
            list2.add("4");
            //交集的补集（析取） 相同的不再放入 不同的放入
            System.out.println(disjunction(list, list2));
            //2个数组取交集
            System.out.println(intersection(list, list2));
            //差集（扣除）第一个集合不存在第二个集合的
            System.out.println(subtract(list, list2));
            //两个集合是否相等
            System.out.println(isEqualCollection(list, list2));
        }
    }
}
