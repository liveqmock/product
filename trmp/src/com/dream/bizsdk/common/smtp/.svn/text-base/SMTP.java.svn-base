package com.dream.bizsdk.common.smtp;

import com.dream.bizsdk.common.util.encoding.Base64;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.*;

public class SMTP
        implements Serializable {
    public static final int PRIORITY_LOWEST = 5;
    public static final int PRIORITY_LOW = 4;
    public static final int PRIORITY_NORMAL = 3;
    public static final int PRIORITY_HIGH = 2;
    public static final int PRIORITY_HIGHEST = 1;
    Socket socket;
    BufferedReader in;
    DataOutputStream out;
    Vector vListener;
    String startMsg;
    String serialKey;
    String version;
    String to;
    String from;
    String fromName;
    String cc;
    String bcc;
    String subject;
    int mailPort;
    int priority;
    String mailServer;
    String helo;
    String body;
    boolean connected;
    boolean htmlContent;
    String attachedFile;
    boolean authLogin;
    boolean multipleSend;
    String username;
    String password;
    int timeout;
    private String boundary;

    public SMTP() {
        vListener = new Vector();
        to = "";
        from = "";
        cc = "";
        bcc = "";
        subject = "";
        mailPort = 25;
        priority = 3;
        mailServer = "";
        helo = "";
        body = "";
        connected = false;
        htmlContent = false;
        attachedFile = "";
        authLogin = false;
        multipleSend = false;
        username = "";
        this.password = "";
        timeout = 0;
        boundary = "";
        fromName = null;
    }

    public SMTP(String user, String password) {
        vListener = new Vector();
        to = "";
        from = "";
        cc = "";
        bcc = "";
        subject = "";
        mailPort = 25;
        priority = 3;
        mailServer = "";
        helo = "";
        body = "";
        connected = false;
        htmlContent = false;
        attachedFile = "";
        authLogin = false;
        multipleSend = false;
        username = user;
        this.password = password;
        timeout = 0;
        boundary = "";
        fromName = null;
    }

    public SMTP(String user, String password, String mailServer, int mailPort) {
        vListener = new Vector();
        to = "";
        from = "";
        cc = "";
        bcc = "";
        subject = "";
        priority = 3;
        helo = "";
        body = "";
        connected = false;
        htmlContent = false;
        attachedFile = "";
        authLogin = false;
        multipleSend = false;
        timeout = 0;
        boundary = "";
        fromName = null;
        this.mailPort = mailPort;
        this.mailServer = mailServer;
        username = user;
        this.password = password;
    }


    /**
     * Connect to the SMTP server;
     */
    private void connect()
            throws SMTPException {
        try {
            socket = new Socket(mailServer, mailPort);
            socket.setSoTimeout(timeout);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new DataOutputStream(socket.getOutputStream());
            connected = true;
            processReply(in.readLine());
            out.writeBytes("RSET \r\n");
            processReply(in.readLine());
            if (helo.length() == 0) {
                helo = InetAddress.getLocalHost().getHostAddress();
            }
            if (authLogin) {
                if (username.length() == 0) {
                    throw new SMTPException("No username set for authentification");
                }
                out.writeBytes(String.valueOf(String.valueOf((new StringBuffer("EHLO ")).append(helo).append("\r\n"))));
                char temp[] = new char[2048];
                int len = in.read(temp);
                processReply(new String(temp, 0, len));
                try {
                    out.writeBytes("AUTH LOGIN \r\n");
                    processReply(in.readLine());
                    out.writeBytes(String.valueOf(String.valueOf(Base64.encodeString(username))).concat("\r\n"));
                    processReply(in.readLine());
                    out.writeBytes(String.valueOf(String.valueOf(Base64.encodeString(password))).concat("\r\n"));
                    processReply(in.readLine());
                } catch (SMTPException e) {
                    throw e;
                }
            } else {
                out.writeBytes(String.valueOf(String.valueOf((new StringBuffer("HELO ")).append(helo).append("\r\n"))));
                processReply(in.readLine());
            }
        } catch (IOException ex) {
            connected = false;
            throw new SMTPException(ex.getMessage());
        }
    }

    /**
     * disconnect from the SMTP Server;
     */
    public void disconnect()
            throws SMTPException {
        try {
            if (connected) {
                out.writeBytes("QUIT\r\n");
                in.close();
                out.close();
                socket.close();
                connected = false;
            }
        } catch (IOException ex) {
            throw new SMTPException(ex.getMessage());
        }
    }


    public synchronized void sendMail()
            throws SMTPException {
        try {
            if (!connected) {
                connect();
            }
            //System.out.println("Mail from");
            out.writeBytes(String.valueOf(String.valueOf((new StringBuffer("MAIL FROM: <")).append(from).append(">\r\n"))));
            processReply(in.readLine());
            String rcptTArray[] = getTokens(to, ";");
            for (int i = 0; i < rcptTArray.length; i++) {
                out.writeBytes(String.valueOf(String.valueOf((new StringBuffer("RCPT TO: <")).append(rcptTArray[i]).append(">\r\n"))));
                processReply(in.readLine());
            }

            String ccArray[] = getTokens(cc, ";");
            for (int i = 0; i < ccArray.length; i++) {
                out.writeBytes(String.valueOf(String.valueOf((new StringBuffer("RCPT TO: <")).append(ccArray[i]).append(">\r\n"))));
                processReply(in.readLine());
            }

            String bccArray[] = getTokens(bcc, ";");
            for (int i = 0; i < bccArray.length; i++) {
                out.writeBytes(String.valueOf(String.valueOf((new StringBuffer("RCPT TO: <")).append(bccArray[i]).append(">\r\n"))));
                processReply(in.readLine());
            }

            out.writeBytes("data\r\n");
            processReply(in.readLine());
            out.writeBytes(String.valueOf(String.valueOf((new StringBuffer("X-priority: ")).append(priority).append("\r\n"))));
            out.writeBytes(String.valueOf(String.valueOf((new StringBuffer("Subject: ")).append(getEncodedSubject()).append(" \r\n"))));
            out.writeBytes(String.valueOf(String.valueOf((new StringBuffer("From: ").append(getEncodedFromName()).append("<")).append(from).append(">\r\n"))));
            for (int i = 0; i < rcptTArray.length; i++) {
                out.writeBytes(String.valueOf(String.valueOf((new StringBuffer("To: <")).append(rcptTArray[i]).append(">\r\n"))));
            }

            for (int i = 0; i < ccArray.length; i++) {
                out.writeBytes(String.valueOf(String.valueOf((new StringBuffer("Cc: <")).append(ccArray[i]).append(">\r\n"))));
            }

            for (int i = 0; i < bccArray.length; i++) {
                out.writeBytes(String.valueOf(String.valueOf((new StringBuffer("Bcc: <")).append(bccArray[i]).append(">\r\n"))));
            }

            out.writeBytes("MIME-Version: 1.0\r\n");
            if (attachedFile.length() > 0) {
                boundary = createBoundary();
                out.writeBytes(String.valueOf(String.valueOf((new StringBuffer("Content-Type: multipart/mixed; boundary=\"")).append(boundary).append("\"\r\n"))));
                out.writeBytes("\r\n");
                out.writeBytes("This is a multi-part message in MIME format.\r\n");
                out.writeBytes("\r\n");
                out.writeBytes(String.valueOf(String.valueOf((new StringBuffer("--")).append(boundary).append("\r\n"))));
            }
            if (htmlContent) {
                out.writeBytes("Content-Type: text/html; charset=\"gb2312\"\r\n");
                out.writeBytes("Content-Transfer-Encoding: base64\r\n\r\n");
            } else {
                out.writeBytes("Content-Type: text/plain; charset=\"gb2312\"\r\n");
                out.writeBytes("Content-Transfer-Encoding: base64\r\n\r\n");
            }

            out.writeBytes(Base64.encodeString(body));
            out.writeBytes("\r\n\r\n");
            if (attachedFile.length() > 0) {
                sendAttachedFiles(boundary);
            }

            out.writeBytes("\r\n.\r\n");
            processReply(in.readLine());

            if (!multipleSend) {
                disconnect();
            }
        } catch (IOException ex) {
            throw new SMTPException(ex.getMessage());
        }
    }

    private String getEncodedFromName() {
        if (fromName == null) {
            return new String("");
        }
        String es = Base64.encodeString(fromName);
        StringBuffer sb = new StringBuffer();
        sb.append("=?gb2312?B?").append(es).append("?=");
        return new String(sb);
    }


    public void setTo(String rcptTo) {
        if (to.length() > 0) {
            to = to + ";" + rcptTo;
        } else {
            to = rcptTo;
        }
    }

    public void setAttachedFile(String f) {
        if (attachedFile.length() > 0) {
            attachedFile = attachedFile + ";" + f;
        } else {
            attachedFile = f;
        }
    }

    public void setAuthLogin(boolean a) {
        authLogin = a;
    }

    public void setPriority(int p) {
        priority = p;
    }

    public void setMultipleSend(boolean b) {
        multipleSend = b;
    }

    public void setCc(String s) {
        if (cc.length() > 0) {
            cc = cc + ";" + s;
        } else {
            cc = s;
        }
    }

    public void setUsername(String u) {
        username = u;
    }

    public void setHtmlContent(boolean b) {
        htmlContent = b;
    }

    public void setPassword(String p) {
        password = p;
    }

    public void setBcc(String s) {
        bcc = s;
    }

    public void setSubject(String s) {
        subject = s;
    }

    public void setHelo(String s) {
        helo = s;
    }

    public void setTimeout(int t) {
        timeout = t;
    }

    public void setBody(String s) {
        body = s;
    }

    public void setMailPort(int port) {
        mailPort = port;
    }

    public void setFrom(String s) {
        from = s;
    }

    public void setFromName(String s) {
        fromName = s;
    }

    public String getFromName() {
        return fromName;
    }

    public void setMailServer(String s) {
        mailServer = s;
    }

    public void setAbout(String s1) {
    }

    public String getTo() {
        return to;
    }

    public int getPriority() {
        return priority;
    }

    public String getAttachedFile() {
        return attachedFile;
    }

    public String getCc() {
        return cc;
    }

    public boolean isAuthLogin() {
        return authLogin;
    }

    public boolean isMultipleSend() {
        return multipleSend;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getBcc() {
        return bcc;
    }

    public boolean getConnected() {
        return connected;
    }

    public String getBody() {
        return body;
    }

    public String getHelo() {
        return helo;
    }

    public int getMailPort() {
        return mailPort;
    }

    public String getSubject() {
        return subject;
    }

    public int getTimeout() {
        return timeout;
    }

    public String getMailServer() {
        return mailServer;
    }

    public String getFrom() {
        return from;
    }

    public boolean isHtmlContent() {
        return htmlContent;
    }

    private String getEncodedSubject() {
        if (subject == null || subject.length() < 1) {
            return new String("");
        }
        String es = Base64.encodeString(subject);
        StringBuffer sb = new StringBuffer();
        sb.append("=?gb2312?B?").append(es).append("?=");
        return new String(sb);
    }

    private String base64Encode(String src) {
        if (src == null || src.length() < 1) {
            return new String("");
        }
        String es = Base64.encodeString(src);
        StringBuffer sb = new StringBuffer();
        sb.append("=?gb2312?B?").append(es).append("?=");
        return new String(sb);
    }

    /**
     * Send attached files with the mail;
     */
    private void sendAttachedFiles(String boundary)
            throws SMTPException {
        try {
            if (connected && attachedFile.length() > 0) {
                String files[] = getTokens(attachedFile, ";");
                for (int y = 0; y < files.length; y++) {
                    File file = new File(files[y]);
                    FileInputStream fileIn = new FileInputStream(file);
                    byte bFile[] = new byte[fileIn.available()];
                    fileIn.read(bFile);
                    String fileEncode = Base64.encodeBytes(bFile);
                    int iLines = fileEncode.length() / 74;

                    out.writeBytes(String.valueOf(String.valueOf((new StringBuffer("--")).append(boundary).append("\r\n"))));
                    out.writeBytes(String.valueOf(String.valueOf((new StringBuffer("Content-Type: text/plain;\r\n\tname=\"")).append(file.getName()).append("\"\r\n"))));//why is Unknown type;
                    out.writeBytes("Content-Transfer-Encoding: base64\r\n");
                    out.writeBytes(String.valueOf(String.valueOf((new StringBuffer("Content-Disposition: attachment;\r\n\tfilename=\"")).append(base64Encode(file.getName())).append("\"\r\n"))));
                    out.writeBytes("\r\n");
                    int i;
                    for (i = 0; i < iLines; i++) {
                        out.writeBytes(String.valueOf(String.valueOf(fileEncode.substring(i * 74, i * 74 + 74))).concat("\r\n"));
                        System.out.print(String.valueOf(String.valueOf(fileEncode.substring(i * 74, i * 74 + 74))).concat("\r\n"));
                    }

                    if (iLines * 74 < fileEncode.length()) {
                        out.writeBytes(String.valueOf(String.valueOf(fileEncode.substring(i * 74, fileEncode.length()))).concat("\r\n"));
                        System.out.print(String.valueOf(String.valueOf(fileEncode.substring(i * 74, fileEncode.length()))).concat("\r\n"));
                    }

                    System.out.print("--".concat(String.valueOf(String.valueOf(boundary))));

                    if (y + 1 == files.length) {
                        out.writeBytes("--".concat(String.valueOf(String.valueOf(boundary))));
                        out.writeBytes("--");
                    } else {
                        out.writeBytes("\r\n");
                    }
                }
            }
        } catch (IOException ex) {
            throw new SMTPException(ex.getMessage());
        }
    }

    private String[] getTokens(String list, String sep) {
        if (list == null) {
            return new String[0];
        }
        StringTokenizer st = new StringTokenizer(list, sep, false);
        String array[] = new String[st.countTokens()];
        for (int i = 0; st.hasMoreTokens(); i++) {
            array[i] = st.nextToken();
        }
        return array;
    }

    private String createBoundary() {
        return String.valueOf(String.valueOf((new StringBuffer("_")).append(getRandom(1, 65535)).append(getRandom(1, 65535)).append(".").append(getRandom(1, 65535)).append(getRandom(1, 65535)).append(":").append(getRandom(1, 65535)).append(getRandom(1, 65535))));
    }

    private String getRandom(int lo, int hi) {
        Random rn = new Random(System.currentTimeMillis());
        int n = (hi - lo) + 1;
        int i = rn.nextInt() % n;
        if (i < 0) {
            i = -i;
        }
        return Integer.toString(lo + i);
    }

    private void processReply(String reply) throws SMTPException {
        if (reply == null) {
            return;
        }
        if (reply.startsWith("500")) {
            throw new SMTPException("Syntax error, command unrecognized");
        }
        if (reply.startsWith("501")) {
            throw new SMTPException("Syntax error in parameters or arguments");
        }
        if (reply.startsWith("502")) {
            throw new SMTPException("Command not implemented");
        }
        if (reply.startsWith("503")) {
            throw new SMTPException("Bad sequence of commands");
        }
        if (reply.startsWith("504")) {
            throw new SMTPException("Command parameter not implemented");
        }
        if (reply.startsWith("421")) {
            throw new SMTPException("<domain> Service not available");
        }
        if (reply.startsWith("450")) {
            throw new SMTPException("Requested mail action not taken: mailbox unavailable");
        }
        if (reply.startsWith("451")) {
            throw new SMTPException("Requested action aborted: error in processing");
        }
        if (reply.startsWith("452")) {
            throw new SMTPException("Requested action not taken: insufficient system storage");
        }
        if (reply.startsWith("553")) {
            throw new SMTPException("Requested action not taken: mailbox name not allowed");
        }
        if (reply.startsWith("552")) {
            throw new SMTPException("Requested mail action aborted: exceeded storage allocation");
        }
        if (reply.startsWith("535")) {
            throw new SMTPException("Auth failure");
        }
        if (reply.startsWith("554")) {
            throw new SMTPException("Transaction failed");
        }
    }

    public static void main(String[] argc) {
        try {
            SMTP smtp = new SMTP("divine", "chug", "192.168.1.9", 25);
            smtp.setAuthLogin(true);
            smtp.setFrom("divine@primeton.com");
            smtp.setFromName("中国");
            smtp.setTo("divine@primeton.com");
            smtp.setTo("divine@primeton.com");
            smtp.setCc("divine@primeton.com");
            smtp.setCc("divine@primeton.com");
            smtp.setSubject("中国");
            smtp.setHtmlContent(true);
            smtp.setBody("<html><body><a href=\"aa\">中国</a></body></html>");
            smtp.sendMail();

            System.out.println("mail sent.");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}