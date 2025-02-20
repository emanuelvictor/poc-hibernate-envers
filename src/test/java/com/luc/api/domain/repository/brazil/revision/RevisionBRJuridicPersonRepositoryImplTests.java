package com.luc.api.domain.repository.brazil.revision;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RevisionBRJuridicPersonRepositoryImplTests {

    @Autowired
    RevisionBRJuridicPersonRepositoryImpl revisionBRJuridicPersonRepositoryImpl;

    @Test
    void verifyInstanceOfRevisionBRJuridicPersonRepository() {
        Assertions.assertThat(revisionBRJuridicPersonRepositoryImpl).isNotNull();
    }
}
