package com.splanes.backend.microavatar.repository

import com.splanes.backend.microavatar.model.Avatar

interface AvatarRepository {
    suspend fun singleRandomDefaultName(): String
    suspend fun allDefaults(): List<Avatar>
    suspend fun findByHash(hash: String): Avatar?
    suspend fun save(hash: String, name: String): Avatar
    suspend fun upload(hash: String, name: String, content: ByteArray): Avatar
}