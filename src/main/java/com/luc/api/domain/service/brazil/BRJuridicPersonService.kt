package com.luc.api.domain.service.brazil

import com.luc.api.domain.entity.brazil.BRJuridicPerson
import com.luc.api.domain.repository.brazil.BRJuridicPersonRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.*

@Service
class BRJuridicPersonService(private val juridicPersonRepository: BRJuridicPersonRepository) {

    /**
     * @param filters  String
     * @param pageable Pageable
     * @return Page<BRJuridicPerson>
    </BRJuridicPerson> */
    fun listByFilters(filters: String?, pageable: Pageable): Page<BRJuridicPerson> {
        return juridicPersonRepository.findAll(pageable)
    }

    /**
     * @param id Long
     * @return Optional<BRJuridicPerson>
    </BRJuridicPerson> */
    fun findById(id: Long): Optional<BRJuridicPerson> {
        return juridicPersonRepository.findById(id)
    }

    /**
     * @param juridicPerson BRJuridicPerson
     * @return BRJuridicPerson
     */
    fun save(juridicPerson: BRJuridicPerson): BRJuridicPerson {
        return juridicPersonRepository.save(juridicPerson)
    }

    /**
     * @param id Long
     */
    fun deleteById(id: Long) {
        juridicPersonRepository.deleteById(id)
    }
}
