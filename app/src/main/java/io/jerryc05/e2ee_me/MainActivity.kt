package io.jerryc05.e2ee_me

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import io.jerryc05.e2ee_me.core.crypto.*
import io.jerryc05.e2ee_me.core.log.logA
import javax.crypto.spec.IvParameterSpec


class MainActivity : AppCompatActivity() {
  @ExperimentalStdlibApi
  @ExperimentalUnsignedTypes
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    val button = findViewById<Button>(R.id.button)
    val text = findViewById<EditText>(R.id.editTextTextMultiLine)
    val encryptedText = findViewById<TextView>(R.id.textView)
    val decryptedText = findViewById<TextView>(R.id.textView2)

    val kp1 = generateEcdhKeyPair()
    val kp2 = generateEcdhKeyPair()

    val k1 = exchangeEcdhAes(kp1.private, kp2.public)
    val k2 = exchangeEcdhAes(kp2.private, kp1.public)

    val ivByte = generateIv()

    button.setOnClickListener {
      val encryptText = encryptAes(
              text.text.toString().encodeToByteArray(),
              k1,
              IvParameterSpec(ivByte)
      )
      val b1 = ivByte + encryptText
      encryptedText.text = String(encodeB85(b1))

      val b2 = decodeB85(encodeB85(b1))
      logA("?", "b1 = ${b1.contentToString()}")
      logA("?", "b2 = ${b2.contentToString()}")

      decryptedText.text = String(decryptAes(
              encryptText,
              k2,
              IvParameterSpec(ivByte)
      ))
    }
  }
}