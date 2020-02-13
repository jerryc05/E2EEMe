package io.jerryc05.e2ee_me.core.report

import android.util.Log
import io.jerryc05.e2ee_me.BuildConfig
import io.jerryc05.e2ee_me.core.authToken
import io.jerryc05.e2ee_me.core.okHttpClient
import io.jerryc05.e2ee_me.core.repoName
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import java.io.IOException

internal fun report(title: String,
                    body: String,
                    labels: Array<String>) {
  return reportInternal(title, body, labels.joinToString(
          prefix = "[", postfix = "]", separator = ","))
}

internal fun report(title: String,
                    body: String,
                    label: String) {
  return reportInternal(title, body, "[\"$label\"]")
}

private const val TAG = "GithubReport"
internal val issueIdRegex by lazy { "\"number\": ?(\\d+)".toRegex() }

private fun reportInternal(title: String,
                           body: String,
                           label: String) {
  val postBody: String = "{\"title\":\"$title\",\"body\":\"$body\",\"labels\": $label}"

  val request = Request.Builder()
          .url("https://api.github.com/repos/$repoName/issues")
          .header("Authorization", "token ${String(authToken)}")
          .post(postBody.toRequestBody(
                  "application/json;charset=utf-8".toMediaTypeOrNull()))
          .build()

  okHttpClient.newCall(request).enqueue(object : Callback {
    override fun onFailure(call: Call, e: IOException) {
      if (BuildConfig.DEBUG)
        Log.e(TAG, "onFailure: ", e)
    }

    override fun onResponse(call: Call, response: Response) {
      response.body?.string()?.let {
        if (!response.isSuccessful && BuildConfig.DEBUG)
          Log.e(TAG, "onResponse: response not successful! body = \n${it}")

        val issueId = issueIdRegex.find(it)?.groupValues?.get(1)?.trim()
        if (BuildConfig.DEBUG)
          Log.i(TAG, "onResponse: new GitHub issues id $issueId")
      }
    }
  })
}