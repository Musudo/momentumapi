package com.musadzeyt.momentumapi.specification;

import com.musadzeyt.momentumapi.domain.Institution;
import com.musadzeyt.momentumapi.api.v1.dto.SearchCriteria;
import com.musadzeyt.momentumapi.util.FilterUtil;
import jakarta.persistence.criteria.*;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

@Data
public class InstitutionSpecification implements Specification<Institution> {
    private final SearchCriteria searchCriteria;

    @Override
    public Predicate toPredicate(Root<Institution> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        Path<?> expression = FilterUtil.getPath(root, searchCriteria.getKey());

        return FilterUtil.buildPredicate(builder, expression, searchCriteria);
    }
}
