/**
 *all rights reserved,@copyright 2003
 */
package com.cots.taglib;

import javax.servlet.jsp.tagext.Tag;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2005-4-5
 * Time: 15:51:39
 * Version: 1.0
 */
public class PaginationTag extends BaseTag{
    public int doStartTag(){
        //write first page link;
        //write previous page link;
        //write goto page link with total pages;
        //write next page tag;
        //write last page link;
        return Tag.SKIP_BODY;
    }
}