package com.splanes.backend.microavatar.dto.mapper

import com.splanes.backend.microavatar.dto.AvatarDto
import com.splanes.backend.microavatar.dto.AvatarsDto
import com.splanes.backend.microavatar.model.Avatar
import com.splanes.backend.microavatar.model.Avatars
import org.springframework.stereotype.Component

@Component
class AvatarMapper {

    fun map(dto: Avatars): AvatarsDto =
        AvatarsDto(
            hash = dto.hash,
            default = dto.default.map(::map),
            usr = dto.usr?.let(::map)
        )

    fun map(avatar: Avatar): AvatarDto =
        AvatarDto(
            name = avatar.name,
            content = avatar.content,
            isDefault = avatar.isDefault
        )
}