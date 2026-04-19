package kr.ac.hansung.cse.exception;

/**
 * 중복된 카테고리 이름 등록 시 발생하는 예외
 */
public class DuplicateCategoryException extends RuntimeException {
    public DuplicateCategoryException(String name) {
        super("이미 존재하는 카테고리입니다: " + name);
    }
}
