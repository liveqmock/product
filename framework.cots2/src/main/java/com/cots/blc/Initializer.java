/**
 *all rights reserved,@copyright 2003
 */
package com.cots.blc;

import javax.servlet.ServletContext;

/**
 * Description:
 *      Initializer will be executed when the application starts up. Application may develop
 * classes implemented this interface and then registher the class to blcontext.xml file.
 *
 * User: chuguanghua
 * Date: 2005-5-11
 * Time: 9:03:22
 * Version: 1.0
 */
public interface Initializer {

    /**
     * get the displayName of this initializer. Each Initializer must have a unique displayName, this displayName
     * will be used as the displayName of the attribute in the ServletContext.
     *
     * @return the displayName of the Initializer;
     */
    String getName();

    /**
     * do the initialization task. can be called multiple times at diferent time.
     */
    void init(ServletContext sc);
}
