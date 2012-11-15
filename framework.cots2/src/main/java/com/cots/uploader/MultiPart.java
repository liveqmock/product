/*
 * MultiPart.java
 *
 * Created on 2003��4��3��, ����10:50
 */

package com.cots.uploader;


import com.cots.util.KMPMatcher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;
import java.util.Iterator;

/**
 * @author chuguanghua
 */
public class MultiPart {

    private byte[] bytes;
    private HashMap parts = new HashMap();
    private ArrayList allParts = new ArrayList();
    private TreeSet partNames = new TreeSet();
    private int currentIndex = 0;

    /**
     * Creates a new instance of MultiPart
     */
    public MultiPart(byte[] bytes) {
        this.bytes = bytes;
        parse();
    }

    /**
     * �����ֽ�����ɶ��part.
     */
    protected void parse() {
        currentIndex = 0;
        byte[] boundary = getBoundary();
        if (boundary == null) {
            return;
        }
        Part part = getNextPart(boundary);
        while (part != null) {
            //add to all Parts Vector;
            allParts.add(part);
            //add to names collection;
            partNames.add(part.getName());

            //add part to hashmap;
            ArrayList v = (ArrayList) parts.get(part.getName());
            if (v == null) {
                v = new ArrayList();
                parts.put(part.getName(), v);
            }
            v.add(part);

            part = getNextPart(boundary);
        }
    }

    /**
     * ��ȡ�ƶ����͵�part��
     *
     * @param filePart ���Ϊtrue,�򷵻����е��ļ�Part,���򷵻�������ͨPart.
     * @return ָ�����͵�Part����
     */
    public ArrayList getParts(boolean filePart) {
        int i = 0;
        int count = allParts.size();
        ArrayList files = new ArrayList();
        while (i < count) {
            Part part = (Part) allParts.get(i);
            if (filePart) {
                if (part instanceof FilePart) {
                    files.add(part);
                }
            } else {
                if (!(part instanceof FilePart)) {
                    files.add(part);
                }
            }
            i++;
        }
        return files;
    }

    /**
     * ���ض��е�Part�������ÿ��part�����򷵻�null.
     *
     * @return ���������ļ�Part��Hashtable����
     */
    public ArrayList getParts() {
        return allParts;
    }

    /**
     * ��ȡ���е��ļ�Part,���û���ļ�Part�򷵻�null.
     *
     * @return �����ļ���Vector ����.
     */
    public ArrayList getFileParts() {
        return getParts(true);
    }

    /**
     * �ӵ�ǰ��MultiPart�л�ȡһ��Part.�����part�����ڣ��򷵻�null.
     *
     * @param name ָ��part�����ơ�
     * @return �����ƶ���part����
     */
    public Part getPart(String name) {
        ArrayList ps = (ArrayList) parts.get(name);
        if (ps != null) {
            return (Part) ps.get(0);
        } else {
            return null;
        }
    }

    /**
     * �ӵ�ǰ��MultiPart�л�ȡһ����������������ƶ�Ӧ�Ĳ�����һ���ļ����򷵻�Ŭnull.
     * ����ò��������ڣ��򷵻�null.
     * ���򷵻ظò������ַ���ֵ��
     *
     * @param name ��������
     * @return ������ֵ
     */
    public String getParameter(String name) {
        Part pt = getPart(name);

        if (pt == null) {
            return null;
        } else {
            return pt.getString();
        }
    }

    public String[] getParameterValues(String name, String cp) {
        int c = 0;
        ArrayList ps = (ArrayList) parts.get(name);
        if (ps != null) {
            ArrayList vals = new ArrayList();
            int len = ps.size();
            for (int i = 0; i < len; i++) {
                Part p = (Part) ps.get(i);
                String v = p.getString();
                vals.add(v);
                c++;
            }
            return (String[]) vals.toArray(new String[c]);
        } else {
            return new String[0];
        }
    }

    public Iterator getNames() {
        return partNames.iterator();
    }

    /**
     * ��ȡ��MultiPart���ֽ������һ�������顣
     *
     * @param start ���������ʼλ�á�
     * @param end   ������Ľ���λ�á�
     * @return �����顣
     */
    public byte[] getBytes(int start, int end) {
        return getBytes(bytes, start, end);
    }

    /**
     * ��ȡĳ���ӽ������һ�����ӽ����顣
     *
     * @param s     Դ�ֽ����顣
     * @param start ���������ʼλ��
     * @param end   ������Ľ���λ��
     * @return ���ֽ����顣
     */
    public byte[] getBytes(byte[] s, int start, int end) {
        int i = 0;
        if (start >= s.length) {
            return null;
        }
        if (end > s.length) {
            end = s.length - 1;
        }
        int len = end - start + 1;
        if (len <= 0) {
            return null;
        }
        byte[] content = new byte[len];
        while (i < len) {
            content[i] = s[start + i];
            i++;
        }
        return content;
    }

    /**
     * ��ȡ��MultiPart�ķֽ��ַ�����
     *
     * @return �ֽ��ֽ����顣
     */
    protected byte[] getBoundary() {
        int i = 2;
        int len = bytes.length;
        while (i < len - 1) {
            if (bytes[i] == '\r' && bytes[i + 1] == '\n') {
                break;
            } else {
                i++;
            }
        }
        if (i < len - 1) {
            len = i;
            i = 0;
            byte[] boundary = new byte[len + 2];
            while (i < len) {
                boundary[i] = bytes[i];
                i++;
            }
            boundary[len] = '\r';
            boundary[len + 1] = '\n';
            return boundary;
        }else{
            return null;
        }
    }


    /**
     * ��ȡ�ӵ�ǰλ�ÿ�ʼ����һ��Part����
     *
     * @param boundary �ֽ��ֽ����顣
     * @return Ŀ��Part��������Ѿ�û����һ�����򷵻�null;
     */
    protected Part getNextPart(byte[] boundary) {
        int NORMAL_PART = 0;
        int FILE_PART = 1;

        byte[] bkLine = {'\r', '\n', '\r', '\n'};

        int start = 0;
        int end = 0;
        int type = 0;
        String name = null;
        String filePath = null;
        Part part = null;

        //find the boundary;
        int pos = KMPMatcher.indexOf(bytes, currentIndex, boundary);
        if (pos < 0) {
            return null;
        }
        //set the current index to the header position of this part
        currentIndex = pos + boundary.length;
        //find the end position of the header of this part;
        pos = KMPMatcher.indexOf(bytes, currentIndex, bkLine);
        if (pos < 0) {
            return null;
        }
        //get the part header;
        byte[] partHeader = getBytes(bytes, currentIndex, pos - 1);
        //set the start position of the part body;
        start = pos + bkLine.length;
        //find end position of this part;
        pos = KMPMatcher.indexOf(bytes, currentIndex, boundary);
        if (pos < 0) {
            boundary[boundary.length - 2] = '-';
            boundary[boundary.length - 1] = '-';
            pos = KMPMatcher.indexOf(bytes, currentIndex, boundary);
            if (pos < 0) {
                return null;
            }
            end = pos - 1;
        } else {
            end = pos - 1;
        }

        type = NORMAL_PART;
        name = getPartName(partHeader);
        filePath = getFilePath(partHeader);
        if (filePath != null) {
            type = FILE_PART;
        }
        if (type == FILE_PART) {
            part = new FilePart(this, name, filePath, start, end - 2);
        } else {
            part = new Part(this, name, start, end - 2);
        }
        currentIndex = part.getEndPos() + 1;
        return part;
    }

    /**
     * ��ȡһ��Part��name���Ե�ֵ��������һ��form����һ��<input type="text" displayName
     * ="birthdate",��ò����ϴ����������˵�����Ϊ��birthdate.
     *
     * @param partHeader part��ͷ��Ϣ����
     * @return �ò��ֵ����ơ�
     */
    protected String getPartName(byte[] partHeader) {
        byte[] partName = {'n', 'a', 'm', 'e', '=', '\"'};
        byte[] quota = {'\"'};
        int pos = KMPMatcher.indexOf(partHeader, 0, partName);
        int pos2 = KMPMatcher.indexOf(partHeader, pos + partName.length, quota);
        if (pos2 < 0) {
            return null;
        }
        byte[] fieldName = getBytes(partHeader, pos + partName.length, pos2 - 1);
        String name = new String(fieldName);
        return name;
    }

    /**
     * ��Part��ͷ��Ϣ�У���ȡ�ļ��������ļ���������ļ���client�˵ı�������·����
     *
     * @param partHeader part��ͷ��Ϣ���顣
     * @return ���ļ���client�˵�����·����
     */
    protected String getFilePath(byte[] partHeader) {
        byte[] fileName = {'f', 'i', 'l', 'e', 'n', 'a', 'm', 'e', '=', '\"'};
        byte[] quota = {'\"'};
        String filePath = null;
        int pos = KMPMatcher.indexOf(partHeader, 0, fileName);
        if (pos > 0) {
            int pos2 = KMPMatcher.indexOf(partHeader, pos + fileName.length, quota);
            if (pos2 < 0) {
                return null;
            }
            byte[] fieldName = getBytes(partHeader, pos + fileName.length, pos2 - 1);

            if (fieldName == null) {
                return null;
            }
            try {
                filePath = new String(fieldName, "GBK");
            } catch (Exception e) {
                filePath = null;
            }
        }
        return filePath;
    }
}
