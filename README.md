## 👨‍👩‍👧‍👦 GrowPlan: 아동 발달 관리 플랫폼

![growplan thumbnail](https://github.com/user-attachments/assets/62574170-2c4c-48f0-98ef-8ac2207834ad)

### 🌟 서비스 요약

GrowPlan은 아동 발달 상태를 체크리스트 기반으로 측정하고, 월별 레포트를 통해 발달 추이를 시각화하는 플랫폼입니다.

| 구분 | 내용 |
| --- | --- |
| **서비스명** | GrowPlan |
| **목표** | 발달 관리 플랫폼 |
| **팀 구성** | PM 4명, 프론트엔드 1명, 백엔드 1명, 디자이너 1명 |
| **역할** | 백엔드 개발 |
| **기간** | 2024. 9 - 2024. 12 |

### 💻 프로젝트 링크

| 🔗 𝗚𝗶𝘁𝗛𝘂𝗯 | 📝 산출물 |
| --- | --- |
| [🔗 바로가기](https://www.google.com/search?q=%23) | [🔗 바로가기](https://www.google.com/search?q=%23) |

### ✨ 주요 기능
* **진단:** 체크리스트 기반 발달 상태 측정 및 내역 조회 기능
* **월별 레포트:** 발달 추이 분석 및 문제 영역 시각화 기능
* **기록:** 아동 발달 기록 조회 및 등록 기능
* **센터:** 지역별 발달 센터 정보 조회 기능

### 🧠 주요 업무 (백엔드 개발)

#### 1. 서울·경기 지역 발달 센터 정보 수집 및 데이터 구축

* 약 1200여 개의 발달 센터 정보를 크롤링 하여 수집
* 수집된 데이터를 기반으로 데이터베이스 구성

#### 2. ChatGPT API 연동 및 가이드라인 자동 생성

* `RestTemplate`을 활용해 OpenAI(ChatGPT) 외부 API와 HTTP 통신 구현
* 발달 점수 요구사항을 사용하여 가이드라인이 포함된 프롬프트 자동 생성

#### 3. CORS 정책 구성 및 클라이언트-서버 연동

* 허용 출처, 허용 헤더, 자격 증명 등을 명시하여 CORS 문제 해결

#### 4. 다중 조건 필터링 기능 구

* 사용자 입력에 따라 동적으로 검색 조건을 조합하는 쿼리 생성 (`createQuery` 메서드)
* 지역 기반 필터링: 대분류(시도), 중분류(시군구), 소분류(읍면동) 적용
* 태그 필터링: 13가지 센터 태그(언어, 놀이, 심리 등) 활용
* `centerTags` (문자열)를 `CenterTagType` (Enum)으로 변환 (`getCenterTagNames` 메서드)

#### 5. 페이징 처리 기능 개발

* Spring Data JPA `Pageable`을 활용하여 페이징 처리
* `@PageableDefault`와 `Pageable` 객체를 통해 `page`와 `size` 파라미터 지원
* 전체 센터 수, 페이지 크기로 마지막 페이지 인덱스 계산 (`getLastPageIndex` 메서드)

#### 6. 발달 설문 및 결과 조회 로직 구현
* `ChronoUnit`을 활용해 개월 수 계산, 해당 연령에 맞는 발달 진단지 그룹 제공
* 사용자의 설문 응답을 합산하여 총 발달 점수 산출
* `calculateRisk` 메서드를 통해 발달 장애 위험 기준(LowScore) 이하 여부 판단


### ⚙️ 백엔드 기술 스택

| 분류 | 기술 스택 | 사용 이유 |
| --- | --- | --- |
| **𝗟𝗮𝗻𝗴𝘂𝗮𝗴𝗲/𝗙𝗿𝗮𝗺𝗲𝘄𝗼𝗿𝗸** | Java 17, Spring Boot 3, Spring Data JPA | **Java 17:** LTS 버전으로 안정적인 개발 환경 구축<br> **Spring Boot:** 애플리케이션 개발<br> **Spring Data JPA:** 관계형 데이터베이스 접근 |
| **𝗔𝘂𝘁𝗵** | OAuth 2.0, JWT, Spring Security | **OAuth 2.0 / JWT:** 인증/인가 시스템 구축<br> **Spring Security:** 인증/인가 로직 구현 |
| **𝗗𝗮𝘁𝗮𝗯𝗮𝘀𝗲** | MySQL (RDS) | 안정성이 높은 관계형 데이터베이스 사용<br> AWS RDS를 통해 운영 및 관리 효율성 제고 |
| **𝗜𝗻𝗳𝗿𝗮/𝗗𝗲𝘃𝗢𝗽𝘀** | Docker, AWS (EC2, S3), GitHub Actions | **Docker:** 배포 환경 통일<br> **AWS:** 안정적인 클라우드 서비스 환경 제공<br> **GitHub Actions:** CI/CD 파이프라인 자동화 |
| **𝗧𝗲𝘀𝘁𝗶𝗻𝗴/𝗗𝗼𝗰𝘀** | JUnit 5, Swagger (OpenAPI 3.0) | **JUnit 5:** 단위 테스트 작성<br> **Swagger:** API 문서 자동화 |
| **𝗖𝗼𝗺𝗺𝘂𝗻𝗶𝗰𝗮𝘁𝗶𝗼𝗻 𝗧𝗼𝗼𝗹𝘀** | Slack, Notion, Figma | **Slack/Notion:** 커뮤니케이션 및 문서화<br> **Figma:** UI/UX 협업 |


### 📐 시스템 아키텍처 설계

![growplan  ERD](https://github.com/user-attachments/assets/321e5357-bf70-4e60-b6c2-f1bda6e2119c)

### 🗄️ 데이터베이스 설계

<img width="966" height="693" alt="1000014796" src="https://github.com/user-attachments/assets/d47d5009-cac1-4062-a6c0-0e23869be88c" />

### 🔑 주요 코드

#### 1. ChatGPT API 연동

```java
// java/com/growplan/report/service/ReportService.java
private String createPrompt(final String developmentType, final List<Integer> scores) {
    final StringBuilder promptBuilder = new StringBuilder();

    promptBuilder.append("다음은 발달 장애 아동의 발달 점수 기록입니다.\n")
            .append("분석 대상 발달 유형: ").append(developmentType).append("\n\n");

    promptBuilder.append("다음은 날짜 순으로 정렬된 발달 점수입니다:\n")
            // ...
            .append("\n\n");

    promptBuilder.append("이 점수를 바탕으로 아래 내용을 분석해 주세요:\n")
            // ...
            .append("제공된 발달 점수와 발달 유형을 바탕으로 상세한 분석을 부탁드립니다.");

    return promptBuilder.toString();
}
```

#### 2. CORS 정책 구성 및 클라이언트-서버 연동

```java
// java/com/growplan/common/config/SecurityConfig.java
config.setAllowedOriginPatterns(List.of("http://localhost:5173", "https://growplan.netlify.app", "https://growplan.site"));
// ...
config.setAllowedHeaders(List.of(
    // ...
    "Authorization"
));
config.setAllowCredentials(true);
```

#### 3. 페이징 처리

```java
// java/com/growplan/center/service/CenterService.java
public CenterListResponse getCentersByPage(final Pageable pageable, final List<String> centerTags, ... ) {
    // ...
    
    centers = centerRepository.findFilteredCentersByPageable(
        centerTagNames,
        locationQuery,
        userId,
        isScraped,
        pageable.previousOrFirst()
    );

    // ... 총 페이지 수 계산 로직 호출 및 응답
}

private Long getLastPageIndex(final int pageSize, final List<String> centerTags, ... ) {
    // ...
    
    final long lastPageIndex = centerCount / pageSize;
    if (centerCount % pageSize == 0) {
        return lastPageIndex;
    }
    return lastPageIndex + 1;
}
```

#### 4. 다중 조건 필터링 기능

```java
// java/com/growplan/center/service/CenterService.java
private String createQuery(final String province, final String city, final String neighborhood) {
    if (province == null)
        return null;
    else if (province != null && city == null) // 대분류
        return ProvinceType.of(province).getName();
    else if (province != null && city != null && neighborhood == null) // 대분류 + 중분류
        return ProvinceType.of(province).getName() + " " + city;
    else if (province != null && city != null && neighborhood != null) // 대분류 + 중분류 + 소분류
        return ProvinceType.of(province).getName() + " " + city + " " + neighborhood;
    // ... 예외 처리 로직
}

private List<String> getCenterTagNames(List<String> centerTags) {
    if (centerTags == null) {
        return null;
    }

    return centerTags.stream()
            .map(centerTag -> CenterTagType.of(centerTag).toString())
            .collect(Collectors.toList());
}
```

#### 4. 발달 설문 및 결과 조회

```java
// java/com/growplan/survey/service/DevelopService.java
public DevelopmentScaleSurveyResponse getDevelopmentScalesAndSurveys(final Long userId, final Long childId) {

    final Integer childMonths = calculateAgeInMonths(userChild.getBirthdate()); 
    final List<SurveyGroup> surveyGroups = surveyGroupRepository.findSurveyGroupByMonths(childMonths);
    final List<SurveyTitle> surveyTitles = getRandomSurveyTitles(surveyGroups); 

    return DevelopmentScaleSurveyResponse.of(userChild, childMonths, surveyResults, surveyTitles);
}
private int calculateAgeInMonths(final String birthdateStr) {
    final long totalDays = ChronoUnit.DAYS.between(birthdate, today);
    return (int) (totalDays / 30);
}

public DevelopmentResultResponse getDevelopmentResult(final Long userId, final Long childId, final String developmentType) {

    final List<ChildSurvey> surveys = getChildSurveys(userChild, currentDate, developmentType); 
    final Integer developmentScore = calculateDevelopmentScore(surveys);
    final Boolean isRisk = calculateRisk(developmentScore, surveyGroup); 
    final List<Feedback> feedbacks = getFeedbacks(surveys, developmentScore, surveyGroup); 
    final SurveyResult surveyResult = getOrCreateSurveyResult(userChild, developmentType, currentDate, developmentScore, isRisk, surveys);
    
    updateSurveyScore(surveyResult, developmentScore, isRisk);

    return DevelopmentResultResponse.of(currentDate, surveys, developmentScore, isRisk, feedbacks);
}
```

