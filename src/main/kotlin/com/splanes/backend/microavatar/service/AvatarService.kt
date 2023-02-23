package com.splanes.backend.microavatar.service

import com.splanes.backend.microavatar.model.Avatar
import com.splanes.backend.microavatar.model.Avatars

interface AvatarService {
    suspend fun getAvatars(hash: String?): Avatars
    suspend fun getAvatar(hash: String, randomIfNull: Boolean): Avatar?
    suspend fun setAvatar(hash: String, name: String): Avatar
    suspend fun uploadAvatar(hash: String, name: String, content: ByteArray): Avatar
}