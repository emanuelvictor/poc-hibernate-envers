package com.luc.api.infrastructure.audit.repository;

import java.util.List;

public interface IRevisionRepository<T> {

    List<Object> findRevisionsById(final Long id);

    List<Object> findRevisions();

}
