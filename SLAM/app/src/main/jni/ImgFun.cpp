#include <jni.h>
#include <stdio.h>
#include <stdlib.h>
#include <iostream>
#include <unistd.h>
#include <string.h>
#include <opencv2/opencv.hpp>
using namespace cv;
static Mat *srcMat = new Mat();
static Mat *depthMat = new Mat();
static string srcfile ;
static string depthfile;
extern "C" {
JNIEXPORT void JNICALL Java_com_lhc_slam_LibImgFun_startWork(JNIEnv *env, jobject obj);
JNIEXPORT void JNICALL Java_com_lhc_slam_LibImgFun_startWork(JNIEnv *env, jobject obj) {
    for(int i = 0 ;i <10 ;i++){
    srcfile=  "/sdcard/TestFiles/1.jpg";
    depthfile= "/sdcard/TestFiles/2.jpg";
    /* Mat img= imread(srcfile);
     *srcMat = img;*/
    *srcMat =imread(srcfile);
    *depthMat = imread(depthfile);
    sleep(1);
    srcfile=  "/sdcard/TestFiles/2.jpg";
    depthfile= "/sdcard/TestFiles/1.jpg";
    /* Mat img= imread(srcfile);
     *srcMat = img;*/
    *srcMat =imread(srcfile);
    *depthMat = imread(depthfile);

    }

    //IplImage* src= cvLoadImage("/sdcard/TestFiles/1.jpg", -1);
    //Mat img=imread("/sdcard/TestFiles/1.jpg");

   // *srcMat = img;


    /*char* srcfile = NULL;
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
    *depthMat = Mat(dst ,true);*/

}

JNIEXPORT jlongArray JNICALL
        Java_com_lhc_slam_LibImgFun_getMatDataFromCpp(JNIEnv* env, jobject obj);

JNIEXPORT jlongArray JNICALL
Java_com_lhc_slam_LibImgFun_getMatDataFromCpp(JNIEnv* env, jobject obj) {

    jlongArray addressToJava = env->NewLongArray(2);
    jlong address[2];

    //对2个数组进行赋值
    address[0] = (jlong)srcMat;
    address[1] = (jlong)depthMat;

    if(address[0]==0 || address[1]==0)
        return NULL;
    else {
        env->SetLongArrayRegion(addressToJava, 0, 2, address);
        return addressToJava;
    }
}

}


