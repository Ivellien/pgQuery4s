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

    char *res = (char *) malloc(strlen(result.parse_tree) + 1);
    strcpy(res, result.parse_tree);
    pg_query_free_parse_result(result);

    return env->NewStringUTF(res);
  }