package com.dream.bizsdk.common.fax;


public class b {

    public boolean _fldint;
    public int _fldfor;
    public int _fldnew;
    public String _fldtry;
    public String _flddo;
    public int _fldif;
    public int a;
    public int _fldcase;
    public int _fldbyte;

    public b() {
        _fldint = true;
        _fldfor = 0;
        _fldnew = 96;
        _fldtry = "V29";
        _flddo = "";
        _fldif = 0;
        a = 1728;
        _fldcase = 1;
        _fldbyte = 0;
    }

    public byte[] a() {
        byte abyte0[] = new byte[3];
        abyte0[0] = 0;
        abyte0[1] = 0;
        abyte0[2] = 0;
        abyte0[1] = 64;
        byte byte0 = 0;
        if (_fldnew == 145 && _fldtry.compareTo("V17") == 0) {
            byte0 = 4;
        }
        if (_fldnew == 145 && _fldtry.compareTo("V33") == 0) {
            byte0 = 8;
        }
        if (_fldnew == 121 && _fldtry.compareTo("V17") == 0) {
            byte0 = 20;
        }
        if (_fldnew == 121 && _fldtry.compareTo("V33") == 0) {
            byte0 = 24;
        }
        if (_fldnew == 96 && _fldtry.compareTo("V17") == 0) {
            byte0 = 36;
        }
        if (_fldnew == 72 && _fldtry.compareTo("V17") == 0) {
            byte0 = 52;
        }
        if (_fldnew == 72 && _fldtry.compareTo("V33") == 0) {
            byte0 = 48;
        }
        if (_fldnew == 72 && _fldtry.compareTo("V29") == 0) {
            byte0 = 48;
        }
        if (_fldnew == 96 && _fldtry.compareTo("V29") == 0) {
            byte0 = 32;
        }
        if (_fldnew == 48) {
            byte0 = 16;
        }
        if (_fldnew == 24) {
            byte0 = 0;
        }
        byte0 &= 0xff;
        abyte0[1] = (byte) (abyte0[1] | byte0);
        if (_fldfor == 1) {
            abyte0[1] = (byte) (abyte0[1] | 2);
        }
        if (_fldcase == 2) {
            abyte0[1] = (byte) (abyte0[1] | 1);
        }
        byte0 = 0;
        if (a == 1728) {
            byte0 = 0;
        }
        if (a == 2432) {
            byte0 = 64;
        }
        if (a == 2048) {
            byte0 = -128;
        }
        byte0 &= 0xff;
        abyte0[2] = (byte) (abyte0[2] | byte0);
        byte0 = 0;
        if (_fldif == 2) {
            byte0 = 16;
        }
        if (_fldif == 1) {
            byte0 = 32;
        }
        byte0 &= 0xff;
        abyte0[2] = (byte) (abyte0[2] | byte0);
        byte0 = 0;
        if (_fldbyte == 20) {
            byte0 = 0;
        }
        if (_fldbyte == 40) {
            byte0 = 2;
        }
        if (_fldbyte == 10) {
            byte0 = 4;
        }
        if (_fldbyte == 5) {
            byte0 = 8;
        }
        if (_fldbyte == 0) {
            byte0 = 14;
        }
        byte0 &= 0xff;
        abyte0[2] = (byte) (abyte0[2] | byte0);
        return abyte0;
    }

    public boolean a(byte abyte0[]) {
        int i = abyte0[1];
        i &= 0x40;
        if (i == 64) {
            _fldint = true;
        } else {
            _fldint = false;
        }
        i = abyte0[1];
        i &= 0x3c;
        _fldnew = 96;
        _fldtry = "V29";
        if (i == 56) {
            _fldnew = 96;
            _fldtry = "V33";
        }
        if (i == 52) {
            _fldnew = 145;
            _fldtry = "V17";
        }
        if (i == 48) {
            _fldnew = 72;
            _fldtry = "V29";
        }
        if (i == 32) {
            _fldnew = 96;
            _fldtry = "V29";
        }
        if (i == 16) {
            _fldnew = 48;
            _fldtry = "V17";
        }
        if (i == 0) {
            _fldnew = 24;
            _fldtry = "V27FB";
        }
        i = abyte0[1];
        i &= 2;
        if (i == 2) {
            _fldfor = 1;
        }
        i = abyte0[1];
        i &= 1;
        if (i == 1) {
            _fldcase = 2;
        }
        i = abyte0[2];
        i &= 0xc0;
        if (i == 0) {
            a = 1728;
        }
        if (i == 64) {
            a = 2432;
        }
        if (i == 128) {
            a = 2048;
        }
        if (i == 192) {
            a = 2432;
        }
        i = abyte0[2];
        i &= 0x30;
        if (i == 0) {
            _fldif = 0;
        }
        if (i == 16) {
            _fldif = 2;
        }
        if (i == 32) {
            _fldif = 1;
        }
        i = abyte0[2];
        i &= 0xe;
        if (i == 0) {
            _fldbyte = 20;
        }
        if (i == 2) {
            _fldbyte = 40;
        }
        if (i == 4) {
            _fldbyte = 10;
        }
        if (i == 6) {
            _fldbyte = 10;
        }
        if (i == 8) {
            _fldbyte = 5;
        }
        if (i == 14) {
            _fldbyte = 0;
        }
        if (i == 10) {
            _fldbyte = 40;
        }
        if (i == 12) {
            _fldbyte = 20;
        }
        return true;
    }
}
