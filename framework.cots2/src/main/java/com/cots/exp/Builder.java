/**
 *all rights reserved,@copyright 2003
 */
package com.cots.exp;

import com.cots.util.DirClassLoader;
import com.cots.util.FileUtil;
import com.cots.util.JavaCompiler;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import java.io.*;

import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-11-4
 * Time: 14:23:35
 */
public class Builder {
    public final static String BASE_PKG="com.cots.exp.autogen";

    private String jarFilePath;
    private String classPath;
    private String classDir;
    private DirClassLoader loader;

    private Logger log;

    public Builder(String classDir){
        char endingChar = classDir.charAt(classDir.length()-1);
        if(endingChar != '\\' && endingChar != '/'){
            this.classDir = classDir+'/';
        }else{
            this.classDir = classDir;
        }

        log = Logger.getLogger(this.getClass());
    }

    public void setLoader(DirClassLoader loader) {
        this.loader = loader;
    }

    /**
     *
     * @return
     */
    public String getJarFilePath() {
        return jarFilePath;
    }

    /**
     *
     * @param jarFilePath
     */
    public void setJarFilePath(String jarFilePath) {
        this.jarFilePath = jarFilePath;
    }

    /**
     *
     * @return
     */
    public String getClassPath() {
        return classPath;
    }

    /**
     *
     * @param classPath
     */
    public void setClassPath(String classPath) {
        this.classPath = classPath;
    }

    /**
     *
     * @return
     */
    public String getClassDir() {
        return classDir;
    }

    /**
     *
     * @param classDir
     */
    public void setClassDir(String classDir) {
        this.classDir = classDir;
    }

    /**
     *
     *
     * @param id
     * @param exp
     * @param paramTypes
     */
    public BooleanExpression buildBoolean(String id,String exp,HashMap paramTypes) throws ExprSyntaxException{
        ArrayList params = new ArrayList();
        String parsed = Parser.parse(exp,params);
        String filePath = null;
        try {
            filePath = makeBoolean(parsed,params,paramTypes,id);
            compileExpression(exp,filePath);
            return (BooleanExpression)load(BASE_PKG+"."+id);
        } catch (IOException e) {
            if(log.isEnabledFor(Priority.ERROR)){
                log.error("can't make expression java source file",e);
            }
        } catch (IllegalAccessException e) {
            if(log.isEnabledFor(Priority.ERROR)){
                log.error("can't access expression "+id,e);
            }
        } catch (InstantiationException e) {
            if(log.isEnabledFor(Priority.ERROR)){
                log.error("can't create expression "+id,e);
            }
        } catch (ClassNotFoundException e) {
            if(log.isEnabledFor(Priority.ERROR)){
                log.error("can't find expression class "+id,e);
            }
        }
        return null;
    }


    /**
     *
     *
     * @param id
     * @param exp
     * @param paramTypes
     */
    public ObjectExpression buildObject(String id,String exp,HashMap paramTypes) throws ExprSyntaxException{
        ArrayList params = new ArrayList();
        String parsed = Parser.parse(exp,params);
        String filePath = null;
        try {
            filePath = makeObject(parsed,params,paramTypes,id);
            compileExpression(exp,filePath);
            return (ObjectExpression)load(BASE_PKG+"."+id);
        } catch (IOException e) {
            if(log.isEnabledFor(Priority.ERROR)){
                log.error("can't make expression java source file",e);
            }
        } catch (IllegalAccessException e) {
            if(log.isEnabledFor(Priority.ERROR)){
                log.error("can't access expression "+id,e);
            }
        } catch (InstantiationException e) {
            if(log.isEnabledFor(Priority.ERROR)){
                log.error("can't create expression "+id,e);
            }
        } catch (ClassNotFoundException e) {
            if(log.isEnabledFor(Priority.ERROR)){
                log.error("can't find expression class "+id,e);
            }
        }
        return null;
    }

    /**
     *
     *
     * @param id
     * @param exp
     * @param paramTypes
     * @return
     */
    public StringExpression buildString(String id,String exp,HashMap paramTypes) throws ExprSyntaxException{
        ArrayList params = new ArrayList();

        String parsed = Parser.parse(exp,params);

        String filePath = null;
        try {
            filePath = makeString(parsed,params,paramTypes,id);

            compileExpression(exp,filePath);
            return (StringExpression)load(BASE_PKG+"."+id);
        } catch (IOException e) {
            if(log.isEnabledFor(Priority.ERROR)){
                log.error("can't write expression class file:"+filePath,e);
            }
        } catch (IllegalAccessException e) {
            if(log.isEnabledFor(Priority.ERROR)){
                log.error("can't access expression class:"+id,e);
            }
        } catch (InstantiationException e) {
            if(log.isEnabledFor(Priority.ERROR)){
                log.error("can't create expression object:"+id,e);
            }
        } catch (ClassNotFoundException e) {
            if(log.isEnabledFor(Priority.ERROR)){
                log.error("can't find expression class:"+id,e);
            }
        }
        return null;
    }

    /**
     *
     *
     * @param exp
     * @param params
     * @param paramTypes
     * @param id
     * @return
     * @throws IOException
     */
    private String makeBoolean(String exp,List params,HashMap paramTypes,String id) throws IOException{
        String filePath = classDir+BASE_PKG.replace('.','/')+"/"+id+".java";
        FileUtil.mkdirs(filePath);
        File javaFile = new File(filePath);
        PrintStream fos = new PrintStream(new FileOutputStream(javaFile));
        fos.println("package "+BASE_PKG+"; ");
        fos.print("public final class ");
        fos.print(id);
        fos.print(" implements com.cots.exp.BooleanExpression");
        fos.println("{");
        fos.println("  public boolean valueOf(java.util.HashMap data){");
        writeParams(fos,params,paramTypes);
        fos.print("    if(");
        fos.print(exp);
        fos.println("){");
        fos.println("       return true;");
        fos.println("    }else{");
        fos.println("       return false;");
        fos.println("    }");
        fos.println("  }");

        exp = exp.replaceAll("\"","\\\\\"");

        fos.println("  public String getSource(){");
        fos.println("     return \""+exp+"\";");
        fos.println("  }");
        fos.println("}");
        return filePath;
    }

    /**
     *
     *
     * @param exp
     * @param params
     * @param paramTypes
     * @param id
     * @return
     * @throws IOException
     */
    private String makeString(String exp,List params,HashMap paramTypes,String id) throws IOException{
        String filePath = classDir+BASE_PKG.replace('.','/')+"/"+id+".java";
        FileUtil.mkdirs(filePath);
        File javaFile = new File(filePath);
        PrintStream fos = new PrintStream(new FileOutputStream(javaFile));

        fos.println("package "+BASE_PKG+"; ");

        fos.print("public final class ");
        fos.print(id);
        fos.print(" implements com.cots.exp.StringExpression");
        fos.println("{");
        fos.println("  public String valueOf(java.util.HashMap data){");
        writeParams(fos,params,paramTypes);
        fos.print("    return ");
        fos.print(exp);
        fos.println(";");
        fos.println("  }");

        exp = exp.replaceAll("\"","\\\\\"");

        fos.println("  public String getSource(){");
        fos.println("     return \""+exp+"\";");
        fos.println("  }");


        fos.println("}");
        return filePath;
    }

    private String makeObject(String exp,List params,HashMap paramTypes,String id) throws IOException{
        String filePath = classDir+BASE_PKG.replace('.','/')+"/"+id+".java";
        FileUtil.mkdirs(filePath);
        File javaFile = new File(filePath);
        PrintStream fos = new PrintStream(new FileOutputStream(javaFile));

        fos.println("package "+BASE_PKG+"; ");

        fos.print("public final class ");
        fos.print(id);
        fos.print(" implements com.cots.exp.ObjectExpression");
        fos.println("{");
        fos.println("  public Object valueOf(java.util.HashMap data){");
        writeParams(fos,params,paramTypes);
        fos.print("    return ");
        fos.print(exp);
        fos.println(";");
        
        fos.println("  }");

        exp = exp.replaceAll("\"","\\\\\"");

        fos.println("  public String getSource(){");
        fos.println("     return \""+exp+"\";");
        fos.println("  }");


        fos.println("}");
        return filePath;
    }

    /**
     *
     * @param javaFile
     * @param params
     * @param paramTypes
     */
    private void writeParams(PrintStream javaFile,List params,HashMap paramTypes){
        int count = params.size();
        TreeSet paramsWrited = new TreeSet();
        for(int i=0;i<count;i++){
            String paramName = (String)params.get(i);
            if(!paramsWrited.contains(paramName)){
                Class paramClass = (Class)paramTypes.get(paramName);
                if(paramClass==null){
                    throw new IllegalArgumentException("unknown parameter \""+paramName+"\"");
                }

                writeParam(javaFile,paramName,paramClass);
                paramsWrited.add(paramName);
            }
        }
    }

    /**
     *
     * @param javaFile
     * @param paramName
     * @param paramClass
     */
    private void writeParam(PrintStream javaFile,String paramName,Class paramClass){
        if(paramClass  == byte.class){      //byte
            javaFile.print("    byte ");
            javaFile.print(paramName);
            javaFile.print("= ((Byte)data.get(\"");
            javaFile.print(paramName);
            javaFile.println("\")).byteValue();");
        }else if(paramClass  == Byte.class){      //Byte
            javaFile.print("    Byte ");
            javaFile.print(paramName);
            javaFile.print("= (Byte)data.get(\"");
            javaFile.print(paramName);
            javaFile.println("\");");
        }else if(paramClass  == byte[].class){          //byte[]
            javaFile.print("    byte[] ");
            javaFile.print(paramName);
            javaFile.print("= (byte[])data.get(\"");
            javaFile.print(paramName);
            javaFile.println("\");");
        }else if(paramClass  == int.class){     //int
            javaFile.print("    int ");
            javaFile.print(paramName);
            javaFile.print("= ((Integer)data.get(\"");
            javaFile.print(paramName);
            javaFile.println("\")).intValue();");
        }else if(paramClass  == Integer.class){      //Integer;
            javaFile.print("    Integer ");
            javaFile.print(paramName);
            javaFile.print("= (Integer)data.get(\"");
            javaFile.print(paramName);
            javaFile.println("\");");
        }else if(paramClass  == int[].class){   //int[]
            javaFile.print("    int[] ");
            javaFile.print(paramName);
            javaFile.print("= (int[])data.get(\"");
            javaFile.print(paramName);
            javaFile.print("\");");
        }else if(paramClass  == short.class){   //short
            javaFile.print("    short ");
            javaFile.print(paramName);
            javaFile.print("= ((Short)data.get(\"");
            javaFile.print(paramName);
            javaFile.println("\")).shortValue();");
        }else if(paramClass  == Short.class){          //Short
            javaFile.print("    Short ");
            javaFile.print(paramName);
            javaFile.print("= (Short)data.get(\"");
            javaFile.print(paramName);
            javaFile.println("\");");
        }else if(paramClass  == short[].class){     //short[]
            javaFile.print("    short[] ");
            javaFile.print(paramName);
            javaFile.print("= (short[])data.get(\"");
            javaFile.print(paramName);
            javaFile.println("\");");
        }else if(paramClass  == float.class){                //float
            javaFile.print("    float ");
            javaFile.print(paramName);
            javaFile.print("= ((Float)data.get(\"");
            javaFile.print(paramName);
            javaFile.println("\")).floatValue();");
        }else if(paramClass  == Float.class){                 //Float
            javaFile.print("    Float ");
            javaFile.print(paramName);
            javaFile.print("= (Float)data.get(\"");
            javaFile.print(paramName);
            javaFile.println("\");");
        }else if(paramClass  == float[].class){            //float[]
            javaFile.print("    float[] ");
            javaFile.print(paramName);
            javaFile.print("= (float[])data.get(\"");
            javaFile.print(paramName);
            javaFile.println("\");");
        }else if(paramClass  == double.class){             //double
            javaFile.print("    double ");
            javaFile.print(paramName);
            javaFile.print("= ((Double)data.get(\"");
            javaFile.print(paramName);
            javaFile.println("\")).doubleValue();");
        }else if(paramClass  == Double.class){              //Double
            javaFile.print("    Double ");
            javaFile.print(paramName);
            javaFile.print("= (Double)data.get(\"");
            javaFile.print(paramName);
            javaFile.println("\");");
        }else if(paramClass  == double[].class){           //double[]
            javaFile.print("    double[] ");
            javaFile.print(paramName);
            javaFile.print("= (double[])data.get(\"");
            javaFile.print(paramName);
            javaFile.println("\");");
        }else if(paramClass  == long.class){               //long
            javaFile.print("    long ");
            javaFile.print(paramName);
            javaFile.print("= ((Long)data.get(\"");
            javaFile.print(paramName);
            javaFile.println("\")).longValue();");
        }else if(paramClass  == Long.class){               //Long
            javaFile.print("    Long ");
            javaFile.print(paramName);
            javaFile.print("= (Long)data.get(\"");
            javaFile.print(paramName);
            javaFile.println("\");");
        }else if(paramClass  == long[].class){      //long[]
            javaFile.print("    long[] ");
            javaFile.print(paramName);
            javaFile.print("= (long[])data.get(\"");
            javaFile.print(paramName);
            javaFile.println("\");");
        }else if(paramClass  == char.class){          //char
            javaFile.print("    char ");
            javaFile.print(paramName);
            javaFile.print("= ((Character)data.get(\"");
            javaFile.print(paramName);
            javaFile.println("\")).charValue();");
        }else if(paramClass  == Character.class){           //Character
            javaFile.print("    Character ");
            javaFile.print(paramName);
            javaFile.print("= (Character)data.get(\"");
            javaFile.print(paramName);
            javaFile.println("\");");
        }else if(paramClass  == char[].class){           //char[]
            javaFile.print("    char[] ");
            javaFile.print(paramName);
            javaFile.print("= (char[])data.get(\"");
            javaFile.print(paramName);
            javaFile.println("\");");
        }else if(paramClass  == java.util.Date.class){           //java.util.Date;
            javaFile.print("    java.util.Date ");
            javaFile.print(paramName);
            javaFile.print("= (java.util.Date)data.get(\"");
            javaFile.print(paramName);
            javaFile.println("\");");
        }else if(paramClass  == java.sql.Date.class){            //java.sql.Date
            javaFile.print("    java.sql.Date ");
            javaFile.print(paramName);
            javaFile.print("= (java.sql.Date)data.get(\"");
            javaFile.print(paramName);
            javaFile.println("\");");
        }else if(paramClass  == java.sql.Time.class){            //java.sql.Time
            javaFile.print("    java.sql.Time ");
            javaFile.print(paramName);
            javaFile.print("= (java.sql.Time)data.get(\"");
            javaFile.print(paramName);
            javaFile.println("\");");
        }else if(paramClass  == java.sql.Timestamp.class){       //java.sql.Timestamp;
            javaFile.print("    java.sql.Timestamp ");
            javaFile.print(paramName);
            javaFile.print("= (java.sql.Timestamp)data.get(\"");
            javaFile.print(paramName);
            javaFile.println("\");");
        }else if(paramClass  == java.util.HashMap.class){       //HashMap;
            javaFile.print("    java.util.HashMap ");
            javaFile.print(paramName);
            javaFile.print("= (java.util.HashMap)data.get(\"");
            javaFile.print(paramName);
            javaFile.println("\");");
        }else if(paramClass  == String.class){      //String;
            javaFile.print("    String ");
            javaFile.print(paramName);
            javaFile.print("= (String)data.get(\"");
            javaFile.print(paramName);
            javaFile.println("\");");
        }else if(paramClass  == String[].class){             //String[]
            javaFile.print("    String[] ");
            javaFile.print(paramName);
            javaFile.print("= (String[])data.get(\"");
            javaFile.print(paramName);
            javaFile.println("\");");
        }else{                                          //Customer Class;
            String className = paramClass.getName();
            javaFile.print("    ");
            javaFile.print(className);
            javaFile.print(" ");
            javaFile.print(paramName);
            javaFile.print("= ("+className+")data.get(\"");
            javaFile.print(paramName);
            javaFile.println("\");");
        }
    }

    /**
     * complie the generated Expression.
     *
     * @param expression the source expression;
     * @param filePath
     */
    private void compileExpression(String expression,String filePath) throws ExprSyntaxException{
        String result = JavaCompiler.compile(filePath,classPath);
        if(result!=null && result.length()>0){
            if(log.isEnabledFor(Priority.ERROR)){
                log.error("failed to comile class:"+filePath+"\n\n"+result);
            }
            throw new ExprSyntaxException(expression,result);
        }
    }

    /**
     *
     * 
     * @param id
     * @return
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    private Object load(String id)
            throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        return loader.loadClass(id).newInstance();
    }
}
