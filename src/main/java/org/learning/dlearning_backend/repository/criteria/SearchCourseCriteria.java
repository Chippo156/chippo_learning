package org.learning.dlearning_backend.repository.criteria;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.learning.dlearning_backend.dto.response.PageResponse;
import org.learning.dlearning_backend.entity.Course;
import org.learning.dlearning_backend.repository.CourseRepository;
import org.learning.dlearning_backend.repository.specification.CourseSpecificationBuilder;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class SearchCourseCriteria {

    @PersistenceContext
    private final EntityManager entityManager;
    private final CourseRepository courseRepository;
    public PageResponse advanceSearchCourse (int page, int size, String sortBy, String... search) {
        // dinh nghia kieu: vd: title: T(gia tri), author: T(gia tri), price: T(gia tri)

        // 1. Lay ra danh sach course
        // --> xu ly chuoi search khi client gui len dau tien, xu ly search thi moi query duoc
        List<SearchCriteria> criteriaList = new ArrayList<>();
        if(search != null) {
            // neu khong null --> duyet mang de lay ra chuoi search (vd: title: T(gia tri), author: T(gia tri), price: T(gia tri))
            for (String s : search) {
                Pattern pattern = Pattern.compile("(\\w+?)([:><=])(.*)");
                Matcher matcher = pattern.matcher(s);
                if(matcher.find()){
                    criteriaList.add(new SearchCriteria(matcher.group(1), matcher.group(2), matcher.group(3)));
                    // vd: firstName: duc -> group 1 se lay ra: firstName
                    //                       group 2 se lay ra: dau :
                    //                       group 3 se lay ra: duc -> value
                }
            }
        }
        // 2. lay ra so luong ban ghi (phan trang)
        // sau khi da lay ra duoc danh sach List Criteria -> thi lay ra dua theo dieu kien do thoi
        List<Course> courses = getCourse(page, size, criteriaList, sortBy);
        // Đặc trưng của criteria này nó trả offset: vi tri ban ghi --> current page
        return PageResponse.builder()
                .currentPage(page)
                .pageSize(size)
                .totalPages((int) Math.ceil((double) courses.size() / size))
                .totalElements(courses.size())
                .data(Collections.singletonList(courses))
                .build();
    }
    private List<Course> getCourse(int page, int size, List<SearchCriteria> criteriaList, String sortBy) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        // sau khi khởi tạo CriteriaBuider thì chung ta tiến hành Query
        // Để query chung ta dung: CriteriaQuery<T> nhận vào 1 generic -> nghĩa la chúng ta sẽ query trên đối tượng nào
        CriteriaQuery<Course> query = criteriaBuilder.createQuery(Course.class);
        // Sau khi dinh nghia CriteriaQuery -> buoc tiep theo chung ta se dinh nghia Root
        Root<Course> root = query.from(Course.class);
        // sau khi khai bao doi tuong root -> chung ta tien hanh xu ly dieu kien query, tim kiem
        Predicate predicate = criteriaBuilder.conjunction();
        CourseSearchCriteriaQueryConsumer queryConsumer = new CourseSearchCriteriaQueryConsumer(criteriaBuilder, predicate, root);
        // tới được đây tức có nghĩa là đã xử lý xong phần toán tử(operator) -> thực hiện việc lấy dữ liệu từ đối tượng root

        criteriaList.forEach(queryConsumer);
        predicate = queryConsumer.getPredicate(); // sau khi xử lý xong thì gán lại giá trị cho nó

        query.where(predicate);

        // sort ở đây nếu muốn: thì bóc tách sortBy thôi
        if(StringUtils.hasLength(sortBy)){
            Pattern pattern = Pattern.compile("(\\w+?)(:)(asc|desc)");
            Matcher matcher = pattern.matcher(sortBy);
            if(matcher.find()){
                String columnName = matcher.group(1);
                if(matcher.group(3).equalsIgnoreCase("desc")){
                    query.orderBy(criteriaBuilder.desc(root.get(columnName)));
                } else {
                    query.orderBy(criteriaBuilder.asc(root.get(columnName)));
                }
            }
        }

        return entityManager.createQuery(query)
                .setFirstResult(page - 1)
                .setMaxResults(size)
                .getResultList();
    }

    public PageResponse<?> advanceSearchWithSpecification (Pageable pageable, String [] course, String [] chapter) {

        List<Course> courses = null;
        if (course != null && chapter != null) {
            // xu ly tim kiem tren course va chapter --> join
        } else if (course != null) {
            // tim kiem tren bảng course và không cần join,
            // query: là câu điều kiện tìm kiếm, query này bản chất là CriteriaQuery -> dùng để query
            // root: tìm kiếm trên đối tượng nào
            // criteriaBuilder: dùng để build câu query đó như thế nao, build thành công thì lại run cái query
            // ==> criteriaBuilder bản chất là : mình muốn tìm kiếm theo cái gì, điều kiện gì thì mình build nó, config nó
            //                                   sau khi build được thì query để thực thi
//            Specification<Course> spec = Specification.where((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("title"), "Java"))
//            Specification<Course> specDescription = Specification.where((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("description"), "Java"))
//            Specification<Course> finalSpec = spec.and(specDescription)

            CourseSpecificationBuilder builder = new CourseSpecificationBuilder();

            for (String c : course) {
                Pattern pattern = Pattern.compile("(\\w+?)([:><~!])(.*)(\\p{Punct}?)(.*)(\\p{Punct}?)");
                Matcher matcher = pattern.matcher(c);
                if (matcher.find()) {
                    builder.with(matcher.group(1), matcher.group(2), matcher.group(3), matcher.group(4), matcher.group(5));
                }
            }

            courses = courseRepository.findAll(builder.build());
        }

        return PageResponse.builder()
                .currentPage(pageable.getPageNumber())
                .pageSize(pageable.getPageSize())
                .data(Collections.singletonList(courses))
                .build();

    }
}
