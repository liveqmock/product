/*
 * 本类是对KMP模式匹配算法的实现。
 *
 * KMPMatcher.java
 * Created on 2003年4月3日, 下午3:58
 * @author chuguanghua.
 */

package com.dream.bizsdk.common.util.kmp;

public class KMPMatcher {

    /**
     * Creates a new instance of KMPMatcher
     */
    public KMPMatcher() {
    }

    /**
     * 在源字节数组中的开始位置查找目标字节数组的出现位置。
     *
     * @param s 源字节数组
     * @param t 所要匹配的目标字节数组。
     */
    public static int indexOf(byte[] s, byte[] t) {
        return indexOf(s, 0, t);
    }

    /**
     * 在源字节数组中的指定位置开始查找目标字节数组的出现位置。
     *
     * @param s    源字节数组
     * @param from 开始查找的位置。
     * @param t    所要匹配的目标字节数组。
     */
    public static int indexOf(byte[] s, int from, byte[] t) {
        int i, j, v;
        i = from;
        j = 0;/*主串和子串指针初始化*/
        int[] Next = next(t);

        while (i < s.length && j < t.length) {/*主串和子串指针各增加1*/
            if (j == -1 || s[i] == t[j]) {
                i++;
                j++;
            }
            /*主串指针I不后退；子串指针j退至next[j]*/
            else {
                j = Next[j];
            }
        }
        if (j >= t.length) {
            v = i - t.length;
        } else {
            v = -1;
        }
        return (v);
    }

    /**
     * 计算目标字节数组每个字节的next值；
     *
     * @param t 目标字节数组
     * @return 目标字节数组的next[]值；
     */
    protected static int[] next(byte[] t) {
        int[] nextpos = new int[t.length];
        int j = 0;
        int k = -1;
        nextpos[0] = -1;
        while (j < t.length - 1) {
            if (k == -1 || t[j] == t[k]) {
                j++;
                k++;
                nextpos[j] = k;
            } else {
                k = nextpos[k];
            }
        }
        return nextpos;
    }
}
