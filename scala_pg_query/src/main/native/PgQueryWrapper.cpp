#include "PgQueryWrapper.h"
#include <string.h>
#include <pg_query.h>

JNIEXPORT jstring JNICALL Java_PgQueryWrapper_pgQueryParse
  (JNIEnv *env, jobject obj, jstring string) {
    const char* str = env->GetStringUTFChars(string, 0);
    char cap[128];
    strcpy(cap, str);
    PgQueryParseResult result = pg_query_parse(cap);

 /*   jclass thisClass = env->GetObjectClass(obj);
    jfieldID fidMessage = env->GetFieldID(thisClass, "parse_tree", "Ljava/lang/String;");
    jstring message = env->GetObjectField(obj, fidMessage);
    message = env->NewStringUTF(result.parse_tree);
    env->SetObjectField(obj, fidMessage, message);
*/
    return env->NewStringUTF(result.parse_tree);
  }