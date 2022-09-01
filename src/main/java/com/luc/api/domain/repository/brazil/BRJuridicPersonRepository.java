package com.luc.api.domain.repository.brazil;

import com.luc.api.domain.entity.brazil.BRJuridicPerson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BRJuridicPersonRepository extends JpaRepository<BRJuridicPerson, Long>, RevisionRepository<BRJuridicPerson, Long, Long> {
}
