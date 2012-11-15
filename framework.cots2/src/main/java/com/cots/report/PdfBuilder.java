/**
 *all rights reserved,@copyright 2003
 */
package com.cots.report;

import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.ChartUtilities;

import java.io.IOException;
import java.io.File;
import java.awt.*;
import java.util.Stack;
import java.util.HashMap;
import java.text.ParseException;
import java.net.URL;
import java.net.MalformedURLException;

import com.lowagie.text.*;
import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.PdfContentByte;
import com.cots.util.XMLFile;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-7-1
 * Time: 8:50:42
 */
public class PdfBuilder implements ReportBuilder {

    private Report r;
    private Document pdfDoc;
    private PdfContext pdfContext;
    private PdfWriter pdfWriter;
    //private float w=0.0f;
    private float h = 0.0f;
    private HashMap data;

    private int nodeID = 1;

    //this element is used to parse Table element;
    private Stack iTextTable = new Stack();

    public PdfBuilder() {

    }

    public void build(Report r) throws IOException, DocumentException {
        init(r);
        beginBuild();
        pdfDoc.close();
    }


    private void init(Report r) throws IOException, DocumentException {
        String t;
        this.r = r;
        this.data = r.getData();
        pdfContext = PdfContext.getDefault(r.getTemplate());
        pdfDoc = new Document();
        pdfWriter = PdfWriter.getInstance(pdfDoc, r.getOut());

        PdfPageSetting ps = pdfContext.getPageSetting();
        if ((t = ps.getHeader()) != null) {
            int a = 0;
            if (ps.isHeaderBold()) {
                a |= Font.BOLD;
            }
            if (ps.isHeaderItaly()) {
                a |= Font.ITALIC;
            }

            Font f = new Font(pdfContext.getBaseFont(), ps.getHeaderSize(), a);
            HeaderFooter hf = new HeaderFooter(new Phrase(new Chunk(t, f)), false);
            if ((t = ps.getHeaderAlign()) != null) {
                hf.setAlignment(getAlign(t));
            }
            if ((t = ps.getHeaderBorder()) != null) {
                hf.setBorder(Integer.parseInt(t));
            }

            pdfDoc.setHeader(hf);
        }

        if ((t = ps.getFooter()) != null) {
            int a = 0;
            if (ps.isFooterBold()) {
                a |= Font.BOLD;
            }
            if (ps.isFooterItaly()) {
                a |= Font.ITALIC;
            }

            Font f = new Font(pdfContext.getBaseFont(), ps.getFooterSize(), a);

            HeaderFooter hf = new HeaderFooter(new Phrase(new Chunk(t, f)), true);

            if ((t = ps.getFooterAlign()) != null) {
                hf.setAlignment(getAlign(t));
            }
            if ((t = ps.getFooterBorder()) != null) {
                hf.setBorder(Integer.parseInt(t));
            }

            pdfDoc.setFooter(hf);
        }
        pdfDoc.setPageSize(ps.getSize());
        pdfDoc.setMargins(ps.getLeftmargin(), ps.getRightmargin(), ps.getTopmargin(), ps.getBottommargin());
        Rectangle rt = pdfDoc.getPageSize();
        //w = rt.width();
        h = rt.height();

        pdfDoc.open();
    }

    private void beginBuild() throws DocumentException {
        int count = 0;

        Element e = r.getTemplate().getBody();
        NodeList nl = e.getChildNodes();
        if (nl != null && (count = nl.getLength()) > 0) {
            for (int i = 0; i < count; i++) {
                com.lowagie.text.Element pe = (com.lowagie.text.Element) beginNode(nl.item(i));
                if (pe != null) {
                    pdfDoc.add(pe);
                }
            }
        }
    }

    private Object beginNode(Node n) throws DocumentException {
        com.lowagie.text.Element pe = null;
        if (n != null) {
            String nodeName = n.getNodeName();

            if (nodeName.compareToIgnoreCase("p") == 0) {
                pe = beginPara(n);
            } else if (nodeName.compareToIgnoreCase("table") == 0) {
                pe = beginTable(n);
            } else if (nodeName.compareToIgnoreCase("#text") == 0) {
                pe = beginText(n);
            } else if (nodeName.compareToIgnoreCase("b") == 0) {
                pe = beginB(n);
            } else if (nodeName.compareToIgnoreCase("i") == 0) {
                pe = beginI(n);
            } else if (nodeName.compareToIgnoreCase("sub") == 0) {
                pe = beginSub(n);
            } else if (nodeName.compareToIgnoreCase("sup") == 0) {
                pe = beginSup(n);
            } else if (nodeName.compareToIgnoreCase("underline") == 0) {
                pe = beginUnderline(n);
            } else if (nodeName.compareToIgnoreCase("nl") == 0) {
                pe = beginNewLine();
            } else if (nodeName.compareToIgnoreCase("font") == 0) {
                pe = beginFont(n);
            } else if (nodeName.compareToIgnoreCase("loop") == 0) {
                pe = beginLoop(n);
            } else if (nodeName.compareToIgnoreCase("data") == 0) {
                pe = beginDi(n);
            } else if (nodeName.compareToIgnoreCase("img") == 0) {
                pe = beginImg(n);
            } else if (nodeName.compareToIgnoreCase("cimg") == 0) {
                pe = beginCImg(n);
            } else if (nodeName.compareToIgnoreCase("text") == 0) {
                pe = beginAbsText(n);
            } else if (nodeName.compareToIgnoreCase("line") == 0) {
                pe = beginLine(n);
            } else if (nodeName.compareToIgnoreCase("hr") == 0) {
                pe = beginHr(n);
            } else if (nodeName.compareToIgnoreCase("np") == 0) {
                pe = beginNewPage();
            }
        }
        return pe;
    }

    private Phrase beginLoop(Node n) throws DocumentException {
        int count = 0;
        int loopCount = 0;

        Phrase p = null;
        Element e = (Element) n;

        NodeList nl = n.getChildNodes();
        if (nl != null && (count = nl.getLength()) > 0) {
            //set the count of Loop;
            String t = e.getAttribute("count");
            if (t.length() > 0) {
            } else {
                loopCount = 1;
            }

            p = new Phrase("", pdfContext.getCurrentFont());

            //begin to loop;
            for (int j = 0; j < loopCount; j++) {
                for (int i = 0; i < count; i++) {
                    Object ck = beginNode(nl.item(i));
                    if (ck != null) {
                        p.add(ck);
                    }
                }
            }
        }

        return p;
    }

    private Paragraph beginPara(Node n) throws DocumentException {
        int count = 0;
        Paragraph p = null;

        NodeList nl = n.getChildNodes();
        if (nl != null && (count = nl.getLength()) > 0) {

            p = new Paragraph("", pdfContext.getCurrentFont());

            for (int i = 0; i < count; i++) {
                com.lowagie.text.Element ck = (com.lowagie.text.Element) beginNode(nl.item(i));
                if (ck != null) {
                    p.add(ck);
                }
            }

            //set the align ment of the para graph;
            Element e = (Element) n;
            String align = e.getAttribute("align");
            if (align.length() > 0) {
                p.setAlignment(align);
            }
        }

        return p;
    }

    private com.lowagie.text.Element beginHr(Node n) {

        float w = 1.0f;
        float l = 80.0f;

        Element e = (Element) n;

        String width = e.getAttribute("width");
        String length = e.getAttribute("length");

        if (length.length() > 0) {
            l = Float.parseFloat(length);
        }
        if (width.length() > 0) {
            w = Float.parseFloat(width);
        }

        Graphic g = new Graphic();
        g.setHorizontalLine(w, l);
        return g;
    }

    private Image beginImg(Node n) throws DocumentException {
        Element e = (Element) n;

        String src = e.getAttribute("src");
        String align = e.getAttribute("align");
        String alt = e.getAttribute("alt");
        String height = e.getAttribute("height");
        String width = e.getAttribute("width");
        String x = e.getAttribute("x");
        String y = e.getAttribute("y");

        if (src.length() > 0) {
            try {
                Image img = Image.getInstance(new URL(src));

                if (img == null) {
                    return null;
                } else {
                    if (align.length() > 0) {
                        img.setAlignment(getAlign(align));
                    }
                    if (alt.length() > 0) {
                        img.setAlt(alt);
                    }
                    if (width.length() > 0) {
                        img.scaleAbsoluteWidth(Float.parseFloat(width));
                    }
                    if (height.length() > 0) {
                        img.scaleAbsoluteWidth(Float.parseFloat(height));
                    }
                    if (x.length() > 0 && y.length() > 0) {
                        img.setAbsolutePosition(Float.parseFloat(x), Float.parseFloat(y));
                    }
                    return img;
                }
            } catch (MalformedURLException me) {
                //throw new DocumentException(me);
                me.printStackTrace();
            } catch (IOException ioe) {
                throw new DocumentException(ioe);
            }
        }
        return null;
    }

    private Image beginCImg(Node n) throws DocumentException {
        return null;
    }

    private Table beginTable(Node n) throws DocumentException {
        int cols = 5;
        int rows = 0;
        int resolvedColWidth = 0;

        Element tb = (Element) n;
        cols = getColsCount(tb);
        float[] w = new float[cols];
        for (int i = 0; i < cols; i++) {
            w[i] = 10;
        }

        String padding = tb.getAttribute("cellpadding");
        String spacing = tb.getAttribute("cellspacing");
        String border = tb.getAttribute("border");
        String width = tb.getAttribute("width");
        String align = tb.getAttribute("align");
        String calign = tb.getAttribute("cellalign");
        String bgColor = tb.getAttribute("bgcolor");
        String borderColor = tb.getAttribute("bordercolor");
        String offset = tb.getAttribute("offset");

        Table table = new Table(cols);
        //push this table to stack for parse tr and td;
        iTextTable.push(table);

        table.setPadding(padding.length() == 0 ? 1 : Integer.parseInt(padding));
        table.setSpacing(spacing.length() == 0 ? 0 : Integer.parseInt(spacing));
        table.setBorder(border.length() == 0 ? 1 : Integer.parseInt(border));
        table.setWidth(width.length() == 0 ? 100 : Float.parseFloat(width));
        if (offset.length() > 0) {
            table.setOffset(Float.parseFloat(offset));
        }

        if (align.length() > 0) {
            table.setAlignment(align);
        }
        if (calign.length() > 0) {
            table.setDefaultHorizontalAlignment(getAlign(calign));
        }
        if (bgColor.length() > 0) {
            table.setDefaultCellBackgroundColor(getColor(bgColor));
        }
        if (borderColor.length() > 0) {
            table.setBorderColor(getColor(borderColor));
        }

        NodeList nl = tb.getChildNodes();
        if (nl != null && (rows = nl.getLength()) > 0) {
            for (int i = 0; i < rows; i++) {
                String nodeName = nl.item(i).getNodeName();
                if (nodeName.compareToIgnoreCase("loop") == 0) {
                    beginTrLoop(nl.item(i), w);
                    continue;
                } else if (nodeName.compareToIgnoreCase("tr") != 0) {
                    continue;
                }
                float[] t = parseTr(nl.item(i));
                if (resolvedColWidth < cols) {
                    for (int j = 0; j < cols; j++) {
                        if (t[j] > 0) {
                            w[j] = t[j];
                            resolvedColWidth++;
                        }
                    }
                }
            }
        }
        table.setWidths(w);
        //pop this table from the stack;
        iTextTable.pop();
        return table;
    }

    private void beginTrLoop(Node loop, float[] width) throws DocumentException {
//        int rows = 0;
//        int from = 0;
//        int to = 1;
//        int cols = width.length;
//        int resolvedColWidth = 0;
//        try {
//            from = Integer.parseInt(fromStr);
//            to = Integer.parseInt(toStr);
//        } catch (ParseException pe) {
//            throw new DocumentException(pe);
//        }
//        String id = ((Element) loop).getAttribute("id");
//
//        NodeList nl = loop.getChildNodes();
//        if (nl != null && (rows = nl.getLength()) > 0) {
//            for (int k = from; k < to; k++) {
//                //save the current index for use by the td;
//                data.add(id, new Integer(k));
//                for (int i = 0; i < rows; i++) {
//                    String nodeName = nl.item(i).getNodeName();
//                    if (nodeName.compareToIgnoreCase("tr") != 0) {
//                        continue;
//                    }
//                    float[] t = parseTr(nl.item(i));
//                    if (resolvedColWidth < cols) {
//                        for (int j = 0; j < cols; j++) {
//                            if (t[j] > 0) {
//                                width[j] = t[j];
//                                resolvedColWidth++;
//                            }
//                        }
//                    }
//                }
//            }
//        }
    }

    private Phrase beginTd(Node n) throws DocumentException {
        int count = 0;
        NodeList nl = n.getChildNodes();
        Phrase p = new Phrase();
        if (nl != null && (count = nl.getLength()) > 0) {
            for (int i = 0; i < count; i++) {
                com.lowagie.text.Element c = (com.lowagie.text.Element) beginNode(nl.item(i));
                if (c != null) {
                    p.add(c);
                }
            }
        }
        return p;
    }

    private Phrase beginB(Node n) throws DocumentException {
        int count = 0;
        Phrase p = null;
        NodeList nl = n.getChildNodes();
        int nodeID = getNextId();

        if (nl != null && (count = nl.getLength()) > 0) {
            pdfContext.pushBold(nodeID, true);
            p = new Phrase("", pdfContext.getCurrentFont());
            for (int i = 0; i < count; i++) {
                com.lowagie.text.Element ck = (com.lowagie.text.Element) beginNode(nl.item(i));
                if (ck != null) {
                    p.add(ck);
                }
            }
            pdfContext.pop(nodeID);
        }

        return p;
    }

    private Phrase beginI(Node n) throws DocumentException {
        int count = 0;
        Phrase p = null;

        int nodeID = getNextId();
        NodeList nl = n.getChildNodes();
        if (nl != null && (count = nl.getLength()) > 0) {
            pdfContext.pushItaly(nodeID, true);
            p = new Phrase("", pdfContext.getCurrentFont());
            for (int i = 0; i < count; i++) {
                com.lowagie.text.Element ck = (com.lowagie.text.Element) beginNode(nl.item(i));
                if (ck != null) {
                    p.add(ck);
                }
            }
            pdfContext.pop(nodeID);
        }

        return p;
    }

    private Chunk beginNewLine() {
        return Chunk.NEWLINE;
    }

    private Chunk beginNewPage(){
        Chunk ck = new Chunk(Chunk.NEWPAGE);
        return ck.setNewPage();
    }

    private Phrase beginUnderline(Node n) throws DocumentException {
        int count = 0;
        Phrase p = null;

        int nodeID = getNextId();
        NodeList nl = n.getChildNodes();
        if (nl != null && (count = nl.getLength()) > 0) {
            pdfContext.pushUnderline(nodeID, true);
            p = new Phrase("", pdfContext.getCurrentFont());
            for (int i = 0; i < count; i++) {
                com.lowagie.text.Element ck = (com.lowagie.text.Element) beginNode(nl.item(i));
                if (ck != null) {
                    p.add(ck);
                }
            }
            pdfContext.pop(nodeID);
        }
        return p;
    }

    private Phrase beginSub(Node n) throws DocumentException {
        int count = 0;
        Phrase p = null;

        int nodeID = getNextId();
        NodeList nl = n.getChildNodes();
        if (nl != null && (count = nl.getLength()) > 0) {
            pdfContext.pushSub(nodeID, true);
            p = new Phrase("", pdfContext.getCurrentFont());
            for (int i = 0; i < count; i++) {
                com.lowagie.text.Element ck = (com.lowagie.text.Element) beginNode(nl.item(i));
                if (ck != null) {
                    p.add(ck);
                }
            }
            pdfContext.pop(nodeID);
        }

        return p;
    }

    private Phrase beginSup(Node n) throws DocumentException {
        int count = 0;
        Phrase p = null;

        int nodeID = getNextId();
        NodeList nl = n.getChildNodes();
        if (nl != null && (count = nl.getLength()) > 0) {
            pdfContext.pushSup(nodeID, true);
            p = new Phrase("", pdfContext.getCurrentFont());
            for (int i = 0; i < count; i++) {
                com.lowagie.text.Element ck = (com.lowagie.text.Element) beginNode(nl.item(i));
                if (ck != null) {
                    p.add(ck);
                }
            }
            pdfContext.pop(nodeID);
        }

        return p;
    }

    private Phrase beginFont(Node n) throws DocumentException {
        int count = 0;
        String t = null;
        Phrase p = null;

        Element e = (Element) n;

        int nodeID = getNextId();
        NodeList nl = n.getChildNodes();
        if (nl != null && (count = nl.getLength()) > 0) {
            t = e.getAttribute("size");
            if (t.length() > 0) {
                pdfContext.pushSize(nodeID, Integer.parseInt(t));
            }
            t = e.getAttribute("name");
            if (t.length() > 0) {
                pdfContext.pushName(nodeID, t);
            }
            t = e.getAttribute("bold");
            if (t.length() > 0) {
                pdfContext.pushBold(nodeID, t.compareToIgnoreCase("true") == 0 ? true : false);
            }
            t = e.getAttribute("italy");
            if (t.length() > 0) {
                pdfContext.pushItaly(nodeID, t.compareToIgnoreCase("true") == 0 ? true : false);
            }
            t = e.getAttribute("underline");
            if (t.length() > 0) {
                pdfContext.pushUnderline(nodeID, t.compareToIgnoreCase("true") == 0 ? true : false);
            }
            t = e.getAttribute("sub");
            if (t.length() > 0) {
                pdfContext.pushSub(nodeID, t.compareToIgnoreCase("true") == 0 ? true : false);
            }
            t = e.getAttribute("sup");
            if (t.length() > 0) {
                pdfContext.pushSup(nodeID, t.compareToIgnoreCase("true") == 0 ? true : false);
            }

            p = new Phrase("", pdfContext.getCurrentFont());
            for (int i = 0; i < count; i++) {
                com.lowagie.text.Element ck = (com.lowagie.text.Element) beginNode(nl.item(i));
                if (ck != null) {
                    p.add(ck);
                }
            }
            pdfContext.pop(nodeID);
        }

        return p;
    }

    private Chunk beginDi(Node n) throws DocumentException {
//        Element e = (Element) n;
//        String displayName = e.getAttribute("displayName");
//        String field = e.getAttribute("field");
//        String row = e.getAttribute("row");
//        String attr = e.getAttribute("attr");
//        String format = e.getAttribute("format");
//
//        if (displayName.length() == 0) {
//            return null;
//        } else {
//            try {
//                displayName = ExpressionResolver.resolveStringExp(displayName, data);
//            } catch (ParseException pe) {
//                throw new DocumentException(pe);
//            }
//
//            if (field.length() == 0) {
//                if (attr.length() == 0) {
//                    return new Chunk(Util.formatValue(data.getString(displayName), format), pdfContext.getCurrentFont());
//                } else {
//                    return new Chunk(Util.formatValue(data.getAttr(displayName, attr), format), pdfContext.getCurrentFont());
//                }
//            } else {
//                if (row.length() == 0) {
//                    if (attr.length() == 0) {
//                        return new Chunk(Util.formatValue(data.get(displayName, field, "0"), format), pdfContext.getCurrentFont());
//                    } else {
//                        return new Chunk(Util.formatValue(data.getAttr(displayName, field, "0", attr), format), pdfContext.getCurrentFont());
//                    }
//                } else {
//                    try {
//                        row = ExpressionResolver.resolveStringExp(row, data);
//                    } catch (ParseException pe) {
//                        throw new DocumentException(pe);
//                    }
//
//                    if (attr.length() == 0) {
//                        return new Chunk(Util.formatValue(data.get(displayName, field, row), format), pdfContext.getCurrentFont());
//                    } else {
//                        return new Chunk(Util.formatValue(data.getAttr(displayName, field, row, attr), format), pdfContext.getCurrentFont());
//                    }
//                }
//            }
//        }
        return null;
    }

    private Chunk beginText(Node n) {
        String text = n.getNodeValue();
        if (text != null) {
            text = text.trim();
            if (text.length() > 0) {
                Chunk ck = new Chunk(n.getNodeValue(), pdfContext.getCurrentFont());
                int size = pdfContext.getSize();
                if (pdfContext.isSub()) {
                    ck.setTextRise(size / 2.0f * (-1));
                } else if (pdfContext.isSup()) {
                    ck.setTextRise(size / 2.0f);
                }
                return ck;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    private Graphic beginLine(Node n) {
        Element e = (Element) n;

        String x0 = e.getAttribute("x0");
        String y0 = e.getAttribute("y0");
        String x1 = e.getAttribute("x1");
        String y1 = e.getAttribute("y1");
        PdfContentByte cb2 = pdfWriter.getDirectContent();

        cb2.setLineWidth(1);
        cb2.newPath();
        cb2.moveTo(Float.parseFloat(x0), h - Float.parseFloat(y0));
        cb2.lineTo(Float.parseFloat(x1), h - Float.parseFloat(y1));
        cb2.stroke();
        return null;
    }

    private Chunk beginAbsText(Node n) throws DocumentException {
        String text = null;
        Element e = (Element) n;

        String x = e.getAttribute("x");
        String y = e.getAttribute("y");
        String align = e.getAttribute("align");

        n = e.getFirstChild();
        if (n != null) {
            text = n.getNodeValue();
        }
        if (text == null || text.length() == 0) {
            return null;
        }

        if (x.length() == 0 || y.length() == 0) {
            Chunk ck = new Chunk(n.getNodeValue(), pdfContext.getCurrentFont());
            int size = pdfContext.getSize();
            if (pdfContext.isSub()) {
                ck.setTextRise(size / 2.0f * (-1));
            } else if (pdfContext.isSup()) {
                ck.setTextRise(size / 2.0f);
            }
            return ck;
        } else {
            PdfContentByte cb = pdfWriter.getDirectContent();
            cb.beginText();
            try {
                cb.setFontAndSize(pdfContext.getBaseFont(), pdfContext.getSize());
                cb.showTextAligned(getAlign(align), text, Float.parseFloat(x), Float.parseFloat(y), 0);
                cb.endText();
            } catch (IOException ioe) {
                throw new DocumentException(ioe);
            }
            return null;
        }
    }

    private float[] parseTr(Node tr) throws DocumentException {

        Table table = (Table) iTextTable.peek();     //should not be null;

        int cols = table.columns();
        int colIndex = 0;
        float[] colWidth = new float[cols];

        int tds = 0;
        Cell cell = null;
        NodeList nl = tr.getChildNodes();
        if (nl != null && (tds = nl.getLength()) > 0) {
            for (int i = 0; i < tds; i++) {
                if (nl.item(i).getNodeName().compareToIgnoreCase("td") != 0) {
                    continue;
                }
                Element td = (Element) nl.item(i);
                String align = td.getAttribute("align");
                String valign = td.getAttribute("valign");
                String rowspan = td.getAttribute("rowspan");
                String colspan = td.getAttribute("colspan");
                String bgColor = td.getAttribute("bgcolor");
                if (colspan.length() == 0) {
                    String t = td.getAttribute("width");
                    colWidth[colIndex] = t.length() == 0 ? 10 : Float.parseFloat(t);
                }

                Phrase ck = beginTd(td);

                cell = new Cell(ck);
                cell.setColspan(colspan.length() == 0 ? 1 : Integer.parseInt(colspan));
                cell.setRowspan(rowspan.length() == 0 ? 1 : Integer.parseInt(rowspan));

                if (align.length() > 0) {
                    cell.setHorizontalAlignment(align);
                }
                if (valign.length() > 0) {
                    cell.setVerticalAlignment(valign);
                }
                if (bgColor.length() > 0) {
                    cell.setBackgroundColor(getColor(bgColor));
                }
                table.addCell(cell);

                colIndex += colspan.length() == 0 ? 1 : Integer.parseInt(colspan);
            }
        }
        return colWidth;
    }

    private int getColsCount(Element table) {
        int count = 0;
        int cols = 0;
        Node tr = null;
        NodeList nl = null;

        XMLFile xml = r.getTemplate().getXMLFile();

        tr = xml.selectSingleNode(table, ".//tr[1]");
        if (tr != null) {
            nl = tr.getChildNodes();
            if (nl != null && (count = nl.getLength()) > 0) {
                for (int i = 0; i < count; i++) {
                    Node e = nl.item(i);
                    if (e.getNodeName().compareToIgnoreCase("td") != 0) {
                        continue;
                    } else {
                        String t = ((Element) e).getAttribute("colspan");
                        if (t.length() > 0) {
                            cols += Integer.parseInt(t);
                        } else {
                            cols += 1;
                        }
                    }
                }
            }
        }
        return cols;
    }

    private int getNextId() {
        return nodeID++;
    }

    private int getAlign(String align) {
        if (align.compareToIgnoreCase("left") == 0) {
            return com.lowagie.text.Element.ALIGN_LEFT;
        } else if (align.compareToIgnoreCase("center") == 0) {
            return com.lowagie.text.Element.ALIGN_CENTER;
        } else if (align.compareToIgnoreCase("right") == 0) {
            return com.lowagie.text.Element.ALIGN_RIGHT;
        } else if (align.compareToIgnoreCase("middle") == 0) {
            return com.lowagie.text.Element.ALIGN_MIDDLE;
        } else if (align.compareToIgnoreCase("top") == 0) {
            return com.lowagie.text.Element.ALIGN_TOP;
        } else if (align.compareToIgnoreCase("bottom") == 0) {
            return com.lowagie.text.Element.ALIGN_BOTTOM;
        } else if (align.compareToIgnoreCase("justified") == 0) {
            return com.lowagie.text.Element.ALIGN_JUSTIFIED;
        } else if (align.compareToIgnoreCase("justified_all") == 0) {
            return com.lowagie.text.Element.ALIGN_JUSTIFIED_ALL;
        } else {
            return com.lowagie.text.Element.ALIGN_UNDEFINED;
        }
    }

    private static Color getColor(String color) {
        return Color.getColor(color);
    }
}