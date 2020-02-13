package io.jerryc05.e2ee_me.core.log

import android.util.Log
import io.jerryc05.e2ee_me.BuildConfig

internal fun logA(tag: String, msg: String) {
  if (BuildConfig.DEBUG)
    Log.println(Log.ASSERT, tag, msg)
}