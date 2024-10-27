package org.sopt.diary.api;

// 일기 작성 및 수정 시 클라이언트로부터 받을 데이터를 정의하는 클래스
public class DiaryRequest {
    private String title;   // 일기 제목
    private String content;// 일기 내용
    private String category; // 카테고리 필드 추가

    // 생성자: 제목과 내용을 받아 초기화
    public DiaryRequest(String title, String content,String category) {
        this.title = title;
        this.content = content;
        this.category = category;

    }

    // Getter for title
    public String getTitle() {
        return title;
    }

    // Setter for title
    public void setTitle(String title) {
        this.title = title;
    }

    // Getter for content
    public String getContent() {
        return content;
    }

    // Setter for content
    public void setContent(String content) {
        this.content = content;
    }
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }


    // 제목과 내용이 모두 유효한지 검증하는 메서드
    // 제목은 null이 아니고 , 내용은 null이 아니고 30자 이하인지 검증
    public boolean isValid() {
        return title != null && title.length() <= 30 &&
                content != null && content.length() <= 100;
    }
}
