package com.cots.fax;

import java.awt.*;
import java.awt.image.PixelGrabber;


public class T4Encoder {

//    private static final int _fldint = 0;
//    private static final int _fldtry = 1;
//    private static final int _fldbyte = 2;
    public boolean debug;
    public int minBytesLine;
    public boolean alignEOL;
    public boolean completeLine;
    public int lineWidth;
    public int pageLength;
    public boolean completePage;
    public boolean scaleImage;
    public int scaleFactor;
    public boolean centerImage;
    public boolean createEOP;
    private int _fldcase[][] = {
        {
            15, 10, 64
        }, {
            200, 12, 128
        }, {
            201, 12, 192
        }, {
            91, 12, 256
        }, {
            51, 12, 320
        }, {
            52, 12, 384
        }, {
            53, 12, 448
        }, {
            108, 13, 512
        }, {
            109, 13, 576
        }, {
            74, 13, 640
        }, {
            75, 13, 704
        }, {
            76, 13, 768
        }, {
            77, 13, 832
        }, {
            114, 13, 896
        }, {
            115, 13, 960
        }, {
            116, 13, 1024
        }, {
            117, 13, 1088
        }, {
            118, 13, 1152
        }, {
            119, 13, 1216
        }, {
            82, 13, 1280
        }, {
            83, 13, 1344
        }, {
            84, 13, 1408
        }, {
            85, 13, 1472
        }, {
            90, 13, 1536
        }, {
            91, 13, 1600
        }, {
            100, 13, 1664
        }, {
            101, 13, 1728
        }
    };
    private int _fldchar[][] = {
        {
            27, 5, 64
        }, {
            18, 5, 128
        }, {
            23, 6, 192
        }, {
            55, 7, 256
        }, {
            54, 8, 320
        }, {
            55, 8, 384
        }, {
            100, 8, 448
        }, {
            101, 8, 512
        }, {
            104, 8, 576
        }, {
            103, 8, 640
        }, {
            204, 9, 704
        }, {
            205, 9, 768
        }, {
            210, 9, 832
        }, {
            211, 9, 896
        }, {
            212, 9, 960
        }, {
            213, 9, 1024
        }, {
            214, 9, 1088
        }, {
            215, 9, 1152
        }, {
            216, 9, 1216
        }, {
            217, 9, 1280
        }, {
            218, 9, 1344
        }, {
            219, 9, 1408
        }, {
            152, 9, 1472
        }, {
            153, 9, 1536
        }, {
            154, 9, 1600
        }, {
            24, 6, 1664
        }, {
            155, 9, 1728
        }
    };
    private int a[][] = {
        {
            55, 10, 0
        }, {
            2, 3, 1
        }, {
            3, 2, 2
        }, {
            2, 2, 3
        }, {
            3, 3, 4
        }, {
            3, 4, 5
        }, {
            2, 4, 6
        }, {
            3, 5, 7
        }, {
            5, 6, 8
        }, {
            4, 6, 9
        }, {
            4, 7, 10
        }, {
            5, 7, 11
        }, {
            7, 7, 12
        }, {
            4, 8, 13
        }, {
            7, 8, 14
        }, {
            24, 9, 15
        }, {
            23, 10, 16
        }, {
            24, 10, 17
        }, {
            8, 10, 18
        }, {
            103, 11, 19
        }, {
            104, 11, 20
        }, {
            108, 11, 21
        }, {
            55, 11, 22
        }, {
            40, 11, 23
        }, {
            23, 11, 24
        }, {
            24, 11, 25
        }, {
            202, 12, 26
        }, {
            203, 12, 27
        }, {
            204, 12, 28
        }, {
            205, 12, 29
        }, {
            104, 12, 30
        }, {
            105, 12, 31
        }, {
            106, 12, 32
        }, {
            107, 12, 33
        }, {
            210, 12, 34
        }, {
            211, 12, 35
        }, {
            212, 12, 36
        }, {
            213, 12, 37
        }, {
            214, 12, 38
        }, {
            215, 12, 39
        }, {
            108, 12, 40
        }, {
            109, 12, 41
        }, {
            218, 12, 42
        }, {
            219, 12, 43
        }, {
            84, 12, 44
        }, {
            85, 12, 45
        }, {
            86, 12, 46
        }, {
            87, 12, 47
        }, {
            100, 12, 48
        }, {
            101, 12, 49
        }, {
            82, 12, 50
        }, {
            83, 12, 51
        }, {
            36, 12, 52
        }, {
            55, 12, 53
        }, {
            56, 12, 54
        }, {
            39, 12, 55
        }, {
            40, 12, 56
        }, {
            88, 12, 57
        }, {
            89, 12, 58
        }, {
            43, 12, 59
        }, {
            44, 12, 60
        }, {
            90, 12, 61
        }, {
            102, 12, 62
        }, {
            103, 12, 63
        }
    };
    private int _fldif[][] = {
        {
            53, 8, 0
        }, {
            7, 6, 1
        }, {
            7, 4, 2
        }, {
            8, 4, 3
        }, {
            11, 4, 4
        }, {
            12, 4, 5
        }, {
            14, 4, 6
        }, {
            15, 4, 7
        }, {
            19, 5, 8
        }, {
            20, 5, 9
        }, {
            7, 5, 10
        }, {
            8, 5, 11
        }, {
            8, 6, 12
        }, {
            3, 6, 13
        }, {
            52, 6, 14
        }, {
            53, 6, 15
        }, {
            42, 6, 16
        }, {
            43, 6, 17
        }, {
            39, 7, 18
        }, {
            12, 7, 19
        }, {
            8, 7, 20
        }, {
            23, 7, 21
        }, {
            3, 7, 22
        }, {
            4, 7, 23
        }, {
            40, 7, 24
        }, {
            43, 7, 25
        }, {
            19, 7, 26
        }, {
            36, 7, 27
        }, {
            24, 7, 28
        }, {
            2, 8, 29
        }, {
            3, 8, 30
        }, {
            26, 8, 31
        }, {
            27, 8, 32
        }, {
            18, 8, 33
        }, {
            19, 8, 34
        }, {
            20, 8, 35
        }, {
            21, 8, 36
        }, {
            22, 8, 37
        }, {
            23, 8, 38
        }, {
            40, 8, 39
        }, {
            41, 8, 40
        }, {
            42, 8, 41
        }, {
            43, 8, 42
        }, {
            44, 8, 43
        }, {
            45, 8, 44
        }, {
            4, 8, 45
        }, {
            5, 8, 46
        }, {
            10, 8, 47
        }, {
            11, 8, 48
        }, {
            82, 8, 49
        }, {
            83, 8, 50
        }, {
            84, 8, 51
        }, {
            85, 8, 52
        }, {
            36, 8, 53
        }, {
            37, 8, 54
        }, {
            88, 8, 55
        }, {
            89, 8, 56
        }, {
            90, 8, 57
        }, {
            91, 8, 58
        }, {
            74, 8, 59
        }, {
            75, 8, 60
        }, {
            50, 8, 61
        }, {
            51, 8, 62
        }, {
            52, 8, 63
        }
    };
    private byte _fldnew[];
    private int _fldelse;
    private byte _flddo;
    private int _fldfor;

    public T4Encoder() {
        debug = false;
        minBytesLine = 32;
        alignEOL = true;
        completeLine = true;
        lineWidth = 1728;
        pageLength = 2387;
        completePage = false;
        scaleImage = false;
        scaleFactor = 1;
        centerImage = false;
        createEOP = false;
        _fldnew = new byte[0x30d40];
        _fldelse = 7;
        _flddo = 0;
        _fldfor = 0;
    }

    private void a(int i) {
        byte byte0 = 0;
        byte0 = (byte) (1 << _fldelse);
        if (i == 1) {
            _flddo = (byte) (_flddo | byte0);
        }
        if (debug) {
            System.out.print("".concat(String.valueOf(i)));
        }
        _fldelse--;
        if (_fldelse < 0) {
            _fldelse = 7;
            _fldnew[_fldfor] = _flddo;
            _fldfor++;
            _flddo = 0;
        }
    }

    private void a(int i, int j) {
//        boolean flag = false;
        for (int k = 1 << j - 1; k != 0; k >>= 1) {
            if ((i & k) > 0) {
                a(1);
            } else {
                a(0);
            }
        }

        if (debug) {
            System.out.print(" ");
        }
    }

    private void _mthif() {
        if (alignEOL) {
            while (_fldelse != 3) {
                a(0);
            }
        }
        for (int i = 0; i < 11; i++) {
            a(0);
        }

        a(1);
    }

    private void a() {
        for (int i = 0; i < 6; i++) {
            _mthif();
        }

    }

    public byte[] encodeImage(Image image) {
        int i = scaleFactor;
        int j = 0;
//        Graphics g = image.getGraphics();
//        g.setColor(Color.black);
//        g.setFont(new Font("Dialog", 0, 18));
//        String s = "RFAX";
//        g.drawString(s, 100, 100);
        if (scaleImage) {
            int k = image.getWidth(null);
            int i1 = image.getHeight(null);
            for (int k1 = 2; k1 < 10 && k1 * k <= lineWidth && k1 * i1 <= pageLength; k1++) {
                i = k1;
            }

        }
        _fldfor = 0;
        _fldelse = 7;
        _flddo = 0;
        _mthif();
        int l = image.getWidth(null);
        int j1 = image.getHeight(null);
        int ai[] = new int[l * j1];
        byte byte0 = -1;
        PixelGrabber pixelgrabber = new PixelGrabber(image, 0, 0, l, j1, ai, 0, l);
        try {
            pixelgrabber.grabPixels();
        } catch (Exception exception) {
            System.err.println("".concat(String.valueOf(exception.getMessage())));
        }
        for (int l2 = 0; l2 < j1; l2++) {
            for (int i3 = 1; i3 <= i; i3++) {
                j++;
                int k3 = 0;
                int k2 = _fldfor;
                for (int l3 = 0; l3 < l;) {
                    int l1 = 0;
                    if (l3 == 0 && completeLine && centerImage) {
                        k3 = (lineWidth - l * i) / 2;
                        l1 = k3;
                    }
                    while (l3 < lineWidth && l3 < l && ai[l2 * l + l3] == byte0) {
                        l1 += i;
                        l3++;
                        k3 += i;
                    }
                    if (l3 >= l && completeLine) {
                        l1 += lineWidth - k3;
                        k3 = lineWidth;
                    }
                    if (l1 >= 64) {
                        int ai1[] = _fldchar[(int) Math.floor(l1 / 64 - 1)];
                        l1 -= ai1[2];
                        a(ai1[0], ai1[1]);
                    }
                    int ai2[] = _fldif[l1];
                    a(ai2[0], ai2[1]);
                    if (l3 >= lineWidth || l3 >= l) {
                        break;
                    }
                    l1 = 0;
                    while (l3 < lineWidth && l3 < l && ai[l2 * l + l3] != byte0) {
                        l1 += i;
                        l3++;
                        k3 += i;
                    }
                    if (l1 >= 64) {
                        ai2 = _fldcase[(int) Math.floor(l1 / 64 - 1)];
                        l1 -= ai2[2];
                        a(ai2[0], ai2[1]);
                    }
                    ai2 = a[l1];
                    a(ai2[0], ai2[1]);
                }

                if (completeLine && k3 < lineWidth) {
                    int i2 = lineWidth - k3;
                    if (i2 >= 64) {
                        int ai3[] = _fldchar[(int) Math.floor(i2 / 64 - 1)];
                        i2 -= ai3[2];
                        a(ai3[0], ai3[1]);
                    }
                    int ai4[] = _fldif[i2];
                    a(ai4[0], ai4[1]);
                }
                while (minBytesLine > _fldfor - k2) {
                    int i4 = 0;
                    while (i4 < 8) {
                        a(0);
                        i4++;
                    }
                }
                _mthif();
                if (debug) {
                    System.out.println(" EOL");
                }
            }

        }

        if (completePage) {
            for (; j < pageLength; j++) {
                int j2 = lineWidth;
                if (j2 >= 64) {
                    int ai5[] = _fldchar[(int) Math.floor(j2 / 64 - 1)];
                    j2 -= ai5[2];
                    a(ai5[0], ai5[1]);
                }
                int ai6[] = _fldif[j2];
                a(ai6[0], ai6[1]);
                _mthif();
            }

        }
        if (createEOP) {
            a();
        }
        if (_fldelse != 7) {
            _fldnew[_fldfor] = _flddo;
            _fldfor++;
        }
        byte abyte0[] = new byte[_fldfor];
        for (int j3 = 0; j3 < _fldfor; j3++) {
            abyte0[j3] = _fldnew[j3];
        }

        return abyte0;
    }
}
