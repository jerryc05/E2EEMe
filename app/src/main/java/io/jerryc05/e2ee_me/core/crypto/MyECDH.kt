package io.jerryc05.e2ee_me.core.crypto

import io.jerryc05.e2ee_me.core.AES_CIPHER_ALGORITHM
import io.jerryc05.e2ee_me.core.ECDH_KEY_EXCHANGE
import io.jerryc05.e2ee_me.core.EC_KEYPAIR_ALGORITHM
import io.jerryc05.e2ee_me.core.EC_KEY_SIZE
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.PrivateKey
import java.security.PublicKey
import javax.crypto.KeyAgreement
import javax.crypto.SecretKey


private val keyAgreement by lazy { KeyAgreement.getInstance(ECDH_KEY_EXCHANGE) }

internal fun generateEcdhKeyPair(): KeyPair {
  val keyGen = KeyPairGenerator.getInstance(EC_KEYPAIR_ALGORITHM);
  keyGen.initialize(EC_KEY_SIZE)
  return keyGen.genKeyPair()
}

internal fun exchangeEcdhRaw(myPrivateKey: PrivateKey,
                             vararg otherPublicKeys: PublicKey): ByteArray {
  exchangeEcdhInternal(myPrivateKey, otherPublicKeys)
  return keyAgreement.generateSecret()
}

internal fun exchangeEcdhAes(myPrivateKey: PrivateKey,
                             vararg otherPublicKeys: PublicKey): SecretKey {
  exchangeEcdhInternal(myPrivateKey, otherPublicKeys)
  return keyAgreement.generateSecret(AES_CIPHER_ALGORITHM)
}

private fun exchangeEcdhInternal(myPrivateKey: PrivateKey,
                                 otherPublicKeys: Array<out PublicKey>) {
  if (otherPublicKeys.isEmpty())
    throw Exception("`otherPublicKeys` cannot be empty!")

  keyAgreement.init(myPrivateKey)

  otherPublicKeys.sort()
  for (pubKey in otherPublicKeys.dropLast(1))
    keyAgreement.doPhase(pubKey, false)

  keyAgreement.doPhase(otherPublicKeys.last(), true)
}