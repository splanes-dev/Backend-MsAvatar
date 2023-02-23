package com.splanes.backend.microavatar.controller

import com.splanes.backend.microavatar.dto.AvatarDto
import com.splanes.backend.microavatar.dto.AvatarsDto
import com.splanes.backend.microavatar.dto.UploadAvatarDto
import com.splanes.backend.microavatar.dto.mapper.AvatarMapper
import com.splanes.backend.microavatar.service.AvatarService
import kotlinx.coroutines.reactor.mono
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/avatar")
class AvatarController(
    @Autowired private val service: AvatarService,
    @Autowired private val mapper: AvatarMapper
) {

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    fun getAvatars(
        @RequestParam(name = "hash", required = false, defaultValue = "") hash: String
    ): Mono<ResponseEntity<AvatarsDto>> = mono {
        val avatars = service.getAvatars(hash.takeUnless { it.isBlank() })
        ResponseEntity.ok(avatars.let(mapper::map))
    }

    @GetMapping("/{hash}")
    @ResponseStatus(HttpStatus.OK)
    fun getAvatar(
        @PathVariable("hash") hash: String,
        @RequestParam(name = "random", required = false, defaultValue = "false") randomIfNull: String
    ): Mono<ResponseEntity<AvatarDto?>> = mono {
        val avatars = service.getAvatar(
            hash = hash,
            randomIfNull = randomIfNull.toBooleanStrictOrNull() ?: false
        )?.let(mapper::map)
        ResponseEntity.ok(avatars)
    }

    @PostMapping("/{hash}")
    @ResponseStatus(HttpStatus.OK)
    fun setAvatar(
        @PathVariable("hash") hash: String,
        @RequestBody(required = true) name: String
    ): Mono<ResponseEntity<AvatarDto>> = mono {
        val avatar = service.setAvatar(
            hash = hash,
            name = name
        ).let(mapper::map)
        ResponseEntity.ok(avatar)
    }

    @PostMapping("/{hash}/upload")
    @ResponseStatus(HttpStatus.OK)
    fun uploadAvatar(
        @PathVariable("hash") hash: String,
        @RequestBody(required = true) request: UploadAvatarDto
    ): Mono<ResponseEntity<AvatarDto>> = mono {
        val avatar = service.uploadAvatar(
            hash = hash,
            name = request.name,
            content = request.content.toByteArray()
        ).let(mapper::map)
        ResponseEntity.ok(avatar)
    }
}