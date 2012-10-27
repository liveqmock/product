/**
 *all rights reserved,@copyright 2003
 */
package com.dream.bizsdk.common.report;

import com.dream.bizsdk.common.databus.BizData;
import com.lowagie.text.DocumentException;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.FileNotFoundException;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-7-1
 * Time: 8:30:24
 */
public class Report {
    private FileOutputStream out;
    private Template template;
    private BizData data;
    private String path;

    public Report() {

    }

/*    public Report(FileOutputStream out,Template template){
        this.out= out;
        this.template = template;
    }
*/
    public Report(String path, Template template) throws FileNotFoundException {
        this.out = new FileOutputStream(path);
        this.template = template;
        this.path = path;
    }

/*    public Report(FileOutputStream out,Template template,BizData data){
        this.out  = out;
        this.template = template;
        this.data=data;
    }
*/
    
    public Report(String path, Template template, BizData data) throws FileNotFoundException {
        this.out = new FileOutputStream(path);
        this.template = template;
        this.data = data;
        this.path = path;
    }

    public FileOutputStream getOut() {
        return this.out;
    }

    public void setOut(FileOutputStream out) {
        this.out = out;
    }

    public Template getTemplate() {
        return this.template;
    }

    public void setTemplate(Template template) {
        this.template = template;
    }

    public BizData getData() {
        return this.data;
    }

    public void setData(BizData data) {
        this.data = data;
    }

    /**
     * close this report instance;
     *
     * @throws IOException
     */
    public void close() throws IOException {
        if (out != null) {
            out.close();
        }
    }

    /**
     * build this template according to the current tempalte and data object;
     *
     * @throws IOException
     */
    public void buildPdf() throws IOException, DocumentException {
        PdfBuilder b = new PdfBuilder();
        b.build(this);
    }

    public String getPath() {
        return path;
    }
}
