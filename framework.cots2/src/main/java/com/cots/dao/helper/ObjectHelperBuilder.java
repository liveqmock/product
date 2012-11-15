/**
 *all rights reserved,@copyright 2003
 */
package com.cots.dao.helper;

import com.cots.util.JavaCompiler;
import com.cots.util.DirClassLoader;
import com.cots.bean.Bean;
import com.cots.bean.BeanProperty;
import com.cots.dao.DBType;
import com.cots.dao.IDAOAccessor;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.FileWriter;
import java.io.File;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-12-3
 * Time: 9:37:54
 */
public final class ObjectHelperBuilder {
    //the base dir to save the generated java source and class file;
    private String baseDir;
    //the class path to compile the generated java source file;
    private String classPath;
    private Logger log;
    //the type of the target database;
    private String dbType;
    //the dir classloader to load the compiled java source file;
    private DirClassLoader loader;

    public ObjectHelperBuilder(String baseDir, String classPath, String dbType) {
        char ch = baseDir.charAt(baseDir.length() - 1);
        if (ch != '\\' && ch != '/') {
            this.baseDir = baseDir + "/";
        } else {
            this.baseDir = baseDir;
        }
        this.classPath = classPath;
        this.dbType = dbType;
        log = Logger.getLogger(ObjectHelperBuilder.class);
    }

    /**
     * get the base directory to save the generated Helper source files and class files;
     *
     * @return
     */
    public String getBaseDir() {
        return baseDir;
    }

    /**
     * set the class loader to load the compiled class.
     * 
     * @param loader
     */
    public void setLoader(DirClassLoader loader) {
        this.loader = loader;
    }

    /**
     * get the classpath used to compile the java source files.
     *
     * @return
     */
    public String getClassPath() {
        return classPath;
    }

    /**
     * build a helper for a object, this object must be
     * an instance of cots-managed-beans.
     *
     * @param obj
     * @return an Object implements ObjectHelper interface.
     */
    public ObjectHelper build(Object obj, Bean bean) {
//        String cname = obj.getClass().getDisplayName();
//        String qname = cname + "_Helper";
//        String filePath = baseDir + qname.replace('.', '/') + ".java";

//        //create the directory if necessary;
//        new File(filePath).getParentFile().mkdirs();
//
//        if (!make(bean, filePath, cname, qname)) {
//            return null;
//        } else {
//            String compileInfo = compile(filePath);
//            if (compileInfo == null || compileInfo.length() < 1) {
//                return load(qname);
//            } else {  //compile error;
//                log.error(compileInfo);
//                return null;
//            }
//        }

        return build(obj.getClass(),bean);
    }

    /**
     * @param clz
     * @param bean
     * @return
     */
    public ObjectHelper build(Class clz, Bean bean) {
        String cname = clz.getName();
        String qname = cname + "_Helper";
        String filePath = baseDir + qname.replace('.', '/') + ".java";

        new File(filePath).getParentFile().mkdirs();

        if (!make(bean, filePath, cname, qname)) {
            return null;
        } else {
            String compileInfo = compile(filePath);
            if (compileInfo == null || compileInfo.length() < 1) {
                return (ObjectHelper)load(qname);
            } else {  //compile error;
                if (log.isEnabledFor(Priority.ERROR)) {
                    log.error(compileInfo);
                }
                return null;
            }
        }
    }

    /**
     * @param clz
     * @param bean
     * @return
     */
    public ResultSetHelper buildRSHelper(Class clz, Bean bean) {
        String cname = clz.getName();
        String qname = cname + "_RSHelper";
        String filePath = baseDir + qname.replace('.', '/') + ".java";

        new File(filePath).getParentFile().mkdirs();

        if (!makeRSHelper(bean, filePath, cname, qname)) {
            return null;
        } else {
            String compileInfo = compile(filePath);
            if (compileInfo == null || compileInfo.length() < 1) {
                return (ResultSetHelper)load(qname);
            } else {  //compile error;
                if (log.isEnabledFor(Priority.ERROR)) {
                    log.error(compileInfo);
                }
                return null;
            }
        }
    }

    /**
     * make
     *
     * @param bean
     * @param path
     * @param srcQName
     * @return
     */
    public boolean make(Bean bean, String path, String srcQName, String qname) {
        try {
            String cn;
            String pkg = null;
            int index = qname.lastIndexOf('.');
            if (index >= 0) {
                cn = qname.substring(index + 1);
                pkg = qname.substring(0, index);
            } else {
                cn = qname;
            }
            BufferedWriter w = new BufferedWriter(new FileWriter(path));
            if (pkg != null) {
                w.write("package " + pkg + ";\n");
            }
            w.write("\n\n");

            w.write("import com.cots.dao.DAO;\n");
            w.write("import com.cots.dao.helper.ObjectHelperFactory;\n");
            w.write("import com.cots.dao.helper.ObjectHelper;\n");

            w.write("import java.sql.*;\n");
            w.write("import java.util.*;\n");
            w.write("import java.io.*;\n");

            w.write("\n\n");
            w.write("public final class " + cn + " implements com.cots.dao.helper.ObjectHelper{\n");
            w.write("    private ObjectHelperFactory ohf;\n");
            w.write("\n");
            writeSetObjectHelperFactory(w);
            w.write("\n");
            writeGetObjectHelperFactory(w);
            w.write("\n");
            writeInsert(w, bean, srcQName);
            w.write("\n");
            writeInsertBatch(w, bean, srcQName);
            w.write("\n");
            writeDelete(w, bean, srcQName);
            w.write("\n");
            writeDeleteBatch(w, bean, srcQName);
            w.write("\n");
            writeUpdate(w, bean, srcQName);
            w.write("\n");
            writeUpdateBatch(w, bean, srcQName);
            w.write("\n");
            writePopulate(w, bean, srcQName);
            w.write("\n");
            writePopulateObject(w, bean, srcQName);
            w.write("\n");
            writePopulateBatch(w, bean, srcQName);
            w.write("\n");

            writePopulateBatch2(w, bean, srcQName);
            w.write("\n");

            writeExpand(w, bean, srcQName);
            w.write("\n");
            w.write("}\n");
            w.flush();
            w.close();
            return true;
        } catch (IOException e) {
            if (log.isEnabledFor(Priority.ERROR)) {
                log.error("failed to write the ObjectHelper java source file", e);
            }
            return false;
        }
    }

    public boolean makeRSHelper(Bean bean, String path, String srcQName, String qname) {
        try {
            String cn;
            String pkg = null;
            int index = qname.lastIndexOf('.');
            if (index >= 0) {
                cn = qname.substring(index + 1);
                pkg = qname.substring(0, index);
            } else {
                cn = qname;
            }
            BufferedWriter w = new BufferedWriter(new FileWriter(path));
            if (pkg != null) {
                w.write("package " + pkg + ";\n");
            }
            w.write("\n\n");

            w.write("import com.cots.dao.DAO;\n");
            w.write("import com.cots.dao.helper.ObjectHelperFactory;\n");
            w.write("import com.cots.dao.helper.ObjectHelper;\n");

            w.write("import java.sql.*;\n");
            w.write("import java.util.*;\n");
            w.write("import java.io.*;\n");

            w.write("\n\n");
            w.write("public final class " + cn + " implements com.cots.dao.helper.ResultSetHelper{\n");
            writePopulate(w, bean, srcQName);
            w.write("\n");
            writePopulateObject(w, bean, srcQName);
            w.write("\n");
            writePopulateBatch(w, bean, srcQName);
            w.write("\n");

            writePopulateBatch2(w, bean, srcQName);
            w.write("\n");

            w.write("\n");
            w.write("}\n");
            w.flush();
            w.close();
            return true;
        } catch (IOException e) {
            if (log.isEnabledFor(Priority.ERROR)) {
                log.error("failed to write the ObjectHelper java source file", e);
            }
            return false;
        }
    }

    private void writeSetObjectHelperFactory(BufferedWriter w) throws IOException{
        w.write("  public void setOwnerFactory(ObjectHelperFactory ohf){\n");
        w.write("    this.ohf = ohf;\n");
        w.write("  }\n");
    }

    private void writeGetObjectHelperFactory(BufferedWriter w) throws IOException{
        w.write("  public ObjectHelperFactory getOwnerFactory(){\n");
        w.write("    return this.ohf;\n");
        w.write("  }\n");
    }

    private void writeInsert(BufferedWriter w, Bean bean, String srcQName) throws IOException {
        StringBuffer sb = new StringBuffer(1024);
        String tableName = bean.getTableName();
        List cols = bean.getPersistentCols();
        int count = cols.size();
        w.write("  public int insert(Object obj,Connection conn) throws SQLException{\n");
        writeInsertParent(w,bean,"obj");
        w.write("    " + srcQName + " bean=(" + srcQName + ")obj;\n");
        w.write("    StringBuffer sql = new StringBuffer();\n");
        w.write("    sql.append(\"insert into \");\n");

        writeTableName(w, tableName);

        w.write("    sql.append(\"(\");\n");
        for (int i = 0; i < count; i++) {
            BeanProperty p = (BeanProperty) cols.get(i);
            String colName = p.getColumnName();

            writeColName(w, colName, false);

            sb.append("?");
            if (i != count - 1) {
                w.write("    sql.append(\",\");\n");
                sb.append(",");
            }
        }
        w.write("    sql.append(\") values(\");\n");
        w.write("    sql.append(\"" + new String(sb) + "\");\n");

        w.write("    sql.append(\")\");\n");

        if (bean.hasBLOBCols()) {
            w.write("    byte[] bytes;\n");
        }
        if (bean.hasCLOBCols()) {
            w.write("    char[] chars;\n");
        }

        w.write("    java.sql.PreparedStatement stmt = conn.prepareStatement(new String(sql));\n");
        w.write("    stmt = conn.prepareStatement(new String(sql));\n");
        w.write("    try{\n");

        writeCols(w, cols, 1);
        w.write("      return stmt.executeUpdate();\n");
        w.write("    }finally{\n");
        w.write("      stmt.close();\n");
        w.write("    }\n");
        w.write("  }\n");
    }

    private void writeInsertBatch(BufferedWriter w, Bean bean, String srcQName) throws IOException {
        StringBuffer sb = new StringBuffer(1024);
        String tableName = bean.getTableName();
        List cols = bean.getPersistentCols();
        int count = cols.size();
        w.write("  public int insertBatch(Object[] objs,Connection conn) throws SQLException{\n");
//        w.write("    if(objs.getClass().isAssignableFrom(" + srcQName + "[].class)){\n");
        w.write("      StringBuffer sql = new StringBuffer();\n");
        w.write("      sql.append(\"insert into \");\n");

        writeTableName(w, tableName);

        w.write("      sql.append(\"(\");\n");
        for (int i = 0; i < count; i++) {
            BeanProperty p = (BeanProperty) cols.get(i);
            String colName = p.getColumnName();

            writeColName(w, colName, false);

            sb.append("?");
            if (i != count - 1) {
                w.write("      sql.append(\",\");\n");
                sb.append(",");
            }
        }
        w.write("      sql.append(\") values(\");\n");
        w.write("      sql.append(\"" + new String(sb) + "\");\n");

        w.write("      sql.append(\")\");\n");

        w.write("      int count=0;\n");
        if (bean.hasBLOBCols()) {
            w.write("      byte[] bytes;\n");
        }

        if (bean.hasCLOBCols()) {
            w.write("      char[] chars;\n");
        }

        w.write("      java.sql.PreparedStatement stmt = conn.prepareStatement(new String(sql));\n");
        w.write("      stmt = conn.prepareStatement(new String(sql));\n");
        w.write("      try{\n");
        w.write("        for(int i=0;i<objs.length;i++){\n");
        w.write("          " + srcQName + " bean=(" + srcQName + ")objs[i];\n");
        writeInsertParent(w,bean,"bean");
        writeCols(w, cols, 2);
        w.write("          count+=stmt.executeUpdate();\n");
        w.write("        }\n");
        w.write("        return count;\n");
        w.write("      }finally{\n");
        w.write("        stmt.close();\n");
        w.write("      }\n");
//        w.write("    }else{\n");
//        w.write("      throw new SQLException(\"not cots managed object\");\n");
//        w.write("    }\n");
        w.write("  }\n");
    }

    private void writeDelete(BufferedWriter w, Bean bean, String srcQName) throws IOException {
        String tableName = bean.getTableName();
        BeanProperty[] cols = bean.getKeyColumns();

        w.write("  public int delete(Object obj,Connection conn,String addWhere) throws SQLException{\n");
//        w.write("    if(obj instanceof " + srcQName + "){\n");
        //if no key specified;
        BeanProperty[] keys = bean.getKeyColumns();
        if(keys==null || keys.length==0){
            w.write("    throw new SQLException(\"no key specified for this bean class.\");");
            w.write("}\n");
            return;
        }

        w.write("      " + srcQName + " bean=(" + srcQName + ")obj;\n");

        w.write("      StringBuffer sql = new StringBuffer();\n");
        w.write("      sql.append(\"delete from \");\n");

        writeTableName(w, tableName);
        w.write("      sql.append(\" where \");\n");

        for (int i = 0; i < cols.length; i++) {
            if (i != 0) {
                w.write("      sql.append(\" and \");\n");
            }

            String colName = cols[i].getColumnName();

            writeColName(w, colName, true);
        }

        w.write("      if(addWhere!=null)sql.append(\" and \").append(addWhere);\n");


        if (bean.hasBLOBCols()) {
            w.write("      byte[] bytes;\n");
        }

        if (bean.hasCLOBCols()) {
            w.write("      char[] chars;\n");
        }

        w.write("      java.sql.PreparedStatement stmt = conn.prepareStatement(new String(sql));\n");
        w.write("      stmt = conn.prepareStatement(new String(sql));\n");

        w.write("      try{\n");
        for (int i = 0; i < cols.length; i++) {
            writeCol(w, cols[i], i + 1, 1);
        }

        w.write("        int count =  stmt.executeUpdate();\n");
        writeDeleteParent(w,bean,"obj");
        w.write("        return count;\n");
        w.write("      }finally{\n");
        w.write("        stmt.close();\n");
        w.write("      }\n");

//        w.write("    }else{\n");
//        w.write("      throw new SQLException(\"not cots managed object\");\n");
//        w.write("    }\n");
        w.write("  }\n");
    }

    private void writeDeleteBatch(BufferedWriter w, Bean bean, String srcQName) throws IOException {
        String tableName = bean.getTableName();
        BeanProperty[] cols = bean.getKeyColumns();

        w.write("  public int deleteBatch(Object[] objs,Connection conn,String addWhere) throws SQLException{\n");
//        w.write("    if(objs.getClass().isAssignableFrom(" + srcQName + "[].class)){\n");
        //if no key specified;
        BeanProperty[] keys = bean.getKeyColumns();
        if(keys==null || keys.length==0){
            w.write("    throw new SQLException(\"no key specified for this bean class.\");");
            w.write("}\n");
            return;
        }

        w.write("      StringBuffer sql = new StringBuffer();\n");
        w.write("      sql.append(\"delete from \");\n");

        writeTableName(w, tableName);
        w.write("      sql.append(\" where \");\n");

        for (int i = 0; i < cols.length; i++) {
            if (i != 0) {
                w.write("      sql.append(\" and \");\n");
            }
            String colName = cols[i].getColumnName();

            writeColName(w, colName, true);
        }
        w.write("      if(addWhere!=null)sql.append(\" and \").append(addWhere);\n");
        w.write("      java.sql.PreparedStatement stmt = conn.prepareStatement(new String(sql));\n");
        w.write("      stmt = conn.prepareStatement(new String(sql));\n");
        w.write("      int count =0;\n");
        if (bean.hasBLOBCols()) {
            w.write("      byte[] bytes;\n");
        }

        if (bean.hasCLOBCols()) {
            w.write("      char[] chars;\n");
        }

        w.write("      try{\n");
        w.write("        for(int i=0;i<objs.length;i++){\n");
        w.write("          " + srcQName + " bean=(" + srcQName + ")objs[i];\n");
        for (int i = 0; i < cols.length; i++) {
            writeCol(w, cols[i], i + 1, 2);
        }
        w.write("          count+=stmt.executeUpdate();\n");
        writeDeleteParent(w,bean,"bean");
        w.write("        }\n");
        w.write("        return count;\n");
        w.write("      }finally{\n");
        w.write("        stmt.close();\n");
        w.write("      }\n");
//        w.write("    }else{\n");
//        w.write("      throw new SQLException(\"not cots managed object\");\n");
//        w.write("    }\n");
        w.write("  }\n");
    }

    private void writeUpdate(BufferedWriter w, Bean bean, String srcQName) throws IOException {
        String tableName = bean.getTableName();
        BeanProperty[] keyCols = bean.getKeyColumns();
        BeanProperty[] nmlCols = bean.getGeneralColumns();

        w.write("  public int update(Object obj,Connection conn,String addWhere) throws SQLException{\n");
        //if no key specified;
        BeanProperty[] keys = bean.getKeyColumns();
        if(keys==null || keys.length==0){
            w.write("    throw new SQLException(\"no key specified for this bean class.\");");
            w.write("}\n");
            return;
        }
        w.write("      " + srcQName + " bean=(" + srcQName + ")obj;\n");
        w.write("      StringBuffer sql = new StringBuffer();\n");
        w.write("      sql.append(\"update \");\n");

        writeTableName(w, tableName);

        w.write("      sql.append(\" set \");\n");

        for (int i = 0; i < nmlCols.length; i++) {
            if (i != 0) {
                w.write("      sql.append(\",\");\n");
            }
            String colName = nmlCols[i].getColumnName();

            writeColName(w, colName, true);
        }

        w.write("      sql.append(\" where \");\n");

        for (int i = 0; i < keyCols.length; i++) {
            if (i != 0) {
                w.write("      sql.append(\" and \");\n");
            }

            String colName = keyCols[i].getColumnName();

            writeColName(w, colName, true);
        }

        w.write("      if(addWhere!=null)sql.append(\" and \").append(addWhere);\n");
        if (bean.hasBLOBCols()) {
            w.write("      byte[] bytes;\n");
        }

        if (bean.hasCLOBCols()) {
            w.write("      char[] chars;\n");
        }

        w.write("      java.sql.PreparedStatement stmt = conn.prepareStatement(new String(sql));\n");
        w.write("      stmt = conn.prepareStatement(new String(sql));\n");
        w.write("      try{\n");

        for (int i = 0; i < nmlCols.length; i++) {
            writeCol(w, nmlCols[i], i + 1, 1);
        }

        for (int i = 0; i < keyCols.length; i++) {
            writeCol(w, keyCols[i], nmlCols.length + i + 1, 1);
        }

        w.write("        int count = stmt.executeUpdate();\n");
        writeUpdateParent(w,bean,"obj");
        w.write("        return count;\n");
        w.write("      }finally{\n");
        w.write("        stmt.close();\n");
        w.write("      }\n");
//        w.write("    }else{\n");
//        w.write("      throw new SQLException(\"not cots managed object\");\n");
//        w.write("    }\n");
        w.write("  }\n");
    }

    /**
     * @param w
     * @param bean
     * @param srcQName
     * @throws IOException
     */
    private void writeUpdateBatch(BufferedWriter w, Bean bean, String srcQName) throws IOException {
        String tableName = bean.getTableName();
        BeanProperty[] keyCols = bean.getKeyColumns();
        BeanProperty[] nmlCols = bean.getGeneralColumns();

        w.write("  public int updateBatch(Object[] objs,Connection conn,String addWhere) throws SQLException{\n");
//        w.write("    if(objs.getClass().isAssignableFrom(" + srcQName + "[].class)){\n");

        //if no key specified;
        BeanProperty[] keys = bean.getKeyColumns();
        if(keys==null || keys.length==0){
            w.write("    throw new SQLException(\"no key specified for this bean class.\");");
            w.write("}\n");
            return;
        }

        w.write("      StringBuffer sql = new StringBuffer();\n");
        w.write("      sql.append(\"update \");\n");

        writeTableName(w, tableName);

        for (int i = 0; i < nmlCols.length; i++) {
            if (i != 0) {
                w.write("      sql.append(\",\");\n");
            }
            String colName = nmlCols[i].getColumnName();

            writeColName(w, colName, true);
        }

        w.write("      sql.append(\" where \");\n");

        for (int i = 0; i < keyCols.length; i++) {
            if (i != 0) {
                w.write("      sql.append(\" and \");\n");
            }
            String colName = keyCols[i].getColumnName();

            writeColName(w, colName, true);
        }
        w.write("      if(addWhere!=null)sql.append(\" and \").append(addWhere);\n");

        w.write("      int count=0;\n");
        if (bean.hasBLOBCols()) {
            w.write("      byte[] bytes;\n");
        }

        if (bean.hasCLOBCols()) {
            w.write("      char[] chars;\n");
        }

        w.write("      java.sql.PreparedStatement stmt = conn.prepareStatement(new String(sql));\n");
        w.write("      stmt = conn.prepareStatement(new String(sql));\n");
        w.write("      try{\n");
        w.write("        for(int i=0;i<objs.length;i++){\n");
        w.write("          " + srcQName + " bean=(" + srcQName + ")objs[i];\n");

        for (int i = 0; i < nmlCols.length; i++) {
            writeCol(w, nmlCols[i], i + 1, 2);
        }

        for (int i = 0; i < keyCols.length; i++) {
            writeCol(w, keyCols[i], nmlCols.length + i + 1, 2);
        }
        w.write("          count+=stmt.executeUpdate();\n");
        writeUpdateParent(w,bean,"bean");
        w.write("        }\n");
        w.write("        return count;\n");
        w.write("      }finally{\n");
        w.write("        stmt.close();\n");
        w.write("      }\n");
//        w.write("    }else{\n");
//        w.write("      throw new SQLException(\"not cots managed object\");\n");
//        w.write("    }\n");
        w.write("  }\n");
    }

    private void writePopulate(BufferedWriter w, Bean bean, String srcQName) throws IOException {
        List cols = bean.getPersistentCols();

        w.write("  public Object populate(ResultSet rs,DAO dao) throws SQLException{\n");

        if (bean.hasBLOBCols()) {
            w.write("      ByteArrayOutputStream bytesOS;\n");
            w.write("      InputStream is;\n");
        }

        if (bean.hasCLOBCols()) {
            w.write("      CharArrayWriter charsOS;\n");
            w.write("      Reader rd;\n");
        }

        w.write("    " + srcQName + " bean=new " + srcQName + "();\n");
        int count = cols.size();
        for (int i = 0; i < count; i++) {
            writePopCol(w, (BeanProperty) cols.get(i), false);
        }

        if(IDAOAccessor.class.isAssignableFrom(bean.getBeanClass())){
            w.write("      bean.setDAO(dao);\n");
        }

        w.write("    return bean;\n");
        w.write("  }\n");
    }

    private void writePopulateObject(BufferedWriter w, Bean bean, String srcQName) throws IOException {
        List cols = bean.getPersistentCols();

        w.write("  public int populate(Object obj,ResultSet rs,DAO dao) throws SQLException{\n");

        if (bean.hasBLOBCols()) {
            w.write("      ByteArrayOutputStream bytesOS;\n");
            w.write("      InputStream is;\n");
        }

        if (bean.hasCLOBCols()) {
            w.write("      CharArrayWriter charsOS;\n");
            w.write("      Reader rd;\n");
        }

        w.write("    if(obj instanceof " + srcQName + "){\n");
        w.write("      " + srcQName + " bean =(" + srcQName + ")obj;\n");
        int count = cols.size();
        for (int i = 0; i < count; i++) {
            writePopCol(w, (BeanProperty) cols.get(i), true);
        }

        if(IDAOAccessor.class.isAssignableFrom(bean.getBeanClass())){
            w.write("      bean.setDAO(dao);\n");
        }
        w.write("      return 1;\n");
        w.write("    }else{\n");
        w.write("      throw new SQLException(\"not cots managed beans\");\n");
        w.write("   }\n");
        w.write("  }\n");
    }

    private void writePopulateBatch(BufferedWriter w, Bean bean, String srcQName) throws IOException {
        List cols = bean.getPersistentCols();

        w.write("  public List populateBatch(ResultSet rs,DAO dao) throws SQLException{\n");

        if (bean.hasBLOBCols()) {
            w.write("    ByteArrayOutputStream bytesOS;\n");
            w.write("    InputStream is;\n");
        }

        if (bean.hasCLOBCols()) {
            w.write("    CharArrayWriter charsOS;\n");
            w.write("    Reader rd;\n");
        }
        w.write("    rs.setFetchSize(50);\n");

        w.write("    ArrayList al = new ArrayList(100);\n");
        w.write("    while(rs.next()){\n");
        w.write("      " + srcQName + " bean=new " + srcQName + "();\n");
        int count = cols.size();
        for (int i = 0; i < count; i++) {
            writePopCol(w, (BeanProperty) cols.get(i), true);
        }

        if(IDAOAccessor.class.isAssignableFrom(bean.getBeanClass())){
            w.write("      bean.setDAO(dao);\n");
        }

        w.write("      al.add(bean);\n");
        w.write("    }\n");
        w.write("    return al;\n");
        w.write("  }\n");
    }

    private void writePopulateBatch2(BufferedWriter w, Bean bean, String srcQName) throws IOException {
        List cols = bean.getPersistentCols();

        w.write("  public int populateBatch(List list,ResultSet rs,DAO dao) throws SQLException{\n");

        if (bean.hasBLOBCols()) {
            w.write("    ByteArrayOutputStream bytesOS;\n");
            w.write("    InputStream is;\n");
        }

        if (bean.hasCLOBCols()) {
            w.write("    CharArrayWriter charsOS;\n");
            w.write("    Reader rd;\n");
        }

        w.write("    rs.setFetchSize(50);\n");


        w.write("    int count=0;\n");
        w.write("    while(rs.next()){\n");
        w.write("      " + srcQName + " bean=new " + srcQName + "();\n");
        int count = cols.size();
        for (int i = 0; i < count; i++) {
            writePopCol(w, (BeanProperty) cols.get(i), true);
        }

        if(IDAOAccessor.class.isAssignableFrom(bean.getBeanClass())){
            w.write("      bean.setDAO(dao);\n");
        }

        w.write("      list.add(bean);\n");
        w.write("      count++;\n");
        w.write("    }\n");
        w.write("    return count;\n");
        w.write("  }\n");
    }

    private void writeExpand(BufferedWriter w, Bean bean, String srcQName) throws IOException {
        String tableName = bean.getTableName();
        BeanProperty[] keyCols = bean.getKeyColumns();

        w.write("  public int expand(Object obj,Connection conn,DAO dao,String addWhere) throws SQLException{\n");
        w.write("    if(obj instanceof " + srcQName + "){\n");
        w.write("      " + srcQName + " bean=(" + srcQName + ")obj;\n");
        w.write("      StringBuffer sql = new StringBuffer();\n");
        w.write("      sql.append(\"select * from \");\n");

        writeTableName(w, tableName);

        w.write("      sql.append(\" where \");\n");

        for (int i = 0; i < keyCols.length; i++) {
            if (i != 0) {
                w.write("      sql.append(\" and \");\n");
            }

            String colName = keyCols[i].getColumnName();

            writeColName(w, colName, true);
        }
        w.write("      if(addWhere!=null)sql.append(\" and \").append(addWhere);\n");

        if (bean.hasBLOBCols()) {
            w.write("      byte[] bytes;\n");
        }

        if (bean.hasCLOBCols()) {
            w.write("      char[] chars;\n");
        }

        w.write("      java.sql.PreparedStatement stmt = conn.prepareStatement(new String(sql));\n");
        w.write("      stmt = conn.prepareStatement(new String(sql));\n");
        w.write("      try{\n");

        for (int i = 0; i < keyCols.length; i++) {
            writeCol(w, keyCols[i], i + 1, 1);
        }

        w.write("        ResultSet rs = stmt.executeQuery();\n");
        w.write("        if(rs.next()){\n");
        w.write("          return populate(bean,rs,dao);\n");
        w.write("        }else{\n");
        w.write("          return 0;\n");
        w.write("        }\n");
        w.write("      }finally{\n");
        w.write("        stmt.close();\n");
        w.write("      }\n");
        w.write("    }else{\n");
        w.write("      throw new SQLException(\"not cots managed object\");\n");
        w.write("    }\n");
        w.write("  }\n");
    }

    private void writeCols(BufferedWriter w, List cols, int indent) throws IOException {
        int count = cols.size();
        for (int i = 0; i < count; i++) {
            writeCol(w, (BeanProperty) cols.get(i), i + 1, indent);
        }
    }

    private void writeCol(BufferedWriter w, BeanProperty p, int index, int indent) throws IOException {
        String type = p.getType();
        for (int i = 0; i < indent; i++) {
            w.write("  ");
        }

        if ("int".equals(type)) {
            w.write("      stmt.setInt(" + index + ",bean." + p.getGetMethodName() + "());\n");
        } else if ("byte".equals(type)) {
            w.write("      stmt.setByte(" + index + ",bean." + p.getGetMethodName() + "());\n");
        } else if ("short".equals(type)) {
            w.write("      stmt.setShort(" + index + ",bean." + p.getGetMethodName() + "());\n");
        } else if ("long".equals(type)) {
            w.write("      stmt.setLong(" + index + ",bean." + p.getGetMethodName() + "());\n");
        } else if ("float".equals(type)) {
            w.write("      stmt.setFloat(" + index + ",bean." + p.getGetMethodName() + "());\n");
        } else if ("double".equals(type)) {
            w.write("      stmt.setDouble(" + index + ",bean." + p.getGetMethodName() + "());\n");
        } else if ("String".equals(type)) {
            w.write("      stmt.setString(" + index + ",bean." + p.getGetMethodName() + "());\n");
        } else if ("char".equals(type)) {
            w.write("      stmt.setChar(" + index + ",bean." + p.getGetMethodName() + "());\n");
        } else if ("java.util.Date".equals(type)) {
            w.write("      stmt.setDate(" + index + ",new java.sql.Date(bean." + p.getGetMethodName() + "().getTime()));\n");
        } else if ("java.sql.Date".equals(type)) {
            w.write("      stmt.setDate(" + index + ",bean." + p.getGetMethodName() + "());\n");
        } else if ("java.sql.Timestamp".equals(type)) {
            w.write("      stmt.setTimestamp(" + index + ",bean." + p.getGetMethodName() + "());\n");
        } else if ("java.sql.Time".equals(type)) {
            w.write("      stmt.setTime(" + index + ",bean." + p.getGetMethodName() + "());\n");
        } else if ("java.sql.BigDecimal".equals(type)) {
            w.write("      stmt.setBigDecimal(" + index + ",bean." + p.getGetMethodName() + "());\n");
        } else if ("byte[]".equals(type)) {
            w.write("      bytes=bean." + p.getGetMethodName() + "();\n");
            for (int i = 0; i < indent; i++) {
                w.write("  ");
            }
            w.write("      if(bytes!=null){\n");
            for (int i = 0; i < indent; i++) {
                w.write("  ");
            }
            w.write("        stmt.setBinaryStream(" + index + ",new ByteArrayInputStream(bytes),bytes.length);\n");
            for (int i = 0; i < indent; i++) {
                w.write("  ");
            }
            w.write("      }else{\n");
            for (int i = 0; i < indent; i++) {
                w.write("  ");
            }
            w.write("        stmt.setNull(" + index + ",java.sql.Types.BLOB);\n");
            for (int i = 0; i < indent; i++) {
                w.write("  ");
            }
            w.write("      }\n");
        } else if ("char[]".equals(type)) {
            w.write("      chars=bean." + p.getGetMethodName() + "();\n");
            for (int i = 0; i < indent; i++) {
                w.write("  ");
            }
            w.write("      if(chars!=null){\n");
            for (int i = 0; i < indent; i++) {
                w.write("  ");
            }
            w.write("        stmt.setCharacterStream(" + index + ",new CharArrayReader(chars),chars.length);\n");
            for (int i = 0; i < indent; i++) {
                w.write("  ");
            }
            w.write("      }else{\n");
            for (int i = 0; i < indent; i++) {
                w.write("  ");
            }
            w.write("        stmt.setNull(" + index + ",java.sql.Types.CLOB);\n");
            for (int i = 0; i < indent; i++) {
                w.write("  ");
            }
            w.write("      }\n");
        }
    }

    /**
     * @param w
     * @param p
     * @param indent
     * @throws IOException
     */
    private void writePopCol(BufferedWriter w, BeanProperty p, boolean indent) throws IOException {
        String type = p.getType();
        String colName = p.getColumnName();
        if (indent) {
            w.write("  ");
        }
        if ("int".equals(type)) {
            w.write("    bean." + p.getSetMethodName() + "(rs.getInt(\"" + colName + "\"));\n");
        } else if ("byte".equals(type)) {
            w.write("    bean." + p.getSetMethodName() + "(rs.getByte(\"" + colName + "\"));\n");
        } else if ("short".equals(type)) {
            w.write("    bean." + p.getSetMethodName() + "(rs.getShort(\"" + colName + "\"));\n");
        } else if ("long".equals(type)) {
            w.write("    bean." + p.getSetMethodName() + "(rs.getLong(\"" + colName + "\"));\n");
        } else if ("float".equals(type)) {
            w.write("    bean." + p.getSetMethodName() + "(rs.getFloat(\"" + colName + "\"));\n");
        } else if ("double".equals(type)) {
            w.write("    bean." + p.getSetMethodName() + "(rs.getDouble(\"" + colName + "\"));\n");
        } else if ("String".equals(type)) {
            w.write("    bean." + p.getSetMethodName() + "(rs.getString(\"" + colName + "\"));\n");
        } else if ("char".equals(type)) {
            w.write("    bean." + p.getSetMethodName() + "(rs.getChar(\"" + colName + "\"));\n");
        } else if ("java.util.Date".equals(type)) {
            w.write("    bean." + p.getSetMethodName() + "(new java.util.Date(rs.getDate(\"" + colName + "\").getTime()));\n");
        } else if ("java.sql.Date".equals(type)) {
            w.write("    bean." + p.getSetMethodName() + "(rs.getDate(\"" + colName + "\"));\n");
        } else if ("java.sql.Timestamp".equals(type)) {
            w.write("    bean." + p.getSetMethodName() + "(rs.getTimestamp(\"" + colName + "\"));\n");
        } else if ("java.sql.Time".equals(type)) {
            w.write("    bean." + p.getSetMethodName() + "(rs.getTime(\"" + colName + "\"));\n");
        } else if ("java.sql.BigDecimal".equals(type)) {
            w.write("    bean." + p.getSetMethodName() + "(rs.getBigDecimal(\"" + colName + "\"));\n");
        } else if ("byte[]".equals(type)) {
            w.write("    bytesOS = new ByteArrayOutputStream(1024);\n");
            if (indent) {
                w.write("  ");
            }
            w.write("    is = rs.getBinaryStream(\"" + colName + "\");\n");
            if (indent) {
                w.write("  ");
            }
            w.write("    if(is!=null){\n");
            if (indent) {
                w.write("  ");
            }
            w.write("      try{\n");
            if (indent) {
                w.write("  ");
            }
            w.write("      int r;\n");

            if (indent) {
                w.write("  ");
            }
            w.write("      while((r = is.read())>=0){\n");
            if (indent) {
                w.write("  ");
            }
            w.write("         bytesOS.write(r);\n");
            if (indent) {
                w.write("  ");
            }
            w.write("      }\n");
            if (indent) {
                w.write("  ");
            }
            w.write("      }catch(java.io.IOException e){ throw new SQLException(\"read BLOB error\");}\n");

            if (indent) {
                w.write("  ");
            }
            w.write("    }\n");
            if (indent) {
                w.write("  ");
            }
            w.write("    bean." + p.getSetMethodName() + "(bytesOS.toByteArray());\n");
        } else if ("char[]".equals(type)) {
            w.write("    charsOS = new CharArrayWriter(1024);\n");
            if (indent) {
                w.write("  ");
            }
            w.write("    rd = rs.getCharacterStream(\"" + colName + "\");\n");
            if (indent) {
                w.write("  ");
            }
            w.write("    if(rd!=null){\n");
            w.write("      try{\n");
            if (indent) {
                w.write("  ");
            }
            if (indent) {
                w.write("  ");
            }
            w.write("      int r;\n");

            if (indent) {
                w.write("  ");
            }
            w.write("      while((r = rd.read())>=0){\n");
            if (indent) {
                w.write("  ");
            }
            w.write("         charsOS.write((char)r);\n");
            if (indent) {
                w.write("  ");
            }
            w.write("      }\n");
            w.write("      }catch(java.io.IOException e){ throw new SQLException(\"read CLOB error\");}\n");
            if (indent) {
                w.write("  ");
            }
            w.write("    }\n");
            if (indent) {
                w.write("  ");
            }
            w.write("    bean." + p.getSetMethodName() + "(charsOS.toCharArray());\n");
        }
    }

    /**
     * compile the ObjectHelper class.
     *
     * @param filePath the path of the java source file.
     * @return  null if the source file is compiled without error. error messages otherwise.
     */
    private String compile(String filePath) {
        return JavaCompiler.compile(filePath, classPath);
    }

    /**
     * load and instantation? an ObjectHelper/ResultSetHelper object.
     *
     * @param qname the qualified displayName of the Helper class.
     * @return an ObjectHelper object.
     */
    private Object load(String qname) {
        Object obj = null;
        try {
            obj = loader.loadClass(qname).newInstance();
            if(log.isInfoEnabled()){
                log.info("Helper class built for class \""+qname+"\"");
            }
        } catch (InstantiationException e) {
            if (log.isEnabledFor(Priority.ERROR)) {
                log.error("cant't create Helper instance of " + qname, e);
            }
        } catch (IllegalAccessException e) {
            if (log.isEnabledFor(Priority.ERROR)) {
                log.error("cant't access Helper of " + qname, e);
            }
        } catch (ClassNotFoundException e) {
            if (log.isEnabledFor(Priority.ERROR)) {
                log.error("cant't find Helper class of " + qname, e);
            }
        }
        return obj;
    }

    /**
     * write a column displayName according to the DBMS type;
     *
     * @param w       the destination writer;
     * @param colName column displayName to be writed;
     * @throws IOException
     */
    private void writeColName(BufferedWriter w, String colName, boolean withValue) throws IOException {
        w.write("      sql.append(\"" + DBType.buildName(colName, dbType) + "\");\n");
        if (withValue) {
            w.write("      sql.append(\"=?\");\n");
        }
    }

    /**
     * write a table displayName according to the DBMS type;
     *
     * @param w         the destination writer;
     * @param tableName the table displayName to be write;
     * @throws IOException
     */
    private void writeTableName(BufferedWriter w, String tableName) throws IOException {
        w.write("      sql.append(\" " + DBType.buildName(tableName, dbType) + " \");\n");
    }

    /**
     * write insert parent;
     *
     * @param w
     * @param bean
     * @param name
     * @throws IOException
     */
    private void writeInsertParent(BufferedWriter w,Bean bean,String name)
            throws IOException{
        if(!bean.isSaveParent()){
            return;
        }

        Class cls = bean.getBeanClass().getSuperclass();
        if(cls!=Object.class){
            w.write("    ObjectHelper oh = this.ohf.getObjectHelper("+cls.getName()+".class);\n");
            w.write("    oh.insert("+name+",conn);");
        }
    }

    /**
     * write delete parent;
     *
     * @param w
     * @param bean
     * @param name
     * @throws IOException
     */
    private void writeDeleteParent(BufferedWriter w,Bean bean,String name)
            throws IOException{
        if(!bean.isSaveParent()){
            return;
        }
        Class cls = bean.getBeanClass().getSuperclass();
        if(cls!=Object.class){
            w.write("    ObjectHelper oh = this.ohf.getObjectHelper("+cls.getName()+".class);\n");
            w.write("    oh.delete("+name+",conn);");
        }
    }

    /**
     * write update parent;
     *
     * @param w
     * @param bean
     * @param name
     * @throws IOException
     */
    private void writeUpdateParent(BufferedWriter w,Bean bean,String name)
            throws IOException{
        if(!bean.isSaveParent()){
            return;
        }
        Class cls = bean.getBeanClass().getSuperclass();
        if(cls!=Object.class){
            w.write("    ObjectHelper oh = this.ohf.getObjectHelper("+cls.getName()+".class);\n");
            w.write("    oh.update("+name+",conn);");
        }
    }
}