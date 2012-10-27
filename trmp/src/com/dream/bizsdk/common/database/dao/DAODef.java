/**
 *all rights reserved,@copyright 2003
 */

package com.dream.bizsdk.common.database.dao;

/**
 * <p>Title: engine</p>
 * <p/>
 * Description: This is the platform of the business development kit.
 * This class is definition for DAO object, an DAO object can be created on
 * the basis of the class.
 * <p/>
 * </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: dream</p>
 *
 * @author chuguanghua
 * @version 1.0
 */

public class DAODef {
	
	protected int _initialSize;
	protected int _minPoolSize;
	protected int _maxPoolSize;
	protected int _maxStatements;
	protected int _maxIdleTime;
    /**
     * name of the DAO object;
     */
    protected String _name;
    /**
     * URL to the DAO object,ursually a database URL;
     */
    protected String _dbURL;
    /**
     * type of the database;
     */
    protected String _dbType;
    /**
     * the database user Account to the databse;
     */
    protected String _dbUser;
    /**
     * the password of the user Account;
     */
    protected String _dbPassword;
    /**
     * the name of the driver of this dao
     */
    protected String _driver;
    /**
     * if using datasource
     */
    protected boolean usingDatasource;
    /**
     * using JTA
     */
    protected boolean usingJTA;

    protected boolean overrideUser;

    protected boolean twoPhaseCommit;
    /**
     * name of the dataSouce
     */
    protected String dataSource;
    /**
     * the count of connection in the DAO's DBLinksPool
     */
    protected int _linksPoolSize = 10;

    /**
     * @param name
     * @param dbURL
     * @param dbType
     * @param dbUser
     * @param dbPassword
     * @param driver
     */
    /*public DAODef(String name, String dbURL, String dbType, String dbUser
                  , String dbPassword, String driver,int initialSize,
                  int minPoolSize, int maxPoolSize, int maxStatements, 
                  int maxIdleTime) {
        _name = name;
        _dbURL = dbURL;
        _dbType = dbType;
        _dbUser = dbUser;
        _dbPassword = dbPassword;
        _driver = driver;
        _initialSize = initialSize;
        _minPoolSize = minPoolSize;
        _maxPoolSize = maxPoolSize;
        _maxStatements = maxStatements;
        _maxIdleTime = maxIdleTime;
    }*/

    /**
     * @param name
     * @param dbType
     * @param dataSource
     * @param usingJTA
     */
    /*public DAODef(String name, String dbType, String dataSource, boolean usingJTA, boolean overrideUser, boolean twoPhaseCommit
                  , String dbUser, String dbPass,int initialSize,
                  int minPoolSize, int maxPoolSize, int maxStatements, 
                  int maxIdleTime) {
        usingDatasource = true;
        this.overrideUser = overrideUser;
        this.usingJTA = usingJTA;
        this.twoPhaseCommit = twoPhaseCommit;
        _name = name;
        _dbType = dbType;
        this.dataSource = dataSource;
        this._dbUser = dbUser;
        this._dbPassword = dbPass;
        this._initialSize = initialSize;
        _minPoolSize = minPoolSize;
        _maxPoolSize = maxPoolSize;
        _maxStatements = maxStatements;
        _maxIdleTime = maxIdleTime;
    }*/

    /**
     * @param name
     * @param dbURL
     * @param dbType
     * @param dbUser
     * @param dbPassword
     * @param conns
     * @param driver
     */
    public DAODef(String name, String dbURL, String dbType, String dbUser
                  , String dbPassword, int conns, String driver,int initialSize,
                  int minPoolSize, int maxPoolSize, int maxStatements, 
                  int maxIdleTime) {
        _name = name;
        _dbURL = dbURL;
        _dbType = dbType;
        _dbUser = dbUser;
        _dbPassword = dbPassword;
        _linksPoolSize = conns;
        _driver = driver;
        _initialSize = initialSize;
        _minPoolSize = minPoolSize;
        _maxPoolSize = maxPoolSize;
        _maxStatements = maxStatements;
        _maxIdleTime = maxIdleTime;
    }
    

	public int get_initialSize() {
		return _initialSize;
	}

	public void set_initialSize(int initialSize) {
		_initialSize = initialSize;
	}

	public int get_minPoolSize() {
		return _minPoolSize;
	}

	public void set_minPoolSize(int minPoolSize) {
		_minPoolSize = minPoolSize;
	}

	public int get_maxPoolSize() {
		return _maxPoolSize;
	}

	public void set_maxPoolSize(int maxPoolSize) {
		_maxPoolSize = maxPoolSize;
	}

	public int get_maxStatements() {
		return _maxStatements;
	}

	public void set_maxStatements(int maxStatements) {
		_maxStatements = maxStatements;
	}

	public int get_maxIdleTime() {
		return _maxIdleTime;
	}

	public void set_maxIdleTime(int maxIdleTime) {
		_maxIdleTime = maxIdleTime;
	}
	
    /**
     * default constructor;
     */
    public DAODef() {
    }

    /**
     * get the name of this DAO Object.
     *
     * @return the DAO object's name.
     */
    public String getName() {
        return _name;
    }

    /**
     * set the name of the DAO object.
     *
     * @param name name of the DAO object.
     */
    public void setName(String name) {
        _name = new String(name);
    }

    /**
     * get the URL to the underlying database.
     *
     * @return the URL.
     */
    public String getDBURL() {
        return _dbURL;
    }

    /**
     * set the DBURL of the underlying database.
     *
     * @param dbURL the url to the Database.
     */
    public void setDBURL(String dbURL) {
        _dbURL = new String(dbURL);
    }

    /**
     * get the dbType value;
     *
     * @return the value of the DBType.
     */
    public String getDBType() {
        return _dbType;
    }

    /**
     * set the DBType
     *
     * @param dbType the new DBType;
     */
    public void setDBType(String dbType) {
        _dbType = new String(dbType);
    }

    /**
     * get the DBUser account.
     *
     * @return
     */
    public String getDBUser() {
        return _dbUser;
    }

    /**
     * set the dbUser account
     *
     * @param dbUser the user name
     */
    public void setDBUser(String dbUser) {
        _dbUser = new String(dbUser);
    }

    /**
     * Get the DBPassword
     *
     * @return the DBPassword;
     */
    public String getDBPassword() {
        return _dbPassword;
    }

    /**
     * set the DBPassword attribute's value.
     *
     * @param dbPassword
     */
    public void setDBPassword(String dbPassword) {
        _dbPassword = new String(dbPassword);
    }

    /**
     * set the size of the dblink
     *
     * @param newVal
     */
    public void setDBConnsCount(int newVal) {
        if (newVal > 0) {
            _linksPoolSize = newVal;
        }
    }

    /**
     * Get count of DBLink in the DBLinksPool;
     *
     * @return
     */
    public int getDBConnsCount() {
        return _linksPoolSize;
    }

    /**
     * Get the driver name;
     *
     * @return the driver;
     */
    public String getDriver() {
        return _driver;
    }

    /**
     * set the driver for this DAO;
     *
     * @param driver the new Dirver;
     */
    public void setDriver(String driver) {
        _driver = driver;
    }

    /**
     * check if the dao is defined to use datasource;
     *
     * @return true if this dao will user a datasource, false otherwise;
     */
    public boolean isUsingDS() {
        return usingDatasource;
    }

    public boolean isUsingJTA() {
        return usingJTA;
    }

    /**
     * return a the jndi name of the datasource.
     *
     * @return the jndi name of the data source;
     */
    public String getDataSourceName() {
        return dataSource;
    }

    /**
     * @return
     */
    public boolean isTwoPhaseCommit() {
        return this.twoPhaseCommit;
    }

    /**
     * @return
     */
    public boolean isOverrideUser() {
        return this.overrideUser;
    }
}