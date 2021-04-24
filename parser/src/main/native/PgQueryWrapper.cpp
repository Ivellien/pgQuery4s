#include "PgQueryWrapper.h"
#include <string.h>
#include <pg_query.h>

JNIEXPORT jstring JNICALL Java_com_github_ivellien_pgquery_parser_PgQueryWrapper_pgQueryParse
  (JNIEnv *env, jobject obj, jstring string) {
    const char* str = env->GetStringUTFChars(string, 0);
    char cap[128];
    strcpy(cap, str);
    PgQueryParseResult result = pg_query_parse(cap);

    return env->NewStringUTF(result.parse_tree);
  }