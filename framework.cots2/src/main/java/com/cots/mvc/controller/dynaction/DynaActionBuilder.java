/**
 *all rights reserved,@copyright 2003
 */
package com.cots.mvc.controller.dynaction;

import com.cots.mvc.controller.DefaultAction;
import com.cots.mvc.controller.DynaAction;
import com.cots.mvc.controller.AbstractAction;
import com.cots.mvc.model.BLModel;
import com.cots.mvc.model.Model;
import com.cots.mvc.model.GenericModel;
import com.cots.mvc.model.ejb.StatlessSessionEJBModel;
import com.cots.mvc.model.ejb.StatfulSessionEJBModel;
import com.cots.mvc.model.parameter.*;
import com.cots.mvc.dispatch.*;
import com.cots.exp.BooleanExpression;
import com.cots.exp.StringExpression;
import com.cots.bean.Bean;
import com.cots.bean.BeanProperty;
import com.cots.bean.PrimitiveType;
import com.cots.util.JavaCompiler;
import com.cots.blc.BLCPool;
import com.cots.blc.BLContext;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.util.*;

import org.apache.log4j.Logger;
import org.apache.log4j.Priority;


/**
 * Description:
 *      Generate and compile a dynamic action;
 *
 * User: chuguanghua
 * Date: 2004-11-21
 * Time: 12:22:09
 * Version: 1.0
 */
public class DynaActionBuilder {

    private String baseDir;
    private String classPath;
    private BLCPool blcPool;
    private Logger logger;
    private BLContext context;

    public DynaActionBuilder(){
        logger = Logger.getLogger(this.getClass().getName());
    }
    /**
     * get the base dir to save the generated java source files;
     *
     * @return
     */
    public String getBaseDir() {
        return baseDir;
    }

    /**
     * get the context;
     *
     * @return
     */
    public BLContext getContext() {
        return context;
    }

    /**
     * set the context;
     *
     * @param context
     */
    public void setContext(BLContext context) {
        this.context = context;
    }

    /**
     * set the base dir to save the generated java source files;
     *
     * @param baseDir
     */
    public void setBaseDir(String baseDir) {
        char ch = baseDir.charAt(baseDir.length()-1);
        if(ch!='\\' && ch!='/'){
            this.baseDir = baseDir +"/";
        }else{
            this.baseDir = baseDir;
        }
        new File(this.baseDir+"com/cots/mvc/controller/dynaction/autogen").mkdirs();
    }

    /**
     * get the class path used when compile java source file;
     *
     * @return the classpath;
     */
    public String getClassPath() {
        return classPath;
    }

    /**
     * set the classpath used when compile java source files;
     *
     * @param classPath the classpath;
     */
    public void setClassPath(String classPath) {
        this.classPath = classPath;
    }

    /**
     * get the BLCPool used by this Builder;
     *
     * @return
     */
    public BLCPool getBlcPool() {
        return blcPool;
    }

    /**
     * set the BLCPool used by tis Builder;
     *
     * @param blcPool
     */
    public void setBlcPool(BLCPool blcPool) {
        this.blcPool = blcPool;
    }

    /**
     *
     *
     * @param da
     * @throws IOException
     */
    public DynaAction build(DefaultAction da) throws IOException{
        String name = da.getName();
        String path = baseDir+"com/cots/mvc/controller/dynaction/autogen/"+name+".java";
        DynaAction action=null;
        BufferedWriter bw = new BufferedWriter(new FileWriter(new File(path)));

        //generate the java source file;
        try{
            bw.write("package com.cots.mvc.controller.dynaction.autogen;\n");
            bw.write("\n\n");
            bw.write("import com.cots.mvc.controller.DynaAction;\n" +
                    "import com.cots.mvc.controller.AbstractAction;\n"+
                    "import com.cots.mvc.controller.ControllerServlet;\n"+
                    "import com.cots.bean.PrimitiveType;\n"+
                     "import com.cots.blc.BLCPool;\n" +
                     "import com.cots.blc.BLContext;\n" +
                     "import com.cots.dao.DAO;\n" +
                     "import com.cots.dao.TransactionLevel;\n" +
                     "import com.cots.util.Base64;\n\n" +
                     "import java.util.*;\n" +
                     "import javax.servlet.http.HttpServletRequest;\n" +
                     "import javax.servlet.http.HttpServletResponse;\n" +
                     "import javax.servlet.http.HttpSession;\n" +
                     "import javax.servlet.ServletException;\n" +
                     "import javax.servlet.ServletContext;\n" +
                    "import javax.servlet.RequestDispatcher;\n" +
                     "import java.io.IOException;\n");
            bw.write("\n\n");
            bw.write("public final class "+name+" extends AbstractAction implements DynaAction{\n");

            bw.write("  private String displayName=\""+name+"\";\n");
            bw.write("  private BLCPool blcPool;\n");
            bw.write("  private BLContext context;\n");

            String input= da.getInput();
            if(input!=null){
                bw.write("  private String input=\""+da.getInput()+"\";\n");
            }else{
                bw.write("  private String input;\n");
            }

            bw.write("  public String getDisplayName() {\n" +
                     "      return displayName;\n" +
                     "  }\n");

            bw.write("  public void setBLCPool(BLCPool pool) {\n" +
                     "      blcPool = pool;\n" +
                     "  }\n");

            bw.write("  public void setBLContext(BLContext context) {\n" +
                     "      this.context = context;\n" +
                     "  }\n");

            /*write run methods;*/
            bw.write("  public void run(HttpServletRequest request, HttpServletResponse response)\n" +
                    "          throws ServletException, IOException {\n");

            bw.write("  DAO dao = null;\n");
            bw.write("  try{\n");
            if(da.isTransatcionRequired()){
                String targetDAO = da.getTargetDAO();
                if(targetDAO!=null){
                    bw.write("   dao = context.getDAO(\""+targetDAO+"\");\n");
                }else{
                    bw.write("   dao = context.getDAO();\n");
                }
                bw.write("   dao.beginTrans(TransactionLevel.strToInt(\""+da.getTransactionLevel()+"\"));\n");
            }
            writeParams(bw,da);

            writeValidaters(bw,da);

            writeModels(bw,da);

            setParameters2Request(bw,da);

            writeDispatches(bw,da);

            if(da.isTransatcionRequired()){
                bw.write("   dao.commit();\n");
                //bw.write("   dao=null;\n");
            }

            bw.write("  }catch(Throwable t){\n");
            //if transaction is required for this action;
            if(da.isTransatcionRequired()){
                bw.write("     if(dao!=null){\n");
                bw.write("        try{\n");
                bw.write("          dao.rollback();\n");
                bw.write("        }catch(Exception e){}\n");
                bw.write("     }");
            }
            bw.write("     throw new ServletException(t);\n");
            bw.write("  }\n");
            bw.write("  }\n");

            bw.write("}\n");
        }finally{
            bw.flush();
            bw.close();
        }

        //compile the generated java source;
        String result = JavaCompiler.compile(path,classPath);

        if(result.length()>0){  //compile error;
            if(logger.isEnabledFor(Priority.ERROR)){
                logger.error(result);
            }
        }else{ //compile succeeds;
            //load the compiled class;
            try {
                action = (DynaAction)context.getLoader().loadClass("com.cots.mvc.controller.dynaction.autogen."+name).newInstance();
                action.setBLCPool(blcPool);
                action.setBLContext(context);

                //copy the fileters;
                ((AbstractAction)action).setFilters(da.getFilters());
                //copy the listeners;
                ((AbstractAction)action).setListeners(da.getListeners());                
            }catch (ClassNotFoundException e) {
                if(logger.isEnabledFor(Priority.ERROR)){
                    logger.error("class not found "+"com.cots.mvc.controller.dynaction.autogen."+name,e);
                }
            }catch (InstantiationException e) {
                if(logger.isEnabledFor(Priority.ERROR)){
                    logger.error("can't create class "+"com.cots.mvc.controller.dynaction.autogen."+name,e);
                }
            }catch (IllegalAccessException e) {
                if(logger.isEnabledFor(Priority.ERROR)){
                    logger.error("can't access class "+"com.cots.mvc.controller.dynaction.autogen."+name,e);
                }
            }
        }

        return action;
    }


    private void writeParams(BufferedWriter bw,DefaultAction da) throws IOException {
        //tmp variable
        bw.write("    String        __tmp    =null;\n");
        bw.write("    Object        __value  =null;\n");
        bw.write("    HashSet       __beanIDs=null;\n");
        bw.write("    Enumeration   __names=null;\n");
        bw.write("    int           __len    =0;\n");
        bw.write("    String[]      __tmpValues    =null;\n");
        bw.write("    StringBuffer __tmpUri        =null;\n");
        bw.write("    Iterator      __it        =null;\n");

        //session object;
        bw.write("    HttpSession session=request.getSession();\n");
        //application object;
        bw.write("    ServletContext application=session.getServletContext();\n");

        /**write all the parameters*/
        Map params = da.getParameters();
        Iterator it = params.values().iterator();
        while(it.hasNext()){
            Parameter param = (Parameter)it.next();
            writeParam(bw,param);
        }
    }

    private void writeParam(BufferedWriter bw,Parameter p) throws IOException {
        if(p instanceof PrimitiveParameter){
            writeParam(bw,(PrimitiveParameter)p);
        }else if(p instanceof BeanParameter){
            writeParam(bw,(BeanParameter)p);
        }else if(p instanceof ListParameter){
            writeParam(bw,(ListParameter)p);
        }else if(p instanceof MapParameter){
            writeParam(bw,(MapParameter)p);
        }else if(p instanceof GenericParameter){
            writeParam(bw,(GenericParameter)p);
        }else if(p instanceof SetParameter){
            writeParam(bw,(SetParameter)p);
        }
    }

    private void writeParam(BufferedWriter bw,PrimitiveParameter p) throws IOException{
        String type = p.getType();
        String name = p.getName();
        boolean array = p.isArray();

        if(!array){
            bw.write("    "+type+" "+name+";\n");
            int source = p.getSource();
            if(source == ParameterSource.REQUEST){
                bw.write("    __tmp=request.getParameter(\""+name+"\");\n");
                if("int".equals(type)){
                    bw.write("    if(__tmp==null){\n");
                    bw.write("        "+name+"=0;\n");
                    bw.write("    }else{\n");
                    bw.write("        "+name+"=Integer.parseInt(__tmp);\n");
                    bw.write("    }\n");
                }else if("Integer".equals(type)){
                    bw.write("    if(__tmp==null){\n");
                    bw.write("        "+name+"=new Integer(0);\n");
                    bw.write("    }else{\n");
                    bw.write("        "+name+"=Integer.valueOf(__tmp);\n");
                    bw.write("    }\n");
                }else if("byte".equals(type)){
                    bw.write("    if(__tmp==null){\n");
                    bw.write("        "+name+"=0;\n");
                    bw.write("    }else{\n");
                    bw.write("        "+name+"=Byte.parseByte(__tmp);\n");
                    bw.write("    }\n");
                }else if("byte[]".equals(type)){
                    bw.write("    if(__tmp==null){\n");
                    bw.write("        "+name+"=null;\n");
                    bw.write("    }else{\n");
                    bw.write("        "+name+"=Base64.decodeBytes(__tmp);\n");
                    bw.write("    }\n");
                }else if("Byte".equals(type)){
                    bw.write("    if(__tmp==null){\n");
                    bw.write("        "+name+"=new Byte(0);\n");
                    bw.write("    }else{\n");
                    bw.write("        "+name+"=Byte.valueOf(__tmp);\n");
                    bw.write("    }\n");
                }else if("short".equals(type)){
                    bw.write("    if(__tmp==null){\n");
                    bw.write("        "+name+"=0;\n");
                    bw.write("    }else{\n");
                    bw.write("        "+name+"=Short.parseShort(__tmp);\n");
                    bw.write("    }\n");
                }else if("Short".equals(type)){
                    bw.write("    if(__tmp==null){\n");
                    bw.write("        "+name+"=new Short(0);\n");
                    bw.write("    }else{\n");
                    bw.write("        "+name+"=Short.valueOf(__tmp);\n");
                    bw.write("    }\n");
                }else if("long".equals(type)){
                    bw.write("    if(__tmp==null){\n");
                    bw.write("        "+name+"=0;\n");
                    bw.write("    }else{\n");
                    bw.write("        "+name+"=Long.parseLong(__tmp);\n");
                    bw.write("    }\n");
                }else if("Long".equals(type)){
                    bw.write("    if(__tmp==null){\n");
                    bw.write("        "+name+"=new Lone(0);\n");
                    bw.write("    }else{\n");
                    bw.write("        "+name+"=Long.valueOf(__tmp);\n");
                    bw.write("    }\n");
                }else if("float".equals(type)){
                    bw.write("    if(__tmp==null){\n");
                    bw.write("        "+name+"=0;\n");
                    bw.write("    }else{\n");
                    bw.write("        "+name+"=Float.parseFloat(__tmp);\n");
                    bw.write("    }\n");
                }else if("Float".equals(type)){
                    bw.write("    if(__tmp==null){\n");
                    bw.write("        "+name+"=new Float(0);\n");
                    bw.write("    }else{\n");
                    bw.write("        "+name+"=Float.valueOf(__tmp);\n");
                    bw.write("    }\n");
                }else if("double".equals(type)){
                    bw.write("    if(__tmp==null){\n");
                    bw.write("        "+name+"=0;\n");
                    bw.write("    }else{\n");
                    bw.write("        "+name+"=Double.parseDouble(__tmp);\n");
                    bw.write("    }\n");
                }else if("Double".equals(type)){
                    bw.write("    if(__tmp==null){\n");
                    bw.write("        "+name+"=new Double(0);\n");
                    bw.write("    }else{\n");
                    bw.write("        "+name+"=Double.valueOf(__tmp);\n");
                    bw.write("    }\n");
                }else if("char".equals(type)){
                    bw.write("    if(__tmp==null || __tmp.length()!=1){\n");
                    bw.write("        "+name+"=0;\n");
                    bw.write("    }else{\n");
                    bw.write("        "+name+"=__tmp.charAt(0);\n");
                    bw.write("    }\n");
                }else if("char[]".equals(type)){
                    bw.write("    if(__tmp==null || __tmp.length()<1){\n");
                    bw.write("        "+name+"=null;\n");
                    bw.write("    }else{\n");
                    bw.write("        "+name+"=new String(Base64.decodeBytes(__tmp)).toCharArray();\n");
                    bw.write("    }\n");
                }else if("Character".equals(type)){
                    bw.write("    if(__tmp==null || __tmp.length()!=1){\n");
                    bw.write("        "+name+"=null;\n");
                    bw.write("    }else{\n");
                    bw.write("        "+name+"=new Character(__tmp.charAt(0));\n");
                    bw.write("    }\n");
                }else if("String".equals(type)){
                    bw.write("    "+name+"=__tmp;\n");
                }else if("java.util.Date".equals(type)){
                    String format = p.getFormat();
                    if(format!=null && format.length()>0){
                        bw.write("    "+name+"=PrimitiveType.createDate(request.getParameter(\""+name+"\",\""+format+"\");\n");
                    }else{
                        bw.write("    "+name+"=PrimitiveType.createDate(request.getParameter(\""+name+"\",null);\n");
                    }
                }else if("java.sql.Date".equals(type)){
                    String format = p.getFormat();
                    if(format!=null && format.length()>0){
                        bw.write("    "+name+"=PrimitiveType.createSqlDate(request.getParameter(\""+name+"\",\""+format+"\");\n");
                    }else{
                        bw.write("    "+name+"=PrimitiveType.createSqlDate(request.getParameter(\""+name+"\",null);\n");
                    }
                }else if("java.sql.Time".equals(type)){
                    String format = p.getFormat();
                    if(format!=null && format.length()>0){
                        bw.write("    "+name+"=PrimitiveType.createTime(request.getParameter(\""+name+"\",\""+format+"\");\n");
                    }else{
                        bw.write("    "+name+"=PrimitiveType.createTime(request.getParameter(\""+name+"\",null);\n");
                    }
                }else if("java.sql.Timestamp".equals(type)){
                    String format = p.getFormat();
                    if(format!=null && format.length()>0){
                        bw.write("    "+name+"=PrimitiveType.createTimestamp(request.getParameter(\""+name+"\",\""+format+"\");\n");
                    }else{
                        bw.write("    "+name+"=PrimitiveType.createTimestamp(request.getParameter(\""+name+"\",null);\n");
                    }
                }
            }
        }else{
            bw.write("    "+type+"[] "+name+";\n");
            int source = p.getSource();
            if(source == ParameterSource.REQUEST){
                bw.write("    __tmpValues=request.getParameterValues(\""+name+"\");\n");
                if("int".equals(type)){
                    String format = p.getFormat();
                    if(format!=null && format.length()>0){
                        bw.write("    "+name+"=PrimitiveType.createIntArray(__tmpValues,\""+p.getFormat()+"\");\n");
                    }else{
                        bw.write("    "+name+"=PrimitiveType.createIntArray(__tmpValues,null);\n");
                    }
                }else if("Integer".equals(type)){
                    String format = p.getFormat();
                    if(format!=null && format.length()>0){
                        bw.write("    "+name+"=PrimitiveType.createIntegerArray(__tmpValues,\""+p.getFormat()+"\");\n");
                    }else{
                        bw.write("    "+name+"=PrimitiveType.createIntegerArray(__tmpValues,null);\n");
                    }
                }else if("byte".equals(type)){
                    String format = p.getFormat();
                    if(format!=null && format.length()>0){
                        bw.write("    "+name+"=PrimitiveType.createByteArray(__tmpValues,\""+p.getFormat()+"\");\n");
                    }else{
                        bw.write("    "+name+"=PrimitiveType.createByteArray(__tmpValues,null);\n");
                    }
                }else if("Byte".equals(type)){
                    String format = p.getFormat();
                    if(format!=null && format.length()>0){
                        bw.write("    "+name+"=PrimitiveType.createByteObjArray(__tmpValues,\""+p.getFormat()+"\");\n");
                    }else{
                        bw.write("    "+name+"=PrimitiveType.createByteObjArray(__tmpValues,null);\n");
                    }
                }else if("short".equals(type)){
                    String format = p.getFormat();
                    if(format!=null && format.length()>0){
                        bw.write("    "+name+"=PrimitiveType.createShortArray(__tmpValues,\""+p.getFormat()+"\");\n");
                    }else{
                        bw.write("    "+name+"=PrimitiveType.createShortArray(__tmpValues,null);\n");
                    }
                }else if("Short".equals(type)){
                    String format = p.getFormat();
                    if(format!=null && format.length()>0){
                        bw.write("    "+name+"=PrimitiveType.createShortObjArray(__tmpValues,\""+p.getFormat()+"\");\n");
                    }else{
                        bw.write("    "+name+"=PrimitiveType.createShortObjArray(__tmpValues,null);\n");
                    }
                }else if("long".equals(type)){
                    String format = p.getFormat();
                    if(format!=null && format.length()>0){
                        bw.write("    "+name+"=PrimitiveType.createLongArray(__tmpValues,\""+p.getFormat()+"\");\n");
                    }else{
                        bw.write("    "+name+"=PrimitiveType.createLongArray(__tmpValues,null);\n");
                    }
                }else if("Long".equals(type)){
                    String format = p.getFormat();
                    if(format!=null && format.length()>0){
                        bw.write("    "+name+"=PrimitiveType.createLongObjArray(__tmpValues,\""+p.getFormat()+"\");\n");
                    }else{
                        bw.write("    "+name+"=PrimitiveType.createLongObjArray(__tmpValues,null);\n");
                    }
                }else if("float".equals(type)){
                    String format = p.getFormat();
                    if(format!=null && format.length()>0){
                        bw.write("    "+name+"=PrimitiveType.createFloatArray(__tmpValues,\""+p.getFormat()+"\");\n");
                    }else{
                        bw.write("    "+name+"=PrimitiveType.createFloatArray(__tmpValues,null);\n");
                    }
                }else if("Float".equals(type)){
                    String format = p.getFormat();
                    if(format!=null && format.length()>0){
                        bw.write("    "+name+"=PrimitiveType.createFloatObjArray(__tmpValues,\""+p.getFormat()+"\");\n");
                    }else{
                        bw.write("    "+name+"=PrimitiveType.createFloatObjArray(__tmpValues,null);\n");
                    }
                }else if("double".equals(type)){
                    String format = p.getFormat();
                    if(format!=null && format.length()>0){
                        bw.write("    "+name+"=PrimitiveType.createDoubleArray(__tmpValues,\""+p.getFormat()+"\");\n");
                    }else{
                        bw.write("    "+name+"=PrimitiveType.createDoubleArray(__tmpValues,null);\n");
                    }
                }else if("Double".equals(type)){
                    String format = p.getFormat();
                    if(format!=null && format.length()>0){
                        bw.write("    "+name+"=PrimitiveType.createDoubleObjArray(__tmpValues,\""+p.getFormat()+"\");\n");
                    }else{
                        bw.write("    "+name+"=PrimitiveType.createDoubleObjArray(__tmpValues,null);\n");
                    }
                }else if("char".equals(type)){
                    String format = p.getFormat();
                    if(format!=null && format.length()>0){
                        bw.write("    "+name+"=PrimitiveType.createCharArray(__tmpValues,\""+p.getFormat()+"\");\n");
                    }else{
                        bw.write("    "+name+"=PrimitiveType.createCharArray(__tmpValues,null);\n");
                    }
                }else if("Character".equals(type)){
                    String format = p.getFormat();
                    if(format!=null && format.length()>0){
                        bw.write("    "+name+"=PrimitiveType.createCharacterArray(__tmpValues,\""+p.getFormat()+"\");\n");
                    }else{
                        bw.write("    "+name+"=PrimitiveType.createCharacterArray(__tmpValues,null);\n");
                    }
                }else if("String".equals(type)){
                    bw.write("    "+name+"=__tmpValues;\n");
                }else if("java.util.Date".equals(type)){
                    String format = p.getFormat();
                    if(format!=null && format.length()>0){
                        bw.write("    "+name+"=PrimitiveType.createDateArray(__tmpValues,\""+p.getFormat()+"\");\n");
                    }else{
                        bw.write("    "+name+"=PrimitiveType.createDateArray(__tmpValues,null);\n");
                    }
                }else if("java.sql.Date".equals(type)){
                    String format = p.getFormat();
                    if(format!=null && format.length()>0){
                        bw.write("    "+name+"=PrimitiveType.createSqlDateArray(__tmpValues,\""+p.getFormat()+"\");\n");
                    }else{
                        bw.write("    "+name+"=PrimitiveType.createSqlDateArray(__tmpValues,null);\n");
                    }
                }else if("java.sql.Time".equals(type)){
                    String format = p.getFormat();
                    if(format!=null && format.length()>0){
                        bw.write("    "+name+"=PrimitiveType.createTimeArray(__tmpValues,\""+p.getFormat()+"\");\n");
                    }else{
                        bw.write("    "+name+"=PrimitiveType.createTimeArray(__tmpValues,null);\n");
                    }
                }else if("java.sql.Timestamp".equals(type)){
                    String format = p.getFormat();
                    if(format!=null && format.length()>0){
                        bw.write("    "+name+"=PrimitiveType.createTimestampArray(__tmpValues,\""+p.getFormat()+"\");\n");
                    }else{
                        bw.write("    "+name+"=PrimitiveType.createTimestampArray(__tmpValues,null);\n");
                    }
                }
            }
        }
    }

    /**
     * write a bean parameter;
     *
     * @param bw
     * @param p
     * @throws IOException
     */
    private void writeParam(BufferedWriter bw,BeanParameter p) throws IOException {
        String type = p.getType();
        String name = p.getName();
        boolean array = p.isArray();
        int source = p.getSource();

        //write bean parameter;
        if(!array){
            bw.write("    ");
            bw.write(type);
            bw.write(" ");
            bw.write(name);
            bw.write("=");
            if(source == ParameterSource.APPLICATION){
                bw.write("("+type+")application.getAttribute(\""+name+"\");\n");
            }else if(source == ParameterSource.SESSION){
                bw.write("("+type+")session.getAttribute(\""+name+"\");\n");
            }else if(source == ParameterSource.REQUEST){
                bw.write("new "+type+"();\n");
                Bean bean = p.getBean();
                BeanProperty[] bps = bean.getSetableColumns();
                int count = bps.length;
                for(int i=0;i<count;i++){
                    String pType = bps[i].getType();
                    bw.write("    __value = PrimitiveType.create(\""+pType+
                            "\",request.getParameter(\""+name+"."+bps[i].getName()+"\"));\n");
                    bw.write("    if(__value!=null){\n");
                    bw.write("       "+name);
                    bw.write(".");
                    bw.write(bps[i].getPropertySetMethod().getName());
                    bw.write("(");
                    if("int".equals(pType)){
                        bw.write("((Integer)__value).intValue()");
                    }else if("boolean".equals(pType)){
                        bw.write("((Boolean)__value).booleanValue()");
                    }else if("byte".equals(pType)){
                        bw.write("((Byte)__value).byteValue()");
                    }else if("short".equals(pType)){
                        bw.write("((Short)__value).shortValue()");
                    }else if("float".equals(pType)){
                        bw.write("((Float)__value).floatValue()");
                    }else if("double".equals(pType)){
                        bw.write("((Double)__value).doubleValue()");
                    }else if("long".equals(pType)){
                        bw.write("((Lone)__value).longValue()");
                    }else if("char".equals(pType)){
                        bw.write("((Character)__value).charValue()");
                    }else if("Boolean".equals(pType)){
                        bw.write("(Boolean)__value");
                    }else if("Integer".equals(pType)){
                        bw.write("(Integer)__value");
                    }else if("Byte".equals(pType)){
                        bw.write("(Byte)__value");
                    }else if("Character".equals(pType)){
                        bw.write("(Character)__value");
                    }else if("Float".equals(pType)){
                        bw.write("(Float)__value");
                    }else if("Double".equals(pType)){
                        bw.write("(Double)__value");
                    }else if("Long".equals(pType)){
                        bw.write("(Long)__value");
                    }else if("Short".equals(pType)){
                        bw.write("(Short)__value");
                    }else if("java.util.Date".equals(pType)){
                        bw.write("(java.util.Date)__value");
                    }else if("java.sql.Date".equals(pType)){
                        bw.write("(java.sql.Date)__value");
                    }else if("java.sql.Time".equals(pType)){
                        bw.write("(java.sql.Time)__value");
                    }else if("java.sql.Timestamp".equals(pType)){
                        bw.write("(java.sql.Timestamp)__value");
                    }else if("String".equals(pType)){
                        bw.write("(String)__value");
                    }else if("char[]".equals(pType)){
                        bw.write("(char[])__value");
                    }else if("byte[]".equals(pType)){
                        bw.write("(byte[])value");
                    }
                    bw.write(");\n");
                    bw.write("    }\n");
                }
            }else if(source == ParameterSource.NEW){
                bw.write("new "+type+"();\n");
            }else if(source == ParameterSource.CODE){
                bw.write("null;\n");
            }
        }else{
            bw.write("    "+type+"[] ");
            bw.write(name);
            bw.write("=");
            if(source == ParameterSource.APPLICATION){
                bw.write(type+"[]application.getAttribute(\""+name+"\");\n");
            }else if(source == ParameterSource.SESSION){
                bw.write(type+"[]session.getAttribute(\""+name+"\");\n");
            }else if(source == ParameterSource.REQUEST){
                bw.write("null;\n");
                //populate this bean;

                bw.write("    __tmp= \""+name+"[\";\n");
                bw.write("    __len = \""+name+"[\".length();\n");

                bw.write("    if(__beanIDs==null){\n");
                bw.write("        __beanIDs=new HashSet();\n");
                bw.write("    }else{\n");
                bw.write("        __beanIDs.clear();\n");
                bw.write("    }\n");
                bw.write("    __names = request.getParameterNames();\n");
                bw.write("    while(__names.hasMoreElements()){\n");
                bw.write("        String orginalName = (String)__names.nextElement();\n");
                bw.write("        if(orginalName.startsWith(__tmp)){\n");
                bw.write("            int index = orginalName.indexOf(\"].\");\n");
                bw.write("            if(index>__len){\n");
                bw.write("                String trueName = orginalName.substring(__len,index);\n");
                bw.write("                __beanIDs.add(trueName);\n");
                bw.write("            }\n");
                bw.write("        }\n");
                bw.write("    }\n");

                bw.write("    "+name+"= new "+type+"[__beanIDs.size()];\n");
                bw.write("    __len=0;\n");

                bw.write("    __it = __beanIDs.iterator();\n");
                bw.write("    while(__it.hasNext()){\n");
                bw.write("      boolean ok = false;\n");
                bw.write("      String key = (String)__it.next();\n");
                bw.write("      "+type+" oo = new "+type+"();\n");
                Bean bean = p.getBean();
                BeanProperty[] bps = bean.getSetableColumns();
                int count = bps.length;
                for(int i=0;i<count;i++){
                    String pType = bps[i].getType();
                    bw.write("      __value = PrimitiveType.create(\""+pType+
                            "\",request.getParameter(\""+name+"[\"+key+\"]."+bps[i].getName()+"\"));\n");
                    bw.write("      if(__value!=null){\n");
                    bw.write("         ok=true;\n");
                    bw.write("         oo");
                    bw.write(".");
                    bw.write(bps[i].getPropertySetMethod().getName());
                    bw.write("(");
                    if("int".equals(pType)){
                        bw.write("((Integer)__value).intValue()");
                    }else if("boolean".equals(pType)){
                        bw.write("((Boolean)__value).booleanValue()");
                    }else if("byte".equals(pType)){
                        bw.write("((Byte)__value).byteValue()");
                    }else if("short".equals(pType)){
                        bw.write("((Short)__value).shortValue()");
                    }else if("float".equals(pType)){
                        bw.write("((Float)__value).floatValue()");
                    }else if("double".equals(pType)){
                        bw.write("((Double)__value).doubleValue()");
                    }else if("long".equals(pType)){
                        bw.write("((Long)__value).longValue()");
                    }else if("char".equals(pType)){
                        bw.write("((Character)__value).charValue()");
                    }else if("Integer".equals(pType)){
                        bw.write("(Integer)__value");
                    }else if("Boolean".equals(pType)){
                        bw.write("(Boolean)__value");
                    }else if("Byte".equals(pType)){
                        bw.write("(Byte)__value");
                    }else if("Character".equals(pType)){
                        bw.write("(Character)__value");
                    }else if("Float".equals(pType)){
                        bw.write("(Float)__value");
                    }else if("Double".equals(pType)){
                        bw.write("(Double)__value");
                    }else if("Long".equals(pType)){
                        bw.write("(Long)__value");
                    }else if("Short".equals(pType)){
                        bw.write("(Short)__value");
                    }else if("java.util.Date".equals(pType)){
                        bw.write("(java.util.Date)__value");
                    }else if("java.sql.Date".equals(pType)){
                        bw.write("(java.sql.Date)__value");
                    }else if("java.sql.Time".equals(pType)){
                        bw.write("(java.sql.Time)__value");
                    }else if("java.sql.Timestamp".equals(pType)){
                        bw.write("(java.sql.Timestamp)__value");
                    }else if("String".equals(pType)){
                        bw.write("(String)__value");
                    }else if("char[]".equals(pType)){
                        bw.write("(char[])__value");
                    }else if("byte[]".equals(pType)){
                        bw.write("null");
                    }
                    bw.write(");\n");
                    bw.write("      }\n");
                }
                bw.write("      if(ok){\n");
                bw.write("        "+name+"[__len++]=oo;\n");
                bw.write("      };\n");
                bw.write("    };\n");
            }else if(source == ParameterSource.NEW){
                bw.write("new "+type+"[100];\n");
            }else{
                bw.write("null\n");
            }
        }
    }

    /**
     * write a MapParameter
     *
     * @param bw bufferedWriter;
     * @param p the MapParameter;
     * @throws IOException
     */
    private void writeParam(BufferedWriter bw,MapParameter p) throws IOException {
        String name = p.getName();
        String type = p.getType();

        bw.write("    ");
        bw.write("java.util.Map");
        bw.write(" ");
        bw.write(name);
        bw.write("=");

        int source = p.getSource();
        if(source == ParameterSource.NEW){
            bw.write("new "+type+"();\n");
        }else if(source == ParameterSource.SESSION){
            bw.write("("+type+")session.getAttribute(\""+name+"\");\n");
        }else if(source == ParameterSource.APPLICATION){
            bw.write("("+type+")application.getAttribute(\""+name+"\");\n");
        }else if(source == ParameterSource.CODE) {
            bw.write("null;\n");
        }else if(source == ParameterSource.REQUEST){
            bw.write("new "+type+"();\n");

            bw.write("    __tmp= \""+name+"[\";\n");
            bw.write("    __len = \""+name+"[\".length();\n");

            bw.write("    if(__beanIDs==null){\n");
            bw.write("        __beanIDs=new HashSet();\n");
            bw.write("    }else{\n");
            bw.write("        __beanIDs.clear();\n");
            bw.write("    }\n");
            bw.write("    __names = request.getParameterNames();\n");
            bw.write("    while(__names.hasMoreElements()){\n");
            bw.write("        String orginalName = (String)__names.nextElement();\n");
            bw.write("        if(orginalName.startsWith(__tmp)){\n");
            bw.write("            int index = orginalName.indexOf(\"].\");\n");
            bw.write("            if(index>__len){\n");
            bw.write("                String trueName = orginalName.substring(__len,index);\n");
            bw.write("                __beanIDs.add(trueName);\n");
            bw.write("            }\n");
            bw.write("        }\n");
            bw.write("    }\n");

            bw.write("    __len=0;\n");

            bw.write("    __it = __beanIDs.iterator();\n");
            bw.write("    while(__it.hasNext()){\n");
            bw.write("      String key = (String)__it.next();\n");
            bw.write("      __value = PrimitiveType.create(\""+p.getValueType()+
                        "\",request.getParameter(\""+name+"[\"+key+\"]\"));\n");
            bw.write("     "+name+".add(kye,__value);\n");
            bw.write("    }\n");
        }
    }

    /**
     * write a ListParameter;
     *
     * @param bw
     * @param p
     * @throws IOException
     */
    private void writeParam(BufferedWriter bw,ListParameter p) throws IOException {
        String name = p.getName();
        String type = p.getType();

        bw.write("    ");
        bw.write("java.util.List");
        bw.write(" ");
        bw.write(name);
        bw.write("=");

        int source = p.getSource();
        if(source == ParameterSource.NEW){
            bw.write("new "+type+"();\n");
        }else if(source == ParameterSource.SESSION){
            bw.write("("+type+")session.getAttribute(\""+name+"\");\n");
        }else if(source == ParameterSource.APPLICATION){
            bw.write("("+type+")application.getAttribute(\""+name+"\");\n");
        }else if(source == ParameterSource.CODE){
            bw.write("null;\n");
        }else if(source == ParameterSource.REQUEST){
            bw.write("new "+type+"();\n");
            bw.write("    __tmpValues = request.getParameterValues(\""+name+"\");\n");
            bw.write("    if(__tmpValues!=null&&__tmpValues.length>0){\n");
            bw.write("       for(int i=0;i<__tmpValues.length;i++){\n");
            bw.write("        __value = PrimitiveType.create(\""+p.getValueType()+"\",__tmpValues[i]);\n");
            bw.write("       "+name+".add(__value);\n");
            bw.write("       }\n");
            bw.write("    }\n");
        }
    }

    private void writeParam(BufferedWriter bw,SetParameter p) throws IOException {
        String name = p.getName();
        String type = p.getType();

        bw.write("    ");
        bw.write("java.util.Set");
        bw.write(" ");
        bw.write(name);
        bw.write("=");

        int source = p.getSource();
        if(source == ParameterSource.NEW){
            bw.write("new "+type+"();\n");
        }else if(source == ParameterSource.SESSION){
            bw.write("("+type+")session.getAttribute(\""+name+"\");\n");
        }else if(source == ParameterSource.APPLICATION){
            bw.write("("+type+")application.getAttribute(\""+name+"\");\n");
        }else if(source == ParameterSource.CODE){
            bw.write("null;\n");
        }else if(source == ParameterSource.REQUEST){
            bw.write("new "+type+"();\n");
            bw.write("    __tmpValues = request.getParameterValues(\""+name+"\");\n");
            bw.write("    if(__tmpValues!=null&&__tmpValues.length>0){\n");
            bw.write("       for(int i=0;i<__tmpValues.length;i++){\n");
            bw.write("        __value = PrimitiveType.create(\""+p.getValueType()+"\",__tmpValues[i]);\n");
            bw.write("       "+name+".add(__value);\n");
            bw.write("       }\n");
            bw.write("    }\n");
        }
    }

    private void writeParam(BufferedWriter bw,GenericParameter p) throws IOException {
        int source = p.getSource();
        switch(source){
            case ParameterSource.SESSION:
                bw.write("    "+p.getType()+" "+p.getName()+"=("+p.getType()+")session.getAttribute("+p.getName()+");\n");
                break;
            case ParameterSource.APPLICATION:
                bw.write("    "+p.getType()+" "+p.getName()+"=("+p.getType()+")application.getAttribute("+p.getName()+");\n");
                break;
            case ParameterSource.NEW:
                bw.write("    "+p.getType()+" "+p.getName()+"= new "+p.getType()+"();\n");
                break;
            default:
                bw.write("    "+p.getType()+" "+p.getName()+"= null;\n");
                break;
        }
    }

    /**
     *
     *
     * @param bw
     * @param da
     * @throws IOException
     */
    private void writeValidaters(BufferedWriter bw,DefaultAction da) throws IOException{
        List validaters = da.getParamValidaters();
        if(validaters!=null){
            int count = validaters.size();
            bw.write("    List errors = new ArrayList(8);\n");
            for(int i=0;i<count;i++){
                Object v = validaters.get(i);
                if(v instanceof BooleanValidater){
                    BooleanValidater bv = (BooleanValidater)v;
                    BooleanExpression be = ((BooleanValidater)v).getExpression();
                    if(be!=null){
                        bw.write("    if(!("+be.getSource()+")){\n");
                        bw.write("        String msg =ValidateMessages.getMessage(\""+bv.getMsgKey()+"\",null);\n");
                        bw.write("        if(msg!=null){\n");
                        bw.write("           errors.add(msg);\n");
                        bw.write("        }else{\n");
                        bw.write("           errors.add(\""+bv.getMessage()+"\");\n");
                        bw.write("        }\n");
                        bw.write("    }\n");
                    }
                }else if(v instanceof CustomerValidater){
                    CustomerValidater cv = (CustomerValidater)v;
                    bw.write("    if((__tmp="+cv.getClassName()+"."+cv.getMethodName()+"("+cv.getParamNamesString()+"))!=null){\n");
                    bw.write("        String msg =ValidateMessages.getMessage(__tmp,null);\n");
                    bw.write("        if(msg!=null){\n");
                    bw.write("           errors.add(msg);\n");
                    bw.write("        }else{\n");
                    bw.write("           errors.add(__tmp);\n");
                    bw.write("        }\n");

                    bw.write("    }\n");
                }else if(v instanceof RegExpValidater){
                    RegExpValidater cv = (RegExpValidater)v;
                    bw.write("    if(!java.util.regex.Pattern.matches(\""+cv.getRegexp()+"\","+cv.getParamName()+")){\n");
                    bw.write("        String msg =ValidateMessages.getMessage(\""+cv.getMsgKey()+"\",new Object[]{"+cv.getParamName()+"});\n");
                    bw.write("        if(msg!=null){\n");
                    bw.write("           errors.add(msg);\n");
                    bw.write("        }else{\n");
                    bw.write("           errors.add(\""+cv.getMessage()+"\");\n");
                    bw.write("        }\n");

                    bw.write("    }\n");
                }
            }
            bw.write("    if(errors.size()>0){\n");
            bw.write("        request.setAttribute(ERRORS,errors);\n");
            bw.write("        RequestDispatcher errorForw = request.getRequestDispatcher(ControllerServlet.ERROR_FORWARD);\n");
            bw.write("        errorForw.forward(request,response);\n");
            bw.write("        return;\n");
            bw.write("     }\n");
        }
    }

    /**
     * write all the models to the Buffer;
     *
     * @param bw
     * @param da
     * @throws IOException
     */
    private void writeModels(BufferedWriter bw,DefaultAction da) throws IOException {
        List models = da.getModels();
        int count = models.size();
        for(int i=0;i<count;i++){
            Model m = (Model)models.get(i);
            if(m instanceof BLModel){
                writeModel(bw,(BLModel)m,da);
            }else if(m instanceof StatlessSessionEJBModel){
                writeModel(bw,(StatlessSessionEJBModel)m,da);
            }else if(m instanceof StatfulSessionEJBModel){
                writeModel(bw,(StatfulSessionEJBModel)m,da);
            }else if(m instanceof GenericModel){
                writeModel(bw,(GenericModel)m,da);
            }
            //write code here to support other types of Model;
        }
    }

    /**
     * write a BLModel to the buffer;
     *
     * @param bw the wriite;
     * @param blModel the BLModel object;
     * @throws IOException
     */
    private void writeModel(BufferedWriter bw,BLModel blModel,DefaultAction da) throws IOException{
        String className = blModel.getClassName();
        String methodName = blModel.getMethodName();

        String returnName = blModel.getReturnName();
        String[] paramRefs = blModel.getParameterRefNames();

        bw.write("    {");

        //if the model shoulbe called conditional;
        BooleanExpression be = blModel.getExpression();
        if(be!=null){
            bw.write("    if(");
            bw.write(be.getSource());
            bw.write("){\n");
        }

        bw.write("    "+className+" blc=("+className+")blcPool.getBLC(\""+className+"\");\n" );
        if(returnName!=null){
            bw.write("    "+returnName+"=blc."+methodName+"(");
        }else{
            bw.write("    blc."+methodName+"(");
        }
        for(int i=0;i<paramRefs.length;i++){
            String paramName = paramRefs[i];
            Parameter param = da.getParameter(paramName);
            if(param instanceof ExprParameter){
                bw.write(((ExprParameter)param).getExprObject().getSource());
            }else{
                bw.write(paramRefs[i]);
            }
            if(i!=(paramRefs.length-1)){
                bw.write(',');
            }
        }
        bw.write(");\n");

        //if the model should be called conditionally;
        if(be!=null){
            bw.write("    }\n");
        }
        bw.write("    }\n");
    }

    private void writeModel(BufferedWriter bw,GenericModel model,DefaultAction da) throws IOException{
        String className = model.getClassName();
        String methodName = model.getMethodName();

        String returnName = model.getReturnName();
        String[] paramRefs = model.getParameterRefNames();

        bw.write("    {\n");

        //if the model shoulbe called conditional;
        BooleanExpression be = model.getExpression();
        if(be!=null){
            bw.write("    if(");
            bw.write(be.getSource());
            bw.write("){\n");
        }

        //check if the blc is thread safe;
        if(blcPool.getBLCRegistry().isThreadSafeBLC(className)){
            bw.write("    "+className+" blc=("+className+")blcPool.getBLC(\""+className+"\");\n" );
        }else{
            bw.write("    "+className+" blc=new "+className+"("+model.getConstructorSignature()+");\n" );
        }

        if(returnName!=null){
            bw.write("    "+returnName+"=blc."+methodName+"(");
        }else{
            bw.write("    blc."+methodName+"(");
        }

        for(int i=0;i<paramRefs.length;i++){
            String paramName = paramRefs[i];
            Parameter param = da.getParameter(paramName);
            if(param instanceof ExprParameter){
                bw.write(((ExprParameter)param).getExprObject().getSource());
            }else{
                bw.write(paramRefs[i]);
            }
            if(i!=(paramRefs.length-1)){
                bw.write(',');
            }
        }
        bw.write(");\n");

        //if the model should be called conditionally;
        if(be!=null){
            bw.write("    }\n");
        }
        bw.write("    }\n");
    }

    private void writeModel(BufferedWriter bw,StatlessSessionEJBModel model,DefaultAction da) throws IOException{
        String home = model.getHome();
        String remote = model.getRemote();
        String jndi = model.getJndi();
        String ejbMethod = model.getEjbMethod();
        String initialFactory = model.getInitialFactory();
        String providerUrl = model.getProviderURL();

        String returnName = model.getReturnName();
        String[] paramRefs = model.getParameterRefNames();

        bw.write("    {\n");

        //if the model shoulbe called conditional;
        BooleanExpression be = model.getExpression();
        if(be!=null){
            bw.write("    if(");
            bw.write(be.getSource());
            bw.write("){\n");
        }

        bw.write("    "+home+" home =(" +home+")EJBHomeCache.get(\""+jndi+"\");\n ");
        bw.write("    if(home==null){\n");
        bw.write("       synchronized(EJBHomeCache.class){\n"+
                "          home = EJBHomeCache.get(\""+jndi+"\n);"+
                "          if(home==null){\n"+
                "            Properties p = new Properties();\n");
        if(providerUrl!=null){
          bw.write("            p.setProperty(Context.PROVIDER_URL,providerURL);\n");
        }
        if(initialFactory!=null){
          bw.write("            p.setProperty(Context.INITIAL_CONTEXT_FACTORY,initialFactory);\n");
        }
        bw.write("            InitialContext ic = new InitialContext(p);\n"+
                "            home = ic.lookup(jndi);\n"+
                "            EJBHomeCache.add(\""+jndi+"\",home);\n"+
                "          }\n"+
                "    }\n"+
                "    "+remote +" remote=home.create();\n");

        if(returnName!=null){
            bw.write("    "+returnName+"=remote."+ejbMethod+"(");
        }else{
            bw.write("    remote."+ejbMethod+"(");
        }
        for(int i=0;i<paramRefs.length;i++){
            String paramName = paramRefs[i];
            Parameter param = da.getParameter(paramName);
            if(param instanceof ExprParameter){
                bw.write(((ExprParameter)param).getExprObject().getSource());
            }else{
                bw.write(paramRefs[i]);
            }
            if(i!=(paramRefs.length-1)){
                bw.write(',');
            }
        }
        bw.write(");\n");

        //if the model should be called conditionally;
        if(be!=null){
            bw.write("    }\n");
        }
        bw.write("    }\n");
    }

    private void writeModel(BufferedWriter bw,StatfulSessionEJBModel model,DefaultAction da) throws IOException{
        String home = model.getHome();
        String remote = model.getRemote();
        String jndi = model.getJndi();
        String ejbMethod = model.getEjbMethod();
        String initialFactory = model.getInitialFactory();
        String providerUrl = model.getProviderURL();
        String createMethod = model.getCreateMethod().getSignature();

        String returnName = model.getReturnName();
        String[] paramRefs = model.getParameterRefNames();

        bw.write("    {\n");

        //if the model shoulbe called conditional;
        BooleanExpression be = model.getExpression();
        if(be!=null){
            bw.write("    if(");
            bw.write(be.getSource());
            bw.write("){\n");
        }

        bw.write("    "+home+" home =(" +home+")EJBHomeCache.get(\""+jndi+"\");\n ");
        bw.write("    if(home==null){\n");
        bw.write("       synchronized(EJBHomeCache.class){\n"+
                "          home = EJBHomeCache.get(\""+jndi+"\n);"+
                "          if(home==null){\n"+
                "            Properties p = new Properties();\n");
        if(providerUrl!=null){
          bw.write("            p.setProperty(Context.PROVIDER_URL,providerURL);\n");
        }
        if(initialFactory!=null){
          bw.write("            p.setProperty(Context.INITIAL_CONTEXT_FACTORY,initialFactory);\n");
        }
        bw.write("            InitialContext ic = new InitialContext(p);\n"+
                "            home = ic.lookup(jndi);\n"+
                "            EJBHomeCache.add(\""+jndi+"\",home);\n"+
                "          }\n"+
                "    }\n"+
                "    "+remote +" remote=home."+createMethod+";\n");

        if(returnName!=null){
            bw.write("    "+returnName+"=remote."+ejbMethod+"(");
        }else{
            bw.write("    remote."+ejbMethod+"(");
        }
        for(int i=0;i<paramRefs.length;i++){
            String paramName = paramRefs[i];
            Parameter param = da.getParameter(paramName);
            if(param instanceof ExprParameter){
                bw.write(((ExprParameter)param).getExprObject().getSource());
            }else{
                bw.write(paramRefs[i]);
            }
            if(i!=(paramRefs.length-1)){
                bw.write(',');
            }
        }
        bw.write(");\n");

        //if the model should be called conditionally;
        if(be!=null){
            bw.write("    }\n");
        }
        
        bw.write("    }\n");
    }

    /**
     *
     *
     * @param bw
     * @param da
     * @throws IOException
     */
    protected void setParameters2Request(BufferedWriter bw,DefaultAction da) throws IOException {
        Map params = da.getParameters();
        Iterator it = params.keySet().iterator();
        while(it.hasNext()){
            String name = (String)it.next();
            Parameter p = (Parameter)params.get(name);
            int target = p.getTarget();
            String type = p.getType();
            if(target != ParameterTarget.NONE){
                String targetScope = ParameterTarget.getString(target);
                bw.write("    ");
                bw.write(targetScope);
                if(p instanceof ExprParameter){
                    String source = ((ExprParameter)p).getExprObject().getSource();

                    bw.write(".setAttribute(\""+name+"\","+source+");\n");
                }else{
                    String wrapper = PrimitiveType.getWrapperClass(type);
                    if(wrapper!=null){
                        bw.write(".setAttribute(\""+name+"\",new "+wrapper+"("+name+"));\n");
                    }else{
                        bw.write(".setAttribute(\""+name+"\","+name+");\n");
                    }
                }
            }
        }
    }

    /**
     * write dispatches;
     *
     * @param bw
     * @param da
     * @throws IOException
     */
    private void writeDispatches(BufferedWriter bw,DefaultAction da) throws IOException {
        List disps = da.getDispatches();
        int count = disps.size();

        bw.write("    javax.servlet.RequestDispatcher dispatcher;\n");

        Screen scr = da.getScreen();
        if(scr!=null){
            List preViews = scr.getPreViews();
            int views = preViews.size();
            for(int i=0;i<views;i++){
                bw.write("    dispatcher = request.getRequestDispatcher(\""
                        +((ScreenView)preViews.get(i)).getUri()+"\");\n");
                bw.write("    dispatcher.include(request,response);\n");
            }
        }

        for(int i=0;i<count;i++){
            AbstractDispatch disp = (AbstractDispatch)disps.get(i);
            String uri = disp.getUri();
            if(disp instanceof RedirectDispatch){
                RedirectDispatch rd = (RedirectDispatch)disp;

                List params = rd.getParams();
                int pc =params.size();

                String targetContext = rd.getTargetContext();

                int index = uri.indexOf("?");
                if(index<0){
                    uri+="?";
                }else if(index < uri.length()-1){
                    if(pc>0){
                        uri+="&";
                    }
                }

                BooleanExpression on = disp.getExpression();
                if(on!=null){
                    bw.write("    if(");
                    bw.write(on.getSource());
                    bw.write("){\n");

                    if(pc>0){

                        bw.write("      if(__tmpUri==null){\n");
                        bw.write("         __tmpUri = new StringBuffer(512);\n");
                        bw.write("      }\n");

                        if(targetContext == null){
                            bw.write("      __tmpUri.append(request.getContextPath());\n");
                        }else{
                            bw.write("      __tmpUri.append(\""+targetContext+"\");\n");
                        }

                        bw.write("      __tmpUri.append(\""+uri+"\");\n");

                        for(int j=0;j<pc;j++){
                            RedirectParameter rp = (RedirectParameter)params.get(j);

                            bw.write("      __tmpUri.append(\""+rp.getName()+"=\");\n");
                            StringExpression se = rp.getExpression();
                            if(se!=null){
                                bw.write("      __tmpUri.append("+se.getSource()+");\n");
                            }
                            if(j<pc-1){
                                bw.write("      __tmpUri.append(\"&\");\n");
                            }
                        }

                        bw.write("      response.sendRedirect(new String(__tmpUri));\n");
                    }else{

                        bw.write("    if(__tmpUri==null){\n");
                        bw.write("       __tmpUri = new StringBuffer(512);\n");
                        bw.write("    }\n");

                        if(targetContext == null){
                            bw.write("    __tmpUri.append(request.getContextPath());\n");
                        }else{
                            bw.write("    __tmpUri.append(\""+targetContext+"\");\n");
                        }
                        bw.write("    __tmpUri.append(\""+uri+"\");\n");

                        bw.write("    response.sendRedirect(new String(__tmpUri));\n");
                    }

                    bw.write("      return;\n");
                    bw.write("    }\n");
                }else{
                    if(pc>0){
                        bw.write("    if(__tmpUri==null){\n");
                        bw.write("         __tmpUri = new StringBuffer(512);\n");
                        bw.write("    }\n");

                        if(targetContext == null){
                            bw.write("      __tmpUri.append(request.getContextPath());\n");
                        }else{
                            bw.write("      __tmpUri.append(\""+targetContext+"\");\n");
                        }

                        bw.write("    __tmpUri.append(\""+uri+"\");\n");
                        for(int j=0;j<pc;j++){
                            RedirectParameter rp = (RedirectParameter)params.get(j);

                            bw.write("    __tmpUri.append(\""+rp.getName()+"=\");\n");
                            StringExpression se = rp.getExpression();
                            if(se!=null){
                                bw.write("    __tmpUri.append("+se.getSource()+");\n");
                            }
                            if(j<pc-1){
                                bw.write("    __tmpUri.append(\"&\");\n");
                            }
                        }

                        bw.write("    response.sendRedirect(new String(__tmpUri));\n");
                    }else{
                        bw.write("    if(__tmpUri==null){\n");
                        bw.write("       __tmpUri = new StringBuffer(512);\n");
                        bw.write("    }\n");

                        if(targetContext == null){
                            bw.write("    __tmpUri.append(request.getContextPath());\n");
                        }else{
                            bw.write("    __tmpUri.append(\""+targetContext+"\");\n");
                        }
                        bw.write("    __tmpUri.append(\""+uri+"\");\n");

                        bw.write("    response.sendRedirect(new String(__tmpUri));\n");
                    }
                    break;
                }
            }else if(disp instanceof IncludeDispatch){
                BooleanExpression on = disp.getExpression();
                if(on!=null){
                    bw.write("    if(\n");
                    bw.write(on.getSource());
                    bw.write("){\n");
                    bw.write("        dispatcher=request.getRequestDispatcher(\""+uri+"\");\n");
                    bw.write("        dispatcher.include(request,response);\n");
                    bw.write("    }\n");
                }else{
                    bw.write("    dispatcher=request.getRequestDispatcher(\""+uri+"\");\n");
                    bw.write("    dispatcher.include(request,response);\n");
                }
            }else if(disp instanceof ForwardDispatch){
                BooleanExpression on = disp.getExpression();
                if(on!=null){
                    bw.write("    if(");
                    bw.write(on.getSource());
                    bw.write("){\n");
                    bw.write("        dispatcher=request.getRequestDispatcher(\""+uri+"\");\n");
                    bw.write("        dispatcher.forward(request,response);\n");
                    bw.write("        return;\n");
                    bw.write("    }\n");
                }else{
                    bw.write("    dispatcher=request.getRequestDispatcher(\""+uri+"\");\n");
                    bw.write("    dispatcher.forward(request,response);\n");
                    break;
                }
            }
        }

        if(scr!=null){
            List postViews = scr.getPostViews();
            int views = postViews.size();
            for(int i=0;i<views;i++){
                bw.write("    dispatcher = request.getRequestDispatcher(\""
                        +((ScreenView)postViews.get(i)).getUri()+"\");\n");
                bw.write("    dispatcher.include(request,response);\n");
            }
        }
    }
}