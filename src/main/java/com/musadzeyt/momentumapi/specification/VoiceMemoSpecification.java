package com.musadzeyt.momentumapi.specification;

import com.musadzeyt.momentumapi.domain.VoiceMemo;
import com.musadzeyt.momentumapi.dto.SearchCriteria;
import com.musadzeyt.momentumapi.util.FilterUtil;
import jakarta.persistence.criteria.*;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

@Data
public class VoiceMemoSpecification implements Specification<VoiceMemo> {
    private final SearchCriteria searchCriteria;

    @Override
    public Predicate toPredicate(Root<VoiceMemo> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        Path<?> expression = FilterUtil.getPath(root, searchCriteria.getKey());

        return FilterUtil.buildPredicate(builder, expression, searchCriteria);
    }
}
