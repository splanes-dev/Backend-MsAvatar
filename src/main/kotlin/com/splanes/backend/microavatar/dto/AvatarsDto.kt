package com.splanes.mstest.web.dto

data class AvatarsDto(
    val hash: String?,
    val default: List<AvatarDto>,
    val usr: AvatarDto?
)
