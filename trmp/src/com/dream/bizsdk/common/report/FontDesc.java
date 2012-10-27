/**
 *all rights reserved,@copyright 2003
 */
package com.dream.bizsdk.common.report;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-7-2
 * Time: 8:28:18
 */
public class FontDesc {
    private int id;
    private String name;
    private boolean bold;
    private boolean italy;
    private int size;
    private boolean underline;
    private boolean sub;
    private boolean sup;

    public FontDesc(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public boolean isBold() {
        return bold;
    }

    public boolean isItaly() {
        return italy;
    }

    public int getSize() {
        return size;
    }

    public boolean isUnderline() {
        return underline;
    }

    public boolean isSub() {
        return sub;
    }

    public boolean isSup() {
        return sup;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBold(boolean v) {
        this.bold = v;
    }

    public void setItaly(boolean v) {
        this.italy = v;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setUnderline(boolean v) {
        this.underline = v;
    }

    public void setSub(boolean v) {
        this.sub = v;
    }

    public void setSup(boolean v) {
        this.sup = v;
    }

    public int getId() {
        return this.id;
    }
}
