package com.trustwallet.core.app.utils

import com.google.protobuf.ByteString

fun ByteArray.toHex(): String {
    return Numeric.toHexString(this)
}

fun ByteArray.toHexNoPrefix(): String {
    return Numeric.toHexString(this, 0, this.size, false)
}

fun String.toHexBytes(): ByteArray {
    return Numeric.hexStringToByteArray(this)
}

fun String.toHexByteArray(): ByteArray {
    return Numeric.hexStringToByteArray(this)
}

fun String.toByteString(): ByteString {
    return ByteString.copyFrom(this, Charsets.UTF_8)
}

fun String.toHexBytesInByteString(): ByteString {
    return ByteString.copyFrom(this.toHexBytes())
}