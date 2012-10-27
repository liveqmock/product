/**
 *all rights reserved,@copyright 2003
 */
package com.dream.bizsdk.common.blc;

import com.dream.bizsdk.common.util.string.StringUtil;
import com.dream.bizsdk.common.databus.BizData;
import com.sun.tools.javac.Main;

import java.io.*;
import java.util.Vector;

import org.apache.log4j.Logger;

/**
 * Description:
 * Generate a Proxy implementation java class for a BL method.
 * User: divine
 * Date: 2004-3-13
 * Time: 9:10:14
 */
public class ProxyBuilder {
    private String root;
    private String cp;
    private Logger log = Logger.getLogger(ProxyBuilder.class);

    /**
     * @param root
     * @param cp
     */
    public ProxyBuilder(String root, String cp) {
        this.root = root;
        this.cp = cp;
    }

    /**
     * Generate a batch of java files;
     *
     * @param names
     * @param dir
     * @throws IOException
     */
    public void gen(Vector names, String dir, boolean overwrite) throws IOException {
        int size = names.size();
        for (int i = 0; i < size; i++) {
            gen((String) names.elementAt(i), dir, overwrite);
        }
    }

    /**
     * @param name
     * @return
     * @throws IOException
     */
    public String gen(String name) throws IOException {
        return gen(name, new File(root), true);
    }

    /**
     * @param name
     * @param overwrite
     * @return
     * @throws IOException
     */
    public String gen(String name, boolean overwrite) throws IOException {
        return gen(name, new File(root), overwrite);
    }

    /**
     * generate a proxy
     *
     * @param name
     * @param dir
     * @throws IOException
     */
    public String gen(String name, String dir, boolean overwrite) throws IOException {
        return gen(name, new File(dir), overwrite);
    }


    /**
     * generate the Proxy java source ;
     *
     * @param name
     * @param dir
     * @throws IOException
     */
    public String gen(String name, File dir, boolean overwrite) throws IOException {
        int index = 0;
        String className;
        String methodName;
        String fileName;
        String root;

        index = name.lastIndexOf('.');
        className = name.substring(0, index);
        methodName = name.substring(index + 1);
        fileName = name.replace('.', '_');
        root = StringUtil.checkPath(dir.getAbsolutePath())+"com/dream/bizsdk/common/blc/";
        File d = new File(root);
        if(!d.exists()){
            d.mkdirs();
        }

        File f = new File(root + fileName + ".java");
        //if file already exists
        if (f.exists() && !overwrite) {
            return fileName;
        }

        PrintStream ps = new PrintStream(new FileOutputStream(f));
        ps.println("package com.dream.bizsdk.common.blc; ");

        ps.println("import com.dream.bizsdk.common.blc.Proxy; ");
        ps.println("import com.dream.bizsdk.common.databus.BizData;");
        ps.println("import com.dream.bizsdk.common.blc.AbstractBLC;");
        ps.println();
        ps.println();
        ps.println();

        ps.print("public class ");
        ps.print(fileName);
        ps.println(" implements Proxy{");
        ps.println("    private " + className + " blc;");

        ps.println();

        ps.println("    public AbstractBLC getBLC(){");
        ps.println("        return blc;");
        ps.println("    }");

        ps.println();

        ps.println("    public void setBLC(AbstractBLC blc){");
        ps.println("        this. blc=(" + className + ")blc;");
        ps.println("    }");
        ps.println();

        int version = getMethodVersion(className, methodName);

        ps.println("    public int run(BizData rd,BizData sd) throws Exception{");
        if (version == 2) {
            ps.println("       return  blc." + methodName + "(rd,sd);");
        } else {
            ps.println("       return  blc." + methodName + "(rd);");
        }
        ps.println("    }");

        ps.println();

        ps.println("}");
        ps.close();

        //complie it now;
        compileProxy(fileName, root);
        return fileName;
    }

    /**
     * generate the proxy class;
     *
     * @param name
     * @param dir
     */
    private void compileProxy(String name, String dir) {
        if (log.isDebugEnabled()) {
            log.debug("complile proxy:" + name + " using classpath:" + this.cp);
        }
        CharArrayWriter caw = new CharArrayWriter();
        PrintWriter pw = new PrintWriter(caw);

        String[] params = new String[3];
        params[0] = "-classpath";
        params[1] = this.cp;
        params[2] = dir + name + ".java";
        Main.compile(params, pw);

        //log the result of the compilation;
        if (log.isDebugEnabled()) {
            log.debug(caw.toString());
        }
    }

    /**
     * get the version of a method, if the method is of method(BizData rd,BizData sd), then the version
     * is 2,otherwise is 1;
     *
     * @param className  the name of the class;
     * @param methodName the name ot the method;
     * @return
     */
    private int getMethodVersion(String className, String methodName) {
        int version = 2;

        try {
            Class cls = Class.forName(className);
            Class[] params = new Class[2];
            params[0] = BizData.class;
            params[1] = BizData.class;
            cls.getMethod(methodName, params);
        } catch (ClassNotFoundException e) {

        } catch (NoSuchMethodException e) {
            version = 1;
        }
        return version;
    }
}
