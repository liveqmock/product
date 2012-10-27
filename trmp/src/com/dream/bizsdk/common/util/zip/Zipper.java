/*
 * Zipper.java
 *
 * Created on 2003年11月25日, 上午11:14
 */

package com.dream.bizsdk.common.util.zip;

import java.io.*;
import java.util.*;
import java.util.zip.*;

/**
 *
 *
 *
 */
public class Zipper {
    //the buffer size for io operation;
    public static final int BUFFER = 2048;

    /**
     * unzip a zip file;
     *
     * @param zipFile the path of the zip file to be unzipped;
     * @param target  the target directory to extract to;
     * @throws FileNotFoundException if the target doest not exist or is not a
     *                               directory.
     */
    public void unzip(String zipFile, String target)
            throws FileNotFoundException, IOException {
        ZipEntry entry = null;
        /**check the target directory*/
        File f = new File(target);
        if (!f.exists() || !f.isDirectory()) {
            throw new FileNotFoundException("target doest not exist or is not an directory");
        }

        /** check the end char of the target*/
        if (target.charAt(target.length() - 1) != File.separatorChar) {
            target += File.separatorChar;
        }
        /**open the zipfile and get all entries*/
        ZipFile zipfile = new ZipFile(zipFile);
        Enumeration e = zipfile.entries();

        /**extract each entry*/
        while (e.hasMoreElements()) {
            entry = (ZipEntry) e.nextElement();
            extractEntry(zipfile, entry, target);
        }
    }

    /**
     * extract a entry in a zip file.
     *
     * @param zipfile the ZipFile object;
     * @param entry   the entry in the zipFile to be extracted;
     * @param target  the target directory to extract the current Entry;this
     *                directory must ends with the File.separtorChar char;
     * @throws FileNotFoundException
     * @throws IOException
     */
    protected void extractEntry(ZipFile zipfile, ZipEntry entry, String target)
            throws FileNotFoundException, IOException {
        int count = 0;
        int index = 0;
        byte data[] = new byte[BUFFER];
        String entryName = null;
        BufferedInputStream is = null;

        //get entry input stream;
        is = new BufferedInputStream(zipfile.getInputStream(entry));
        /**create outputstream*/
        entryName = entry.getName();
        index = entryName.lastIndexOf("/");
        if (index > 0) {
            mkdirs(target + entryName.substring(0, index));
        }
        /**if the entry is of type file,then extract it*/
        if (!entry.isDirectory()) {
            FileOutputStream fos = new FileOutputStream(new File(target + entryName));
            BufferedOutputStream dest = new BufferedOutputStream(fos, BUFFER);

            /**begin copy from the input stream to the output stream*/
            while ((count = is.read(data, 0, BUFFER)) != -1) {
                dest.write(data, 0, count);
            }
            /**close all the streams*/
            dest.flush();
            dest.close();
            is.close();
        }
    }

    public byte[] extractEntry(ZipFile zipfile, ZipEntry entry)
            throws FileNotFoundException, IOException {
        int count = 0;
        byte data[] = new byte[BUFFER];
        BufferedInputStream is = null;

        //get entry input stream;
        is = new BufferedInputStream(zipfile.getInputStream(entry));
        ByteArrayOutputStream fos = new ByteArrayOutputStream();
        BufferedOutputStream dest = new BufferedOutputStream(fos, BUFFER);

        /**begin copy from the input stream to the output stream*/
        while ((count = is.read(data, 0, BUFFER)) != -1) {
            dest.write(data, 0, count);
        }

        /**close all the streams*/
        dest.flush();
        dest.close();
        is.close();
        return fos.toByteArray();
    }

    /**
     * zip a directory into a zip file;
     */
    public boolean zipDir(String dir, String zipFileName)
            throws FileNotFoundException, IOException {
        int i = 0;
        int count = 0;
        boolean succ = false;
        String root = null;

        /**check if the dir ends with File.separatorChar cha*/
        if (dir.charAt(dir.length() - 1) == File.separatorChar) {
            dir = dir.substring(0, dir.length() - 1);
        }

        /******/
        File f = new File(dir);
        if (f.exists() && f.isDirectory()) {
            String path = f.getPath();
            root = path.substring(0, path.indexOf(File.separatorChar) + 1);
            ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(new File(zipFileName)));
            File[] files = f.listFiles();
            if (files != null) {
                count = files.length;
                while (i < count) {
                    compress(root, files[i], zos);
                    i++;
                }
            }
            zos.flush();
            zos.close();
        }
        return succ;
    }

    /**
     * zip a directory into a zip file;
     */
    public boolean zipSubDir(String dir, String zipFileName)
            throws FileNotFoundException, IOException {
        int i = 0;
        int count = 0;
        boolean succ = false;
        String root = null;

        /**check if the dir ends with File.separatorChar cha*/
        if (dir.charAt(dir.length() - 1) != File.separatorChar) {
            dir = dir + File.separatorChar;
        }

        /******/
        File f = new File(dir);
        if (f.exists() && f.isDirectory()) {
            root = dir;
            ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(new File(zipFileName)));
            File[] files = f.listFiles();
            if (files != null) {
                count = files.length;
                while (i < count) {
                    compress(root, files[i], zos);
                    i++;
                }
            }
            zos.flush();
            zos.close();
        }
        return succ;
    }

    /**
     * compress a file into a ZipOutputStream object;
     */
    protected void compress(String root, File f, ZipOutputStream zos)
            throws IOException {
        int i = 0;
        //int index=0;
        int count = 0;
        String path = null;
        String name = null;
        byte[] data = new byte[BUFFER];

        if (f == null) {
            return;
        }

        /**compress sub directory*/
        if (f.isDirectory()) {
            File[] files = f.listFiles();
            if (files != null) {
                count = files.length;
                while (i < count) {
                    compress(root, files[i], zos);
                    i++;
                }
            }
            return;
        }

        /**make the name of the file in the zip file;*/
        path = f.getPath();
        //index=path.indexOf(root);
        name = path.substring(root.length());
        name = name.replace('\\', '/');

        /**begin to zip the file*/
        ZipEntry entry = new ZipEntry(name);
        entry.setTime(f.lastModified());
        zos.putNextEntry(entry);
        BufferedInputStream bi = new BufferedInputStream(new FileInputStream(f));
        while ((count = bi.read(data)) != -1) {
            zos.write(data, 0, count);
        }
        bi.close();
    }

    /**
     * check the existence of a dir and make it if not exists;
     *
     * @param path the target path to check;
     */
    protected void mkdirs(String path) {
        try {
            File dirs = new File(path);
            if (!dirs.exists()) {
                dirs.mkdirs();
            } else if (dirs.isFile()) {
                dirs.delete();
                dirs.mkdirs();
            }
        } catch (Exception e) {
        }
    }

    public void updateEntry(ZipOutputStream zos, String entryName, byte[] bytes, long time) throws FileNotFoundException, IOException {
        int read = 0;
        ZipEntry ze = new ZipEntry(entryName);
        ze.setTime(time);
        zos.closeEntry();
        zos.putNextEntry(ze);
        BufferedInputStream bis = new BufferedInputStream(new ByteArrayInputStream(bytes));
        byte[] buf = new byte[4096];
        while ((read = bis.read(buf)) >= 0) {
            zos.write(buf, 0, read);
        }
        bis.close();
    }
}