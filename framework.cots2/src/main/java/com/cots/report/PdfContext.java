/**
 *all rights reserved,@copyright 2003
 */
package com.cots.report;

import com.lowagie.text.Font;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.BaseFont;
import com.cots.util.XMLFile;

import java.util.Stack;
import java.io.IOException;

import org.w3c.dom.Element;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-7-1
 * Time: 9:05:57
 */
public class PdfContext {
    private Stack name = new Stack();
    private Stack bold = new Stack();
    private Stack size = new Stack();
    private Stack italy = new Stack();
    private Stack underline = new Stack();
    private Stack sup = new Stack();
    private Stack sub = new Stack();
    private BaseFont bfChinese;
    private PdfPageSetting pageSetting;

    public PdfContext() throws DocumentException, IOException {
        bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
        pushName(-1, "STSong-Light");
        pushSize(-1, 12);
        pushBold(-1, false);
        pushItaly(-1, false);
        pushUnderline(-1, true);
        pushSub(-1, false);
        pushSup(-1, false);
    }

    public static PdfContext getDefault(Template t) throws DocumentException, IOException {
        XMLFile xml = t.getXMLFile();
        PdfContext c = new PdfContext();
        Element e = (Element) xml.selectSingleNode("/html/head/defaultfont");
        if (e != null) {
            String size = xml.getSingleNodeValue(e, "size");
            String name = xml.getSingleNodeValue(e, "name");
            String bold = xml.getSingleNodeValue(e, "bold");
            String italy = xml.getSingleNodeValue(e, "italy");
            if (name != null) {
                c.pushName(0, name);
            }
            if (size != null) {
                c.pushSize(0, Integer.parseInt(size));
            }
            if (bold != null) {
                c.pushBold(0, Boolean.valueOf(bold).booleanValue());
            }
            if (italy != null) {
                c.pushItaly(0, Boolean.valueOf(italy).booleanValue());
            }
        }
        c.pageSetting = new PdfPageSetting();
        e = (Element) xml.selectSingleNode("/html/head/page");
        if (e != null) {
            String ps = xml.getChildElementValue(e, "size");
            String lm = xml.getChildElementValue(e, "leftmargin");
            String rm = xml.getChildElementValue(e, "rigthmargin");
            String tm = xml.getChildElementValue(e, "topmargin");
            String bm = xml.getChildElementValue(e, "bottommargin");
            String hd = xml.getChildElementValue(e, "header");
            String hdAlign = xml.getChildElementAttr(e, "header", "align");
            String hdBorder = xml.getChildElementAttr(e, "header", "border");
            String hdBold = xml.getChildElementAttr(e, "header", "bold");
            String hdSize = xml.getChildElementAttr(e, "header", "size");
            String hdItaly = xml.getChildElementAttr(e, "header", "italy");


            String ft = xml.getChildElementValue(e, "footer");
            String ftAlign = xml.getChildElementAttr(e, "footer", "align");
            String ftBorder = xml.getChildElementAttr(e, "footer", "border");
            String ftBold = xml.getChildElementAttr(e, "footer", "bold");
            String ftSize = xml.getChildElementAttr(e, "footer", "size");
            String ftItaly = xml.getChildElementAttr(e, "footer", "italy");

            if (ps != null) {
                c.pageSetting.setSize(ps);
            }
            if (lm != null) {
                c.pageSetting.setLeftmargin(Float.parseFloat(lm));
            }
            if (rm != null) {
                c.pageSetting.setRightmargin(Float.parseFloat(rm));
            }
            if (tm != null) {
                c.pageSetting.setTopmargin(Float.parseFloat(tm));
            }
            if (bm != null) {
                c.pageSetting.setBottommargin(Float.parseFloat(bm));
            }
            if (hd != null) {
                c.pageSetting.setHeader(hd);
            }
            if (hdAlign != null) {
                c.pageSetting.setHeaderAlign(hdAlign);
            }
            if (hdBorder != null) {
                c.pageSetting.setHeaderBorder(hdBorder);
            }
            if (hdSize != null && hdSize.length() > 0) {
                c.pageSetting.setHeaderSize(Integer.parseInt(hdSize));
            }
            if (hdBold != null && hdBold.length() > 0) {
                c.pageSetting.setHeaderBold(Boolean.valueOf(hdBold).booleanValue());
            }
            if (hdItaly != null && hdItaly.length() > 0) {
                c.pageSetting.setHeaderItaly(Boolean.valueOf(hdItaly).booleanValue());
            }


            if (ft != null) {
                c.pageSetting.setFooter(ft);
            }
            if (ftAlign != null) {
                c.pageSetting.setFooterAlign(ftAlign);
            }
            if (ftBorder != null) {
                c.pageSetting.setFooterBorder(ftBorder);
            }
            if (ftSize != null && ftSize.length() > 0) {
                c.pageSetting.setFooterSize(Integer.parseInt(ftSize));
            }
            if (ftBold != null && ftBold.length() > 0) {
                c.pageSetting.setFooterBold(Boolean.valueOf(ftBold).booleanValue());
            }
            if (ftItaly != null && ftItaly.length() > 0) {
                c.pageSetting.setFooterItaly(Boolean.valueOf(ftItaly).booleanValue());
            }
        }
        return c;
    }

    public Font getCurrentFont() {
        int s = 12;
        int f = 0;
        boolean b;
        boolean i;
        boolean ul;
        FontDesc fd;

        fd = (FontDesc) size.peek();
        s = fd.getSize();
        fd = (FontDesc) bold.peek();
        b = fd.isBold();
        fd = (FontDesc) italy.peek();
        i = fd.isItaly();
        fd = (FontDesc) underline.peek();
        ul = fd.isUnderline();

        if (b) {
            f |= Font.BOLD;
        }
        if (i) {
            f |= Font.ITALIC;
        }
        if (ul) {
            f |= Font.UNDEFINED;
        }

        return new Font(bfChinese, s, f);
    }

    public void pushName(int id, String b) {
        FontDesc fd = new FontDesc(id);
        fd.setName(b);
        name.push(fd);
    }

    public void pushBold(int id, boolean b) {
        FontDesc fd = new FontDesc(id);
        fd.setBold(b);
        bold.push(fd);
    }

    public void pushSize(int id, int s) {
        FontDesc fd = new FontDesc(id);
        fd.setSize(s);
        size.push(fd);
    }

    public void pushItaly(int id, boolean i) {
        FontDesc fd = new FontDesc(id);
        fd.setItaly(i);
        italy.push(fd);
    }

    public void pushUnderline(int id, boolean i) {
        FontDesc fd = new FontDesc(id);
        fd.setUnderline(i);
        underline.push(fd);
    }

    public void pushSub(int id, boolean flag) {
        FontDesc fd = new FontDesc(id);
        fd.setSub(flag);
        sub.push(fd);
    }

    public void pushSup(int id, boolean flag) {
        FontDesc fd = new FontDesc(id);
        fd.setSup(flag);
        sup.push(fd);
    }

    public boolean isSub() {
        FontDesc fd = (FontDesc) sub.peek();
        return fd.isSub();
    }

    public boolean isSup() {
        FontDesc fd = (FontDesc) sup.peek();
        return fd.isSup();
    }

    public int getSize() {
        FontDesc fd = (FontDesc) size.peek();
        return fd.getSize();
    }

    public String getName() {
        FontDesc fd = (FontDesc) name.peek();
        return fd.getName();
    }

    public void pop(int id) {
        FontDesc fd;
        fd = (FontDesc) name.peek();
        if (fd.getId() == id) {
            name.pop();
        }
        fd = (FontDesc) size.peek();
        if (fd.getId() == id) {
            size.pop();
        }
        fd = (FontDesc) bold.peek();
        if (fd.getId() == id) {
            bold.pop();
        }
        fd = (FontDesc) italy.peek();
        if (fd.getId() == id) {
            italy.pop();
        }
        fd = (FontDesc) underline.peek();
        if (fd.getId() == id) {
            underline.pop();
        }
        fd = (FontDesc) sub.peek();
        if (fd.getId() == id) {
            sub.pop();
        }
        fd = (FontDesc) sup.peek();
        if (fd.getId() == id) {
            sup.pop();
        }
    }

    public PdfPageSetting getPageSetting() {
        return this.pageSetting;
    }

    public BaseFont getBaseFont() throws DocumentException, IOException {
        return BaseFont.createFont(getName(), "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
    }
}
