/**
 *all rights reserved,@copyright 2003
 */
package com.cots.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.StringTokenizer;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-12-17
 * Time: 10:10:41
 */
public class DirsClassLoader extends ClassLoader{
    ClassLoader parent;
    String[] baseDirs;

    /**
     *
     * @param baseDirs
     */
    public DirsClassLoader(String baseDirs){
        super();
        init(baseDirs);
    }

    /**
     *
     * @param baseDirs
     * @param parent
     */
    public DirsClassLoader(String baseDirs,ClassLoader parent){
        super(parent);
        init(baseDirs);
    }

    private void init(String baseDirs){
        StringTokenizer st = new StringTokenizer(baseDirs,File.pathSeparator);
        int count = st.countTokens();
        this.baseDirs = new String[count];
        for(int i=0;i<count;i++){
            String dir = st.nextToken();
            char ch = dir.charAt(dir.length()-1);
            if(ch != '\\' && ch!= '/'){
                dir = dir+File.separatorChar;
            }

            this.baseDirs[i] = dir;
        }
    }

    /**
     *
     *
     * @param cls
     * @return the desired Class object;
     * @throws ClassNotFoundException if the class is not found;
     */
    protected Class findClass(String cls) throws ClassNotFoundException{
        Class clz = null;
        int read;
        byte[] buf = new byte[4096];
        FileInputStream fis = null;
        for(int i=0;i<baseDirs.length;i++){
            String filePath = baseDirs[i]+cls.replace('.','/')+".class";
            if(new File(filePath).exists()){
                ByteArrayOutputStream bytes = new ByteArrayOutputStream(4096);
                try{
                    fis = new FileInputStream(new File(filePath));
                    while((read=fis.read(buf))>=0){
                        bytes.write(buf,0,read);
                    }
                    buf = bytes.toByteArray();
                    clz = this.defineClass(cls,buf,0,buf.length);
                    //class has been loaded,exit the loop;
                    break;
                }catch(IOException e){
                    //if io exception is thrown, then find in next directory.
                }finally{
                    if(fis!=null){
                        try{
                            fis.close();
                        }catch(IOException e){
                            //no handler;
                        }
                    }
                }
            }
        }
        //up to here, all the dirs has been searched.
        if(clz==null){
            throw new ClassNotFoundException(cls);
        }
        return clz;
    }
}
