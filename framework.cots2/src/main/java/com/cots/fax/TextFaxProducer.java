package com.cots.fax;

import java.awt.*;


public class TextFaxProducer
        implements FaxProducer {

    public Font textFont;
    public String text;
    public int linesPage;
    public int topMargin;
    public int leftMargin;
    public int rightMargin;
    public int bottomMargin;
    public Image pageImage;
    private int index = 0;

    /**
     *
     */
    public TextFaxProducer() {
        textFont = new Font("Serif", 0, 12);
        text = "";
        linesPage = 66;
        topMargin = 2;  //two lines;
        bottomMargin = 2;   //two lines;
        leftMargin = 4; //four chars;
        rightMargin = 4; //four chars;

        pageImage = null;
    }

    /**
     * @param i
     * @return
     */
    public Image getFaxPage(int i) {
        return getPageImage();
    }

    /**
     * @return
     */
    private Image getPageImage() {
        int pw;
        int ph;
        int lineWidth = 0;
        Graphics g = pageImage.getGraphics();
        g.setColor(Color.white);
        g.fillRect(0, 0, pw = pageImage.getWidth(null), ph = pageImage.getHeight(null));
        g.setColor(Color.black);
        g.setFont(textFont);
        int fh = g.getFontMetrics().getHeight();
        int fw = g.getFontMetrics().stringWidth("X");
        int l = ph / fh - topMargin - bottomMargin;
        if (l < linesPage) {
            linesPage = l;
        }
        lineWidth = pw - (leftMargin + rightMargin) * fw;

        int textLength = text.length();
        int lines = 0;

        char[] chs = new char[1000];
        int i1 = fh * topMargin;

        if (index >= textLength) {
            index = 0;
            return null;
        }

        while (index < textLength && lines < l) {
            int currentLineWidth = 0;
            int lineChars = 0;
            while (index < textLength && currentLineWidth < lineWidth) {
                char ch = text.charAt(index++);
                if (ch == '\r' || ch == '\n') {   //\r \n char
                    if (index < textLength) {
                        char ch2 = text.charAt(index);
                        if ((ch2 == '\r' || ch2 == '\n') && ch2 != ch) {
                            index++;
                        }
                    }
                    break;
                } else {
                    if (ch == '\t') {   //tab char;
                        for (int c = 0; c < 4; c++) {
                            chs[lineChars++] = ' ';
                            currentLineWidth += g.getFontMetrics().charWidth(' ');
                        }
                    } else {  //other chars;
                        chs[lineChars++] = ch;
                        currentLineWidth += g.getFontMetrics().charWidth(ch);
                    }
                }
            }

            lines++;
            g.drawChars(chs, 0, lineChars, leftMargin * fw, i1);
            i1 += fh;
        }

        return pageImage;
    }
}