package org.opencv.samples.tutorial2;

/**
 * Created by Luohongcheng on 2015/11/24.
 */
public class LibImgFun {
//    static {
//        System.loadLibrary("mixed_sample");
//
//    }
    public static native long[] getMatDataFromCpp();


    public static native void  startWork(long addrS , long addrD);

    public static native float getX();
    public static native float getY();
    public static native float getZ();
    public static native float getAX();
    public static native float getAY();
    public static native float getAZ();


}
