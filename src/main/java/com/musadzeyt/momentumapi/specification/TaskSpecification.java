package com.musadzeyt.momentumapi.specification;

import com.musadzeyt.momentumapi.domain.Task;
import com.musadzeyt.momentumapi.dto.SearchCriteria;
import com.musadzeyt.momentumapi.util.FilterUtil;
import jakarta.persistence.criteria.*;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

@Data
public class TaskSpecification implements Specification<Task> {
    private final SearchCriteria searchCriteria;

    @Override
    public Predicate toPredicate(Root<Task> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        Path<?> expression = FilterUtil.getPath(root, searchCriteria.getKey());

        return FilterUtil.buildPredicate(builder, expression, searchCriteria);
    }
}
