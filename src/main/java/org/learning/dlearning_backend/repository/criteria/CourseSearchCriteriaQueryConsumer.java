package org.learning.dlearning_backend.repository.criteria;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Consumer;

@Setter
@Getter
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class CourseSearchCriteriaQueryConsumer implements Consumer<SearchCriteria> {

    private CriteriaBuilder criteriaBuilder;
    private Predicate predicate;
    private Root root;
    @Override
    public void accept(SearchCriteria searchCriteria) {

        if(searchCriteria.getOperator().equals(">")){
            predicate = criteriaBuilder.and(predicate, criteriaBuilder
                    .greaterThanOrEqualTo(root.get(searchCriteria.getKey()), searchCriteria.getValue().toString()));
        }else if(searchCriteria.getOperator().equals("<")){
            predicate = criteriaBuilder.and(predicate, criteriaBuilder
                    .lessThanOrEqualTo(root.get(searchCriteria.getKey()), searchCriteria.getValue().toString()));

        } else if (searchCriteria.getOperator().equals(":")){
            if(root.get(searchCriteria.getKey()).getJavaType() == String.class){
                predicate = criteriaBuilder.and(predicate, criteriaBuilder
                        .like(root.get(searchCriteria.getKey()), "%" + searchCriteria.getValue() + "%"));
            } else {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder
                        .equal(root.get(searchCriteria.getKey()), searchCriteria.getValue().toString()));
            }
        }
    }
}
