@file:Suppress("unused", "SpellCheckingInspection",
        "EXPERIMENTAL_API_USAGE", "EXPERIMENTAL_UNSIGNED_LITERALS")

package io.jerryc05.e2ee_me.core.crypto

import kotlin.math.ceil
import kotlin.math.floor

/*private const val VALID_CHARS =
 *        "()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
 *        "[\\]^_`abcdefghijklmnopqrstuvwxyz{|"
 */

private const val OFFSET: UByte = 40u // VALID_CHARS[0].toInt()

internal fun encodeB85(src: ByteArray): CharArray {
  val sourceSize = src.size
  val result = CharArray(ceil(sourceSize / 4F * 5).toInt())

  var sourceIndex = 0
  var resultIndex = 0
  while (sourceIndex + 4 <= sourceSize) {
    var temp = 0u
    repeat(4) {
      temp = (temp * (UByte.MAX_VALUE + 1u)) or
              src[sourceIndex++].mapToUByte().toUInt()
    }

    var tempIndex = resultIndex + 4
    repeat(5) {
      result[tempIndex--] = ((temp % 85u) + OFFSET).toByte().toChar()
      temp /= 85u
    }
    resultIndex += 5
  }

  if (sourceIndex < sourceSize) {
    var temp = 0u
    val sourceIndex2 = sourceIndex
    while (sourceIndex < sourceSize) {
      temp = (temp shl 8) or src[sourceIndex++].mapToUByte().toUInt()
    }
    temp = temp shl (when (sourceSize - sourceIndex2) {
      1 -> 4
      2 -> 2
      3 -> 1
      else -> throw Exception("sourceSie - sourceIndex2 != 1 or 2 or 3")
    })
    when (sourceSize - sourceIndex2) {
      1 -> {
        result[resultIndex + 1] = ((temp % 85u) + OFFSET).toByte().toChar()
        temp /= 85u
        result[resultIndex + 0] = ((temp % 85u) + OFFSET).toByte().toChar()
      }
      2 -> {
        result[resultIndex + 2] = ((temp % 85u) + OFFSET).toByte().toChar()
        temp /= 85u
        result[resultIndex + 1] = ((temp % 85u) + OFFSET).toByte().toChar()
        temp /= 85u
        result[resultIndex + 0] = ((temp % 85u) + OFFSET).toByte().toChar()
      }
      3 -> {
        result[resultIndex + 3] = ((temp % 85u) + OFFSET).toByte().toChar()
        temp /= 85u
        result[resultIndex + 2] = ((temp % 85u) + OFFSET).toByte().toChar()
        temp /= 85u
        result[resultIndex + 1] = ((temp % 85u) + OFFSET).toByte().toChar()
        temp /= 85u
        result[resultIndex + 0] = ((temp % 85u) + OFFSET).toByte().toChar()
      }
      else -> throw Exception("sourceSie - sourceIndex2 != 1 or 2 or 3")
    }
  }

  return result
}

internal fun decodeB85(src: CharArray): ByteArray {
  val sourceSize = src.size
  val result = ByteArray(floor(sourceSize / 5F * 4).toInt())

  var sourceIndex = 0
  var resultIndex = 0
  while (sourceIndex + 5 <= sourceSize) {
    var temp = 0u
    repeat(5) {
      temp = temp * 85u + (src[sourceIndex++].toInt().toUInt() - OFFSET)
    }

    var tempIndex = resultIndex + 3
    repeat(4) {
      result[tempIndex--] = (temp % (UByte.MAX_VALUE + 1u))
              .toUByte().mapToByte()
      temp /= (UByte.MAX_VALUE + 1u)
    }
    resultIndex += 4
  }

  if (sourceIndex < sourceSize) {
    var temp = 0u
    val sourceIndex2 = sourceIndex
    while (sourceIndex < sourceSize) {
      temp = temp * 85u + (src[sourceIndex++].toInt().toUByte() - OFFSET)
    }
    temp = temp shr (when (sourceSize - sourceIndex2) {
      2 -> 4
      3 -> 2
      4 -> 1
      else -> throw Exception("sourceSie - sourceIndex2 != 2 or 3 or 4")
    })
    when (sourceSize - sourceIndex2) {
      2 -> {
        result[resultIndex + 0] = (temp % (UByte.MAX_VALUE + 1u)).toUByte().mapToByte()
      }
      3 -> {
        result[resultIndex + 1] = (temp % (UByte.MAX_VALUE + 1u)).toUByte().mapToByte()
        temp /= (UByte.MAX_VALUE + 1u)
        result[resultIndex + 0] = (temp % (UByte.MAX_VALUE + 1u)).toUByte().mapToByte()
      }
      4 -> {
        result[resultIndex + 2] = (temp % (UByte.MAX_VALUE + 1u)).toUByte().mapToByte()
        temp /= (UByte.MAX_VALUE + 1u)
        result[resultIndex + 1] = (temp % (UByte.MAX_VALUE + 1u)).toUByte().mapToByte()
        temp /= (UByte.MAX_VALUE + 1u)
        result[resultIndex + 0] = (temp % (UByte.MAX_VALUE + 1u)).toUByte().mapToByte()
      }
      else -> throw Exception("sourceSie - sourceIndex2 != 2 or 3 or 4")
    }
  }

  return result
}

private const val startMark1 = '#'
private const val startMark2 = '$'
private const val endMark = startMark1

internal fun wrapB85Array(chars: CharArray): CharArray {
  val result = CharArray(chars.size + 3)
  result[0] = startMark1
  result[1] = startMark2
  result[result.size - 1] = endMark
  chars.copyInto(result, 2)
  return result
}

internal fun unwrapB85Array(chars: CharArray): CharArray {
  val start = chars.indexOf(startMark1) + 2
  if (start < 0 || chars[start - 2] != startMark1 || chars[start - 1] != startMark2)
    throw Exception("Invalid start mark!")

  val end = chars.sliceArray(start until chars.size)
          .indexOf(endMark) - 1
  if (end < 0)
    throw Exception("Invalid end mark!")
  return chars.sliceArray(start..end)
}

private fun Byte.mapToUByte(): UByte {
  return (this - Byte.MIN_VALUE).toUByte()
}

private fun UByte.mapToByte(): Byte {
  return (this.toInt() + Byte.MIN_VALUE).toByte()
}