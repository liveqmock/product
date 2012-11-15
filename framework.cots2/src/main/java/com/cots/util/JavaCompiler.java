/**
 *all rights reserved,@copyright 2003
 */
package com.cots.util;

import com.sun.tools.javac.Main;

import java.io.CharArrayWriter;
import java.io.PrintWriter;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-11-12
 * Time: 17:47:52
 */
public class JavaCompiler {
    public static String compile(String filePath,String classPath){
        CharArrayWriter caw = new CharArrayWriter(4096);
        PrintWriter pw = new PrintWriter(caw);
        String[] params = new String[3];
        params[0] = "-classpath";
        params[1] = classPath;
        params[2] = filePath;
        Main.compile(params, pw);
        return caw.toString();
    }
}
