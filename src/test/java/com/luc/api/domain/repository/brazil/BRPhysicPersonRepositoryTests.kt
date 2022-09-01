package com.luc.api.domain.repository.brazil

import com.luc.api.domain.entity.brazil.BRPhysicPerson
import com.luc.api.infrastructure.audit.FiservRevision
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.history.Revision
import org.springframework.data.history.RevisionMetadata
import org.springframework.security.core.userdetails.User
import org.springframework.security.provisioning.UserDetailsManager
import org.springframework.security.test.context.support.WithUserDetails
import org.springframework.test.context.jdbc.Sql
import java.time.LocalDate

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class BRPhysicPersonRepositoryTests(
    @Autowired val userDetailsManager: UserDetailsManager,
    @Autowired val physicPersonRepository: BRPhysicPersonRepository
) {

    /**
     * Is executed before all tests
     */
    @BeforeAll
    fun beforeAll() {
        if (!userDetailsManager.userExists(USERNAME)) userDetailsManager.createUser(
            User.builder().username(USERNAME).password("password").authorities("ROLE").build()
        )
        if (!userDetailsManager.userExists(ANOTHER_USERNAME)) userDetailsManager.createUser(
            User.builder().username(ANOTHER_USERNAME).password("password").authorities("ROLE").build()
        )
    }

    /**
     *
     */
    @Test
    @Sql("/dataset/truncate-all-tables.sql")
    fun insertBRPhysicPersonMustPass() {
        val pep = false
        val fatherName = "Israel"
        val motherName = "Valdina"
        val name = "Emanuel"
        val birthDate = LocalDate.now().minusYears(32)
        val physicPerson = physicPersonRepository.save(BRPhysicPerson(pep, fatherName, motherName, name, birthDate))
        Assertions.assertThat(physicPerson).isNotNull
        Assertions.assertThat(physicPerson.id).isNotNull
        Assertions.assertThat(physicPerson.pep).isNotNull
        Assertions.assertThat(physicPerson.fatherName).isNotNull
        Assertions.assertThat(physicPerson.motherName).isNotNull
        Assertions.assertThat(physicPerson.name).isNotNull
        Assertions.assertThat(physicPerson.birthDate).isNotNull
        Assertions.assertThat(pep).isEqualTo(physicPerson.pep)
        Assertions.assertThat(fatherName).isEqualTo(physicPerson.fatherName)
        Assertions.assertThat(motherName).isEqualTo(physicPerson.motherName)
        Assertions.assertThat(name).isEqualTo(physicPerson.name)
        Assertions.assertThat(birthDate).isEqualTo(physicPerson.birthDate)
    }

    /**
     *
     */
    @Test
    @WithUserDetails(USERNAME)
    @Sql("/dataset/truncate-all-tables.sql")
    fun historyFromBRPhysicPersonMustPass() {
        val john = BRPhysicPerson()
        john.name = "John"
        john.fatherName = "Father name of John"
        john.motherName = "Mother name of John"
        john.pep = false
        john.birthDate = LocalDate.now().minusYears(32)

        // create
        val saved = physicPersonRepository.save(john)
        Assertions.assertThat(saved).isNotNull
        saved.name = "Jonny"

        // update
        val updated = physicPersonRepository.save(saved)
        Assertions.assertThat(updated).isNotNull

        // delete
        physicPersonRepository.delete(updated)

        //// REVISION ASSERTS FROM HERE ////
        val revisions = physicPersonRepository.findRevisions(updated.id)
        val revisionIterator: Iterator<Revision<Long, BRPhysicPerson>> = revisions.iterator()
        checkNextRevision(revisionIterator, "John", RevisionMetadata.RevisionType.INSERT, USERNAME)
        checkNextRevision(revisionIterator, "Jonny", RevisionMetadata.RevisionType.UPDATE, USERNAME)
        checkNextRevision(revisionIterator, null, RevisionMetadata.RevisionType.DELETE, USERNAME)
        Assertions.assertThat(revisionIterator.hasNext()).isFalse
    }

    /**
     *
     */
    @Test
    @WithUserDetails(ANOTHER_USERNAME)
    @Sql("/dataset/truncate-all-tables.sql")
    fun historyFromBRPhysicPersonMustPassWithAnotherUser() {
        val maria = BRPhysicPerson()
        maria.name = "Mary"
        maria.fatherName = "Father name of John"
        maria.motherName = "Mother name of John"
        maria.pep = false
        maria.birthDate = LocalDate.now().minusYears(32)

        // create
        val saved = physicPersonRepository.save(maria)
        Assertions.assertThat(saved).isNotNull
        saved.name = "Maria"

        // update
        val updated = physicPersonRepository.save(saved)
        Assertions.assertThat(updated).isNotNull
        updated.fatherName = "Father name of Maria"
        updated.motherName = "Mother name of Maria"
        val updatedDadsOfMaria = physicPersonRepository.save(updated)
        Assertions.assertThat(updatedDadsOfMaria).isNotNull

        //// REVISIONS ASSERTS FROM HERE ////
        val revisions = physicPersonRepository.findRevisions(updatedDadsOfMaria.id)
        val revisionIterator: Iterator<Revision<Long, BRPhysicPerson>> = revisions.iterator()
        checkNextRevision(revisionIterator, "Mary", RevisionMetadata.RevisionType.INSERT, ANOTHER_USERNAME)
        checkNextRevision(revisionIterator, "Maria", RevisionMetadata.RevisionType.UPDATE, ANOTHER_USERNAME)
        checkNextRevision(revisionIterator, "Maria", RevisionMetadata.RevisionType.UPDATE, ANOTHER_USERNAME)
        Assertions.assertThat(revisionIterator.hasNext()).isFalse
    }

    /**
     * @param revisionIterator the iterator to be tested.
     * @param name             the expected name of the Person referenced by the Revision.
     * @param revisionType     the type of the audit denoting if it represents an insert, update or delete.
     * @param username         User that make the action
     */
    private fun checkNextRevision(
        revisionIterator: Iterator<Revision<Long, BRPhysicPerson>>,
        name: String?,
        revisionType: RevisionMetadata.RevisionType,
        username: String
    ) {
        Assertions.assertThat(revisionIterator.hasNext()).isTrue
        val revision = revisionIterator.next()
        Assertions.assertThat(revision.entity.name).isEqualTo(name)
        Assertions.assertThat(revision.metadata.revisionType).isEqualTo(revisionType)
        Assertions.assertThat((revision.metadata.getDelegate<Any>() as FiservRevision<*, *>).username)
            .isEqualTo(username.toUpperCase())
    }

    companion object {
        /**
         *
         */
        private const val USERNAME = "f5wwb41"

        /**
         *
         */
        private const val ANOTHER_USERNAME = "f5wwb42"
    }
}