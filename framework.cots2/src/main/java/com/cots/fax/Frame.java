package com.cots.fax;


public class Frame {

    protected static final int o = 126;
    protected static final int _fldgoto = 255;
    protected static final int _fldlong = 19;
    protected static final int _flddo = 3;
    protected static final int t = 128;
    protected static final int p = 64;
    protected static final int i = 32;
    protected static final int _fldcase = 129;
    protected static final int E = 65;
    protected static final int e = 33;
    protected static final int D = 130;
    protected static final int y = 131;
    protected static final int G = 66;
    protected static final int H = 67;
    protected static final int _fldint = 34;
    protected static final int s = 35;
    protected static final int u = 132;
    protected static final int a = 133;
    protected static final int d = 68;
    protected static final int l = 69;
    protected static final int w = 142;
    protected static final int z = 143;
    protected static final int r = 78;
    protected static final int n = 79;
    protected static final int F = 46;
    protected static final int _fldvoid = 47;
    protected static final int v = 158;
    protected static final int _fldnull = 159;
    protected static final int q = 94;
    protected static final int _fldfor = 95;
    protected static final int C = 62;
    protected static final int I = 63;
    protected static final int j = 140;
    protected static final int x = 141;
    protected static final int k = 204;
    protected static final int g = 205;
    protected static final int _fldif = 76;
    protected static final int c = 77;
    protected static final int A = 172;
    protected static final int _fldtry = 173;
    protected static final int b = 44;
    protected static final int _fldnew = 45;
    protected static final int _fldelse = 250;
    protected static final int _fldbyte = 251;
    protected static final int h = 26;
    protected static final int _fldchar = 27;
    byte J[];
    int f;

    public Frame(byte abyte0[], int i1) {
        J = new byte[256];
        f = 0;
        J = abyte0;
        f = i1;
    }

    public Frame() {
        J = new byte[256];
        f = 0;
        J[0] = -1;
        f++;
        J[1] = 3;
        f++;
        J[2] = 0;
        f++;
    }

    public void addByte(byte byte0) {
        J[f++] = byte0;
    }

    public void setFlag(boolean flag) {
        if (flag) {
            J[1] = 19;
        } else {
            J[1] = 3;
        }
    }

    public int getByte3() {
        if (f > 2) {
            int i1 = J[2];
            if (i1 < 0) {
                i1 = 256 + i1;
            }
            return i1;
        } else {
            return 0;
        }
    }

    public void setByte3(byte byte0) {
        if (f > 2) {
            J[2] = byte0;
        }
    }

    public boolean isByte1() {
        if (f > 1) {
            return J[1] == 19;
        } else {
            return false;
        }
    }

    public byte[] getBytesIndex5() {
        if (f <= 5) {
            return null;
        }
        byte abyte0[] = new byte[f - 5];
        for (int i1 = 0; i1 < abyte0.length; i1++) {
            abyte0[i1] = J[i1 + 3];
        }
        return abyte0;
    }

    public byte[] getBytes() {
        byte abyte0[] = new byte[f];
        for (int i1 = 0; i1 < abyte0.length; i1++) {
            abyte0[i1] = J[i1];
        }

        return abyte0;
    }
}
