/*
 * Part.java
 *
 * Created on 2003年4月3日, 上午10:50
 */

package com.cots.uploader;

import java.io.UnsupportedEncodingException;

/**
 * User: chugh
 * Date: 2004-12-18
 * Time: 2004-12-18
 * Version: 1.0
 */
public class Part {

    protected MultiPart parent = null;
    protected String name = null;
    protected int startPos = 0;
    protected int endPos = -1;

    /**
     * Creates a new instance of Part，this is a Normal Form Filed
     */
    public Part(MultiPart parent, String name, int startPos, int endPos) {
        this.parent = parent;
        this.name = name;
        this.startPos = startPos;
        this.endPos = endPos;
    }

    public Part() {
    }


    public int getStartPos() {
        return startPos;
    }

    public void setStartPos(int startPos) {
        this.startPos = startPos;
    }

    public int getEndPos() {
        return endPos;
    }

    public void setEndPos(int endPos) {
        this.endPos = endPos;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取该part的内容，如果为文件，则为该文件的内容；
     * 如果为一般的form字段，则为该字段的内容。
     *
     * @return 字节数组
     */
    public byte[] getBytes() {
        return parent.getBytes(startPos, endPos);
    }

    public String getString() {
        byte[] bytes = getBytes();
        if (bytes != null) {
            return new String(bytes);
        } else {
            return null;
        }
    }

    /**
     *
     * @param cp
     * @return
     */
    public String getString(String cp) {
        byte[] bytes = getBytes();
        if (bytes != null) {
            try {
                return new String(bytes, cp);
            } catch (UnsupportedEncodingException uee) {
                uee.printStackTrace();
                return null;
            }
        } else {
            return null;
        }
    }

    public int size() {
        return endPos - startPos+1;
    }
}
