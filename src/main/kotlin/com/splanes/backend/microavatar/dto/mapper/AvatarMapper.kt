package com.splanes.mstest.web.dto.mapper

import com.splanes.mstest.domain.model.Avatar
import com.splanes.mstest.domain.model.Avatars
import com.splanes.mstest.web.dto.AvatarDto
import com.splanes.mstest.web.dto.AvatarsDto
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