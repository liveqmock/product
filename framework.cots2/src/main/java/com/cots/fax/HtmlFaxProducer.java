package com.cots.fax;

import java.awt.*;

import javax.swing.JEditorPane;


public class HtmlFaxProducer
        implements FaxProducer {

    public String text[];
    public Image pageImage;
    private JEditorPane a;

    public HtmlFaxProducer() {
        pageImage = null;
        a = new JEditorPane();
    }

    public Image getFaxPage(int i) {
        if (text == null) {
            return null;
        }
        if (i >= text.length) {
            return null;
        } else {
            return a(i);
        }
    }

    private Image a(int i) {
        Graphics g = pageImage.getGraphics();
        a.setSize(pageImage.getWidth(null), pageImage.getHeight(null));
        a.setBackground(Color.white);
        a.setEditable(false);
        a.setContentType("text/html");
        a.setText(text[i]);
        g.setClip(0, 0, pageImage.getWidth(null), pageImage.getHeight(null));
        a.paint(g);
        return pageImage;
    }
}