#include <jni.h>
#include <string>

extern "C"
JNIEXPORT jstring JNICALL
Java_org_gen_screensharesdk_Wfd_listen(JNIEnv *env, jobject obj) {

    // TODO
    std::string hello = "Hello from C++ in sdk !!!";

    return env->NewStringUTF(hello.c_str());
}