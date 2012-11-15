/**
 *all rights reserved,@copyright 2003
 */
package com.cots.mvc.controller;

import com.cots.blc.BLCPool;
import com.cots.blc.BLContext;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-11-29
 * Time: 18:42:48
 * Version: 1.0
 */
public interface DynaAction extends Action{
    void setBLCPool( BLCPool blcPool);
    void setBLContext(BLContext context);
}