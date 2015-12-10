#include <jni.h>
#include <stdio.h>
#include <stdlib.h>
#include <opencv2/opencv.hpp>
extern "C" {
Mat *srcMat = new Mat();
Mat *depthMat = new Mat();

JNIEXPORT void JNICALL Java_com_lhc_slam_LibImgFun_startWork(JNIEnv *env, jobject obj);

JNIEXPORT void JNICALL Java_com_lhc_slam_LibImgFun_startWork(JNIEnv *env, jobject obj) {
    char* srcfile = NULL;
    char* dstfile = NULL;
    IplImage* src = NULL;
    IplImage* dst = NULL;


    srcfile = "sdcard/FilesTestImage/";

    //_sleep(5*1000);

    for(int i=0,j =1;i< 20;i++,j++){
        if(j==4)
            j = 1;
        srcfile = srcfile + to_string(j) + ".jpg";
        src = cvLoadImage(srcfile, -1);
        if (!src)
        {
            fprintf(stderr, "can not load image %s/n", srcfile);
            break;
        }
        dst = cvCreateImage(cvGetSize(src), 8, 1);
        _sleep(100);
    }

    if (!dst)
    {
        fprintf(stderr, "can not create image %s/n", dstfile);
        exit(-1);
    }


    cvCvtColor(src, dst, CV_RGB2GRAY);

    *srcMat = Mat(srcImg, true);
    *depthMat = Mat(dst ,true);
    return 0;
}


JNIEXPORT jlongArray JNICALL
        Java_com_lhc_slam_LibImgFun_getMatDataFromCpp(JNIEnv* env, jobject obj);

JNIEXPORT jlongArray JNICALL
Java_com_lhc_slam_LibImgFun_getMatDataFromCpp(JNIEnv* env, jobject obj) {
    /*Mat *bitmpaMat = (Mat*) address;
    Mat *hist = new Mat();
    *hist = *bitmpaMat;*/
    jlongArray addressToJava = env->NewLongArray(2);
    jlong matData[2];

    matData[1] = (jlong)*srcMat;
    matData[2] = (jlong)*depthMat;

    if(address[1]==0 || address[2]==0)
        return NULL;
    else {
        env->SetLongArrayRegion(addressToJava, 0, 2, matData[2]);
        return addressToJava;
    }
}
}
