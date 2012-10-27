/**
 *all rights reserved,@copyright 2003
 */
package com.dream.bizsdk.common.report;

import com.lowagie.text.Rectangle;
import com.lowagie.text.PageSize;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-7-4
 * Time: 12:52:23
 */
public class PdfPageSetting {
    private Rectangle size = PageSize.A4;
    private float leftmargin = 48.0f;
    private float rightmargin = 48.0f;
    private float topmargin = 48.0f;
    private float bottommargin = 36.0f;
    private String header;
    private String headerAlign;
    private String headerBorder = "2";
    private int headerSize = 10;
    private boolean headerBold;
    private boolean headerItaly;

    private String footer;
    private String footerAlign;
    private String footerBorder;
    private int footerSize = 10;
    private boolean footerBold;
    private boolean footerItaly;

    public float getLeftmargin() {
        return leftmargin;
    }

    public void setLeftmargin(float leftmargin) {
        this.leftmargin = leftmargin;
    }

    public float getTopmargin() {
        return this.topmargin;
    }

    public void setTopmargin(float topmargin) {
        this.topmargin = topmargin;
    }

    public float getRightmargin() {
        return this.rightmargin;
    }

    public void setRightmargin(float rightmargin) {
        this.rightmargin = rightmargin;
    }

    public float getBottommargin() {
        return this.bottommargin;
    }

    public void setBottommargin(float bottommargin) {
        this.bottommargin = bottommargin;
    }

    public Rectangle getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = PdfPageSetting.fromString(size);
    }

    public String getHeader() {
        return this.header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getFooter() {
        return this.footer;
    }

    public void setFooter(String footer) {
        this.footer = footer;
    }

    public String getHeaderAlign() {
        return headerAlign;
    }

    public void setHeaderAlign(String align) {
        this.headerAlign = align;
    }

    public String getFooterAlign() {
        return footerAlign;
    }

    public void setFooterAlign(String align) {
        footerAlign = align;
    }

    public String getHeaderBorder() {
        return headerBorder;
    }

    public void setHeaderBorder(String headerBorder) {
        this.headerBorder = headerBorder;
    }

    public void setHeaderSize(int size) {
        this.headerSize = size;
    }

    public int getHeaderSize() {
        return headerSize;
    }

    public void setFooterSize(int size) {
        this.footerSize = size;
    }

    public int getFooterSize() {
        return footerSize;
    }

    public void setHeaderBold(boolean bold) {
        this.headerBold = bold;
    }

    public void setHeaderItaly(boolean italy) {
        this.headerItaly = italy;
    }

    public void setFooterBold(boolean bold) {
        this.footerBold = bold;
    }

    public void setFooterItaly(boolean italy) {
        this.footerItaly = italy;
    }

    public boolean isHeaderBold() {
        return headerBold;
    }

    public boolean isFooterItaly() {
        return footerItaly;
    }

    public boolean isFooterBold() {
        return footerBold;
    }

    public boolean isHeaderItaly() {
        return headerItaly;
    }

    public String getFooterBorder() {
        return footerBorder;
    }

    public void setFooterBorder(String border) {
        this.footerBorder = border;
    }

    public static Rectangle fromString(String size) {
        if (size == null) {
            return PageSize.A4;
        } else {
            if (size.compareToIgnoreCase("A0") == 0) {
                return PageSize.A0;
            } else if (size.compareToIgnoreCase("A1") == 0) {
                return PageSize.A1;
            } else if (size.compareToIgnoreCase("A2") == 0) {
                return PageSize.A2;
            } else if (size.compareToIgnoreCase("A3") == 0) {
                return PageSize.A3;
            } else if (size.compareToIgnoreCase("A4") == 0) {
                return PageSize.A4;
            } else if (size.compareToIgnoreCase("A5") == 0) {
                return PageSize.A5;
            } else if (size.compareToIgnoreCase("A6") == 0) {
                return PageSize.A6;
            } else if (size.compareToIgnoreCase("A7") == 0) {
                return PageSize.A7;
            } else if (size.compareToIgnoreCase("A8") == 0) {
                return PageSize.A8;
            } else if (size.compareToIgnoreCase("A9") == 0) {
                return PageSize.A9;
            } else if (size.compareToIgnoreCase("A10") == 0) {
                return PageSize.A10;
            } else if (size.compareToIgnoreCase("B0") == 0) {
                return PageSize.B0;
            } else if (size.compareToIgnoreCase("B1") == 0) {
                return PageSize.B1;
            } else if (size.compareToIgnoreCase("B2") == 0) {
                return PageSize.B2;
            } else if (size.compareToIgnoreCase("B3") == 0) {
                return PageSize.B3;
            } else if (size.compareToIgnoreCase("B4") == 0) {
                return PageSize.B4;
            } else if (size.compareToIgnoreCase("B5") == 0) {
                return PageSize.B5;
            } else {
                return PageSize.A4;
            }
        }
    }
}
