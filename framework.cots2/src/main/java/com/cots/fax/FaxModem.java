package com.cots.fax;

import java.io.*;
import java.util.Calendar;
import javax.comm.*;


public class FaxModem {

    CommPortIdentifier com;
    SerialPort sPort;
    OutputStream os;
    InputStream is;
    private byte e[];
    String _fldnull;
    boolean m;
    boolean l;
    boolean f;
    boolean t;
    boolean u;
    boolean c;
    String p;
    private static double _fldvoid = 1.5D;
    private b j;
    public int maxRetries;
    public static final int BITRATE_2400 = 0;
    public static final int BITRATE_4800 = 1;
    public static final int BITRATE_7200 = 2;
    public static final int BITRATE_9600 = 3;
    public static final int BITRATE_12000 = 4;
    public static final int BITRATE_14400 = 5;
    private int b[] = {
        24, 48, 72, 96, 121, 145
    };
    private String vVersion[] = {
        "V27", "V27", "V29", "V29", "V17", "V17"
    };
    public int bitRate;
    public int resolution;
    public static final int RESOLUTION_NORMAL = 0;
    public static final int RESOLUTION_FINE = 1;
    public String faxFile;
    public String lastError;
    public String resetCommand;
    public int timeout;
    public int resetDelay;
    public int lastResponse;
    public int pageCode;
    public int hangCode;
/*    private String A[][] = {
        {
            " 0", " None"
        }, {
            " 1", " Ring Detect without successful handshake"
        }, {
            " 2", " Call aborted; from +FK or AN"
        }, {
            " 3", " No Loop Current"
        }, {
            " 10", " Unspecified Phase A error"
        }, {
            " 11", " No Answer (T.30 T1 timeout)"
        }, {
            " 20", " Unspecified Transmit Phase B error"
        }, {
            " 21", " Remote cannot receive or sendBody"
        }, {
            " 22", " COMREC error in transmit Phase B"
        }, {
            " 23", " COMREC invalid command received"
        }, {
            " 24", " RSPEC error"
        }, {
            " 25", " DCS sent three times without response"
        }, {
            " 26", " DIS/DTC received 3 times; DCS not recognised"
        }, {
            " 27", " Failure to train at 2400bps or +FMINSP value"
        }, {
            " 28", " RSPREC invalid response received"
        }, {
            " 40", " Unspecified Transmit Phase C error"
        }, {
            " 43", " DTE to DCE  underflow"
        }, {
            " 50", " Unspecified Transmit Phase D error"
        }, {
            " 51", " RSPREC error"
        }, {
            " 52", " No response to MPS repeated three times"
        }, {
            " 53", " Invalid response to MPS"
        }, {
            " 54", " No response to EOP repeated three times"
        }, {
            " 55", " Invalid response to EOP"
        }, {
            " 56", " No response to EOM repeated three times"
        }, {
            " 57", " Invalid response to EOM"
        }, {
            " 58", " Unable to continue after PIN or PIP"
        }
    };
*/
    public static final int FLOWCONTROL_NONE = 0;
    public static final int FLOWCONTROL_XONXOFF = 1;
    public static final int FLOWCONTROL_RTSCTS = 2;
    public int flowControl;
    public String ATFlowControlNone;
    public String ATFlowControlXONXOFF;
    public String ATFlowControlRTSCTS;
    private String B;
    public boolean directBitOrder;
    public boolean AtFBOR;
    public boolean dialTone;
    public int faxClass;
    public String ownId;
    public String noEcho;
    public boolean log;
    public boolean debug;
    public String logStr;
    public FaxProducer producer;
    public String initCommands[];
    private int _fldcase;
    private T4Encoder t4Encoder;
    public FaxStatusListener listener;
    public int MPSEOPdelay;
    public int sendBufferSize;
    long _fldgoto;
    long z;

    private Frame readResponse(int i1) throws Exception {
        Calendar calendar = Calendar.getInstance();
        long l1 = calendar.getTime().getTime();
        String s1 = "";
        boolean flag = false;
        boolean flag1 = false;
        byte abyte0[] = new byte[1024];
        int j1 = 0;
        try {
            int k1 = is.read();
            do {
                long l4 = Calendar.getInstance().getTime().getTime();
                if (l4 - l1 > (long) i1) {
                    _mthdo();
                    lastResponse = 9;
                    lastError = "Timeout waiting for frame";
                    return null;
                }
                if (k1 == 13) {
                    if (s1.indexOf("ERROR") >= 0) {
                        lastResponse = 1;
                        return null;
                    }
                    if (s1.indexOf("FCERROR") >= 0) {
                        lastResponse = 1;
                        return null;
                    }
                    if (s1.indexOf("OK") >= 0) {
                        lastResponse = 10;
                        return null;
                    }
                    s1 = "";
                }
                if (k1 == 255 && !flag1) {
                    flag1 = true;
                }
                if (flag1) {
                    if (!flag) {
                        if (k1 == 16) {
                            flag = true;
                        } else {
                            abyte0[j1++] = (byte) k1;
                        }
                    } else {
                        if (k1 == 16) {
                            flag = false;
                            abyte0[j1++] = (byte) k1;
                        }
                        if (k1 == 3) {
                            break;
                        }
                    }
                }
                k1 = is.read();
                if (k1 != 10 && k1 != 13) {
                    s1 = s1 + (char) k1;
                }
            } while (true);
        } catch (Exception exception) {
            throw exception;
        }
        if (j1 == 0) {
            return null;
        } else {
            return new Frame(abyte0, j1);
        }
    }

    private boolean connect(String s1) throws Exception {
        String s2 = "P";
        if (dialTone) {
            s2 = "T";
        }
        if (faxClass == 1) {
            if (!writeCmd("ATD" + s2 + s1)) {
                return false;
            }
            waitFor("CONNECT", false);
        } else if (!postCmd("ATD" + s2 + s1)) {
            return false;
        }
        return lastResponse == 5;
    }

    public void setPortName(String s1) {
        _fldnull = s1;
    }

    private void a(b b1) {
        int i1 = b[bitRate];
        if (i1 < b1._fldnew) {
            b1._fldnew = i1;
            b1._fldtry = vVersion[bitRate];
        }
        if (b1._fldfor == 1 && resolution == 0) {
            b1._fldfor = 0;
        }
        if (t4Encoder.lineWidth < b1.a) {
            b1.a = t4Encoder.lineWidth;
        }
        b1._fldif = 0;
        b1._fldcase = 1;
    }

    private void log2(String s1) {
        logStr = logStr + "\n" + s1;
        if (debug) {
            System.out.println(s1);
        }
    }

    private void a(int i1) {
        z = i1;
        Calendar calendar = Calendar.getInstance();
        _fldgoto = calendar.getTime().getTime();
    }

    public boolean open(FaxProducer faxproducer) throws Exception {
        if (!createFaxFile(faxproducer)) {
            throw new Exception("Could not create fax files.");
        }
        if (listener != null) {
            listener.faxProgress(1, 0);
        }
        try {
            com = CommPortIdentifier.getPortIdentifier(_fldnull);
        } catch (Exception exception) {
            throw exception;
        }
        if (com == null) {
            throw new Exception("Could not find port " + _fldnull);
        }
        if (com.getPortType() != 1) {
            throw new Exception("" + _fldnull + " is not a serial port");
        }
        try {
            sPort = (SerialPort) com.open("RFax", 2000);
        } catch (PortInUseException e) {
            throw e;
        }
        try {
            sPort.enableReceiveTimeout(1000);
        } catch (Exception exception1) {

        }
        try {
            os = sPort.getOutputStream();
            is = sPort.getInputStream();
        } catch (IOException e) {
            throw e;
        }
        try {
            sPort.setSerialPortParams(19200, 8, 1, 0);
        } catch (UnsupportedCommOperationException e) {
            throw new Exception("Cound't not config port", e);
        }
        if (listener != null) {
            listener.faxProgress(2, 0);
        }
        if (!postCmd(resetCommand)) {
            throw new Exception("Could not reset modem with ATZ");
        }
        if (resetDelay > 0) {
            sleep(resetDelay);
        }
        if (flowControl == 2 && ATFlowControlRTSCTS.length() >= 0) {
            postCmd(ATFlowControlRTSCTS);
        }
        if (flowControl == 1 && ATFlowControlXONXOFF.length() > 0) {
            postCmd(ATFlowControlXONXOFF);
        }
        if (flowControl == 0 && ATFlowControlNone.length() > 0) {
            postCmd(ATFlowControlNone);
        }
        try {
            if (flowControl == 2) {
                sPort.setFlowControlMode(2);
            }
            if (faxClass != 1 && flowControl == 1) {
                sPort.setFlowControlMode(8);
            }
            if (flowControl == 0) {
                sPort.setFlowControlMode(0);
            }
        } catch (Exception e) {
            throw e;
        }
        if (log) {
            if ((sPort.getFlowControlMode() & 8) > 0) {
                log2("FLOWCONTROL_XONXOFF_OUT");
            }
            if ((sPort.getFlowControlMode() & 4) > 0) {
                log2("FLOWCONTROL_XONXOFF_IN");
            }
            if ((sPort.getFlowControlMode() & 1) > 0) {
                log2("FLOWCONTROL_RTSCTS_IN");
            }
            if ((sPort.getFlowControlMode() & 2) > 0) {
                log2("FLOWCONTROL_RTSCTS_OUT");
            }
        }
        postCmd(noEcho);
        postCmd(p);
        _mthchar();
        if (m && faxClass == 1) {
            if (!postCmd("AT+FCLASS=1")) {
                throw new Exception("could not set class 1");
            }
            _mthtry();
        }
        if (l && faxClass == 2) {
            if (!postCmd("AT+FCLASS=2")) {
                throw new Exception("Could not set class 2");
            }
            if (debug) {
                postCmd("AT+FBUG=1");
            }
        }
        if (f && faxClass == 20) {
            if (!postCmd("AT+FCLASS=2.0")) {
                throw new Exception("Could not set class 2.0");
            }
            postCmd("AT+FNR=1,1,1,0");
            if (debug) {
                postCmd("AT+FBU=1");
            }
        }
        if (AtFBOR) {
            if (faxClass == 2) {
                if (!directBitOrder) {
                    postCmd("AT+FBOR=1");
                }
                if (directBitOrder) {
                    postCmd("AT+FBOR=0");
                }
            }
            if (faxClass == 20) {
                if (!directBitOrder) {
                    postCmd("AT+FBO=1");
                }
                if (directBitOrder) {
                    postCmd("AT+FBO=0");
                }
            }
        }
        if (ownId.length() > 0) {
            if (faxClass == 20) {
                postCmd("AT+FLI=\"" + ownId + "\"");
            }
            if (faxClass == 2) {
                postCmd("AT+FLID=\"" + ownId + "\"");
            }
        }
        if (faxClass == 20) {
            postCmd("AT+FCC=" + resolution + "," + bitRate + ",0,2,0,0,0,0");
        }
        if (faxClass == 2) {
            postCmd("AT+FDCC=" + resolution + "," + bitRate + ",0,2,0,0,0,0");
        }
        for (int i1 = 0; i1 < initCommands.length; i1++) {
            postCmd(initCommands[i1]);
        }

        return true;
    }

    private boolean postCmd(String s1) throws Exception {
        if (writeCmd(s1)) {
            return waitFor("OK");
        } else {
            return false;
        }
    }

    private boolean _mthif(int i1) throws Exception {
        String s1 = "AT+FTM=" + i1;
        if (!writeCmd(s1)) {
            return false;
        }
        return waitFor("CONNECT", false);
    }

    public String getInitString() {
        return p;
    }

    public void setInitString(String s1) {
        p = s1;
    }

    private void _mthdo() throws Exception {
        if (log) {
            log2("Cancel: ");
        }
        byte abyte0[] = {
            24
        };
        sendBytes(abyte0, 1);
        waitFor("OK");
    }

    private boolean sendPage(byte abyte0[]) {
        int j1 = 0;
        byte abyte1[] = new byte[65];
        try {
            while (j1 < abyte0.length) {
                int i1;
                for (i1 = 0; i1 < 64 && j1 < abyte0.length;) {
                    byte byte0 = 0;
                    if (directBitOrder) {
                        int l1 = abyte0[j1++];
                        if (l1 < 0) {
                            l1 = 256 + l1;
                        }
                        byte0 = e[l1];
                    } else {
                        byte0 = abyte0[j1++];
                    }
                    if (byte0 == 16) {
                        abyte1[i1++] = 16;
                    }
                    abyte1[i1++] = byte0;
                }

                if (!sendBytes(abyte1, i1)) {
                    return false;
                }
                if (!log) ;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return true;
    }

    private boolean _mthint() {
        long l1 = Calendar.getInstance().getTime().getTime();
        return l1 - _fldgoto > z * 1000L;
    }

    private boolean _mthelse() throws Exception {
        boolean flag = false;
        do {
            Frame a1 = readResponse(10000);
            if (a1 == null) {
                return false;
            }
            if (!waitFor("OK", false)) {
                return false;
            }
            if (a1.getByte3() == 128) {
                flag = true;
                j = new b();
                byte abyte0[] = a1.getBytesIndex5();
                for (int i1 = 0; i1 < abyte0.length; i1++) {
                    int j1 = abyte0[i1];
                    if (j1 < 0) {
                        j1 = 256 + j1;
                    }
                    abyte0[i1] = e[j1];
                }

                j.a(abyte0);
            }
            if (a1.isByte1()) {
                break;
            }
            writeCmd("AT+FRH=3");
        } while (true);
        if (!flag) {
            throw new Exception("DIS not received");
        }
        if (!j._fldint) {
            throw new Exception("Receiver does not support T.4");
        } else {
            return true;
        }
    }

    public boolean supportsClass1() {
        return m;
    }

    public boolean supportsClass20() {
        return f;
    }

    private boolean sendBytes(byte abyte0[], int i1) {
        try {
            log("Send " + i1 + " bytes  ... ");
            os.write(abyte0, 0, i1);
            log2("OK");
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private boolean open(int i1) throws Exception {
        return open(i1, timeout);
    }

    private boolean open(int i1, int j1) throws Exception {
        String s1 = "AT+FTH=" + i1;
        int k1;
        for (k1 = 0; k1 < 3; k1++) {
            if (!writeCmd(s1)) {
                return false;
            }
            if (waitFor("CONNECT", false, j1)) {
                break;
            }
        }

        return k1 < 3;
    }

    private void log(int i1) {
        String s1 = "";
        if (i1 < 32) {
            s1 = "<" + i1 + ">";
        } else {
            s1 = "" + (char) i1;
        }
        logStr = logStr + s1;
        if (debug) {
            System.out.print(s1);
        }
    }

    public boolean close() throws Exception {

        for (int i1 = 0; i1 < _fldcase; i1++) {
            File file;
            try {
                file = new File(faxFile + "." + i1);
                file.delete();
            } catch (Exception exception1) {
            }
        }

        try {
            is.close();
            os.close();
            sPort.close();
        } catch (Exception exception) {
            throw exception;
        }
        return true;
    }

    private boolean waitFor(String s1, boolean flag) throws Exception {
        return waitFor(s1, flag, timeout);
    }

    private void sleep(int i1) {
        try {
            Thread.currentThread();
            Thread.sleep(i1);
        } catch (Exception exception) {
        }
    }

    private boolean waitFor(String s1, boolean flag, int i1) throws Exception {
        String s2 = "";
        String s3 = "";
        B = "";
        Calendar calendar = Calendar.getInstance();
        long l1 = calendar.getTime().getTime();
        try {
            if (log) {
                log2("");
                log("Response: ");
            }
            int j1 = is.read();
            s2 = s2 + (char) j1;
            do {
                long l2 = Calendar.getInstance().getTime().getTime();
                Calendar calendar1 = Calendar.getInstance();
                calendar1.add(6, 7);
                if (l2 - l1 > (long) (i1 * 1000)) {
                    timeout = 9;
                    throw new Exception("Timeout waiting for response");
                }
                if (j1 == 13) {
                    if (s3.indexOf("+FHNG:") >= 0 || s3.indexOf("+FHS:") >= 0 || s3.indexOf("+FHG:") >= 0) {
                        hangCode = (new Integer(s3.substring(s3.indexOf(":") + 1, s3.length()))).intValue();
                    }
                    if (s3.indexOf("+FPS:") >= 0 || s3.indexOf("+FPTS:") >= 0) {
                        pageCode = (new Integer(s3.substring(s3.indexOf(":") + 1, s3.length()))).intValue();
                    }
                    if (s3.indexOf("+FCON") >= 0 || s3.indexOf("+FCO") >= 0) {
                        lastResponse = 5;
                    }
                    if (s3.indexOf("CONNECT") >= 0 && faxClass == 1) {
                        lastResponse = 5;
                    }
                    if (s3.indexOf("ERROR") >= 0 || s3.indexOf("FCERROR") >= 0) {
                        lastResponse = 1;
                        return false;
                    }
                    if (s3.indexOf("BUSY") >= 0) {
                        lastResponse = 8;
                        throw new Exception("Line busy");
                    }
                    if (s3.indexOf("NO DIALTONE") >= 0) {
                        lastResponse = 7;
                        throw new Exception("No dial tone");
                    }
                    if (s3.indexOf("NO CARRIER") >= 0) {
                        lastResponse = 6;
                        throw new Exception("No carrier");
                    }
                    s3 = "";
                }
                if (log) {
                    log(j1);
                }
                if (j1 == 13 && s2.indexOf(s1) >= 0 && (s2.indexOf("OK") >= 0 || !flag)) {
                    break;
                }
                if (j1 == 13 && s2.indexOf("+FHNG") >= 0 && (s2.indexOf("OK") >= 0 || !flag)) {
                    return false;
                }
                if (j1 == 13 && s2.indexOf("+FHG") >= 0 && (s2.indexOf("OK") >= 0 || !flag)) {
                    return false;
                }
                if (j1 == 13 && s2.indexOf("+FHS") >= 0 && (s2.indexOf("OK") >= 0 || !flag)) {
                    return false;
                }
                if (s2.indexOf("ERROR") >= 0) {
                    return false;
                }
                j1 = is.read();
                s2 = s2 + (char) j1;
                B = B + (char) j1;
                if (j1 != 10 && j1 != 13) {
                    s3 = s3 + (char) j1;
                }
            } while (true);
            if (log) {
                log2("");
            }
        } catch (Exception exception) {
            throw exception;
        }
        return true;
    }

    private void a() throws Exception {
        postCmd("ATH");
    }

    public FaxModem() {
        e = new byte[256];
        _fldnull = "COM1";
        m = false;
        l = false;
        f = false;
        p = "ATV1Q0";
        maxRetries = 3;
        bitRate = 3;
        resolution = 1;
        faxFile = "tmpFax";
        lastError = "";
        resetCommand = "ATZ";
        timeout = 30;
        resetDelay = 0;
        lastResponse = 0;
        pageCode = -1;
        hangCode = -1;
        flowControl = 0;
        ATFlowControlNone = "";
        ATFlowControlXONXOFF = "";
        ATFlowControlRTSCTS = "";
        B = "";
        directBitOrder = false;
        AtFBOR = true;
        dialTone = true;
        faxClass = 2;
        ownId = "12345";
        noEcho = "ATE0";
        log = true;
        debug = true;
        logStr = "";
        initCommands = new String[0];
        _fldcase = 0;
        t4Encoder = null;
        listener = null;
        MPSEOPdelay = 8;
        sendBufferSize = 0x3d090;
        _fldgoto = 0L;
        z = 0L;
        t4Encoder = new T4Encoder();
        t4Encoder.minBytesLine = 0;
        for (int i1 = 0; i1 < 256; i1++) {
            byte byte0 = (byte) i1;
            byte byte1 = 0;
            if ((byte0 & 0x80) > 0) {
                byte1 |= 1;
            }
            if ((byte0 & 0x40) > 0) {
                byte1 |= 2;
            }
            if ((byte0 & 0x20) > 0) {
                byte1 |= 4;
            }
            if ((byte0 & 0x10) > 0) {
                byte1 |= 8;
            }
            if ((byte0 & 8) > 0) {
                byte1 |= 0x10;
            }
            if ((byte0 & 4) > 0) {
                byte1 |= 0x20;
            }
            if ((byte0 & 2) > 0) {
                byte1 |= 0x40;
            }
            if ((byte0 & 1) > 0) {
                byte1 |= 0x80;
            }
            e[i1] = byte1;
        }

    }

    private void _mthtry() throws Exception {
        if (m) {
            postCmd("AT+FTM=?");
            if (B.indexOf("48") > 0) {
                t = true;
            }
            if (B.indexOf("96") > 0) {
                u = true;
            }
            if (B.indexOf("97") > 0) {
                c = true;
            }
            if (B.indexOf("98") > 0) {
                c = true;
            }
            if (B.indexOf("145") > 0) {
                c = true;
            }
            if (B.indexOf("146") > 0) {
                c = true;
            }
        }
    }

    private byte[] getFileBytes(String s1) throws Exception {
        byte abyte0[] = null;
        try {
            FileInputStream fileinputstream = new FileInputStream(s1);
            abyte0 = new byte[(int) (new File(s1)).length()];
            fileinputstream.read(abyte0, 0, abyte0.length);
            fileinputstream.close();
        } catch (Exception exception) {
            throw exception;
        }
        return abyte0;
    }

    private boolean writeFile(String s1, byte abyte0[]) throws Exception {
        try {
            FileOutputStream fileoutputstream = new FileOutputStream(s1);
            fileoutputstream.write(abyte0, 0, abyte0.length);
            fileoutputstream.close();
        } catch (Exception exception) {
            throw exception;
        }
        return true;
    }

    private boolean sendPage(byte abyte0[], boolean flag) throws Exception {
        int i2 = 0;
        int j1 = 0;
        int j2 = 0;
        boolean flag3 = false;
        byte abyte1[] = new byte[sendBufferSize];
        if (!flag && j._fldbyte > 0) {
            double d1 = (double) j._fldbyte / 1000D;
            i2 = (int) ((double) ((j._fldnew * 100) / 8) * d1);
        }
        if (log) {
            log2("Number of byte/line " + i2 + " (" + j._fldbyte + " ms )");
        }
        if (!_mthif(j._fldnew)) {
            return false;
        }
        try {
            if (flowControl == 1) {
                sPort.setFlowControlMode(8);
            }
        } catch (Exception exception) {
        }
        try {
            int l1 = 0;
            while (j1 < abyte0.length && !flag3) {
                int i1;
                for (i1 = 0; i1 < sendBufferSize && j1 < abyte0.length && !flag3;) {
                    byte byte0 = 0;
                    byte byte1 = 0;
                    boolean flag4 = false;
                    if (j1 < abyte0.length - 1) {
                        byte1 = abyte0[j1 + 1];
                    }
                    if ((abyte0[j1] & 7) == 0 && byte1 == 1) {
                        flag4 = true;
                    }
                    if (directBitOrder) {
                        int k2 = abyte0[j1++];
                        if (k2 < 0) {
                            k2 = 256 + k2;
                        }
                        byte0 = e[k2];
                    } else {
                        byte0 = abyte0[j1++];
                    }
                    if (byte0 == 16) {
                        abyte1[i1++] = 16;
                    }
                    abyte1[i1++] = byte0;
                    l1++;
                    if (flag4 && j2 > 0 && l1 < i2) {
                        for (; l1 < i2; l1++) {
                            abyte1[i1++] = 0;
                        }
                    }
                    if (flag4) {
                        if (directBitOrder) {
                            abyte1[i1++] = -128;
                        } else {
                            abyte1[i1++] = 1;
                        }
                        j1++;
                        j2++;
                        l1 = 0;
                    }
                }

                if (!sendBytes(abyte1, i1)) {
                    flag3 = true;
                }
            }
        } catch (Exception e) {
            throw e;
        }
        try {
            if (flowControl == 1) {
                sPort.setFlowControlMode(0);
            }
        } catch (Exception e) {
            throw e;
        }
        return !flag3;
    }

    private boolean createFaxFile(FaxProducer faxproducer) throws Exception {
        if (faxClass == 1) {
            directBitOrder = true;
        }
        if (faxproducer != null) {
            producer = faxproducer;
        }
        if (producer == null) {
            return false;
        }
        if (listener != null) {
            listener.faxProgress(6, 0);
        }
        _fldcase = 0;
        t4Encoder.createEOP = faxClass == 20;
        if (faxClass == 1) {
            t4Encoder.createEOP = false;
        }

        for (java.awt.Image image = producer.getFaxPage(_fldcase); image != null; image = producer.getFaxPage(_fldcase)) {
            byte abyte0[] = t4Encoder.encodeImage(image);
            if (abyte0 != null) {
                writeFile(faxFile + "." + _fldcase, abyte0);
                _fldcase++;
            }
        }

        return true;
    }

    private boolean sendFrame(Frame a1) throws Exception {
        byte abyte0[] = a1.getBytes();
        if (log) {
            log2("");
            log("Send frame: ");
            for (int i1 = 0; i1 < abyte0.length; i1++) {
                log(" " + Integer.toHexString(abyte0[i1] & 0xff));
            }
        }
        a(2500);
        if (!sendBytes(abyte0, abyte0.length)) {
            return false;
        }
        byte abyte1[] = {
            16, 3
        };
        if (!sendBytes(abyte1, 2)) {
            return false;
        }
        if (_mthint()) {
            throw new Exception("timeout sending frame.");

        } else {
            return true;
        }
    }

    public boolean supportsClass2() {
        return l;
    }

    private boolean a(boolean flag) throws Exception {
        if (!open(3, 3)) {
            return false;
        }
        Frame a1 = new Frame();
        a1.setFlag(true);
        if (flag) {
            a1.setByte3((byte) 47);
        } else {
            a1.setByte3((byte) 79);
        }
        if (!sendFrame(a1)) {
            return false;
        }
        return waitFor("OK", false);
    }

    private boolean _mthfor() throws Exception {
        boolean flag = true;
        Frame a1 = null;
        while (flag) {
            _mthnew();
            flag = false;
            postCmd("AT+FTS=1");
            writeCmd("AT+FRH=3");
            do {
                a1 = readResponse(30000);
                if (a1 == null) {
                    return false;
                }
            } while (!a1.isByte1());
            if (a1.getByte3() == 68) {
                lastError = "Training failed at speed " + j._fldnew;
                flag = true;
                int i1 = 1;
                if (bitRate == 5) {
                    i1 = 3;
                }
                if (bitRate == 4) {
                    i1 = 3;
                }
                if (bitRate == 3) {
                    i1 = 1;
                }
                if (bitRate == 2) {
                    i1 = 1;
                }
                if (bitRate == 1) {
                    i1 = 0;
                }
                if (bitRate == 0) {
                    flag = false;
                }
                if (flag) {
                    bitRate = i1;
                }
                open(3);
                if (!_mthbyte()) {
                    return false;
                }
            }
        }
        if (a1 != null && a1.getByte3() != 132) {
            lastError = "Confirmation to receive excepted.";
            return false;
        }
        return waitFor("OK", false);
    }

    private boolean _mthnew() throws Exception {
        postCmd("AT+FTS=7");
        if (!_mthif(j._fldnew)) {
            return false;
        }
        try {
            if (flowControl == 1) {
                sPort.setFlowControlMode(8);
            }
        } catch (Exception exception) {
        }
        byte abyte0[] = new byte[(int) ((double) ((j._fldnew * 100) / 8) * _fldvoid) + 1];
        for (int i1 = 0; i1 < abyte0.length; i1++) {
            abyte0[i1] = 0;
        }

        abyte0[abyte0.length - 1] = 1;
        sendBytes(abyte0, abyte0.length);
        byte abyte1[] = {
            16, 3
        };
        sendBytes(abyte1, abyte1.length);
        try {
            if (flowControl == 1) {
                sPort.setFlowControlMode(0);
            }
        } catch (Exception exception1) {
        }
        return waitFor("OK");
    }

    private boolean _mthif() throws Exception {
        if (!open(3)) {
            return false;
        }
        Frame a1 = new Frame();
        a1.setFlag(false);
        a1.setByte3((byte) 67);
        for (int i1 = ownId.length() - 1; i1 >= 0; i1--) {
            a1.addByte((byte) ownId.charAt(i1));
        }

        for (int j1 = 0; j1 < 20 - ownId.length(); j1++) {
            a1.addByte((byte) 32);
        }

        if (!sendFrame(a1)) {
            return false;
        }
        return waitFor("CONNECT", false);
    }

    public boolean sendFax(String s1) throws Exception {
        int i1 = 0;
        if (listener != null) {
            listener.faxProgress(3, 0);
        }
        if (connect(s1)) {
            if (faxClass == 1) {
                if (!_mthelse()) {
                    return false;
                }
                if (!_mthif()) {
                    return false;
                }
            }
            int j1 = 0;
            boolean flag2 = true;
            while (j1 < _fldcase) {
                if (listener != null) {
                    listener.faxProgress(4, j1);
                }
                boolean flag3 = false;
                byte abyte0[];
                if ((new File("sendBody.g3")).exists()) {
                    abyte0 = getFileBytes("sendBody.g3");
                    flag3 = true;
                    System.out.println("** SENDING USER FILE *** sendBody.g3");
                } else {
                    abyte0 = getFileBytes(faxFile + "." + j1);
                }
                if (abyte0 == null) {
                    lastError = "Could not read fax file.";
                    return false;
                }
                if (faxClass == 1 && flag2) {
                    if (!_mthbyte()) {
                        return false;
                    }
                    if (!_mthfor()) {
                        return false;
                    }
                }
                flag2 = false;
                if ((faxClass == 2 || faxClass == 20) && !writeCmd("AT+FDT")) {
                    lastError = "Could not start phase C";
                    return false;
                }
                if (faxClass == 20) {
                    waitFor("CONNECT", false);
                }
                if (faxClass == 2) {
                    int k1 = sPort.getFlowControlMode();
                    try {
                        sPort.setFlowControlMode(0);
                    } catch (Exception exception) {
                    }
                    try {
                        if (log) {
                            log("Response: ");
                        }
                        String s3 = "";
                        int i2 = is.read();
                        s3 = s3 + i2;
                        while (i2 != 17) {
                            i2 = is.read();
                            if (log) {
                                log(i2);
                            }
                            s3 = s3 + i2;
                            if (i2 == 13 && s3.indexOf("OK") >= 0) {
                                break;
                            }
                            if (i2 == 17 && log) {
                                log2("XON received");
                            }
                        }
                        if (s3.indexOf("+FHNG") >= 0) {
                            lastResponse = 2;
                            lastError = "Remote closed connection";
                            return false;
                        }
                        if (s3.indexOf("+FHS") >= 0) {
                            lastResponse = 2;
                            lastError = "Remote closed connection";
                            return false;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        lastError = "Error reading (wait for XON)";
                        return false;
                    }
                    try {
                        sPort.setFlowControlMode(k1);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                pageCode = -1;
                if (faxClass == 1) {
                    if (!sendPage(abyte0, flag3)) {
                        lastError = "Could not sendBody page bytes";
                        return false;
                    }
                    byte abyte1[] = {
                        0, 8, -128, 0, 8, -128, 0, 8, -128
                    };
                    sendBytes(abyte1, abyte1.length);
                    byte abyte5[] = {
                        16, 3
                    };
                    sendBytes(abyte5, abyte5.length);
                } else if (!sendPage(abyte0)) {
                    lastError = "Could not sendBody page bytes";
                    return false;
                }
                if (faxClass == 20) {
                    if (j1 == _fldcase - 1) {
                        byte abyte2[] = {
                            16, 46
                        };
                        sendBytes(abyte2, abyte2.length);
                    } else {
                        byte abyte3[] = {
                            16, 44
                        };
                        sendBytes(abyte3, abyte3.length);
                    }
                    postCmd("AT+FPS?");
                }
                if (faxClass == 2) {
                    byte abyte4[] = {
                        16, 3
                    };
                    sendBytes(abyte4, abyte4.length);
                    try {
                        Thread.currentThread();
                        Thread.sleep(500L);
                    } catch (Exception exception3) {
                    }
                    if (j1 == _fldcase - 1) {
                        postCmd("AT+FET=" + 2);
                    } else {
                        postCmd("AT+FET=" + 0);
                    }
                    if (pageCode == -1) {
                        postCmd("AT+FPTS?");
                    }
                }
                boolean flag1 = false;
                if (faxClass == 1) {
                    if (!postCmd("AT+FTS=" + MPSEOPdelay)) {
                        return false;
                    }
                    int l1 = 1;
                    Frame a1 = null;
                    for (; l1 <= 3; l1++) {
                        if (!a(j1 == _fldcase - 1)) {
                            return false;
                        }
                        if (!writeCmd("AT+FRH=3")) {
                            return false;
                        }
                        a1 = readResponse(3000);
                        if (a1 != null) {
                            break;
                        }
                        lastError = "";
                    }

                    if (a1 == null) {
                        lastError = "Response to MPS/EOP not received after 3 retries.";
                        return false;
                    }
                    if (a1.getByte3() == 44) {
                        lastError = "Procedure interrupted";
                        return false;
                    }
                    if (a1.getByte3() == 172) {
                        lastError = "Procedure interrupted";
                        return false;
                    }
                    if (a1.getByte3() == 140 && log) {
                        log2("Positive message confirmation.");
                    }
                    if (j1 == _fldcase - 1 && a1.getByte3() == 204) {
                        flag2 = true;
                        if (log) {
                            log2("Retrain positive.");
                        }
                    }
                    if (a1.getByte3() == 76) {
                        flag2 = true;
                        if (i1 < maxRetries) {
                            flag1 = true;
                            i1++;
                        }
                    }
                    if (a1.getByte3() == 250) {
                        lastError = "Disconnect frame received";
                        hangCode = 20;
                        break;
                    }
                }
                if (faxClass == 2 || faxClass == 20) {
                    switch (pageCode) {
                        case -1:
                        case 0: // '\0'
                        case 1: // '\001'
                        default:
                            break;

                        case 2: // '\002'
                            if (i1 < maxRetries) {
                                flag1 = true;
                                i1++;
                            }
                            break;
                    }
                }
                if (!flag1) {
                    j1++;
                }
                if (hangCode >= 0) {
                    break;
                }
            }
            if (listener != null) {
                listener.faxProgress(5, 0);
            }
            if (faxClass == 1 && _mthcase()) {
                hangCode = 0;
            }
            a();
            if (hangCode == 0) {
                return true;
            }
        }
        return false;
    }

    private void _mthchar() throws Exception {
        postCmd("AT+FCLASS=?");
        if (B.indexOf("1") > 0) {
            m = true;
        }
        if (B.indexOf("2") > 0) {
            l = true;
        }
        if (B.indexOf("2.0") > 0) {
            f = true;
        }
    }

    public T4Encoder getEncoder() {
        return t4Encoder;
    }

    private void log(String s1) {
        logStr = logStr + s1;
        if (debug) {
            System.out.print(s1);
        }
    }

    private boolean writeCmd(String s1) {
        char c1 = '\r';
        try {
            if (log) {
                log2("");
                log2("Command out.");
            }
            if (log) {
                log(s1);
            }
            s1 = s1 + c1;
            os.write(s1.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean waitFor(String s1) throws Exception {
        return waitFor(s1, true);
    }

    private boolean _mthbyte() throws Exception {
        Frame a1 = new Frame();
        a1.setFlag(true);
        a1.setByte3((byte) -125);
        a(j);
        byte abyte0[] = j.a();
        for (int i1 = 0; i1 < abyte0.length; i1++) {
            int j1 = abyte0[i1];
            if (j1 < 0) {
                j1 = 256 + j1;
            }
            abyte0[i1] = e[j1];
        }

        a1.addByte(abyte0[0]);
        a1.addByte(abyte0[1]);
        a1.addByte(abyte0[2]);
        if (!sendFrame(a1)) {
            return false;
        }
        return waitFor("OK", false);
    }

    private boolean _mthcase() throws Exception {
        if (!open(3)) {
            return false;
        }
        Frame a1 = new Frame();
        a1.setFlag(true);
        a1.setByte3((byte) -5);
        if (!sendFrame(a1)) {
            return false;
        }
        return waitFor("OK", false);
    }

}
