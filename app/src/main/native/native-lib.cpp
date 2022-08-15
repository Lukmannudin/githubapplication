#include <jni.h>
#include <iostream>

extern "C" {

    JNIEXPORT jstring JNICALL
    Java_com_lukmannudin_githubapp_common_Keys_getTokenNative(JNIEnv *env, jobject instance) {
        std::string token;
        token = "dG9rZW4gZ2hwX0lQUlpYNjlpNGE2NmNMR05DYmNDUlYzQmZxUndVOTJMMHFDQQ==";
        return env->NewStringUTF(token.c_str());
    }

    JNIEXPORT jstring JNICALL
    Java_com_lukmannudin_githubapp_common_Keys_getUserAgentNative(JNIEnv *env, jobject instance) {
        std::string user;
        user = "THVrbWFubnVkaW4=";
        return env->NewStringUTF(user.c_str());
    }
}