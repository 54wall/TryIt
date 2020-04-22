//
// Created by weiqaing on 2019/2/19.
//

#inclue "pri_weiqiang_tryit_jni_JNIUtils.h"
JNIEXPORT jstring JNICALL Java_pri_weiqiang_tryit_jni_JNIUtils_sayHelloFromJNI
  (JNIEnv *env, jclass){
  return rev->NewStringUTF("Hello World From JNI !!!!!");
  }