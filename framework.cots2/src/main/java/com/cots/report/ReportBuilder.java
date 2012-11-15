/**
 *all rights reserved,@copyright 2003
 */
package com.cots.report;

import com.lowagie.text.DocumentException;

import java.io.IOException;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-7-1
 * Time: 9:17:03
 */
public interface ReportBuilder {
    public void build(Report r) throws IOException, DocumentException;
}
