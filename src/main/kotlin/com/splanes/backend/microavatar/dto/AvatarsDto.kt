package com.splanes.backend.microavatar.dto

data class AvatarsDto(
    val hash: String?,
    val default: List<AvatarDto>,
    val usr: AvatarDto?
)
