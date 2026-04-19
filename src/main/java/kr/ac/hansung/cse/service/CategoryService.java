package kr.ac.hansung.cse.service;

import kr.ac.hansung.cse.exception.DuplicateCategoryException;
import kr.ac.hansung.cse.model.Category;
import kr.ac.hansung.cse.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

//카테고리 관리 비즈니스 로직을 담당하는 서비스 클래스

@Service
@Transactional(readOnly = true)
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    //모든 카테고리 목록 조회
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    //새 카테고리 생성
    @Transactional
    public Category createCategory(String name) {
        // 중복 검사: 이름이 이미 있으면 예외 발생
        categoryRepository.findByName(name)
                .ifPresent(c -> {
                    throw new DuplicateCategoryException(name);
                });
        return categoryRepository.save(new Category(name));
    }

    // 카테고리 삭제
    @Transactional
    public void deleteCategory(Long id) {
        long count = categoryRepository.countProductsByCategoryId(id);
        if (count > 0) {
            throw new IllegalStateException("상품 " + count + "개가 연결되어 있어 삭제할 수 없습니다.");
        }
        categoryRepository.delete(id);
    }
}
