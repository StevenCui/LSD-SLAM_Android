package com.lhc.slam;

/**
 * Created by Luohongcheng on 2015/11/24.
 */
public class LibImgFun {
    static {
        System.loadLibrary("getImageAddressFromCpp");

    }
    public static native long[] getMatDataFromCpp();

    //public static native String getInfomationFromCpp();

    //public static native void startWork();

    //public static native int intfun();

    public static native void startWork();

}
