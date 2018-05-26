#include "jni_HelloWorld.h"
#include <cstdio>

/*
 * Class:     jni_HelloWorld
 * Method:    displayHelloWorld
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_jni_HelloWorld_displayHelloWorld
  (JNIEnv *, jobject)
  {
  	printf("Hello World!\n");
  	return;
  }
