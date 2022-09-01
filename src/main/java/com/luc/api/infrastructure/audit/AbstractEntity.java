package com.luc.api.infrastructure.audit;

import lombok.Data;
import org.hibernate.envers.Audited;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 *
 */
@Data
@MappedSuperclass
@Audited(withModifiedFlag = true)
public abstract class AbstractEntity implements IEntity<Long> {

    private static final long serialVersionUID = -3875946542616104733L;

    /**
     *
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    protected Long id;

    /**
     *
     */
    public AbstractEntity() {
    }

    /**
     * @param id Long
     */
    public AbstractEntity(final Long id) {
        this.setId(id);
    }
}
