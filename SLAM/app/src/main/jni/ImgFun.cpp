#include <jni.h>
#include <stdio.h>
#include <stdlib.h>
#include <opencv2/opencv.hpp>
extern "C" {
JNIEXPORT jint JNICALL Java_com_lhc_slam_LibImgFun_intfun(JNIEnv *env, jobject obj);

JNIEXPORT jint JNICALL Java_com_lhc_slam_LibImgFun_intfun(JNIEnv *env, jobject obj) {
    jint a = 2;
    return a;
}


JNIEXPORT jlongArray JNICALL
        Java_com_lhc_slam_LibImgFun_getImageAddressFromCpp(JNIEnv* env, jobject obj);

JNIEXPORT jlongArray JNICALL
Java_com_lhc_slam_LibImgFun_getImageAddressFromCpp(JNIEnv* env, jobject obj) {
    /*Mat *bitmpaMat = (Mat*) address;
    Mat *hist = new Mat();
    *hist = *bitmpaMat;*/
    jlongArray addressToJava = env->NewLongArray(3);
    jlong address[3];

    //对三个数组进行赋值
    /*address[0] = (jlong)*hist;
    address[1] = (jlong)*hist;*/

    //测试用
    address[0] = 6325;
    address[1] = 4546;
    address[2] = 5656;

    if(address[0]==0 || address[1]==0)
        return NULL;
    else {
        env->SetLongArrayRegion(addressToJava, 0, 3, address);
        return addressToJava;
    }
}
}
