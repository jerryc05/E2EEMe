@file:Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS", "unused")

package io.jerryc05.e2ee_me.core.log

import android.app.AlertDialog
import android.content.DialogInterface
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

private fun logEInternal(tag: String, msg0: String?, tr: Throwable?) {
  val msg = msg0 ?: ""

  if (BuildConfig.DEBUG)
    Log.e(tag, msg, tr)

  val errMsg = "$tag: $msg\n```\n${Log.getStackTraceString(tr)}\n```"

  val callback by lazy {
    DialogInterface.OnClickListener { dialogInterface: DialogInterface, i: Int ->
      when (i) {
        DialogInterface.BUTTON_POSITIVE -> {
          reportGithub(
                  "$tag:${if (msg.isBlank()) "" else "$msg |"} $tr",
                  errMsg,
                  "bug"
          )
        }
        DialogInterface.BUTTON_NEGATIVE -> {
        }
        else -> throw Exception("Invalid button!")
      }
      dialogInterface.dismiss()
    }
  }

  weakActivity.get()?.let {
    AlertDialog.Builder(it)
            .setTitle("Error!")
            .setMessage(errMsg)
            .setPositiveButton("Report", callback)
            .setNegativeButton("Cancel", callback)
            .show()
  }
}