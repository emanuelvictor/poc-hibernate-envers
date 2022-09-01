package com.luc.api.domain.repository.brazil.file

import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.nio.charset.StandardCharsets
import java.util.*

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
open class DownloadAndDeleteFileTests(@Autowired val fileRepository: IFileRepository) {

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
        fileRepository.upload(key, content)
        fileRepository.upload(folder, key, content)
    }

    /**
     *
     */
    @Test
    fun `Download file uploaded to Amazon S3 Bucket`() {
        val byteArray: ByteArray = fileRepository.download(key)

        assertThat(byteArray).isNotNull
        assertThat(String(byteArray, StandardCharsets.UTF_8)).isEqualTo(content)
    }

    /**
     *
     */
    @Test
    fun `Download file that was uploaded in a folder`() {
        val byteArray: ByteArray = fileRepository.download(folder, key)

        assertThat(byteArray).isNotNull
        assertThat(String(byteArray, StandardCharsets.UTF_8)).isEqualTo(content)
    }

    @Nested
    @DisplayName("When uploaded file")
    inner class WhenUploadedFile {

        /**
         *
         */
        @Test
        fun `Deleted file`() {
            fileRepository.delete(key)
        }

        /**
         *
         */
        @Test
        fun `Deleted file from path`() {
            fileRepository.delete(folder, key)
        }

        @Nested
        @DisplayName("When deleted file")
        inner class WhenDeletedFile {

            /**
             *
             */
            @Test
            fun `Download deleted file`() {
                Assertions.assertThatThrownBy {
                    fileRepository.download(key)
                }.isInstanceOf(DownloadException::class.java)
            }

            /**
             *
             */
            @Test
            fun `Download deleted file that was uploaded in a folder`() {
                Assertions.assertThatThrownBy {
                    fileRepository.download(folder, key)
                }.isInstanceOf(DownloadException::class.java)
            }
        }

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