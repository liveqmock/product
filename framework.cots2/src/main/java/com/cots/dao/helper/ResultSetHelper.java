package com.cots.dao.helper;

import com.cots.dao.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * This interface will help user to generate Object from a SesultSet.
 * the cots framework will build a class implemented this interface
 * for each object. Suppose the following Object:
 *    public class Customer{
 *        private String id;
 *        private String displayName;
 *
 *        public String getId(){
 *              return id;
 *        }
 *        public void setId(String id){
 *              this.id = id;
 *        }
 *        public String getDisplayName(){
 *              return displayName;
 *        }
 *        public void setDisplayName(String displayName){
 *              this.displayName = displayName;
 *        }
 *    }
 *
 *    the generated helper class will look like the following:
 *    public class Customer_Helper implements ResultSetHelper{
 *        Object populate(ResultSet rs,DAO dao) throws SQLException{
 *            Cusomter obj = new Customer();
 *            obj.setId(rs.getString("id"));
 *            obj.setDisplayName(rs.getString("displayName");
 *            reutrn obj;
 *        }
 *    }
 *
 *
 * User: chugh
 * Date: 2005-6-4
 * Time: 20:20:58
 */
public interface ResultSetHelper {

    /**
     * generate an Object from the current row in a ResultSet.
     * The called shuold call next() method to move the position
     * pointer.
     *
     * @param rs the source ResultSet
     * @param dao the DAO that returns the ResultSet.
     * @return the generated Object, shouldn't be null.
     * @throws SQLException
     */
    Object populate(ResultSet rs,DAO dao) throws SQLException;

    /**
     * Generate Ojbects matching the records from the current position
     * to the last.
     *
     * @param rs the source ResuleSet.
     * @param dao the DAO that returns the ResultSet.
     * @return list of Objects
     * @throws SQLException
     */
    List populateBatch(ResultSet rs,DAO dao) throws SQLException;

    /**
     *
     *
     * @param list called provided list,must not null.
     * @param rs the source ResultSet object.
     * @param dao the DAO that returnes the ResultSet.
     * @return 
     * @throws SQLException
     */
    int populateBatch(List list,ResultSet rs,DAO dao) throws SQLException;
}
