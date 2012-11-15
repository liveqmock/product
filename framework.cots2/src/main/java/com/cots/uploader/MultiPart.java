/*
 * MultiPart.java
 *
 * Created on 2003年4月3日, 上午10:50
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
     * 解析字节数组成多个part.
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
     * 获取制定类型的part。
     *
     * @param filePart 如果为true,则返回所有的文件Part,否则返回所有普通Part.
     * @return 指定类型的Part对象。
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
     * 返回多有的Part对象，如果每有part对象，则返回null.
     *
     * @return 包含所有文件Part的Hashtable对象。
     */
    public ArrayList getParts() {
        return allParts;
    }

    /**
     * 获取所有的文件Part,如果没有文件Part则返回null.
     *
     * @return 包含文件的Vector 对象.
     */
    public ArrayList getFileParts() {
        return getParts(true);
    }

    /**
     * 从当前的MultiPart中获取一个Part.如果该part不存在，则返回null.
     *
     * @param name 指定part的名称。
     * @return 返回制定的part对象。
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
     * 从当前的MultiPart中获取一个参数，如果该名称对应的参数是一个文件，则返回努null.
     * 如果该参数不存在，则返回null.
     * 否则返回该参数的字符串值。
     *
     * @param name 参数名称
     * @return 参数的值
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
     * 获取该MultiPart的字节数组的一个子数组。
     *
     * @param start 子数组的起始位置。
     * @param end   子数组的结束位置。
     * @return 子数组。
     */
    public byte[] getBytes(int start, int end) {
        return getBytes(bytes, start, end);
    }

    /**
     * 获取某个子节数组的一个子子节数组。
     *
     * @param s     源字节数组。
     * @param start 子数组的起始位置
     * @param end   子数组的结束位置
     * @return 子字节数组。
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
     * 获取该MultiPart的分界字符串。
     *
     * @return 分界字节数组。
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
     * 获取从当前位置开始的下一个Part对象。
     *
     * @param boundary 分界字节数组。
     * @return 目标Part对象，如果已经没有下一个，则返回null;
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
     * 获取一个Part的name属性的值，例如在一个form中有一个<input type="text" displayName
     * ="birthdate",则该部分上传到服务器端的名称为：birthdate.
     *
     * @param partHeader part的头信息数组
     * @return 该部分的名称。
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
     * 从Part的头信息中，获取文件名，该文件名是这个文件在client端的本地完整路径。
     *
     * @param partHeader part的头信息数组。
     * @return 该文件在client端的完整路径名
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
