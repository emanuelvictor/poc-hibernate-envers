package com.luc.api.application.resource;

import com.luc.api.application.aspect.exceptions.NotFoundException;
import com.luc.api.domain.entity.brazil.BRJuridicPerson;
import com.luc.api.domain.service.brazil.BRJuridicPersonService;
import com.luc.api.infrastructure.audit.repository.IRevisionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;

import java.util.List;

@RequestScope
@RestController
@RequiredArgsConstructor
@RequestMapping("/br/juridic-persons")
public class BRJuridicPersonResource {

    /**
     *
     */
    private final BRJuridicPersonService juridicPersonService;

    /**
     *
     */
    private final IRevisionRepository<BRJuridicPerson> revisionPhysicPersonService;

    /**
     * @param filters  String
     * @param pageable Pageable
     * @return Page<BRJuridicPerson>
     */
    @GetMapping
    public Page<BRJuridicPerson> listByFilters(final String filters, final Pageable pageable) {
        return juridicPersonService.listByFilters(filters, pageable);
    }

    /**
     * @param id Long
     * @return BRJuridicPerson
     */
    @GetMapping("{id}")
    public BRJuridicPerson findById(final @PathVariable Long id) {
        return juridicPersonService.findById(id).orElseThrow(NotFoundException::new);
    }

    /**
     * @param juridicPerson BRJuridicPerson
     * @return BRJuridicPerson
     */
    @PostMapping
    public BRJuridicPerson save(final @RequestBody BRJuridicPerson juridicPerson) {
        return juridicPersonService.save(juridicPerson);
    }

    /**
     * @param juridicPerson BRJuridicPerson
     * @return BRJuridicPerson
     */
    @PutMapping("{id}")
    public BRJuridicPerson save(final @PathVariable Long id, final @RequestBody BRJuridicPerson juridicPerson) {
        juridicPerson.setId(id);
        return juridicPersonService.save(juridicPerson);
    }

    /**
     * @param id Long
     */
    @DeleteMapping("{id}")
    public void deleteById(final @PathVariable Long id) {
        juridicPersonService.deleteById(id);
    }

    /**
     * @param id Long
     */
    @GetMapping("{id}/revisions")
    public List<Object> findRevisionsById(final @PathVariable Long id) {
         return revisionPhysicPersonService.findRevisionsById(id);
    }


    /**
     *
     * @return
     */
    @GetMapping("revisions")
    public List<Object> findRevisions() {
        return revisionPhysicPersonService.findRevisions();
    }
}
