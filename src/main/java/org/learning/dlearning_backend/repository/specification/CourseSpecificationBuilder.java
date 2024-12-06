package org.learning.dlearning_backend.repository.specification;

import org.learning.dlearning_backend.entity.Course;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

import static org.learning.dlearning_backend.repository.specification.SearchOperation.ZERO_OR_MORE_REGEX;


public class CourseSpecificationBuilder {

    private final List<SpecSearchCriteria> param;

    public CourseSpecificationBuilder() {
        this.param = new ArrayList<>();
    }

    public CourseSpecificationBuilder with(String key, String operation, String value, String prefix, String suffix) {
        return with(null, key, operation,  value,  prefix,  suffix);
    }

    public CourseSpecificationBuilder with(String orPredicate, String key, String operation, String value, String prefix, String suffix) {
        SearchOperation oper = SearchOperation.getSimpleOperation(operation.charAt(0));

        if (oper == SearchOperation.EQUALITY) {
            boolean startWith = prefix != null && prefix.equals(ZERO_OR_MORE_REGEX);
            boolean endWith = suffix != null && suffix.equals(ZERO_OR_MORE_REGEX);

            if (startWith && endWith) {
                oper = SearchOperation.CONTAINS;
            } else if (startWith) {
                oper = SearchOperation.ENDS_WITH;
            } else if (endWith) {
                oper = SearchOperation.START_WITH;
            }
        }
        param.add(new SpecSearchCriteria(orPredicate, key, oper, value));
        return this;
    }

    public Specification<Course> build () {
        if(param.isEmpty()) return null;

        Specification<Course> specification = new CourseSpecification(param.getFirst());
        for (int i = 1; i < param.size(); i++) {
            specification = param.get(i).getOrPredicate()
                    ? Specification.where(specification).or(new CourseSpecification(param.get(i)))
                    : Specification.where(specification).and(new CourseSpecification(param.get(i)));
        }
        return specification;
    }
}
