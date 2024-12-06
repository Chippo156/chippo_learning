package org.learning.dlearning_backend.repository.specification;

import lombok.Getter;
import lombok.Setter;

import static org.learning.dlearning_backend.repository.specification.SearchOperation.OR_PREDICATE_FLAG;
import static org.learning.dlearning_backend.repository.specification.SearchOperation.ZERO_OR_MORE_REGEX;


@Getter
@Setter
public class SpecSearchCriteria {
    private String key;
    private SearchOperation operation;
    private Object value;
    private Boolean orPredicate; // Xác định có sử dụng OR hay không

        public SpecSearchCriteria(String key, SearchOperation operation, Object value) {
            super();
            this.key = key;
            this.operation = operation;
            this.value = value;
        }

        public SpecSearchCriteria(String orPredicate, String key, SearchOperation operation, Object value) {
            super();
            this.orPredicate = orPredicate != null && orPredicate.equals(OR_PREDICATE_FLAG);
            this.key = key;
            this.operation = operation;
            this.value = value;
        }

        public SpecSearchCriteria(String key, String operation, String value, String prefix, String suffix) {
            if(operation != null) {
                SearchOperation oper = SearchOperation.getSimpleOperation(operation.charAt(0));
                if(oper == SearchOperation.EQUALITY){
                    boolean startWith = prefix !=null && prefix.equals(ZERO_OR_MORE_REGEX);
                    boolean endWith = suffix !=null && suffix.equals(ZERO_OR_MORE_REGEX);

                    if(startWith && endWith){
                        oper = SearchOperation.CONTAINS;
                    } else if(startWith){
                        oper = SearchOperation.ENDS_WITH; // neu bắt đầu là */ thì endwith -> chỉ quan tâm phía sau *
                    } else if(endWith){
                        oper = SearchOperation.START_WITH;// neu kết thúc là \* thì startWith -> chỉ quan tâm phía trước *
                    }
                }
                this.key = key;
                this.operation = oper;
                this.value = value;
            }
        }
}
