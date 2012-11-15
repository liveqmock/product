/**
 *all rights reserved,@copyright 2003
 */
package com.cots.fax;

import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

import java.awt.*;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-9-4
 * Time: 13:10:57
 */
public class FaxProxy {
    private String fromNO = "58956500";
    private boolean dialTone = true;
    private String modemClass = "class1";
    private String modemInitialString = "ATQ0V1E0";
    private String port = "COM3";
    private String flowControl = "rtscts";
    private String flowControlCommand = "AT+FLO=1";

    private Logger log = Logger.getLogger("fax");

    private int bitRate = 9600;

    public FaxProxy() {

    }

    public void setFromNO(String fromNO) {
        this.fromNO = fromNO;
    }

    public String getFromNO() {
        return this.fromNO;
    }

    public void setDialTone(boolean v) {
        this.dialTone = v;
    }

    public boolean isDiaoTome() {
        return this.dialTone;
    }

    public void setModemClass(String c) {
        this.modemClass = c;
    }

    public String getModemClass() {
        return this.modemClass;
    }

    public void setInitialString(String is) {
        this.modemInitialString = is;
    }

    public String getInitialString() {
        return this.modemInitialString;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getPort() {
        return this.port;
    }

    public void setFlowControl(String fc) {
        this.flowControl = fc;
    }

    public String getFlowControl() {
        return this.flowControl;
    }

    public void setFlowControlCommand(String fcc) {
        this.flowControlCommand = fcc;
    }

    public String getFlowControlCommand() {
        return this.flowControlCommand;
    }

    public void setBitRate(int bitRate) {
        this.bitRate = bitRate;
    }

    public int getBitRate() {
        return this.bitRate;
    }

    public boolean sendText(String telNo, String title, String text) {
        try {
            return send(telNo, title, text, false);
        } catch (Throwable t) {
            t.printStackTrace();
            return false;
        }
    }

    public boolean sendHtml(String telNo, String title, String text) {
        try {
            return send(telNo, title, text, true);
        } catch (Throwable t) {
            t.printStackTrace();
            return false;
        }
    }

    /**
     * @param telNo
     * @param title
     * @param text
     * @param isHtml
     * @return
     */
    protected boolean send(String telNo, String title, String text, boolean isHtml) {
        FaxModem fm = new FaxModem();
        Panel imgC = new Panel();
        imgC.setBounds(new Rectangle(0, 0, 800, 550));
        imgC.setBackground(Color.black);

        fm.setPortName(port);
        fm.setInitString(modemInitialString);
        if (modemClass.equalsIgnoreCase("class1")) {
            fm.faxClass = 1;
        } else if (modemClass.equalsIgnoreCase("class2")) {
            fm.faxClass = 2;
        } else if (modemClass.equalsIgnoreCase("class20")) {
            fm.faxClass = 20;
        } else {
            fm.faxClass = 1;
        }
        if (flowControl.equalsIgnoreCase("none")) {
            fm.flowControl = FaxModem.FLOWCONTROL_NONE;
            fm.ATFlowControlNone = ""; // no command needed
            if (flowControlCommand != null) {
                fm.ATFlowControlNone = flowControlCommand;
            }
        } else if (flowControl.equalsIgnoreCase("rtscts")) {
            fm.flowControl = FaxModem.FLOWCONTROL_RTSCTS;
            fm.ATFlowControlRTSCTS = ""; // no command needed
            if (flowControlCommand != null) {
                fm.ATFlowControlRTSCTS = flowControlCommand;
            }
        } else if (flowControl.equalsIgnoreCase("xonxoff")) {
            fm.flowControl = FaxModem.FLOWCONTROL_XONXOFF;
            fm.ATFlowControlXONXOFF = ""; // no command needed
            if (flowControlCommand != null) {
                fm.ATFlowControlXONXOFF = flowControlCommand;
            }
        } else {
            fm.flowControl = FaxModem.FLOWCONTROL_NONE;
            fm.ATFlowControlNone = ""; // no command needed
            if (flowControlCommand != null) {
                fm.ATFlowControlNone = flowControlCommand;
            }
        }

        fm.dialTone = dialTone;
        fm.debug = false;

        if (bitRate == 2400) {
            fm.bitRate = FaxModem.BITRATE_2400;
        } else if (bitRate == 4800) {
            fm.bitRate = FaxModem.BITRATE_4800;
        } else if (bitRate == 7200) {
            fm.bitRate = FaxModem.BITRATE_7200;
        } else if (bitRate == 9600) {
            fm.bitRate = FaxModem.BITRATE_9600;
        } else if (bitRate == 12000) {
            fm.bitRate = FaxModem.BITRATE_12000;
        } else if (bitRate == 14400) {
            fm.bitRate = FaxModem.BITRATE_14400;
        }

        if (isHtml) {
            fm.producer = new HtmlFaxProducer();
            ImageFrame ifm = new ImageFrame();
            ifm.setVisible(true);
            ((HtmlFaxProducer) fm.producer).pageImage = ifm.createImage(800, 550);

            ifm.setVisible(false);
            ifm.dispose();
        } else {
            fm.producer = new TextFaxProducer();
            ImageFrame ifm = new ImageFrame();
            ifm.setVisible(true);
            ((TextFaxProducer) fm.producer).pageImage = ifm.createImage(800, 550);

            ((TextFaxProducer) fm.producer).text = title + "\n" + "from:\t" + fromNO +
                    "\t\t\t\t\t\t" + "to:\t" + telNo + "\n\n" + text;

            ifm.setVisible(false);
            ifm.dispose();
        }

        fm.ownId = fromNO;

        fm.getEncoder().scaleFactor = 2;
        fm.getEncoder().centerImage = true;

        fm.directBitOrder = true;
        fm.log = false;

        fm.resolution = FaxModem.RESOLUTION_NORMAL;
        fm.timeout = 60;

        fm.AtFBOR = true;
        boolean succ = false;

        try {
            if (fm.open(fm.producer)) {
                succ = fm.sendFax(telNo);

                fm.close();
            }

            if (succ) {
                if (log.isInfoEnabled()) {
                    log.info("fax with title '" + title + "' sent to telephone number '" + telNo + "'");
                }
            } else {
                if (log.isEnabledFor(Priority.ERROR)) {
                    log.error("failed to sendBody fax with title '" + title + "' to telephone number '" + telNo + "'");
                }
            }
        } catch (Exception e) {
            if (log.isEnabledFor(Priority.ERROR)) {
                log.error("failed to sendBody fax with title '" + title + "' to telephone number '" + telNo + "'", e);
            }
        }
        return succ;
    }

    public static void main(String[] argc) {
        FaxProxy fp = new FaxProxy();
        fp.sendText("50801900", "", "Hello");
    }
}