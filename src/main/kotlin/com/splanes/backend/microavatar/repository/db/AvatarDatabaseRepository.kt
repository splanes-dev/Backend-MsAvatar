package com.splanes.backend.microavatar.repository.db

import com.splanes.backend.microavatar.entity.HashAvatarNameEntity
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
interface AvatarDatabaseRepository : ReactiveCrudRepository<HashAvatarNameEntity, Long> {

    @Query("INSERT INTO tbl_hash_name(hash, name, is_default) VALUES(:hash, :name, :isDefault)")
    fun insert(hash: String, name: String, isDefault: Int): Mono<HashAvatarNameEntity>

    @Query("SELECT * FROM tbl_hash_name WHERE hash LIKE :hash")
    fun findByHash(hash: String): Mono<HashAvatarNameEntity>

    @Query("DELETE FROM tbl_hash_name WHERE hash LIKE :hash")
    fun remove(hash: String): Mono<Unit>
}