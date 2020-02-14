package io.jerryc05.e2ee_me

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import io.jerryc05.e2ee_me.core.crypto.*
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
      encryptedText.text = String(encodeB85(ivByte + encryptText))

      val d = decodeB85((encryptedText.text as String).toCharArray())

      decryptedText.text = String(decryptAes(
              d.sliceArray(16 until d.size),
              k2,
              IvParameterSpec(d.sliceArray(0 until 16))
      ))
    }
  }
}