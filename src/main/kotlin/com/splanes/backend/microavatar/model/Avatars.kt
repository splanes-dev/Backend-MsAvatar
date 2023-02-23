package com.splanes.backend.microavatar.model

data class Avatars(
    val hash: String?,
    val default: List<Avatar>,
    val usr: Avatar?
)
