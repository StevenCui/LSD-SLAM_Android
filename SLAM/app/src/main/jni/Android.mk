LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

include D:/resouce_soft/OpenCV-2.4.10-android-sdk/sdk/native/jni/OpenCV.mk

LOCAL_MODULE :=getImageAddressFromCpp
LOCAL_SRC_FILES :=ImgFun.cpp
LOCAL_LDLIBS +=  -llog -ldl

include $(BUILD_SHARED_LIBRARY)
