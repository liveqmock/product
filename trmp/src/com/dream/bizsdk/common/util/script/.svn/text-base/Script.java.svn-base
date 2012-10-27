/*
 * Script.java
 *
 * Created on 2003年11月10日, 下午4:15
 */

package com.dream.bizsdk.common.util.script;

import com.dream.bizsdk.common.database.dao.DAO;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;


/**
 * @author chuguanghua
 */
public class Script {

    public static final int INFO = 0;
    public static final int ERROR = 1;

    protected String logFilePath = null;
    protected BufferedWriter log = null;
    protected int level = Script.INFO;

    protected boolean singleStmt = false;
    protected boolean singleTrsact = false;
    protected boolean exitOnError = false;

    public Script() {
    }

    /**
     * @return
     */
    public boolean isSingleStmt() {
        return singleStmt;
    }

    /**
     * @param single
     */
    public void setSingleStmt(boolean single) {
        singleStmt = single;
    }

    public boolean isSingleTrsact() {
        return singleTrsact;
    }

    public void setSingleTrsact(boolean single) {
        singleTrsact = single;
    }

    public boolean isExitOnError() {
        return this.exitOnError;
    }

    public void setExitOnError(boolean exitOnError) {
        this.exitOnError = exitOnError;
    }

    /**
     *run a script file;
     *return 1 if succeed;
     *if errors occur,return the number of the error line,the first line is 1;
     */
    /**
     * run a script file;
     * return 1 if succeed;
     * if errors occur,return the number of the error line,the first line is 1;
     */
    public boolean run(File f, boolean singleStmt, DAO dao) throws SQLException {
        boolean isEnd = false;
        String strStmt = null;
        Connection conn = dao.getConnection();
        try {
            if (singleTrsact) {
                conn.setAutoCommit(false);
            } else {
                conn.setAutoCommit(true);
            }
            BufferedReader r = new BufferedReader(new FileReader(f));
            while (!isEnd) {
                strStmt = getNextStatement(r, singleStmt);
                if (strStmt != null) {
                    //check if a empty linke;
                    if (strStmt.length() == 0) {
                        continue;
                    }
                    //execute the statement;
                    if (!execStmt(strStmt, conn)) {
                        if (exitOnError) {
                            if (singleTrsact) {
                                conn.rollback();
                            }
                            return false;
                        }
                    }
                } else {
                    isEnd = true;
                }
            }
            if (singleTrsact) {
                conn.commit();
                conn.setAutoCommit(true);
            }
            return true;
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return false;
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return false;
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }


    /**
     * run a script file in a specified location;
     * return 1 if succeed;
     * if errors occur,return the number of the error line,the first line is 1;
     */
    public boolean run(String filePath, boolean singleStmt, DAO dao) throws SQLException {
        return run(new File(filePath), singleStmt, dao);
    }

    public String getNextStatement(BufferedReader r, boolean single) throws IOException {
        int i = 0;
        boolean isEnd = false;
        boolean inString = false;
        boolean bComment = false; //if is in block comment;
        int count = 0;
        //a line in the script file;
        String line = null;
        //the length of the current line;
        int lineLength = 0;
        //char array to save the content of the new sql statemtnt;
        CharArrayWriter caw = new CharArrayWriter();
        while (!isEnd) {
            line = r.readLine();
            if (line == null) {
                isEnd = true;
            } else {
                line = line.trim();    //trim the line;
                if (line.compareToIgnoreCase("go") == 0) {
                    break;
                }

                lineLength = line.length();
                i = 0;
                while (i < lineLength) {
                    char c = line.charAt(i);
                    /**comment block process*/
                    if (bComment) {
                        if (c == '*' && i < (lineLength - 1) && line.charAt(i + 1) == '/') {
                            bComment = false;
                            i += 2;
                        } else {
                            i++;
                        }
                        continue;
                    }

                    /*first check if a string begins*/
                    if (c == '\'') {
                        caw.write(c);
                        if (inString) {
                            count++;
                            if (count >= 2) {
                                count = 0;
                            }
                        } else {
                            inString = true;
                            count = 0;
                        }
                    } else {
                        if (inString && count == 1) {
                            inString = false;
                        }
                        if (!inString) {
                            if (c == '/') { //end of sql statement;
                                if (i == (lineLength - 1)) {
                                    isEnd = true;
                                    break;
                                } else {
                                    if (line.charAt(i + 1) == '*') {
                                        bComment = true;
                                    } else {
                                        caw.write(c);
                                    }
                                }
                            } else if (c == ';') {
                                if (single) {
                                    caw.write(c);
                                } else {
                                    isEnd = true;
                                    break;
                                }
                            } else if (c == '-') {
                                //check if single line comment;
                                if (i < (lineLength - 1) && line.charAt(i + 1) == '-') {
                                    break;
                                }
                            } else {
                                caw.write(c);
                            }
                        } else {
                            caw.write(c);
                        }
                    }
                    i++;
                }
            }
            caw.write(' ');
        }
        //check if the end of the file met;
        if (line == null) {
            return null;
        }
        //check the statement length;
        if (caw.size() < 1) {
            return new String("");
        } else {
            return caw.toString();
        }
    }

    /**
     * execute a sql on a specified Connection object;
     *
     * @param sql  the sql to be executed;
     * @param conn the connection object to execute the sql;
     */
    protected boolean execStmt(String sql, Connection conn) {
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            stmt.execute(sql);
            logMsg(sql, true, "");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            logMsg(sql, false, e.getMessage());
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (Exception e2) {
            }
        }
        return false;
    }

    /**
     * log a statement and its result;
     */
    public void logMsg(String strStmt, boolean result, String msg) {
        if (this.log == null) {
            return;
        }
        try {
            if (result && Script.INFO >= level) {
                log.write(strStmt);
                log.write("\r\n");
                log.write("Statement executed successfully.\r\n\r\n");
                log.flush();
            } else if (!result && Script.ERROR >= level) {
                log.write(strStmt);
                log.write("\r\n");
                log.write("Error in execution:" + msg + ".");
                log.write("\r\n\r\n");
                log.flush();
            }
        } catch (IOException ios) {
            System.out.println("IOException when write log!");
        }
    }

    /**
     * log a msg ;
     */
    public void logMsg(String strmsg) {
        if (this.log == null) {
            return;
        }
        try {
            log.write(strmsg);
            log.write("\r\n\r\n");
            log.flush();
        } catch (IOException ios) {
            System.out.println("IOException when write log!");
        }
    }

    /**
     * @param level
     */
    public void setLogLevel(int level) {
        this.level = level;
    }

    /**
     * @param filePath
     */
    public void setLogFilePath(String filePath) {
        try {
            this.logFilePath = filePath;
            if (logFilePath != null) {
                File logFile = new File(logFilePath);
                if (!logFile.exists()) {
                    logFile.createNewFile();
                }
                if (!logFile.canWrite()) {
                    this.log = null;
                } else {
                    this.log = new BufferedWriter(new FileWriter(logFile));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}