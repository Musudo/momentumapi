package com.musadzeyt.momentumapi.specification;

import com.musadzeyt.momentumapi.domain.Contact;
import com.musadzeyt.momentumapi.api.v1.dto.SearchCriteria;
import com.musadzeyt.momentumapi.util.FilterUtil;
import jakarta.persistence.criteria.*;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

@Data
public class ContactSpecification implements Specification<Contact> {
    private final SearchCriteria searchCriteria;

    @Override
    public Predicate toPredicate(Root<Contact> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        Path<?> expression = FilterUtil.getPath(root, searchCriteria.getKey());

        return FilterUtil.buildPredicate(builder, expression, searchCriteria);
    }
}
