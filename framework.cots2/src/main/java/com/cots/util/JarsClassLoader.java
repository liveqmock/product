/**
 *all rights reserved,@copyright 2003
 */
package com.cots.util;

import java.util.jar.JarFile;
import java.util.zip.ZipEntry;
import java.util.StringTokenizer;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.File;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-12-17
 * Time: 9:58:56
 */
public class JarsClassLoader extends ClassLoader{

    private String[] jarFiles;

    /**
     *
     * @param jarFiles
     */
    public  JarsClassLoader(String jarFiles){
        super();
        init(jarFiles);
    }

    /**
     *
     * 
     * @param parent
     * @param jarFiles
     */
    public JarsClassLoader(ClassLoader parent,String jarFiles){
        super(parent);
        init(jarFiles);
    }

    /**
     * initialize the jarFiles,and create an array of File pathes.
     *
     * @param jarFiles
     */
    private void init(String jarFiles){
        StringTokenizer st = new StringTokenizer(jarFiles,File.pathSeparator);
        int count = st.countTokens();
        this.jarFiles = new String[count];
        for(int i=0;i<count;i++){
            this.jarFiles[i] = st.nextToken();
        }
    }

    /**
     * find the class from the jars.
     *
     * @param className  the qualified displayName of the class to be found.
     * @return the found Class object of the tartget class.
     * @throws ClassNotFoundException if the clss was not found;
     */
    protected Class findClass(String className) throws ClassNotFoundException{
        int count;
        //check if the class has already be loaded;
        Class clz = null;
        //find the .class file from the jar;
        String clzName = className.replace('.','/')+".class";
        JarFile jar = null;
        for(int i=0;i<jarFiles.length;i++){
            try{
                jar = new JarFile(jarFiles[i]);
                ZipEntry entry = jar.getEntry(clzName);
                if(entry == null){      //if the .class file is not found, then find in the next jar file;
                    continue;
                }else{       //class is found, begin to read the class file;
                    BufferedInputStream is = new BufferedInputStream(jar.getInputStream(entry));
                    ByteArrayOutputStream baos = new ByteArrayOutputStream(4096);
                    byte[] buf = new byte[4096];
                    while((count = is.read(buf))>=0){
                        baos.write(buf,0,count);
                    }
                    buf = baos.toByteArray();
                    clz = this.defineClass(className,buf,0,buf.length);
                    break;
                }
            }catch(IOException ioe){
                //no handler here;
            }finally{
                if(jar!=null){
                    try{
                        jar.close();
                    }catch(IOException e){
                        //no handler;
                    }
                }
            }
        }
        if(clz==null){  //check if the class has be forund;
            throw new ClassNotFoundException(className);
        }
        return clz;
    }
}