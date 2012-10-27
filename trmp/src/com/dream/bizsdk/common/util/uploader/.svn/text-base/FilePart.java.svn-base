/*
 * FilePart.java
 *
 * Created on 2003年11月10日, 上午8:40
 */

package com.dream.bizsdk.common.util.uploader;

import com.dream.bizsdk.common.util.encoding.EncodedASCII;

import java.io.*;

/**
 * @author chuguanghua
 */
public class FilePart extends Part {

    //文件的路径，
    private String filePath;

    private String virPath;

    public FilePart(MultiPart parent, String name, String filePath
                    , int startPos, int endPos) {
        this.parent = parent;
        this.name = name;
        this.type = Part.FILE_PART;
        /**
         *  the file name maybe contains chinese;
         *  A chinese char contains two bytes which values are great than 128;
         *  Internet Explorer encodes chinese strings to ascii string by subtract each byte 128
         *  then wrap the encoded strings within ~{ and ~}, an ascii native char ~ will be escaped
         * as ~~.
         * !!!!!!!!!!Don't know the behaviours of other explorer in HTTP File Upload.!!!!!!!!!!!!!!!!
         */
        this.filePath = EncodedASCII.toChinese(filePath);
        this.startPos = startPos;
        this.endPos = endPos;
    }

    public String getFilePath() {
        return this.filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    /**
     * 保存该文件，文件的路径由filePath确定
     *
     * @return true 如果成功保存。
     */
    public int save(boolean overwrite) throws IOException {
        return save(filePath, overwrite);
    }


    /**
     * 将本文件保存到指定的路径下。
     *
     * @param path 完整的路径名，包括文件路基和文件名；
     * @return 0-succeed to save this file;
     *         -1-file already exists;
     *         -2-io exception;
     */
    public int save(String path, boolean overwrite) throws IOException {
        File f = new File(path);
        if (f.exists() && !overwrite) {
            throw new IOException("File already exists, can't overwrite it.");
        }
        FileOutputStream fos = new FileOutputStream(f);
        byte[] bs = getBytes();
        if (bs != null) {
            fos.write(getBytes());
        }
        fos.flush();
        fos.close();
        return 0;
    }

    public String getExtName() {
        int pos = filePath.lastIndexOf('.');
        if (pos < 0) {
            return null;
        } else {
            return filePath.substring(pos + 1, filePath.length());
        }
    }

    public String getFileName() {
        int pos1 = filePath.lastIndexOf('.');
        int pos2 = filePath.lastIndexOf('/');
        int pos3 = filePath.lastIndexOf('\\');
        if (pos1 < 0) {
            pos1 = filePath.length();
        }
        if (pos2 < 0) {
            if (pos3 < 0) {
                return filePath.substring(0, pos1);
            } else {
                return filePath.substring(pos3 + 1, pos1);
            }
        } else {
            if (pos3 < 0) {
                return filePath.substring(pos2 + 1, pos1);
            } else {
                if (pos2 > pos3) {
                    return filePath.substring(pos2 + 1, pos1);
                } else {
                    return filePath.substring(pos3 + 1, pos1);
                }
            }
        }
    }

    /**
     * get the full file name;
     *
     * @return
     */
    public String getFullFileName() {
        int pos2 = filePath.lastIndexOf('/');
        int pos3 = filePath.lastIndexOf('\\');
        if (pos2 < 0) {
            if (pos3 < 0) {
                return filePath.substring(0);
            } else {
                return filePath.substring(pos3 + 1);
            }
        } else {
            if (pos3 < 0) {
                return filePath.substring(pos2 + 1);
            } else {
                if (pos2 > pos3) {
                    return filePath.substring(pos2 + 1);
                } else {
                    return filePath.substring(pos3 + 1);
                }
            }
        }
    }

    /**
     * get the virPath of this file part;
     *
     * @return
     */
    public String getString() {
        return virPath;
    }

    /**
     * get the virpath with specified charset;
     *
     * @param cp the target charset;
     * @return
     */
    public String getString(String cp) {
        return virPath;
    }

    /**
     * set the vir path of this file part;
     *
     * @param virPath
     */
    public void setVirPath(String virPath) {
        this.virPath = virPath;
    }
}
