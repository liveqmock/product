/**
 *all rights reserved,@copyright 2003
 */
package com.dream.bizsdk.common.util;

import java.io.*;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-8-23
 * Time: 13:02:38
 */
public class FileSystem {
    public static final void copy(String src, String target) {

    }

    /**
     * copy a file to another file;
     *
     * @param src    the absolute path of the source file;
     * @param target the absolute path of the target file;
     * @throws IOException
     */
    public static final void copyFile(String src, String target) throws IOException {
        int bytesRead;
        byte[] buf = new byte[8096];
        File f = new File(src);
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(target));
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(f));
        while ((bytesRead = bis.read(buf)) >= 0) {
            bos.write(buf, 0, bytesRead);
        }
        bos.flush();
        bos.close();
        bis.close();
    }

    /**
     * copy a file into another directory;
     *
     * @param src    the absolute path of the source file;
     * @param target the target directory to hold the copied file.
     * @throws IOException
     */
    public static final void copyFile2Dir(String src, String target) throws IOException {
        copyFile2Dir(new File(src), target);
    }

    /**
     * @param src    the source File object;
     * @param target the target directory;
     * @throws IOException
     */
    public static final void copyFile2Dir(File src, String target) throws IOException {
        int bytesRead;
        byte[] buf = new byte[8096];

        //compute the taget file absolute path;
        String name = src.getName();
        if (target.charAt(target.length() - 1) != File.separatorChar) {
            target += File.separatorChar;
        }
        target += name;
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(target));

        //open the source file and copy it into the target output stream.
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(src));
        while ((bytesRead = bis.read(buf)) >= 0) {
            bos.write(buf, 0, bytesRead);
        }
        bos.flush();
        bos.close();
        bis.close();
    }

    /**
     * @param src
     * @param target
     * @param deep
     * @throws IOException
     */
    public static final void copyDir(String src, String target, boolean deep) throws IOException {
        int count;
        if (src.charAt(src.length() - 1) != File.separatorChar) {
            src += File.separatorChar;
        }
        File f = new File(src);
        if (f.exists() && f.isDirectory()) {
            File[] fs = f.listFiles();
            count = fs.length;
            for (int i = 0; i < count; i++) {
                if (fs[i].isFile()) {
                    copyFile2Dir(fs[i], target);
                } else {
                    String path = fs[i].getAbsolutePath();
                    if (deep) {

                    }
                }
            }
        }
    }

    /**
     * @param src
     * @param target
     * @throws IOException
     */
    public static final void copyDir(String src, String target) throws IOException {
        copyDir(src, target, true);
    }

    /**
     * @param path
     */
    public static final void mkdir(String path) {
        new File(path).mkdirs();
    }

    /**
     * @param path
     */
    public static final void remove(String path) {
        new File(path).delete();
    }

    /**
     * @param old
     * @param newName
     */
    public static final void rename(String old, String newName) {
        File f = new File(old);
        f.renameTo(new File(newName));
    }

    /**
     * root  and target must ends with File.separatChar;
     *
     * @param root
     * @param src
     * @param target
     * @throws IOException
     */
    private static final void copySubDir(String root, String src, String target) throws IOException {
        String subName = src.substring(root.length());
        String targetFile = target + subName;
        new File(targetFile).mkdirs();
    }
}