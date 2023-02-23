package com.splanes.backend.microavatar.utils

import java.util.*

fun ByteArray.base64() =
    Base64.getEncoder().encodeToString(this)