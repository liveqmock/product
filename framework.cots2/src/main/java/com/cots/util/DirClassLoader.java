/**
 *all rights reserved,@copyright 2003
 */
package com.cots.util;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.File;
import java.io.IOException;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-11-12
 * Time: 17:04:40
 */
public class DirClassLoader extends ClassLoader{
    //ClassLoader parent;
    String baseDir;

    /**
     *
     * @param baseDir
     */
    public DirClassLoader(String baseDir){
        super();

        char ch = baseDir.charAt(baseDir.length()-1);
        if(ch != '\\' && ch!= '/'){
            this.baseDir = baseDir+File.separatorChar;
        }else{
            this.baseDir = baseDir;
        }
    }

    /**
     *
     * @param baseDir
     * @param parent
     */
    public DirClassLoader(String baseDir,ClassLoader parent){
        super(parent);
        char ch = baseDir.charAt(baseDir.length()-1);
        if(ch != '\\' && ch!= '/'){
            this.baseDir = baseDir+File.separatorChar;
        }else{
            this.baseDir = baseDir;
        }
    }

    /**
     *
     *
     * @param cls
     * @return
     * @throws ClassNotFoundException
     */
    protected Class findClass(String cls) throws ClassNotFoundException{
        Class clz;
        int read;
        byte[] buf = new byte[4096];
        FileInputStream fis = null;
        String filePath = baseDir+cls.replace('.','/')+".class";
        ByteArrayOutputStream bytes = new ByteArrayOutputStream(4096);
        try{
            fis = new FileInputStream(new File(filePath));
            while((read=fis.read(buf))>=0){
                bytes.write(buf,0,read);
            }
            buf = bytes.toByteArray();
            clz = this.defineClass(cls,buf,0,buf.length);
        }catch(IOException e){
            throw new ClassNotFoundException(cls);
        }finally{
            if(fis!=null){
                try{
                    fis.close();
                }catch(IOException e){
                    //no handler;
                }
            }
        }
        return clz;
    }

    /**
     * set the base directory to find class files;
     *
     * @param baseDir
     */
    public void setBaseDir(String baseDir) {
        char ch = baseDir.charAt(baseDir.length()-1);
        if(ch != '\\' && ch!= '/'){
            this.baseDir = baseDir+File.separatorChar;
        }else{
            this.baseDir = baseDir;
        }
    }

    /**
     * get the base dir for the ClassLoader to find class files;
     *
     * @return
     */
    public String getBaseDir() {
        return baseDir;
    }
}
