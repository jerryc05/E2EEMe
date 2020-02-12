package io.jerryc05.e2ee_me.core.crypto

import kotlin.math.ceil
import kotlin.math.floor

//private const val VALID_CHARS =
//        "()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
//        "[\\]^_`abcdefghijklmnopqrstuvwxyz{|"

private const val OFFSET = 40 // VALID_CHARS[0].toInt()

@ExperimentalUnsignedTypes
fun encode(source: ByteArray): CharArray {
  val sourceSize = source.size
  val result = CharArray(ceil(sourceSize.toFloat() / 4 * 5).toInt())

  var sourceIndex = 0
  var resultIndex = 0
  while (sourceIndex + 4 <= sourceSize) {
    var temp = (source[sourceIndex].toUInt() shl 24) or
            (source[sourceIndex + 1].toUInt() shl 16) or
            (source[sourceIndex + 2].toUInt() shl 8) or
            (source[sourceIndex + 3].toUInt())
    sourceIndex += 4

    result[resultIndex + 4] = ((temp % 85u).toInt() + OFFSET).toChar()
    temp /= 85u
    result[resultIndex + 3] = ((temp % 85u).toInt() + OFFSET).toChar()
    temp /= 85u
    result[resultIndex + 2] = ((temp % 85u).toInt() + OFFSET).toChar()
    temp /= 85u
    result[resultIndex + 1] = ((temp % 85u).toInt() + OFFSET).toChar()
    temp /= 85u
    result[resultIndex] = ((temp % 85u).toInt() + OFFSET).toChar()
    resultIndex += 5
  }

  if (sourceIndex  < sourceSize) {
    var temp = 0u
    val sourceIndex2 = sourceIndex
    while (sourceIndex < sourceSize) {
      temp = (temp shl 8) or source[sourceIndex++].toUInt()
    }
    println("e $temp")
    temp = temp shl (when (sourceSize - sourceIndex2) {
      1 -> 5
      2 -> 3
      3 -> 2
      else -> throw Exception("sourceSie - sourceIndex2 != 1 or 2 or 3")
    })
    when (sourceSize - sourceIndex2) {
      1 -> {
        result[resultIndex + 1] = ((temp % 85u).toInt() + OFFSET).toChar()
        temp /= 85u
        result[resultIndex] = ((temp % 85u).toInt() + OFFSET).toChar()
      }
      2 -> {
        result[resultIndex + 2] = ((temp % 85u).toInt() + OFFSET).toChar()
        temp /= 85u
        result[resultIndex + 1] = ((temp % 85u).toInt() + OFFSET).toChar()
        temp /= 85u
        result[resultIndex] = ((temp % 85u).toInt() + OFFSET).toChar()
      }
      3 -> {
        result[resultIndex + 3] = ((temp % 85u).toInt() + OFFSET).toChar()
        temp /= 85u
        result[resultIndex + 2] = ((temp % 85u).toInt() + OFFSET).toChar()
        temp /= 85u
        result[resultIndex + 1] = ((temp % 85u).toInt() + OFFSET).toChar()
        temp /= 85u
        result[resultIndex] = ((temp % 85u).toInt() + OFFSET).toChar()
      }
      else -> throw Exception("sourceSie - sourceIndex2 != 1 or 2 or 3")
    }
  }

  return result
}

@ExperimentalUnsignedTypes
fun decode(source: CharArray): ByteArray {
  val sourceSize = source.size
  val result = ByteArray(floor(sourceSize.toFloat() / 5 * 4).toInt())

  var sourceIndex = 0
  var resultIndex = 0
  while (sourceIndex + 5 <= sourceSize) {
    var temp = (source[sourceIndex++].toInt() - OFFSET).toUInt() * 85u * 85u * 85u * 85u
    temp += (source[sourceIndex++].toInt() - OFFSET).toUInt() * 85u * 85u * 85u
    temp += (source[sourceIndex++].toInt() - OFFSET).toUInt() * 85u * 85u
    temp += (source[sourceIndex++].toInt() - OFFSET).toUInt() * 85u
    temp += (source[sourceIndex++].toInt() - OFFSET).toUInt()

    result[resultIndex + 3] = (temp % 256u).toByte()
    temp /= 256u
    result[resultIndex + 2] = (temp % 256u).toByte()
    temp /= 256u
    result[resultIndex + 1] = (temp % 256u).toByte()
    temp /= 256u
    result[resultIndex] = (temp % 256u).toByte()
    resultIndex += 4
  }


  if (sourceIndex < sourceSize) {
    var temp = 0u
    val sourceIndex2 = sourceIndex
    while (sourceIndex < sourceSize) {
      temp *= 85u
      temp += (source[sourceIndex++].toInt() - OFFSET).toUInt()
    }
    temp = temp shr (when (sourceSize - sourceIndex2) {
      2 -> 5
      3 -> 3
      4 -> 2
      else -> throw Exception("sourceSie - sourceIndex2 != 2 or 3 or 4")
    })
    println("d $temp")
    when (sourceSize - sourceIndex2) {
      2 -> {
        result[resultIndex] = (temp % 256u).toByte()
      }
      3 -> {
        result[resultIndex + 1] = (temp % 256u).toByte()
        temp /= 256u
        result[resultIndex] = (temp % 256u).toByte()
      }
      4 -> {
        result[resultIndex + 2] = (temp % 256u).toByte()
        temp /= 256u
        result[resultIndex + 1] = (temp % 256u).toByte()
        temp /= 256u
        result[resultIndex] = (temp % 256u).toByte()
      }
      else -> throw Exception("sourceSie - sourceIndex2 != 2 or 3 or 4")
    }
  }

  return result
}