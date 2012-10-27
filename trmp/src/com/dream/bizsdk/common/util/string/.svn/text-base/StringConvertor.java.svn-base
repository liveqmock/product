package com.dream.bizsdk.common.util.string;

import com.dream.bizsdk.common.databus.BizData;

import java.net.URLEncoder;
import java.io.*;
import java.util.Properties;

/**
 * <p>Title: MVC框架实现</p>
 * <p>Description: 本软件由禇光华所有</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: dream</p>
 *
 * @author divine
 * @version 1.2
 */

public class StringConvertor {

    public static char SP_CHAR = '%';
    public static char EC_CHAR = '$';
    public static String CHARSET = "GBK";

    /**
     * replace variables in a string with the value in a BizData object;
     *
     * @param src the source string object;
     * @param d   the BizData object contains the values;
     * @return the result string that variables have been replaced;
     */
    public static final String replace(String src, BizData d) {
        int i = 0;
        int len = 0;
        int tempLen = 0;
        boolean beginSP = false;
        StringBuffer sb = new StringBuffer(1024);
        StringBuffer temp = new StringBuffer(1024);
        if (src == null) {
            return null;
        }
        len = src.length();
        while (i < len) {
            char ch = src.charAt(i);
            if (ch != StringConvertor.SP_CHAR) {
                if (beginSP) {
                    temp.append(ch);
                } else {
                    sb.append(ch);
                }
            } else {
                if (beginSP) {
                    if ((tempLen = temp.length()) < 1) {
                        sb.append(StringConvertor.SP_CHAR);
                        beginSP = false;
                    } else {
                        String fpath = new String(temp);
                        String value = d.getStringByPath(fpath);
                        sb.append(value);
                        temp.delete(0, tempLen);
                        beginSP = false;
                    }
                } else {
                    beginSP = true;
                }
            }
            i++;
        }
        if (temp.length() > 0) {
            sb.append(StringConvertor.SP_CHAR).append(temp);
        }
        return new String(sb);
    }

    /**
     * encode variables in a string.
     *
     * @param src the source string that contains variables to be encoded;
     * @return
     * @throws UnsupportedEncodingException
     */
    public static final String encode(String src) throws UnsupportedEncodingException {
        int i = 0;
        int len = 0;
        int tempLen = 0;
        boolean beginEC = false;
        StringBuffer sb = new StringBuffer(1024);
        StringBuffer temp = new StringBuffer(1024);
        if (src == null) {
            return null;
        }
        len = src.length();
        while (i < len) {
            char ch = src.charAt(i);
            if (ch != StringConvertor.EC_CHAR) {
                if (beginEC) {
                    temp.append(ch);
                } else {
                    sb.append(ch);
                }
            } else {
                if (beginEC) {
                    if ((tempLen = temp.length()) < 1) {
                        sb.append(StringConvertor.EC_CHAR);
                        beginEC = false;
                    } else {
                        String orgValue = new String(temp);
                        String encValue = URLEncoder.encode(orgValue, StringConvertor.CHARSET);
                        sb.append(encValue);
                        temp.delete(0, tempLen);
                        beginEC = false;
                    }
                } else {
                    beginEC = true;
                }
            }
            i++;
        }
        if (temp.length() > 0) {
            sb.append(StringConvertor.EC_CHAR).append(temp);
        }
        return new String(sb);
    }

    /**
     * replace and encode variables in a string object;
     *
     * @param src the source string containing variables to be replaced and encoded;
     * @param d   the BizData object containing values;
     * @return
     * @throws UnsupportedEncodingException
     */
    public final static String replaceAndEncode(String src, BizData d)
            throws UnsupportedEncodingException {
        String temp = StringConvertor.replace(src, d);
        return StringConvertor.encode(temp);
    }

    /**
     * @param src
     * @param prefix
     * @param suffix
     * @return
     */
    public final static String replace(String src, String prefix, String suffix, Properties props) {
        int index1;
        int index2;
        int len1 = prefix.length();
        int len2 = suffix.length();

        StringBuffer sb = new StringBuffer();

        index1 = src.indexOf(prefix);
        while (index1 >= 0) {
            sb.append(src.substring(0, index1));
            src = src.substring(index1 + len1);
            if (src.startsWith(prefix)) {
                sb.append(prefix);
                break;
            } else {
                index2 = src.indexOf(suffix);
                if (index2 >= 0) {
                    String t = src.substring(0, index2);
                    String sp = props.getProperty(t);
                    sb.append(sp);
                    src = src.substring((index2 + len2));
                    index1 = src.indexOf(prefix);
                } else {
                    sb.append(prefix);
                    break;
                }
            }
        }
        sb.append(src);
        return new String(sb);
    }

    /**
     * @param src
     * @param prefix
     * @param suffix
     * @return
     */
    public static final String replace(String src, String prefix, String suffix) {
        Properties propies = System.getProperties();
        return replace(src, prefix, suffix, propies);
    }

    /**
     * @param src
     * @return
     */
    public static final String replace(String src) {
        Properties propies = System.getProperties();
        return replace(src, "%", "%", propies);
    }

    /**
     * @param sb
     * @param from
     * @param src
     * @return
     */
    public static int getNextLine(StringBuffer sb, int from, String src) {
        boolean inStr = false;
        boolean inComment = false;

        int len = src.length();
        sb.delete(0, sb.length());
        for (; from < len - 1; from++) {
            char ch = src.charAt(from);
            switch (ch) {
                case '/':
                    if (inStr) {
                        sb.append(ch);
                    } else if (inComment) {
                    } else {
                        char chNext = src.charAt(from + 1);
                        if (chNext == '*') {
                            inComment = true;
                            from++;
                        } else if (chNext == '/') {
                            from += 2;
                            while (from < len) {
                                char rn = src.charAt(from);
                                if (rn == '\r' || rn == '\n') {
                                    if (from < len - 1) {
                                        if (src.charAt(from + 1) == '\r' || src.charAt(from + 1) == '\n') {
                                            from++;
                                        }
                                    }
                                    break;
                                }
                                from++;
                            }
                        }
                    }
                    break;
                case '*':
                    break;
            }
        }
        return from;
    }

    public static void replaceFile(File file, String prefix, String suffix, Properties props) throws IOException {
        CharArrayWriter caw = new CharArrayWriter(8096);
        BufferedReader r = new BufferedReader(new FileReader(file));

        //read and replace symbols in the file;
        String line = r.readLine();
        while (line != null) {
            String rl = replace(line, prefix, suffix, props);
            caw.write(rl);
            caw.write("\r\n");
            line = r.readLine();
        }
        r.close();

        caw.flush();
        char[] ca = caw.toCharArray();
        caw.close();

        //write back the replaced contents to the orinal files;
        BufferedWriter bw = new BufferedWriter(new FileWriter(file));
        bw.write(ca);
        bw.flush();
        bw.close();
    }

    /**
     * @param filePath
     * @param prefix
     * @param suffix
     * @param props
     * @throws java.io.IOException
     */
    public static void replaceFile(String filePath, String prefix, String suffix, Properties props) throws IOException {
        replaceFile(new File(filePath), prefix, suffix, props);
    }


    /**
     * @param filePath
     * @throws IOException
     */
    public static void replaceFile(String filePath) throws IOException {
        Properties props = System.getProperties();
        replaceFile(filePath, "${", "}", props);
    }

    /**
     * replace a file using system properties;
     *
     * @param file
     * @throws IOException
     */
    public static void replaceFile(File file) throws IOException {
        Properties props = System.getProperties();
        replaceFile(file, "${", "}", props);
    }

    /**
     * replace a file using a specified Properties object;
     *
     * @param filePath
     * @param props
     * @throws IOException
     */
    public static void replaceFile(String filePath, Properties props) throws IOException {
        replaceFile(filePath, "${", "}", props);
    }

    /**
     * replace a file using a specified Properties object;
     *
     * @param file
     * @param props
     * @throws IOException
     */
    public static void replaceFile(File file, Properties props) throws IOException {
        replaceFile(file, "${", "}", props);
    }

    /**
     * @param s
     * @return
     */
    public static String ascii2native(String s) {
        StringBuffer sb = new StringBuffer(500);
        char[] ch = new char[4];
        int len = s.length();

        for (int i = 0; i < len - 1; i++) {
            if (s.charAt(i) == '\\' && s.charAt(i + 1) == 'u') {
                ch[0] = s.charAt(i + 2);
                ch[1] = s.charAt(i + 3);
                ch[2] = s.charAt(i + 4);
                ch[3] = s.charAt(i + 5);
                String str = new String(ch);
                sb.append((char) Integer.parseInt(str, 16));
                i += 5;
            } else {
                sb.append(s.charAt(i));
            }
        }
        return new String(sb);
    }

    /**
     * format a path to make it always ending with a File.sepatetorChar;
     *
     * @param path
     * @return
     */
    public static String formatPath(String path) {
        if (path == null || path.length() < 1) {
            return path;
        } else {
            if (path.charAt(path.length() - 1) != File.separatorChar) {
                return path + File.separatorChar;
            } else {
                return path;
            }
        }
    }
}