@file:Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")

package io.jerryc05.e2ee_me.core.log

import android.app.AlertDialog
import android.util.Log
import io.jerryc05.e2ee_me.BuildConfig
import io.jerryc05.e2ee_me.core.weakActivity

internal fun logA(tag: String, msg: String?) {
  if (BuildConfig.DEBUG)
    Log.println(Log.ASSERT, tag, msg)
}

internal fun logE(tag: String, msg: String?) {
  logEInternal(tag, msg, null)
}

internal fun logE(tag: String, msg: String?, tr: Throwable) {
  logEInternal(tag, msg, tr)
}

private fun logEInternal(tag: String, msg: String?, tr: Throwable?) {
  if (BuildConfig.DEBUG)
    Log.e(tag, msg, tr)

  weakActivity.get()?.let {
    AlertDialog.Builder(it)
            .setTitle("Error!")
            .setMessage("$tag: $msg\n${Log.getStackTraceString(tr)}")
            .show()
  }
}