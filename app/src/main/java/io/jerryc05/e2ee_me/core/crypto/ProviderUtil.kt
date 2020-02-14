package io.jerryc05.e2ee_me.core.crypto

import io.jerryc05.e2ee_me.core.log.logA
import java.security.Security

private const val TAG = "Provider"

fun printProviders() {
  for (provider in Security.getProviders()) {
    logA(TAG, "=== ${provider.name} | ${provider.info} ===")
    val iterator = provider.services.iterator()
    while (iterator.hasNext()) {
      val service = iterator.next()
      logA(TAG, " " + (if (iterator.hasNext()) '├' else '└') +
              " ${service.type}  \t| ${service.algorithm}")
    }
  }

  /*
A/Provider: === AndroidNSSP | Android Network Security Policy Provider ===
A/Provider:  └ TrustManagerFactory  	| PKIX
   */
  /*
A/Provider: === AndroidOpenSSL | Android's OpenSSL-backed security provider ===
A/Provider:  ├ SSLContext  	| SSL
A/Provider:  ├ SSLContext  	| TLS
A/Provider:  ├ SSLContext  	| TLSv1
A/Provider:  ├ SSLContext  	| TLSv1.1
A/Provider:  ├ SSLContext  	| TLSv1.2
A/Provider:  ├ SSLContext  	| TLSv1.3
A/Provider:  ├ SSLContext  	| Default
A/Provider:  ├ AlgorithmParameters  	| AES
A/Provider:  ├ AlgorithmParameters  	| ChaCha20
A/Provider:  ├ AlgorithmParameters  	| DESEDE
A/Provider:  ├ AlgorithmParameters  	| GCM
A/Provider:  ├ AlgorithmParameters  	| OAEP
A/Provider:  ├ AlgorithmParameters  	| PSS
A/Provider:  ├ AlgorithmParameters  	| EC
A/Provider:  ├ MessageDigest  	| SHA-1
A/Provider:  ├ MessageDigest  	| SHA-224
A/Provider:  ├ MessageDigest  	| SHA-256
A/Provider:  ├ MessageDigest  	| SHA-384
A/Provider:  ├ MessageDigest  	| SHA-512
A/Provider:  ├ MessageDigest  	| MD5
A/Provider:  ├ KeyGenerator  	| ARC4
A/Provider:  ├ KeyGenerator  	| AES
A/Provider:  ├ KeyGenerator  	| ChaCha20
A/Provider:  ├ KeyGenerator  	| DESEDE
A/Provider:  ├ KeyGenerator  	| HmacMD5
A/Provider:  ├ KeyGenerator  	| HmacSHA1
A/Provider:  ├ KeyGenerator  	| HmacSHA224
A/Provider:  ├ KeyGenerator  	| HmacSHA256
A/Provider:  ├ KeyGenerator  	| HmacSHA384
A/Provider:  ├ KeyGenerator  	| HmacSHA512
A/Provider:  ├ KeyPairGenerator  	| RSA
A/Provider:  ├ KeyPairGenerator  	| EC
A/Provider:  ├ KeyFactory  	| RSA
A/Provider:  ├ KeyFactory  	| EC
A/Provider:  ├ SecretKeyFactory  	| DESEDE
A/Provider:  ├ KeyAgreement  	| ECDH
A/Provider:  ├ Signature  	| MD5withRSA
A/Provider:  ├ Signature  	| SHA1withRSA
A/Provider:  ├ Signature  	| SHA224withRSA
A/Provider:  ├ Signature  	| SHA256withRSA
A/Provider:  ├ Signature  	| SHA384withRSA
A/Provider:  ├ Signature  	| SHA512withRSA
A/Provider:  ├ Signature  	| NONEwithRSA
A/Provider:  ├ Signature  	| NONEwithECDSA
A/Provider:  ├ Signature  	| SHA1withECDSA
A/Provider:  ├ Signature  	| SHA224withECDSA
A/Provider:  ├ Signature  	| SHA256withECDSA
A/Provider:  ├ Signature  	| SHA384withECDSA
A/Provider:  ├ Signature  	| SHA512withECDSA
A/Provider:  ├ Signature  	| SHA1withRSA/PSS
A/Provider:  ├ Signature  	| SHA224withRSA/PSS
A/Provider:  ├ Signature  	| SHA256withRSA/PSS
A/Provider:  ├ Signature  	| SHA384withRSA/PSS
A/Provider:  ├ Signature  	| SHA512withRSA/PSS
A/Provider:  ├ SecureRandom  	| SHA1PRNG
A/Provider:  ├ Cipher  	| RSA/ECB/NoPadding
A/Provider:  ├ Cipher  	| RSA/ECB/PKCS1Padding
A/Provider:  ├ Cipher  	| RSA/ECB/OAEPPadding
A/Provider:  ├ Cipher  	| RSA/ECB/OAEPWithSHA-1AndMGF1Padding
A/Provider:  ├ Cipher  	| RSA/ECB/OAEPWithSHA-224AndMGF1Padding
A/Provider:  ├ Cipher  	| RSA/ECB/OAEPWithSHA-256AndMGF1Padding
A/Provider:  ├ Cipher  	| RSA/ECB/OAEPWithSHA-384AndMGF1Padding
A/Provider:  ├ Cipher  	| RSA/ECB/OAEPWithSHA-512AndMGF1Padding
A/Provider:  ├ Cipher  	| AES/ECB/NoPadding
A/Provider:  ├ Cipher  	| AES/ECB/PKCS5Padding
A/Provider:  ├ Cipher  	| AES/CBC/NoPadding
A/Provider:  ├ Cipher  	| AES/CBC/PKCS5Padding
A/Provider:  ├ Cipher  	| AES/CTR/NoPadding
A/Provider:  ├ Cipher  	| AES_128/ECB/NoPadding
A/Provider:  ├ Cipher  	| AES_128/ECB/PKCS5Padding
A/Provider:  ├ Cipher  	| AES_128/CBC/NoPadding
A/Provider:  ├ Cipher  	| AES_128/CBC/PKCS5Padding
A/Provider:  ├ Cipher  	| AES_256/ECB/NoPadding
A/Provider:  ├ Cipher  	| AES_256/ECB/PKCS5Padding
A/Provider:  ├ Cipher  	| AES_256/CBC/NoPadding
A/Provider:  ├ Cipher  	| AES_256/CBC/PKCS5Padding
A/Provider:  ├ Cipher  	| DESEDE/CBC/NoPadding
A/Provider:  ├ Cipher  	| DESEDE/CBC/PKCS5Padding
A/Provider:  ├ Cipher  	| ARC4
A/Provider:  ├ Cipher  	| AES/GCM/NoPadding
A/Provider:  ├ Cipher  	| AES_128/GCM/NoPadding
A/Provider:  ├ Cipher  	| AES_256/GCM/NoPadding
A/Provider:  ├ Cipher  	| ChaCha20
A/Provider:  ├ Cipher  	| ChaCha20/Poly1305/NoPadding
A/Provider:  ├ Mac  	| HmacMD5
A/Provider:  ├ Mac  	| HmacSHA1
A/Provider:  ├ Mac  	| HmacSHA224
A/Provider:  ├ Mac  	| HmacSHA256
A/Provider:  ├ Mac  	| HmacSHA384
A/Provider:  ├ Mac  	| HmacSHA512
A/Provider:  └ CertificateFactory  	| X509
   */
  /*
A/Provider: === CertPathProvider | Provider of CertPathBuilder and CertPathVerifier ===
A/Provider:  ├ CertPathBuilder  	| PKIX
A/Provider:  └ CertPathValidator  	| PKIX
   */
  /*
A/Provider: === AndroidKeyStoreBCWorkaround | Android KeyStore security provider to work around Bouncy Castle ===
A/Provider:  ├ Mac  	| HmacSHA1
A/Provider:  ├ Mac  	| HmacSHA224
A/Provider:  ├ Mac  	| HmacSHA256
A/Provider:  ├ Mac  	| HmacSHA384
A/Provider:  ├ Mac  	| HmacSHA512
A/Provider:  ├ Cipher  	| AES/ECB/NoPadding
A/Provider:  ├ Cipher  	| AES/ECB/PKCS7Padding
A/Provider:  ├ Cipher  	| AES/CBC/NoPadding
A/Provider:  ├ Cipher  	| AES/CBC/PKCS7Padding
A/Provider:  ├ Cipher  	| AES/CTR/NoPadding
A/Provider:  ├ Cipher  	| AES/GCM/NoPadding
A/Provider:  ├ Cipher  	| RSA/ECB/NoPadding
A/Provider:  ├ Cipher  	| RSA/ECB/PKCS1Padding
A/Provider:  ├ Cipher  	| RSA/ECB/OAEPPadding
A/Provider:  ├ Cipher  	| RSA/ECB/OAEPWithSHA-1AndMGF1Padding
A/Provider:  ├ Cipher  	| RSA/ECB/OAEPWithSHA-224AndMGF1Padding
A/Provider:  ├ Cipher  	| RSA/ECB/OAEPWithSHA-256AndMGF1Padding
A/Provider:  ├ Cipher  	| RSA/ECB/OAEPWithSHA-384AndMGF1Padding
A/Provider:  ├ Cipher  	| RSA/ECB/OAEPWithSHA-512AndMGF1Padding
A/Provider:  ├ Signature  	| NONEwithRSA
A/Provider:  ├ Signature  	| MD5withRSA
A/Provider:  ├ Signature  	| SHA1withRSA
A/Provider:  ├ Signature  	| SHA224withRSA
A/Provider:  ├ Signature  	| SHA256withRSA
A/Provider:  ├ Signature  	| SHA384withRSA
A/Provider:  ├ Signature  	| SHA512withRSA
A/Provider:  ├ Signature  	| SHA1withRSA/PSS
A/Provider:  ├ Signature  	| SHA224withRSA/PSS
A/Provider:  ├ Signature  	| SHA256withRSA/PSS
A/Provider:  ├ Signature  	| SHA384withRSA/PSS
A/Provider:  ├ Signature  	| SHA512withRSA/PSS
A/Provider:  ├ Signature  	| NONEwithECDSA
A/Provider:  ├ Signature  	| SHA1withECDSA
A/Provider:  ├ Signature  	| SHA224withECDSA
A/Provider:  ├ Signature  	| SHA256withECDSA
A/Provider:  ├ Signature  	| SHA384withECDSA
A/Provider:  └ Signature  	| SHA512withECDSA
   */
  /*
A/Provider: === BC | BouncyCastle Security Provider v1.61 ===
A/Provider:  ├ MessageDigest  	| MD5
A/Provider:  ├ Mac  	| HMACMD5
A/Provider:  ├ KeyGenerator  	| HMACMD5
A/Provider:  ├ MessageDigest  	| SHA-1
A/Provider:  ├ Mac  	| HMACSHA1
A/Provider:  ├ KeyGenerator  	| HMACSHA1
A/Provider:  ├ Mac  	| PBEWITHHMACSHA
A/Provider:  ├ Mac  	| PBEWITHHMACSHA1
A/Provider:  ├ SecretKeyFactory  	| PBEWITHHMACSHA1
A/Provider:  ├ MessageDigest  	| SHA-224
A/Provider:  ├ Mac  	| PBEWITHHMACSHA224
A/Provider:  ├ Mac  	| HMACSHA224
A/Provider:  ├ KeyGenerator  	| HMACSHA224
A/Provider:  ├ MessageDigest  	| SHA-256
A/Provider:  ├ Mac  	| PBEWITHHMACSHA256
A/Provider:  ├ Mac  	| HMACSHA256
A/Provider:  ├ KeyGenerator  	| HMACSHA256
A/Provider:  ├ MessageDigest  	| SHA-384
A/Provider:  ├ Mac  	| PBEWITHHMACSHA384
A/Provider:  ├ Mac  	| HMACSHA384
A/Provider:  ├ KeyGenerator  	| HMACSHA384
A/Provider:  ├ MessageDigest  	| SHA-512
A/Provider:  ├ Mac  	| PBEWITHHMACSHA512
A/Provider:  ├ Mac  	| HMACSHA512
A/Provider:  ├ KeyGenerator  	| HMACSHA512
A/Provider:  ├ SecretKeyFactory  	| PBKDF2WithHmacSHA1
A/Provider:  ├ SecretKeyFactory  	| PBKDF2WithHmacSHA1And8BIT
A/Provider:  ├ SecretKeyFactory  	| PBKDF2WithHmacSHA224
A/Provider:  ├ SecretKeyFactory  	| PBKDF2WithHmacSHA256
A/Provider:  ├ SecretKeyFactory  	| PBKDF2WithHmacSHA384
A/Provider:  ├ SecretKeyFactory  	| PBKDF2WithHmacSHA512
A/Provider:  ├ SecretKeyFactory  	| PBEWithHmacSHA1AndAES_128
A/Provider:  ├ SecretKeyFactory  	| PBEWithHmacSHA224AndAES_128
A/Provider:  ├ SecretKeyFactory  	| PBEWithHmacSHA256AndAES_128
A/Provider:  ├ SecretKeyFactory  	| PBEWithHmacSHA384AndAES_128
A/Provider:  ├ SecretKeyFactory  	| PBEWithHmacSHA512AndAES_128
A/Provider:  ├ SecretKeyFactory  	| PBEWithHmacSHA1AndAES_256
A/Provider:  ├ SecretKeyFactory  	| PBEWithHmacSHA224AndAES_256
A/Provider:  ├ SecretKeyFactory  	| PBEWithHmacSHA256AndAES_256
A/Provider:  ├ SecretKeyFactory  	| PBEWithHmacSHA384AndAES_256
A/Provider:  ├ SecretKeyFactory  	| PBEWithHmacSHA512AndAES_256
A/Provider:  ├ AlgorithmParameters  	| PKCS12PBE
A/Provider:  ├ AlgorithmParameters  	| PBEWithHmacSHA1AndAES_128
A/Provider:  ├ AlgorithmParameters  	| PBEWithHmacSHA224AndAES_128
A/Provider:  ├ AlgorithmParameters  	| PBEWithHmacSHA256AndAES_128
A/Provider:  ├ AlgorithmParameters  	| PBEWithHmacSHA384AndAES_128
A/Provider:  ├ AlgorithmParameters  	| PBEWithHmacSHA512AndAES_128
A/Provider:  ├ AlgorithmParameters  	| PBEWithHmacSHA1AndAES_256
A/Provider:  ├ AlgorithmParameters  	| PBEWithHmacSHA224AndAES_256
A/Provider:  ├ AlgorithmParameters  	| PBEWithHmacSHA256AndAES_256
A/Provider:  ├ AlgorithmParameters  	| PBEWithHmacSHA384AndAES_256
A/Provider:  ├ AlgorithmParameters  	| PBEWithHmacSHA512AndAES_256
A/Provider:  ├ AlgorithmParameters  	| AES
A/Provider:  ├ AlgorithmParameters  	| GCM
A/Provider:  ├ Cipher  	| AES
A/Provider:  ├ Cipher  	| AESWRAP
A/Provider:  ├ Cipher  	| AES/GCM/NOPADDING
A/Provider:  ├ KeyGenerator  	| AES
A/Provider:  ├ Cipher  	| PBEWITHSHAAND128BITAES-CBC-BC
A/Provider:  ├ Cipher  	| PBEWITHSHAAND192BITAES-CBC-BC
A/Provider:  ├ Cipher  	| PBEWITHSHAAND256BITAES-CBC-BC
A/Provider:  ├ Cipher  	| PBEWITHSHA256AND128BITAES-CBC-BC
A/Provider:  ├ Cipher  	| PBEWITHSHA256AND192BITAES-CBC-BC
A/Provider:  ├ Cipher  	| PBEWITHSHA256AND256BITAES-CBC-BC
A/Provider:  ├ Cipher  	| PBEWITHMD5AND128BITAES-CBC-OPENSSL
A/Provider:  ├ Cipher  	| PBEWITHMD5AND192BITAES-CBC-OPENSSL
A/Provider:  ├ Cipher  	| PBEWITHMD5AND256BITAES-CBC-OPENSSL
A/Provider:  ├ SecretKeyFactory  	| PBEWITHMD5AND128BITAES-CBC-OPENSSL
A/Provider:  ├ SecretKeyFactory  	| PBEWITHMD5AND192BITAES-CBC-OPENSSL
A/Provider:  ├ SecretKeyFactory  	| PBEWITHMD5AND256BITAES-CBC-OPENSSL
A/Provider:  ├ SecretKeyFactory  	| PBEWITHSHAAND128BITAES-CBC-BC
A/Provider:  ├ SecretKeyFactory  	| PBEWITHSHAAND192BITAES-CBC-BC
A/Provider:  ├ SecretKeyFactory  	| PBEWITHSHAAND256BITAES-CBC-BC
A/Provider:  ├ SecretKeyFactory  	| PBEWITHSHA256AND128BITAES-CBC-BC
A/Provider:  ├ SecretKeyFactory  	| PBEWITHSHA256AND192BITAES-CBC-BC
A/Provider:  ├ SecretKeyFactory  	| PBEWITHSHA256AND256BITAES-CBC-BC
A/Provider:  ├ Cipher  	| ARC4
A/Provider:  ├ KeyGenerator  	| ARC4
A/Provider:  ├ SecretKeyFactory  	| PBEWITHSHAAND128BITRC4
A/Provider:  ├ SecretKeyFactory  	| PBEWITHSHAAND40BITRC4
A/Provider:  ├ Cipher  	| PBEWITHSHAAND128BITRC4
A/Provider:  ├ Cipher  	| PBEWITHSHAAND40BITRC4
A/Provider:  ├ Cipher  	| BLOWFISH
A/Provider:  ├ KeyGenerator  	| BLOWFISH
A/Provider:  ├ AlgorithmParameters  	| BLOWFISH
A/Provider:  ├ Cipher  	| DES
A/Provider:  ├ KeyGenerator  	| DES
A/Provider:  ├ SecretKeyFactory  	| DES
A/Provider:  ├ AlgorithmParameters  	| DES
A/Provider:  ├ Cipher  	| PBEWITHMD5ANDDES
A/Provider:  ├ Cipher  	| PBEWITHSHA1ANDDES
A/Provider:  ├ SecretKeyFactory  	| PBEWITHMD5ANDDES
A/Provider:  ├ SecretKeyFactory  	| PBEWITHSHA1ANDDES
A/Provider:  ├ Cipher  	| DESEDE
A/Provider:  ├ Cipher  	| DESEDEWRAP
A/Provider:  ├ KeyGenerator  	| DESEDE
A/Provider:  ├ AlgorithmParameters  	| DESEDE
A/Provider:  ├ SecretKeyFactory  	| DESEDE
A/Provider:  ├ Cipher  	| PBEWITHSHAAND3-KEYTRIPLEDES-CBC
A/Provider:  ├ Cipher  	| PBEWITHSHAAND2-KEYTRIPLEDES-CBC
A/Provider:  ├ SecretKeyFactory  	| PBEWITHSHAAND3-KEYTRIPLEDES-CBC
A/Provider:  ├ SecretKeyFactory  	| PBEWITHSHAAND2-KEYTRIPLEDES-CBC
A/Provider:  ├ SecretKeyFactory  	| PBEWITHMD5ANDRC2
A/Provider:  ├ SecretKeyFactory  	| PBEWITHSHA1ANDRC2
A/Provider:  ├ SecretKeyFactory  	| PBEWITHSHAAND128BITRC2-CBC
A/Provider:  ├ SecretKeyFactory  	| PBEWITHSHAAND40BITRC2-CBC
A/Provider:  ├ Cipher  	| PBEWITHMD5ANDRC2
A/Provider:  ├ Cipher  	| PBEWITHSHA1ANDRC2
A/Provider:  ├ Cipher  	| PBEWITHSHAAND128BITRC2-CBC
A/Provider:  ├ Cipher  	| PBEWITHSHAAND40BITRC2-CBC
A/Provider:  ├ Cipher  	| PBEWITHSHAANDTWOFISH-CBC
A/Provider:  ├ SecretKeyFactory  	| PBEWITHSHAANDTWOFISH-CBC
A/Provider:  ├ CertificateFactory  	| X.509
A/Provider:  ├ AlgorithmParameters  	| DSA
A/Provider:  ├ AlgorithmParameterGenerator  	| DSA
A/Provider:  ├ KeyPairGenerator  	| DSA
A/Provider:  ├ KeyFactory  	| DSA
A/Provider:  ├ Signature  	| SHA1withDSA
A/Provider:  ├ Signature  	| NONEWITHDSA
A/Provider:  ├ Signature  	| SHA224WITHDSA
A/Provider:  ├ Signature  	| SHA256WITHDSA
A/Provider:  ├ KeyPairGenerator  	| DH
A/Provider:  ├ KeyAgreement  	| DH
A/Provider:  ├ KeyFactory  	| DH
A/Provider:  ├ AlgorithmParameters  	| DH
A/Provider:  ├ AlgorithmParameterGenerator  	| DH
A/Provider:  ├ AlgorithmParameters  	| EC
A/Provider:  ├ KeyAgreement  	| ECDH
A/Provider:  ├ KeyFactory  	| EC
A/Provider:  ├ KeyPairGenerator  	| EC
A/Provider:  ├ Signature  	| SHA1withECDSA
A/Provider:  ├ Signature  	| NONEwithECDSA
A/Provider:  ├ Signature  	| SHA224WITHECDSA
A/Provider:  ├ Signature  	| SHA256WITHECDSA
A/Provider:  ├ Signature  	| SHA384WITHECDSA
A/Provider:  ├ Signature  	| SHA512WITHECDSA
A/Provider:  ├ AlgorithmParameters  	| OAEP
A/Provider:  ├ AlgorithmParameters  	| PSS
A/Provider:  ├ Cipher  	| RSA
A/Provider:  ├ KeyFactory  	| RSA
A/Provider:  ├ KeyPairGenerator  	| RSA
A/Provider:  ├ Signature  	| MD5WITHRSA
A/Provider:  ├ Signature  	| SHA1WITHRSA
A/Provider:  ├ Signature  	| SHA224WITHRSA
A/Provider:  ├ Signature  	| SHA256WITHRSA
A/Provider:  ├ Signature  	| SHA384WITHRSA
A/Provider:  ├ Signature  	| SHA512WITHRSA
A/Provider:  ├ KeyStore  	| BKS
A/Provider:  ├ KeyStore  	| BouncyCastle
A/Provider:  ├ KeyStore  	| PKCS12
A/Provider:  ├ CertPathValidator  	| PKIX
A/Provider:  ├ CertPathBuilder  	| PKIX
A/Provider:  └ CertStore  	| Collection
   */
  /*
A/Provider: === HarmonyJSSE | Harmony JSSE Provider ===
A/Provider:  ├ KeyManagerFactory  	| PKIX
A/Provider:  ├ TrustManagerFactory  	| PKIX
A/Provider:  └ KeyStore  	| AndroidCAStore
   */
  /*
A/Provider: === AndroidKeyStore | Android KeyStore security provider ===
A/Provider:  ├ KeyStore  	| AndroidKeyStore
A/Provider:  ├ KeyPairGenerator  	| EC
A/Provider:  ├ KeyPairGenerator  	| RSA
A/Provider:  ├ KeyFactory  	| EC
A/Provider:  ├ KeyFactory  	| RSA
A/Provider:  ├ KeyGenerator  	| AES
A/Provider:  ├ KeyGenerator  	| HmacSHA1
A/Provider:  ├ KeyGenerator  	| HmacSHA224
A/Provider:  ├ KeyGenerator  	| HmacSHA256
A/Provider:  ├ KeyGenerator  	| HmacSHA384
A/Provider:  ├ KeyGenerator  	| HmacSHA512
A/Provider:  ├ SecretKeyFactory  	| AES
A/Provider:  ├ SecretKeyFactory  	| HmacSHA1
A/Provider:  ├ SecretKeyFactory  	| HmacSHA224
A/Provider:  ├ SecretKeyFactory  	| HmacSHA256
A/Provider:  ├ SecretKeyFactory  	| HmacSHA384
A/Provider:  └ SecretKeyFactory  	| HmacSHA512
   */
}