package com.luc.api.domain.entity.brazil;

import com.luc.api.Application;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.envers.Audited;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

import static com.luc.api.domain.entity.brazil.BRJuridicPerson.TABLE_NAME;

@Data
@Entity
@Audited(withModifiedFlag = true)
@EqualsAndHashCode(callSuper = true)
@Table(name = Application.PREFIX + TABLE_NAME)
public class BRJuridicPerson extends BRPerson implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -54858544349107167L;

    /**
     *
     */
    public static final String TABLE_NAME = "BR_JURIDIC_PERSON";

}
