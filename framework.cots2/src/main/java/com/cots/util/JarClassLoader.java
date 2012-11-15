/**
 *all rights reserved,@copyright 2003
 */
package com.cots.util;

import java.util.jar.JarFile;
import java.util.zip.ZipEntry;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.BufferedInputStream;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-11-12
 * Time: 17:35:55
 */
public class JarClassLoader extends ClassLoader{
    private String jarFile;

    /**
     *
     * @param jarFile
     */
    public  JarClassLoader(String jarFile){
        //current ClassLoader as the Parent;
        super();
        this.jarFile = jarFile;
    }

    /**
     *
     * @param parent
     * @param jarFile
     */
    public JarClassLoader(ClassLoader parent,String jarFile){
        super(parent);
        this.jarFile = jarFile;
    }

    /**
     * load the a class loader first from the jar, if failed then delegate to parent classloader;
     *
     * @param name
     * @return
     * @throws ClassNotFoundException
     */
    protected Class findClass(String name) throws ClassNotFoundException{
        int count;
        //check if the class has already be loaded;
        Class clz;

        //find the .class file from the jar;
        String clzName = name.replace('.','/')+".class";
        JarFile jar = null;
        try{
            jar = new JarFile(jarFile);
            ZipEntry entry = jar.getEntry(clzName);
            if(entry == null){  //if the .class file is not found, then delegate to the parent laoder;
                throw new ClassNotFoundException(name);
            }else{
                BufferedInputStream is = new BufferedInputStream(jar.getInputStream(entry));
                ByteArrayOutputStream baos = new ByteArrayOutputStream(4096);
                byte[] buf = new byte[4096];
                while((count = is.read(buf))>=0){
                    baos.write(buf,0,count);
                }
                buf = baos.toByteArray();
                clz = this.defineClass(name,buf,0,buf.length);
            }
        }catch(IOException ioe){
            throw new ClassNotFoundException(name);
        }finally{
            if(jar!=null){
                try{
                    jar.close();
                }catch(IOException e){
                    //no handler;
                }
            }
        }
        return clz;
    }
}