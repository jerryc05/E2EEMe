package io.jerryc05.e2ee_me.core.log

import io.jerryc05.e2ee_me.core.authToken
import io.jerryc05.e2ee_me.core.crypto.decodeB85
import io.jerryc05.e2ee_me.core.crypto.tryUnwrapB85Array
import io.jerryc05.e2ee_me.core.repoName
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.net.URL
import java.nio.charset.StandardCharsets
import javax.net.ssl.HttpsURLConnection


private const val TAG = "GithubReport"
internal val issueIdRegex by lazy { "\"number\": ?(\\d+)".toRegex() }
//private val callback by lazy {
//  object : Callback {
//    override fun onResponse(call: Call, response: Response) {
//      response.body?.string()?.let {
//        if (!response.isSuccessful && BuildConfig.DEBUG)
//          Log.e(TAG, "onResponse: response not successful! body = \n${it}")
//
//        val issueId = issueIdRegex.find(it)?.groupValues?.get(1)?.trim()
//        if (BuildConfig.DEBUG)
//          Log.i(TAG, "onResponse: new GitHub issues id $issueId")
//      }
//    }
//
//    override fun onFailure(call: Call, e: IOException) {
//      if (BuildConfig.DEBUG)
//        Log.e(TAG, "onFailure: ", e)
//    }
//  }
//}

internal fun reportGithub(title: String, body: String) {
  Thread {
    val map: Map<String, Any> = hashMapOf(
            Pair("title", title), Pair("body", body))
    val postBody = JSONObject(map).toString(0)
    logA(TAG, "Post Body:\n$postBody")


    val urlConn = URL(
            "https://api.github.com/repos/$repoName/issues"
    ).openConnection() as HttpsURLConnection

    urlConn.doInput = true
    urlConn.doOutput = true
    urlConn.useCaches = false
    urlConn.requestMethod = "POST"
    urlConn.setRequestProperty("User-Agent", null)
    urlConn.setRequestProperty(
            "Authorization",
            "token ${String(authToken.toCharArray().tryUnwrapB85Array().decodeB85())}"
    )

    urlConn.outputStream.let {
      it.write(postBody.toByteArray())
      it.flush()
      it.close()
    }

    urlConn.connect()

    val resBody = urlConn.inputStream.let { iStream ->
      val oStream = ByteArrayOutputStream()
      val buffer = ByteArray(1024)
      var length: Int
      while (iStream.read(buffer).also { length = it } != -1) {
        oStream.write(buffer, 0, length)
      }
      oStream.toString(StandardCharsets.UTF_8.name())
    }
    logA(TAG, "Respond Body:\n$resBody")

    if (urlConn.responseCode in 200 until 300) {
      val issueId = issueIdRegex.find(resBody)?.groupValues?.get(1)?.trim()
      logA(TAG, "onResponse: new GitHub issues id $issueId")
    } else {
      logA(TAG, "onResponse: response not successful! body = \n$resBody")
    }
  }.start()

//  val request = Request.Builder()
//          .url()
//          .header()
//          .post(postBody.toRequestBody(
//                  "application/json;charset=utf-8".toMediaTypeOrNull()))
//          .build()
//
//  okHttpClient.newCall(request).enqueue(callback)
}