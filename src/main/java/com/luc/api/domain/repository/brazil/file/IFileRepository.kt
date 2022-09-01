package com.luc.api.domain.repository.brazil.file

import java.io.File
import java.io.InputStream

interface IDeleteRepository {

    fun delete(key: String)

    fun delete(path: String, key: String)

}

interface IDownloadRepository {

    fun download(key: String): ByteArray

    fun download(path: String, key: String): ByteArray
}

interface IUploadRepository {

    fun upload(content: String): UploadResponse

    fun upload(key: String, content: String): UploadResponse

    fun upload(path: String, key: String, content: String): UploadResponse

    fun upload(file: File): UploadResponse

    fun upload(key: String, file: File): UploadResponse

    fun upload(path: String, key: String, file: File): UploadResponse

    fun upload(bytes: ByteArray): UploadResponse

    fun upload(key: String, bytes: ByteArray): UploadResponse

    fun upload(path: String, key: String, bytes: ByteArray): UploadResponse

    fun upload(inputStream: InputStream): UploadResponse

    fun upload(key: String, inputStream: InputStream): UploadResponse

    fun upload(path: String, key: String, inputStream: InputStream): UploadResponse

}

class UploadResponse(var path: String, var key: String)

interface IFileRepository : IUploadRepository, IDownloadRepository, IDeleteRepository


class DownloadException(override val message: String?) : RuntimeException()
