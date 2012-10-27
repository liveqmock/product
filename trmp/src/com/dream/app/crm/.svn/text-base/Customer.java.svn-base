/**
 *all rights reserved,@copyright 2003
 */
package com.dream.app.crm;

import com.dream.bizsdk.common.blc.AbstractBLC;
import com.dream.bizsdk.common.blc.BLContext;
import com.dream.bizsdk.common.databus.BizData;
import com.dream.bizsdk.common.SysError;
import com.dream.bizsdk.common.database.dao.DAO;
import com.dream.bizsdk.common.SysError;

import java.sql.SQLException;

/**
 * Description:
 * Customers mamnagement.
 * <p/>
 * User: chuguanghua
 * Date: 2004-4-19
 * Time: 14:07:56
 */
public class Customer extends AbstractBLC {
    public final static String PERSON_CUST = "CMPersonCustomer";
    public final static String ORG_CUST = "CMOrgCustomer";
    public final static String BASIC_CUST = "CMCustomerBasic";

    protected DAO crmDAO;

    /**
     * init this blc;
     *
     * @param context
     * @return
     */
    public boolean init(BLContext context) {
        super.init(context);
        crmDAO = context.getDAO("core");
        return true;
    }


    /**
     * insert one or more person customers into system;
     *
     * @param rd
     * @param sd
     * @return
     */
    public int insertPerson(BizData rd, BizData sd) throws SQLException {
        int rows = rd.getTableRowsCount(Customer.PERSON_CUST);
        if (rows < 1) { //no record to be inserted;
            return SysError.BL_PARAM_ERROR;
        }
        BizData d = new BizData();
        d.copyEntity(rd, Customer.PERSON_CUST);
        d.copyEntity(rd, Customer.PERSON_CUST, Customer.BASIC_CUST);

        /**set the insert seq*/
        d.addAttr(Customer.BASIC_CUST, "NO", 0);
        d.addAttr(Customer.PERSON_CUST, "NO", 1);

        //insert the records;
        rows = crmDAO.insert(d);
        if (rows < 1) {
            return SysError.INSERT_ERROR;
        } else {
            return rows;
        }
    }

    /**
     * insert one or more org custs into system;
     *
     * @param rd
     * @param sd
     * @return
     */
    public int insertOrg(BizData rd, BizData sd) throws SQLException {
        int rows = rd.getTableRowsCount(Customer.ORG_CUST);
        if (rows < 1) { //no record to be inserted;
            return SysError.BL_PARAM_ERROR;
        }
        BizData d = new BizData();
        d.copyEntity(rd, Customer.ORG_CUST);
        d.copyEntity(rd, Customer.ORG_CUST, Customer.BASIC_CUST);

        /**set the insert seq*/
        d.addAttr(Customer.BASIC_CUST, "NO", 0);
        d.addAttr(Customer.PERSON_CUST, "NO", 1);

        //insert the records;
        rows = crmDAO.insert(d);
        if (rows < 1) {
            return SysError.INSERT_ERROR;
        } else {
            return rows;
        }
    }

    /**
     * "delete" one or more customers from system: make the status of the customer
     * to be "deleted";
     * INPUT:
     * array of custID;
     *
     * @param rd
     * @param sd
     * @return rows deleted;
     */
    public int delete(BizData rd, BizData sd) throws SQLException {
        int i = 0;
        int rows = rd.getArrayLength("CustID");
        if (rows < 1) { //no record to be inserted;
            return 0;
        }

        BizData d = new BizData();

        while (i < rows) {
            String custID = (String) rd.get("custID", i);
            d.add(Customer.BASIC_CUST, "custID", i, custID);
            d.add(Customer.PERSON_CUST, "custID", i, custID);
            d.add(Customer.ORG_CUST, "custID", i, custID);
            i++;
        }
        /**set the insert seq*/
        d.addAttr(Customer.PERSON_CUST, "NO", 0);
        d.addAttr(Customer.ORG_CUST, "NO", 1);
        d.addAttr(Customer.BASIC_CUST, "NO", 2);

        //insert the records;
        rows = crmDAO.delete(d);
        if (rows < 1) {
            return SysError.DELETE_ERROR;
        } else {
            return rows;
        }
    }

    /**
     * update the information of a customer;
     *
     * @param rd
     * @param sd
     * @return
     */
    public int updatePerson(BizData rd, BizData sd) throws SQLException {
        int rows = rd.getTableRowsCount(Customer.PERSON_CUST);
        if (rows != 1) { //no record to be inserted;
            return SysError.BL_PARAM_ERROR;
        }
        BizData d = new BizData();
        d.copyEntity(rd, Customer.PERSON_CUST);
        d.copyEntity(rd, Customer.PERSON_CUST, Customer.BASIC_CUST);

        /**set the insert seq*/
        d.addAttr(Customer.BASIC_CUST, "NO", 0);
        d.addAttr(Customer.PERSON_CUST, "NO", 1);

        //insert the records;
        rows = crmDAO.update(d);
        if (rows < 1) {
            return SysError.UPDATE_ERROR;
        } else {
            return rows;
        }
    }

    /**
     * update an organziational customer;
     *
     * @param rd
     * @param sd
     * @return
     */
    public int updateOrg(BizData rd, BizData sd) throws SQLException {
        int rows = rd.getTableRowsCount(Customer.ORG_CUST);
        if (rows != 1) { //no record to be inserted;
            return SysError.BL_PARAM_ERROR;
        }
        BizData d = new BizData();
        d.copyEntity(rd, Customer.ORG_CUST);
        d.copyEntity(rd, Customer.ORG_CUST, Customer.BASIC_CUST);

        /**set the insert seq*/
        d.addAttr(Customer.BASIC_CUST, "NO", 0);
        d.addAttr(Customer.ORG_CUST, "NO", 1);

        //insert the records;
        rows = crmDAO.update(d);
        if (rows < 1) {
            return SysError.UPDATE_ERROR;
        } else {
            return rows;
        }
    }

    /**
     * query customers matches the criteria;
     *
     * @param rd
     * @param sd
     * @return
     */
    public int query(BizData rd, BizData sd) {
        try {
            return crmDAO.selectPage(Customer.BASIC_CUST, rd, true);
        } catch (SQLException e) {
            e.printStackTrace();
            return SysError.DB_ERROR;
        }
    }

    /**
     * get the list of my customers;
     *
     * @param rd
     * @param sd
     * @return
     */
    public int getMyCusts(BizData rd, BizData sd) {
        return 0;
    }


    /**
     * assign one or more customers to an employee;
     *
     * @param rd
     * @param sd
     * @return
     */
    public int assignCusts(BizData rd, BizData sd) {
        return 0;
    }

    /**
     * send mail to one or more customers;
     *
     * @param rd
     * @param sd
     * @return
     */
    public int sendMail2Custs(BizData rd, BizData sd) {
        return 0;
    }
}
