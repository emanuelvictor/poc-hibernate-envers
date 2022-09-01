package com.luc.api.domain.repository.brazil.file.impl

import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.DeleteObjectsRequest
import com.luc.api.domain.repository.brazil.file.DownloadException
import com.luc.api.domain.repository.brazil.file.IFileRepository
import com.luc.api.domain.repository.brazil.file.UploadResponse
import org.apache.commons.io.IOUtils
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Repository
import java.io.ByteArrayInputStream
import java.io.File
import java.io.InputStream
import java.util.*


@Repository
open class IFileRepositoryImpl(private val amazonS3: AmazonS3) : IFileRepository {

    @Value("\${cloud.aws.s3.bucketName}")
    private val bucket = "my-bucket"

    private val EMPTY = ""

    override fun upload(content: String): UploadResponse {
        return upload(UUID.randomUUID().toString(), content)
    }

    override fun upload(key: String, content: String): UploadResponse {
        return upload(EMPTY, key, content)
    }

    override fun upload(path: String, key: String, content: String): UploadResponse {
        this.amazonS3.putObject(if (path.isNotEmpty()) "$bucket/$path" else bucket, key, content)
        return UploadResponse(if (path.isNotEmpty()) "$bucket/$path" else bucket, key)
    }

//   string ok

    override fun upload(bytes: ByteArray): UploadResponse {
        return upload(UUID.randomUUID().toString(), bytes)
    }

    override fun upload(key: String, bytes: ByteArray): UploadResponse {
        return upload(EMPTY, key, bytes)
    }

    override fun upload(path: String, key: String, bytes: ByteArray): UploadResponse {
        this.amazonS3.putObject(if (path.isNotEmpty()) "$bucket/$path" else bucket, key, ByteArrayInputStream(bytes), null)
        return UploadResponse(if (path.isNotEmpty()) "$bucket/$path" else bucket, key)
    }

    //   bytes ok

    override fun upload(file: File): UploadResponse {
        return upload(UUID.randomUUID().toString(), file)
    }

    override fun upload(key: String, file: File): UploadResponse {
        return upload(EMPTY, key, file)
    }

    override fun upload(path: String, key: String, file: File): UploadResponse {
        this.amazonS3.putObject(if (path.isNotEmpty()) "$bucket/$path" else bucket, key, file)
        return UploadResponse(if (path.isNotEmpty()) "$bucket/$path" else bucket, key)
    }

    override fun upload(inputStream: InputStream): UploadResponse {
        return upload(UUID.randomUUID().toString(), inputStream)
    }

    override fun upload(key: String, inputStream: InputStream): UploadResponse {
        return upload(EMPTY, key, inputStream)
    }

    override fun upload(path: String, key: String, inputStream: InputStream): UploadResponse {
        this.amazonS3.putObject(if (path.isNotEmpty()) "$bucket/$path" else bucket, key, inputStream, null)
        return UploadResponse(if (path.isNotEmpty()) "$bucket/$path" else bucket, key)
    }

    override fun download(key: String): ByteArray {
        return try {
            val `object` = amazonS3.getObject(bucket, key)
            val objectContent = `object`.objectContent
            IOUtils.toByteArray(objectContent)
        } catch (e: Exception) {
            throw DownloadException(e.message)
        }
    }

    override fun download(path: String, key: String): ByteArray {
        return try {
            val `object` = amazonS3.getObject("$bucket/$path", key)
            val objectContent = `object`.objectContent
            IOUtils.toByteArray(objectContent)
        } catch (e: Exception) {
            throw DownloadException(e.message)
        }
    }

    override fun delete(key: String) {
        amazonS3.deleteObject(bucket, key)
    }

    override fun delete(path: String, key: String) {
        amazonS3.deleteObject("$bucket/$path", key)
    }

}