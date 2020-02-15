package io.jerryc05.e2ee_me.core.log

import android.util.Log
import io.jerryc05.e2ee_me.BuildConfig
import io.jerryc05.e2ee_me.core.authToken
import io.jerryc05.e2ee_me.core.crypto.decodeB85
import io.jerryc05.e2ee_me.core.crypto.tryUnwrapB85Array
import io.jerryc05.e2ee_me.core.okHttpClient
import io.jerryc05.e2ee_me.core.repoName
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException


private const val TAG = "GithubReport"
internal val issueIdRegex by lazy { "\"number\": ?(\\d+)".toRegex() }
private val callback by lazy {
  object : Callback {
    override fun onResponse(call: Call, response: Response) {
      response.body?.string()?.let {
        if (!response.isSuccessful && BuildConfig.DEBUG)
          Log.e(TAG, "onResponse: response not successful! body = \n${it}")

        val issueId = issueIdRegex.find(it)?.groupValues?.get(1)?.trim()
        if (BuildConfig.DEBUG)
          Log.i(TAG, "onResponse: new GitHub issues id $issueId")
      }
    }

    override fun onFailure(call: Call, e: IOException) {
      if (BuildConfig.DEBUG)
        Log.e(TAG, "onFailure: ", e)
    }
  }
}

internal fun reportGithub(title: String, body: String) {
  val map: Map<String, Any> = hashMapOf(
          Pair("title", title), Pair("body", body))
  val postBody = JSONObject(map).toString(0)
  logA(TAG, "Post Body:\n$postBody")

  val request = Request.Builder()
          .url("https://api.github.com/repos/$repoName/issues")
          .header("Authorization",
                  "token ${String(authToken
                          .toCharArray()
                          .tryUnwrapB85Array()
                          .decodeB85())}")
          .post(postBody.toRequestBody(
                  "application/json;charset=utf-8".toMediaTypeOrNull()))
          .build()

  okHttpClient.newCall(request).enqueue(callback)
}

/*


private fun reportGithubInternal(title: String,
                                 body: String,
                                 label: String) {
  val map: Map<String, String> = hashMapOf(
          Pair("title", title),
          Pair("body", body),
          Pair("labels", label)
  )
  val postBody = JSONObject(map).toString(0)
  logA(TAG, "Post Body:\n$postBody")

  val request = Request.Builder()
          .url("https://api.github.com/repos/$repoName/issues")
          .header("Authorization",
                  "token ${String(authToken
                          .toCharArray()
                          .tryUnwrapB85Array()
                          .decodeB85()
                  )}")
          .post(postBody.toRequestBody(
                  "application/json;charset=utf-8".toMediaTypeOrNull()))
          .build()

  okHttpClient.newCall(request).enqueue(callback)
}
 */