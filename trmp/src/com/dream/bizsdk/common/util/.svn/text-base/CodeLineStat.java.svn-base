/**
 *all rights reserved,@copyright 2003
 */
package com.dream.bizsdk.common.util;

import java.io.*;
import java.util.TreeSet;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-8-12
 * Time: 13:53:31
 */
public class CodeLineStat {
    private TreeSet omitLines = new TreeSet();
    public String lineCommentPrefix = "//";
    public String blockCommentPrefix = "/*";
    public String blockCommentSuffix = "*/";

    private int codeLines;
    private int omitedLines;
    private int commentLines;
    private int files;
    private long codeBytes;
    private long commentBytes;
    private long omitedBytes;

    public CodeLineStat() {

    }

    public CodeLineStat(String[] omitedLines) {
        if (omitedLines != null) {
            for (int i = 0; i < omitedLines.length; i++) {
                omitLines.add(omitedLines[i]);
            }
        }
    }

    public void clear() {
        codeLines = 0;
        omitedLines = 0;
        commentLines = 0;
        files = 0;
        codeBytes = 0;
        commentBytes = 0;
        omitedBytes = 0;
    }

    public void stat(String rootPath) throws IOException {
        stat(rootPath, ".java");
    }

    public void stat(String rootPath, String suffix) throws IOException {
        File f = new File(rootPath);
        if (f.exists()) {
            stat(f, suffix);
        }
    }

    public void stat(File f) throws IOException {
        stat(f, ".java");
    }

    public void stat(File f, String suffix) throws IOException {
        String line = null;
        if (!f.isDirectory()) {
            if (f.getName().toLowerCase().endsWith(suffix)) {
                boolean inBlockRemark = false;
                files++;
                BufferedReader br = new BufferedReader(new FileReader(f));
                while ((line = br.readLine()) != null) {
                    line = line.trim();
                    if (!isOmited(line)) {   //omit empty line;
                        if (line.startsWith(lineCommentPrefix)) {  //remark line;
                            commentLines++;
                            commentBytes += line.length();
                            continue;
                        } else if (inBlockRemark) {
                            if (line.endsWith(blockCommentSuffix)) {
                                inBlockRemark = false;
                            }
                            commentBytes += line.length();
                            commentLines++;
                        } else {
                            if (line.startsWith(blockCommentPrefix)) {
                                inBlockRemark = true;
                                commentBytes += line.length();
                                commentLines++;
                            } else {
                                codeLines++;
                                codeBytes += line.length();
                            }
                        }
                    } else {
                        omitedBytes += line.length();
                        omitedLines++;
                    }
                }
                br.close();
            }
        } else {
            File[] subFiles = f.listFiles();
            for (int i = 0; i < subFiles.length; i++) {
                stat(subFiles[i], suffix);
            }
        }
    }

    private boolean isOmited(String line) {
        if (omitLines.contains(line)) {
            return true;
        } else {
            return false;
        }
    }

    public int getCodeLines() {
        return codeLines;
    }

    public int getOmitedLines() {
        return omitedLines;
    }

    public int getCommentLines() {
        return commentLines;
    }

    public long getCodeBytes() {
        return codeBytes;
    }

    public long getCommentBytes() {
        return commentBytes;
    }

    public long getOmitedBytes() {
        return omitedBytes;
    }

    public long getTotlaBytes() {
        return omitedBytes + codeBytes + commentBytes;
    }

    public int getTotalLines() {
        return omitedLines + codeLines + commentLines;
    }

    public int getFiles() {
        return files;
    }

    public void printResult() {
        System.out.println("          files:\t" + getFiles());
        System.out.println("     code Bytes:\t" + getCodeBytes());
        System.out.println("  ommited bytes:\t" + getOmitedBytes());
        System.out.println("commented bytes:\t" + getCommentBytes());
        System.out.println("    Total bytes:\t" + getTotlaBytes());
        System.out.println();
        System.out.println("effective lines:\t" + getCodeLines());
        System.out.println("  ommited lines:\t" + getOmitedLines());
        System.out.println("commented lines:\t" + getCommentLines());
        System.out.println("    Total lines:\t" + getTotalLines());
    }

    public static void main(String[] argc) throws IOException {
        CodeLineStat clt = new CodeLineStat(new String[]{""});

        clt.stat("D:\\DREAM\\bizsdk2\\src\\com");
        clt.printResult();
    }
}