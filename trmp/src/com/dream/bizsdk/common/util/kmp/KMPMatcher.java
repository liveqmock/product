/*
 * �����Ƕ�KMPģʽƥ���㷨��ʵ�֡�
 *
 * KMPMatcher.java
 * Created on 2003��4��3��, ����3:58
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
     * ��Դ�ֽ������еĿ�ʼλ�ò���Ŀ���ֽ�����ĳ���λ�á�
     *
     * @param s Դ�ֽ�����
     * @param t ��Ҫƥ���Ŀ���ֽ����顣
     */
    public static int indexOf(byte[] s, byte[] t) {
        return indexOf(s, 0, t);
    }

    /**
     * ��Դ�ֽ������е�ָ��λ�ÿ�ʼ����Ŀ���ֽ�����ĳ���λ�á�
     *
     * @param s    Դ�ֽ�����
     * @param from ��ʼ���ҵ�λ�á�
     * @param t    ��Ҫƥ���Ŀ���ֽ����顣
     */
    public static int indexOf(byte[] s, int from, byte[] t) {
        int i, j, v;
        i = from;
        j = 0;/*�������Ӵ�ָ���ʼ��*/
        int[] Next = next(t);

        while (i < s.length && j < t.length) {/*�������Ӵ�ָ�������1*/
            if (j == -1 || s[i] == t[j]) {
                i++;
                j++;
            }
            /*����ָ��I�����ˣ��Ӵ�ָ��j����next[j]*/
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
     * ����Ŀ���ֽ�����ÿ���ֽڵ�nextֵ��
     *
     * @param t Ŀ���ֽ�����
     * @return Ŀ���ֽ������next[]ֵ��
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
