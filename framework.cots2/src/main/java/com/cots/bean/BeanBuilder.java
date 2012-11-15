/**
 *all rights reserved,@copyright 2003
 */
package com.cots.bean;

import java.io.*;

/**
 * Description:
 *      Bean Builder can automatically generate java source file according to a BeanProperty
 * definitions.
 *
 * User: chuguanghua
 * Date: 2005-3-7
 * Time: 15:15:32
 * Version: 1.0
 */
public class BeanBuilder {

    //the parent directory of the root package;
    private String baseDirectory;

    //content that will be written at the begining of a java source file;
    private String fileHeader;

    //content that will be written just before the class declariation;
    private String classHeader;

    public BeanBuilder() {
    }

    public BeanBuilder(String baseDirectory) {
        this.baseDirectory = baseDirectory;
    }

    public String getBaseDirectory() {
        return baseDirectory;
    }

    public void setBaseDirectory(String baseDirectory) {
        this.baseDirectory = baseDirectory;
    }

    public String getFileHeader() {
        return fileHeader;
    }

    public void setFileHeader(String fileHeader) {
        this.fileHeader = fileHeader;
    }

    public String getClassHeader() {
        return classHeader;
    }

    public void setClassHeader(String classHeader) {
        this.classHeader = classHeader;
    }


    public void build(String qname, BeanProperty[] props,boolean daoAccessor) throws IOException {
        String pkg = null;
        String clsName = qname;
        int index = qname.lastIndexOf('.');
        if (index > 0) {
            pkg = qname.substring(0, index);
            clsName = qname.substring(index + 1);
        }

        String filePath = baseDirectory + qname.replace('.', '/') + ".java";
        new File(filePath).getParentFile().mkdirs();

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath), "GBK"));

        writeHeader(fileHeader, bw);
        writePkgAndImport(pkg, bw,daoAccessor);
        this.writeHeader(classHeader, bw);
        this.writeBeginClassDeclariation(clsName, bw,daoAccessor);

        if(daoAccessor){
            writeSetDAO(bw);
        }

        for (int i = 0; i < props.length; i++) {
            this.writeProperty(props[i], bw);
        }
        this.writeEndClassDeclariation(bw);
        bw.flush();
        bw.close();
    }

    /**
     *
     *
     * @param qname
     * @param props
     * @throws IOException
     */
    public void build(String qname, BeanProperty[] props) throws IOException {
        build(qname,props,false);
    }


    /**
     *
     */
    private void writeHeader(String header, BufferedWriter out) throws IOException {
        if (header == null || header.length() < 1) {
            return;
        }
        BufferedReader br = new BufferedReader(new StringReader(header));
        String line;
        out.write("/**\n");
        while ((line = br.readLine()) != null) {
            out.write(" *" + line + "\n");
        }
        out.write(" */\n");
    }


    private void writeSetDAO(BufferedWriter out) throws IOException {
        out.write("    private DAO dao;\n\n");

        out.write("    public void setDAO(DAO dao) {\n");
        out.write("        this.dao = dao;\n");
        out.write("    }\n\n");
    }

    /**
     *
     * @param pkg
     * @param out
     * @throws IOException
     */
    private void writePkgAndImport(String pkg, BufferedWriter out,boolean daoAccessor) throws IOException {
        if (pkg != null && pkg.length() > 0) {
            out.write("package " + pkg + ";\n");
        }

        if(daoAccessor){
            out.write("\n\n");
            out.write("import com.cots.dao.DAO;\n");
            out.write("import com.cots.dao.IDAOAccessor;\n");
        }
        out.write("\n\n");
    }

    /**
     *
     * @param className
     * @param out
     * @throws IOException
     */
    private void writeBeginClassDeclariation(String className, BufferedWriter out,boolean daoAccessor) throws IOException {
        out.write("public class " + className + " ");
        if(daoAccessor){
            out.write("implements IDAOAccessor ");
        }
        out.write("{\n");
    }

    /**
     *
     * @param out
     * @throws IOException
     */
    private void writeEndClassDeclariation(BufferedWriter out) throws IOException {
        out.write("}\n");
    }

    /**
     *
     * @param prop
     * @param out
     */
    private void writeProperty(BeanProperty prop, BufferedWriter out) throws IOException {
        String name = prop.getName();
        String type = prop.getType();
        String getter = prop.getGetMethodName();
        String setter = prop.getSetMethodName();

        out.write("    private "+type+" "+name+";\n\n");
        out.write("    public " + type + " ");
        if (getter != null && getter.length() > 0) {
            out.write(getter);
        } else {
            out.write("get" + Character.toUpperCase(name.charAt(0)) + name.substring(1));
        }

        out.write("(){\n");
        out.write("        return " + name + ";\n");
        out.write("    }\n\n");

        out.write("    public void ");
        if (setter != null && setter.length() > 0) {
            out.write(setter);
        } else {
            out.write("set" + Character.toUpperCase(name.charAt(0)) + name.substring(1));
        }

        out.write("(" + type + " " + name + "){\n");
        out.write("        this." + name + "=" + name + ";\n");
        out.write("    }\n\n");
    }

    public static void main(String[] argc) throws IOException{
        BeanBuilder builder = new BeanBuilder("e:\\");
        builder.setFileHeader("版权所有，2004-2008");
        builder.setClassHeader("作者:chugh\n时间:2005-3-7");
        BeanProperty[] props = new BeanProperty[3];
        props[0]= new BeanProperty("name","String");
        props[1]= new BeanProperty("age","int");
        props[2]= new BeanProperty("birthDate","java.util.Date");

        builder.build("com.cots.role.beans.User",props,true);
    }
}