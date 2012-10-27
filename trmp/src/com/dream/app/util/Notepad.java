/*
 * Notepad.java
 *
 * Created on 2003年11月25日, 下午5:53
 */

package com.dream.app.util;

import com.dream.bizsdk.common.blc.DBBLC;
import com.dream.bizsdk.common.database.datadict.Types;
import com.dream.bizsdk.common.databus.BizData;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author chuguanghua
 */
public class Notepad extends DBBLC {

    /**
     * Creates a new instance of Notepad
     */
    public Notepad() {
        entityName = "NPNotepad";
    }

    /**
     * 创建一个新的记事本;
     * in npName-记事本名称
     * out npID-新创建的记事本编号，整数
     */
    public int create(BizData data, Connection conn) throws SQLException {
        int npID = 1;
        Integer nextID = (Integer) coreDAO.getFieldValue(entityName, "max(npID)+1", Types.INT);
        if (nextID != null) {
            npID = nextID.intValue();
        }
        //get the npName;
        String npName = (String) data.get("npName");
        BizData d = new BizData();
        d.add(entityName, "npID", new Integer(npID));
        d.add(entityName, "npName", npName);
        coreDAO.insert(d, conn);
        return npID;
    }

    /**
     * *in npID-记事本编号
     */
    public int delete(BizData data, Connection conn) throws SQLException {
        int npID = 0;
        try {
            npID = data.getInt("npID");
        } catch (Exception e) {
            return 0;
        }
        BizData d = new BizData();
        d.add(entityName, "npID", new Integer(npID));
        d.add("NPNote", "npID", new Integer(npID));
        d.addAttr("NPNote", "NO", new Integer(0));
        d.addAttr("NPNote", "NO", new Integer(1));
        return coreDAO.delete(d, conn);
    }

    /**
     * int npID-记事本编号
     * in  pageNO-要显示的页号；
     * in pageSize-要显示的页大小；
     * out NPNotes
     */
    public int queryNotes(BizData data) throws SQLException {
        int npID = 0;
        int pageNO = 1;
        int pageSize = 12;
        try {
            npID = data.getInt("npID");
            pageNO = data.getInt("pageNO");
            pageSize = data.getInt("pageSize");
        } catch (Exception e) {
        }
        if (pageSize == 0) {
            pageSize = 10;
        }
        String sql = "select * from npnote where npID=" + npID + " order by noteTime desc";
        return coreDAO.executeQueryPage(sql, "NPNotes", pageNO, pageSize, data);
    }

    /**
     * 向指定的记事本中写入一条记事；
     * in npID-记事本编号
     * in noteSubject-记事主题
     * in noteContent-记事内容;
     *
     * @param data
     * @param conn
     * @return rows inserted into the database;
     */
    public int writeNote(BizData data, BizData sd, Connection conn)
            throws SQLException {
        int noteID = 0;
        String empID = null;
        String empName = null;
        /**get the next noteid from the database*/
        try {
            Integer id = (Integer) coreDAO.getFieldValue(entityName, "max(noteID)+1", Types.INT);
            noteID = id.intValue();
        } catch (Exception e) {
        }
        /**get the current empName and empID*/
        empID = sd.getString("empID");
        empName = sd.getString("empName");
        /**prepare the record to be inserted*/
        BizData d = new BizData();
        d.add("NPNote", "npID", data.get("npID"));
        d.add("NPNote", "noteID", new Integer(noteID));
        d.add("NPNote", "noteSubject", data.get("noteSubject"));
        d.add("NPNote", "noteContent", data.get("noteContent"));
        d.add("NPNote", "noteWriterID", empID);
        d.add("NPNote", "noteWriterName", empName);
        //insert the record；
        return coreDAO.insert(d, conn);
    }

    /**
     *
     */
    public int deleteNotes(BizData rd, BizData sd) {
        return 0;
    }
}