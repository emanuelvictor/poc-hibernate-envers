package com.luc.api.domain.repository.brazil.file

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.io.ByteArrayInputStream
import java.io.File
import java.nio.charset.Charset
import java.nio.file.Files
import java.util.*

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class UploadFileTests(@Autowired private val fileRepository: IFileRepository) {

    private lateinit var key: String
    private lateinit var folder: String
    private lateinit var content: String

    /**
     * Is executed before all tests
     */
    @BeforeAll
    fun beforeAll() {
        key = UUID.randomUUID().toString()
        folder = UUID.randomUUID().toString()
        content = UUID.randomUUID().toString()
    }

    /**
     *
     */
    @Test
    fun `Upload String without key`() {
        val key = fileRepository.upload(content).key
        assertThat(key).isNotNull

        assertThat(fileRepository.download(key).toString(Charset.defaultCharset())).isEqualTo(content)
        fileRepository.delete(key)
    }

    /**
     *
     */
    @Test
    fun `Upload String with key`() {
        assertThat(fileRepository.upload(key, content.toByteArray(Charset.defaultCharset()))).isNotNull
        assertThat(fileRepository.download(key).toString(Charset.defaultCharset())).isEqualTo(content)
    }

    /**
     *
     */
    @Test
    fun `Upload String with key and content`() {
        assertThat(fileRepository.upload(folder, key, content)).isNotNull
        assertThat(fileRepository.download(folder, key).toString(Charset.defaultCharset())).isEqualTo(content)
    }

    /**
     *
     */
    @Test
    fun `Upload ByteArray without key`() {
        val key = fileRepository.upload(content.toByteArray(Charset.defaultCharset())).key
        assertThat(key).isNotNull

        assertThat(fileRepository.download(key).toString(Charset.defaultCharset())).isEqualTo(content)
        fileRepository.delete(key)
    }

    /**
     *
     */
    @Test
    fun `Upload ByteArray with key`() {
        fileRepository.upload(key, content.toByteArray(Charset.defaultCharset()))

        assertThat(fileRepository.download(key).toString(Charset.defaultCharset())).isEqualTo(content)
        fileRepository.delete(key)
    }

    /**
     *
     */
    @Test
    fun `Upload ByteArray with key and path`() {
        fileRepository.upload(folder, key, content.toByteArray(Charset.defaultCharset()))

        assertThat(fileRepository.download(folder, key).toString(Charset.defaultCharset())).isEqualTo(content)
        fileRepository.delete(folder, key)
    }

    /**
     *
     */
    @Test
    fun `Upload File without key`() {
        val file = File(content)
        Files.write(file.toPath(), content.toByteArray(Charset.defaultCharset()));
        val key = fileRepository.upload(file).key
        assertThat(key).isNotNull

        assertThat(fileRepository.download(key).toString(Charset.defaultCharset())).isEqualTo(content)
        fileRepository.delete(key)
        file.deleteOnExit()
    }

    /**
     *
     */
    @Test
    fun `Upload File with key`() {
        val file = File(content)
        Files.write(file.toPath(), content.toByteArray(Charset.defaultCharset()));
        fileRepository.upload(key, file)

        assertThat(fileRepository.download(key).toString(Charset.defaultCharset())).isEqualTo(content)
        file.deleteOnExit()
    }

    /**
     *
     */
    @Test
    fun `Upload File with key and path`() {
        val file = File(content)
        Files.write(file.toPath(), content.toByteArray(Charset.defaultCharset()));
        fileRepository.upload(folder, key, file)

        assertThat(fileRepository.download(key).toString(Charset.defaultCharset())).isEqualTo(content)
        file.deleteOnExit()
    }

    /**
     *
     */
    @Test
    fun `Upload InputStream without key`() {
        val key = fileRepository.upload(ByteArrayInputStream(content.toByteArray(Charset.defaultCharset()))).key
        assertThat(key).isNotNull

        assertThat(fileRepository.download(key).toString(Charset.defaultCharset())).isEqualTo(content)
        fileRepository.delete(key)
    }

    /**
     *
     */
    @Test
    fun `Upload InputStream with key`() {
        assertThat(fileRepository.upload(key, ByteArrayInputStream(content.toByteArray(Charset.defaultCharset())))).isNotNull
        assertThat(fileRepository.download(key).toString(Charset.defaultCharset())).isEqualTo(content)
    }

    /**
     *
     */
    @Test
    fun `Upload InputStream with key and path`() {
        assertThat(fileRepository.upload(folder, key, ByteArrayInputStream(content.toByteArray(Charset.defaultCharset())))).isNotNull
        assertThat(fileRepository.download(folder, key).toString(Charset.defaultCharset())).isEqualTo(content)
    }

    /**
     * Is executed after all tests
     */
    @AfterAll
    fun afterAll() {
        fileRepository.delete(key)
        fileRepository.delete(folder, key)
    }

}