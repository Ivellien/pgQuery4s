#include "PgQueryWrapper.h"
#include <string.h>
#include <pg_query.h>
#include <cstdlib>

JNIEXPORT jstring JNICALL Java_com_github_ivellien_pgquery_native_PgQueryWrapper_pgQueryParse
  (JNIEnv *env, jobject obj, jstring string) {
    // Guard clause to check for null jstring argument
    if (string == NULL) {
        env->ThrowNew(env->FindClass("java/lang/NullPointerException"), "Input string cannot be null");
        return NULL;
    }
    
    const char* cstr = env->GetStringUTFChars(string, 0);

    // Check for NULL to ensure GetStringUTFChars succeeded
    if (cstr == NULL) {
        env->ThrowNew(env->FindClass("java/lang/OutOfMemoryError"), "GetStringUTFChars failed in Java_com_github_ivellien_pgquery_native_PgQueryWrapper_pgQueryParse");
        return NULL;
    }

    PgQueryParseResult parse_result = pg_query_parse(cstr);

    // Release the string as it's no longer needed
    env->ReleaseStringUTFChars(string, cstr);

    // Check for parsing errors
    if (parse_result.error) {
        env->ThrowNew(env->FindClass("com/github/ivellien/pgquery/core/PgQueryException"), parse_result.error->message);
        pg_query_free_parse_result(parse_result);
        return NULL;
    }

    // Create a new Java string from the parse result
    jstring result = env->NewStringUTF(parse_result.parse_tree);

    // Check for NULL to ensure NewStringUTF succeeded
    if (result == NULL) {
        env->ThrowNew(env->FindClass("java/lang/OutOfMemoryError"), "NewStringUTF failed in Java_com_github_ivellien_pgquery_native_PgQueryWrapper_pgQueryParse");
        pg_query_free_parse_result(parse_result);
        return NULL;
    }

    // Free the parse result as it's no longer needed
    pg_query_free_parse_result(parse_result);

    return result;
}
