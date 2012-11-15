/**
 *all rights reserved,@copyright 2003
 */
package com.cots.report;

import com.lowagie.text.DocumentException;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.HashMap;

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
    private HashMap data;
    private String path;

    public Report() {

    }

    public Report(String path, Template template) throws FileNotFoundException {
        this.out = new FileOutputStream(path);
        this.template = template;
        this.path = path;
    }


    public Report(String path, Template template, HashMap data) throws FileNotFoundException {
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

    public HashMap getData() {
        return this.data;
    }

    public void setData(HashMap data) {
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
