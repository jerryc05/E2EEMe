package io.jerryc05.e2ee_me.ui

//import androidx.appcompat.app.AppCompatActivity
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import io.jerryc05.e2ee_me.R
import io.jerryc05.e2ee_me.core.AES_CIPHER_ALGORITHM
import io.jerryc05.e2ee_me.core.AES_KEY_SIZE
import io.jerryc05.e2ee_me.core.ANDROID_KEYSTORE_PROVIDER
import io.jerryc05.e2ee_me.core.EC_KEYPAIR_ALGORITHM
import io.jerryc05.e2ee_me.core.crypto.*
import io.jerryc05.e2ee_me.core.log.logE
import java.security.KeyFactory
import java.security.KeyPair
import java.security.spec.X509EncodedKeySpec
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec


//class MainActivity : AppCompatActivity() {
class MainActivity : Activity(), View.OnClickListener, TextWatcher {

  private lateinit var textPubKey: TextView
  private lateinit var btnCopyKey: Button

  private lateinit var editTextPubKey: EditText
  private lateinit var editTextText: EditText
  private lateinit var textContent: TextView

  private lateinit var btnEncrypt: Button
  private lateinit var btnDecrypt: Button
  private lateinit var btnCopyContent: Button

  private var keyPair: KeyPair? = null
  private var exchangedKey: SecretKey? = null

  private val clipboardManager by lazy {
    getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
  }

  @ExperimentalUnsignedTypes
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    textPubKey = findViewById(R.id.textView_pubKey)
    val btnGenKey = findViewById<Button>(R.id.btn_genKey)
    btnCopyKey = findViewById(R.id.btn_copyKey)
    btnCopyKey.isEnabled = false

    editTextPubKey = findViewById(R.id.editText_pubKey)
    editTextPubKey.addTextChangedListener(this)
    editTextText = findViewById(R.id.editText_text)

    btnEncrypt = findViewById(R.id.btn_encrypt)
    btnEncrypt.isEnabled = false
    btnDecrypt = findViewById(R.id.btn_decrypt)
    btnDecrypt.isEnabled = false

    textContent = findViewById(R.id.textView_content)
    btnCopyContent = findViewById(R.id.btn_copyContent)

    btnGenKey.setOnClickListener(this)
    btnCopyKey.setOnClickListener(this)
    btnEncrypt.setOnClickListener(this)
    btnDecrypt.setOnClickListener(this)
  }

  @SuppressLint("SetTextI18n")
  override fun onClick(v: View?) {
    when (v?.id) {
      R.id.btn_genKey -> {
        keyPair = generateEcdhKeyPair()
        textPubKey.text = "== My Public Key ==\n" +
                String(keyPair!!.public.encoded.encodeB85().wrapB85Array())
        btnCopyKey.isEnabled = true
      }

      R.id.btn_copyKey -> {
        if (keyPair == null) {
          btnCopyKey.isEnabled = false
          textPubKey.text = "No Public Key!"
        } else {
          val clip = ClipData.newPlainText(null,
                  String(keyPair!!.public.encoded.encodeB85().wrapB85Array()))
          clipboardManager.setPrimaryClip(clip)
          Toast.makeText(applicationContext, "Copied!", Toast.LENGTH_SHORT).show()
        }
      }

      R.id.btn_encrypt, R.id.btn_decrypt -> {
        if (exchangedKey != null) {
          val str =
                  if (v.id == R.id.btn_encrypt) {
                    val ivByte = generateIv()
                    String(encryptAes(
                            editTextText.text.toString().toByteArray(),
                            exchangedKey!!,
                            IvParameterSpec(ivByte)
                    ).encodeB85().wrapB85Array())
                  } else {
                    val temp = editTextText.text.toString()
                            .toCharArray().tryUnwrapB85Array()
                    val data = temp.decodeB85()
                    String(decryptAes(
                            data.sliceArray(
                                    AES_KEY_SIZE / Byte.SIZE_BITS until data.size),
                            exchangedKey!!,
                            IvParameterSpec(data.sliceArray(
                                    0 until AES_KEY_SIZE / Byte.SIZE_BITS))))
                  }
          textContent.text = str
        } else {
          btnEncrypt.isEnabled = false
          btnDecrypt.isEnabled = false
        }
      }

      R.id.btn_copyContent -> {
        val clip = ClipData.newPlainText(
                null, textContent.text.toString())
        clipboardManager.setPrimaryClip(clip)
        Toast.makeText(applicationContext, "Copied!", Toast.LENGTH_SHORT).show()
      }
    }
  }

  override fun afterTextChanged(s: Editable?) {
    try {
      val pubKeyBytes = s.toString()
              .toCharArray().tryUnwrapB85Array().decodeB85()
      val keyFactory = KeyFactory.getInstance(
              EC_KEYPAIR_ALGORITHM
              ,
              ANDROID_KEYSTORE_PROVIDER
      )
      val pubKey = keyFactory.generatePublic(
              X509EncodedKeySpec(pubKeyBytes)
      )

      keyAgreement.doPhase(pubKey, true)
      exchangedKey = keyAgreement.generateSecret(AES_CIPHER_ALGORITHM)
//            exchangedKey = exchangeEcdhAes(it, pubKey)
      btnEncrypt.isEnabled = true
      btnDecrypt.isEnabled = true
    } catch (e: Exception) {
      logE("MainActivity", null, e)
      exchangedKey = null
      btnEncrypt.isEnabled = false
      btnDecrypt.isEnabled = false
    }
  }

  override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
  }

  override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
  }
}