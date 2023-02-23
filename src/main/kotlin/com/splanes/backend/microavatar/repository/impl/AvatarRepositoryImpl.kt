package com.splanes.backend.microavatar.repository.impl

import com.splanes.backend.microavatar.entity.HashAvatarNameEntity
import com.splanes.backend.microavatar.model.Avatar
import com.splanes.backend.microavatar.repository.AvatarRepository
import com.splanes.backend.microavatar.repository.db.AvatarDatabaseRepository
import com.splanes.backend.microavatar.repository.db.AvatarFirebaseRepository
import com.splanes.backend.microavatar.utils.base64
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class AvatarRepositoryImpl(
    @Autowired val firebaseRepository: AvatarFirebaseRepository,
    @Autowired val dbRepository: AvatarDatabaseRepository
) : AvatarRepository {

    override suspend fun singleRandomDefaultName(): String =
        firebaseRepository.findDefaultNames().random()

    override suspend fun allDefaults(): List<Avatar> {
        val defaults = firebaseRepository.findDefaultNames()
        return defaults.map { name ->
            Avatar(
                name = name,
                content = firebaseRepository.download(name).base64(),
                isDefault = true
            )
        }
    }

    override suspend fun findByHash(hash: String): Avatar? {
        val result = dbRepository.findByHash(hash).awaitSingleOrNull()
        return if (result != null) {
            Avatar(
                name = result.name,
                content = if (result.isDefault == 1) {
                    firebaseRepository.download(result.name)
                } else {
                    firebaseRepository.download(hash, result.name)
                }.base64(),
                isDefault = result.isDefault == 1
            )
        } else {
            null
        }
    }

    override suspend fun save(hash: String, name: String): Avatar {
        val defaults = firebaseRepository.findDefaultNames()
        val isDefault = defaults.contains(name)
        val entity = HashAvatarNameEntity(
            name = name,
            hash = hash,
            isDefault = if (isDefault) 1 else 0
        )
        dbRepository.remove(hash).awaitSingleOrNull()
        dbRepository.insert(
            entity.hash,
            entity.name,
            entity.isDefault
        ).awaitSingleOrNull()

        return Avatar(
            name = name,
            content = if (isDefault) {
                firebaseRepository.download(name)
            } else {
                firebaseRepository.download(hash, name)
            }.base64(),
            isDefault = isDefault
        )
    }

    override suspend fun upload(hash: String, name: String, content: ByteArray): Avatar {
        firebaseRepository.upload(hash, name, content)
        return save(hash, name)
    }
}