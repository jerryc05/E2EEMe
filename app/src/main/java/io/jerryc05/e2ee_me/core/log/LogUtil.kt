@file:Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")

package io.jerryc05.e2ee_me.core.log

import android.util.Log
import io.jerryc05.e2ee_me.BuildConfig

internal fun logA(tag: String, msg: String?) {
  if (BuildConfig.DEBUG)
    Log.println(Log.ASSERT, tag, msg)
}

internal fun logE(tag: String, msg: String?) {
  if (BuildConfig.DEBUG)
    Log.e(tag, msg)
}

internal fun logE(tag: String, msg: String?, tr: Throwable) {
  if (BuildConfig.DEBUG)
    Log.e(tag, msg, tr)
}