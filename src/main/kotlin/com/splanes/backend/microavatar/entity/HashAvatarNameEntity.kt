package com.splanes.backend.microavatar.entity

import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("tbl_hash_name")
data class HashAvatarNameEntity(
    @Column("hash") val hash: String,
    @Column("name") val name: String,
    @Column("is_default") val isDefault: Int
)
