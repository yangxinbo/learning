package com.shanks.learning.example;

import java.util.HashMap;
import java.util.Map;

/**
 * FileName    : com.shanks.learning.example
 * Description :
 *
 * @author : Shanks
 * @version : 1.0
 * Create Date : 2021/5/15 14:15
 **/
public class Example1 {

    public static void main(String[] args) {
        int input = 4;
        Integer[] arr = new Integer[]{1, 2, 2, 3, 3, 3, 4, 4, 4, 4, 5, 5, 5, 5, 5};

        method1(arr, input);

        method2(arr, input);
    }

    /**
     * 找出一个数组中重复元素的个数
     *
     * @param arr
     * @param input
     * @return
     */
    public static int method1(Integer[] arr, int input) {
        int count = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] > input) {
                break;
            }
            if (arr[i] == input) {
                count++;
            }
        }
        System.out.println("input:" + input + " output:" + count);
        return count;
    }


    /**
     * 找出一个数组中重复元素的个数
     * 利用map特性
     *
     * @param arr
     * @param input
     * @return
     */
    public static int method2(Integer[] arr, int input) {
        Map<String, Integer> countMap = new HashMap<>();
        String key = null;
        Integer value = null;
        for (int i = 0; i < arr.length; i++) {
            key = String.valueOf(arr[i]);
            value = countMap.containsKey(key) ? countMap.get(key) : 0;
            countMap.put(key, ++value);
        }
        int count = countMap.getOrDefault(String.valueOf(input), 0);
        System.out.println("input:" + input + " output:" + count);
        return count;
    }
}
