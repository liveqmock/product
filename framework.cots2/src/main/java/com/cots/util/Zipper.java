/**
 *all rights reserved,@copyright 2003
 */
package com.cots.util;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;
import java.util.zip.ZipInputStream;
import java.util.Enumeration;

/**
 *
 *
 *
 */
public class Zipper {
    //the buffer size for io operation;
    public static final int BUFFER = 4096;

    /**
     * unzip a zip file;
     *
     * @param zipFile the path of the zip file to be unzipped;
     * @param target  the target directory to extract to;
     * @throws java.io.FileNotFoundException if the target doest not exist or is not a
     *                                       directory.
     */
    public static void unzip(String zipFile, String target)
            throws FileNotFoundException, IOException {
        unzip(new File(zipFile), target);
    }

    /**
     * @param zipFile
     * @param target
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static void unzip(File zipFile, String target)
            throws IOException {
        ZipEntry entry = null;

        /** check the end char of the target*/
        char ch = target.charAt(target.length() - 1);
        if ( ch!= '\\' && ch!= '/') {
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
     * @throws IOException
     */
    public static void extractEntry(ZipFile zipfile, ZipEntry entry, String target)
            throws IOException {
        int count = 0;
        int index = 0;
        byte data[] = new byte[BUFFER];
        String entryName = null;
        BufferedInputStream is = null;

        //get entry input stream;
        is = new BufferedInputStream(zipfile.getInputStream(entry));

        /**create outputstream*/
        entryName = entry.getName();
        if(entryName.charAt(0)=='/'){
            entryName = entryName.substring(1);
        }
        index = entryName.lastIndexOf("/");
        if (index > 0) {
            mkdirs(target + entryName.substring(0, index));
        }
        /**if the entry is of type file,then extract it*/
        if (!entry.isDirectory()) {
            FileOutputStream fos = new FileOutputStream(new File(target + entryName));
            BufferedOutputStream dest = new BufferedOutputStream(fos, BUFFER);

            try{
                /**begin copy from the input stream to the output stream*/
                while ((count = is.read(data, 0, BUFFER)) != -1) {
                    dest.write(data, 0, count);
                }
                dest.flush();
                /**close all the streams*/
            }finally{
                dest.close();
                is.close();
            }
        }else{
            mkdirs(target + entryName);
        }
    }

    /**
     *
     *
     * @param zipfile
     * @param entry
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static byte[] extractEntry(ZipFile zipfile, ZipEntry entry)
            throws IOException {
        int count = 0;
        byte data[] = new byte[BUFFER];
        BufferedInputStream is = null;
        BufferedOutputStream dest=null;
        ByteArrayOutputStream fos=null;

        //get entry input stream;
        try{
            is = new BufferedInputStream(zipfile.getInputStream(entry));
            fos = new ByteArrayOutputStream(BUFFER);
            dest = new BufferedOutputStream(fos, BUFFER);

            /**begin copy from the input stream to the output stream*/
            while ((count = is.read(data, 0, BUFFER)) != -1){
                dest.write(data, 0, count);
            }
            dest.flush();
        }finally{
            if(dest!=null){
                dest.close();
            }
            if(is!=null){
                is.close();
            }
        }

        return fos.toByteArray();
    }

    /**
     * zip a directory into a zip file;
     *
     * @param dir
     * @param zipFileName
     */
    public static boolean zipDir(String dir, String zipFileName)
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
            try{
                File[] files = f.listFiles();
                if (files != null) {
                    count = files.length;
                    while (i < count) {
                        compress(root, files[i], zos);
                        i++;
                    }
                }
                zos.flush();
            }finally{
                zos.close();
            }
        }
        return succ;
    }

    /**
     * zip all the contents of a directory into a zipfile, not including the directory itself.
     *
     * @param dir the root directory;
     * @param zipFilePath the path of the zipfile to be generated;
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static boolean zipSubDir(String dir, String zipFilePath)
            throws FileNotFoundException, IOException {
        int i = 0;
        int count = 0;
        boolean succ = false;
        String root = null;

        /**check if the dir ends with File.separatorChar cha*/
        char ch = dir.charAt(dir.length() - 1);
        if (ch != '\\' && ch != '/') {
            dir = dir + File.separatorChar;
        }

        /******/
        File f = new File(dir);
        if (f.exists() && f.isDirectory()) {
            root = dir;
            ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(new File(zipFilePath)));
            try{
                File[] files = f.listFiles();
                if (files != null) {
                    count = files.length;
                    while (i < count) {
                        compress(root, files[i], zos);
                        i++;
                    }
                }
                zos.flush();
            }finally{
                zos.close();
            }
        }
        return succ;
    }

    /**
     * compress a file into a ZipOutputStream object;
     */
    protected static void compress(String root, File f, ZipOutputStream zos)
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

        /**make the displayName of the file in the zip file;*/
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
    protected static void mkdirs(String path) {
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

    /**
     * Update an entry;
     *
     * @param zos
     * @param entryName
     * @param bytes
     * @param time
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static void updateEntry(ZipOutputStream zos, String entryName, byte[] bytes, long time)
            throws FileNotFoundException, IOException {
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

    /**
     * add/update a file into a zip file;
     *
     * @param zipFile the path of the zip file;
     * @param targetEntryName the entry displayName of the newFile in the zip file;
     * @param newFile the path of the file to be added to the zip file.
     * @throws IOException
     */
    public static void addFile(String zipFile,String targetEntryName,String newFile) throws IOException {
        boolean existing = false;

        String tmpFile = zipFile+System.currentTimeMillis();

        FileInputStream fis = new FileInputStream(newFile);
        int len = fis.available();
        byte[] content = new byte[len];
        fis.read(content);

        ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(tmpFile));
        ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile));
        try{
            ZipEntry entry = zis.getNextEntry();
            while(entry!=null){
                String entryName = entry.getName();

                if(entryName.equals(targetEntryName)){
                    updateEntry(zos,entryName,content,new File(newFile).lastModified());
                    existing = true;
                }else{
                    ZipEntry newEntry = new ZipEntry(entry);
                    zos.putNextEntry(newEntry);
                    if(entry.getSize()>0){
                        int read =0;
                        byte[] buf = new byte[4069];
                        while((read=zis.read(buf))>=0){
                            zos.write(buf,0,read);
                        }
                        zos.closeEntry();
                    }
                }
                zis.closeEntry();
                entry = zis.getNextEntry();
            }

            //if the taget entry doest not exist,then add it;
            if(!existing){
                entry = new ZipEntry(targetEntryName);
                zos.putNextEntry(entry);
                zos.write(content);
                zos.closeEntry();
            }
            zos.flush();

        }finally{
            zos.close();
            zis.close();
        }

        //copy the file and delete the temp file;
        FileUtil.copy(tmpFile,zipFile);
        FileUtil.delete(tmpFile);
    }
}