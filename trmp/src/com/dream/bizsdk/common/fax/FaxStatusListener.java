package com.dream.bizsdk.common.fax;


public interface FaxStatusListener {

    public static final int ST_OPEN_PORT = 1;
    public static final int ST_INIT_MODEM = 2;
    public static final int ST_CONNECTING = 3;
    public static final int ST_SEND_PAGE = 4;
    public static final int ST_CLOSE = 5;
    public static final int ST_CONVERT_FIlES = 6;

    public abstract void faxProgress(int i, int j);
}
