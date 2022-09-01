package com.luc.api.infrastructure.audit;

import com.luc.api.Application;
import lombok.Data;
import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;

import javax.persistence.*;
import java.io.Serializable;

import static com.luc.api.infrastructure.audit.FiservRevision.TABLE_NAME;


/**
 * @param <T>
 * @param <ID>
 */
@Data
@Entity
@lombok.EqualsAndHashCode
//@JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
@Table(name = Application.PREFIX /*TODO ACOPLAMENTO*/ + TABLE_NAME)
@org.hibernate.envers.RevisionEntity(EntityTrackingRevisionListener.class)
public class FiservRevision<T extends IEntity<ID>, ID extends Serializable> implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 4193623660483050410L;

    /**
     *
     */
    public static final String TABLE_NAME = "REVISION";

    /**
     * id da {@link FiservRevision}
     */
    @Id
    @RevisionNumber
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    /**
     * data da {@link FiservRevision}
     */
    @RevisionTimestamp
    private long timestamp;

    /**
     * Username of the logged user {@link FiservRevision}
     */
    private String username;


}
