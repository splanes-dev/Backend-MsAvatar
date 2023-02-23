package com.splanes.backend.microavatar.service.impl

import com.splanes.backend.microavatar.model.Avatar
import com.splanes.backend.microavatar.model.Avatars
import com.splanes.backend.microavatar.repository.AvatarRepository
import com.splanes.backend.microavatar.service.AvatarService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class AvatarServiceImpl(
    @Autowired private val repository: AvatarRepository
) : AvatarService {

    override suspend fun getAvatars(hash: String?): Avatars {
        val default = repository.allDefaults()
        val usr = if (!hash.isNullOrBlank()) {
            repository.findByHash(hash)
        } else {
            null
        }
        return Avatars(
            hash = hash,
            default = default,
            usr = usr
        )
    }

    override suspend fun getAvatar(hash: String, randomIfNull: Boolean): Avatar? {
        val avatar = repository.findByHash(hash)
        return if (avatar == null && randomIfNull) {
            val name = repository.singleRandomDefaultName()
            setAvatar(hash, name)
        } else {
            avatar
        }
    }

    override suspend fun setAvatar(hash: String, name: String): Avatar =
        repository.save(hash, name)

    override suspend fun uploadAvatar(hash: String, name: String, content: ByteArray): Avatar =
        repository.upload(hash, name, content)
}