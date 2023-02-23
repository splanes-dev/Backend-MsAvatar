package com.splanes.backend.microavatar.repository.db

import com.google.cloud.storage.Storage
import com.google.firebase.cloud.StorageClient
import org.springframework.stereotype.Component
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@Component
class AvatarFirebaseRepository {
    private val bucket by lazy { StorageClient.getInstance().bucket() }

    suspend fun findDefaultNames(): List<String> = suspendCoroutine { continuation ->
        val names = bucket.list(Storage.BlobListOption.prefix(BucketDefault)).values.map { blob -> blob.name }
        continuation.resume(names)
    }

    suspend fun download(name: String): ByteArray = suspendCoroutine { continuation ->
        val content = bucket.get(name).getContent()
        continuation.resume(content)
    }

    suspend fun download(hash: String, name: String): ByteArray = suspendCoroutine { continuation ->
        val content = bucket.get("$hash/$name").getContent()
        continuation.resume(content)
    }

    suspend fun upload(hash: String, name: String, content: ByteArray) = suspendCoroutine { continuation ->
        bucket.list(Storage.BlobListOption.prefix(hash)).values.forEach { blob -> blob.delete() }
        bucket.create("$hash/$name", content)
        continuation.resume(Unit)
    }
}

private const val BucketDefault = "default/"