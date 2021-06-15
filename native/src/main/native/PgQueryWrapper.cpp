#include "PgQueryWrapper.h"
#include <string.h>
#include <pg_query.h>
#include <cstdlib>

JNIEXPORT jstring JNICALL Java_com_github_ivellien_pgquery_native_PgQueryWrapper_pgQueryParse
  (JNIEnv *env, jobject obj, jstring string) {
    const char* str = env->GetStringUTFChars(string, 0);
    char *cap = (char *) malloc(strlen(str) + 1);
    strcpy(cap, str);
    PgQueryParseResult result = pg_query_parse(cap);

    free(cap);
    return env->NewStringUTF(result.parse_tree);
  }