# TogeDutch
### UMC 3기 가치더치 팀프로젝트 
|정채원 (PM)|유승연|김혜주|김정환|김시현|
|:------:|:---:|:------:|:---:|:------:|

<br>

## 1️⃣ 서비스 소개
- **TogeDutch(가치더치)** : 배달팁 + 최소 주문 금액 나누는 "배달 메이트 구인 서비스"
- 부담스러운 배달팁, 최소 주문 금액 때문에 주문을 망설인 경험이 있나요? 
- 배달 메이트 공고를 올리고, 메이트를 랜덤 추천 받을 수 있는 서비스

<br>

## 2️⃣ 기획 의도 및 주제 선정 배경
<img src="https://github.com/UMC-TogeDutch-Project/TogeDutch_BACKEND_JDBC/assets/97737822/fa5a83de-d6c6-437a-9c0e-f9b6f1f69924" width="500" height="500"/>

### 📌 **배달 업계를 둘러싼 논란**

✅  배달 대행 서비스가 꾸준한 인기를 누리고 있는 요즘!

✅ 배달비와 최소 주문 금액에 대한 끊이지 않는 논란

✅ 특히 1인 가구에게 크게 느껴지는 부담

✅ 업주가 부담해야 하는 배달 수수료 증가

> 배달 대행 서비스는 우리에게 **편리함**을 가져다 주었습니다. 
하지만 소비자와 가게에게 **좋은 생태계**를 만들어주지는 **못했습니다**.
> 

따라서 위의 문제점을 해결할 수 있는 `배달 메이트 구인 서비스 : 가치더치`를 만들게 되었습니다!

<br>

## 3️⃣ 핵심 기능
**1. 배달 메이트 공고 업로드**
- 공고 제목, 가게 정보 URL, 배달팁, 최소 주문 금액, 위치, 주문 시간, 모집 인원 등을 상세하게 적어서 업로드
- 공고를 최신순, 주문임박 순으로 조회 가능
![image](https://github.com/UMC-TogeDutch-Project/TogeDutch_BACKEND_JDBC/assets/97737822/eaed7016-174d-4bbb-824a-2953e7ec64a8)


**2. 유저에게 알림 전송**
- 유저가 설정했던 알림 키워드를 포함하는 공고가 업로드되면 유저에게 알림
- 메이트 신청 현황을 유저에게 알림 → 알림 탭에서 메이트 수락/거절 가능
![image](https://github.com/UMC-TogeDutch-Project/TogeDutch_BACKEND_JDBC/assets/97737822/ed2f04da-3ec2-49a3-a319-31d6a0f8fbe5)

**3. 채팅**
- 공고자와 신청자가 함께하는 채팅방 생성 → 채팅을 통한 공동 주문
- 메시지, 사진, 약속 장소, 약속 시간 전송 가능
![image](https://github.com/UMC-TogeDutch-Project/TogeDutch_BACKEND_JDBC/assets/97737822/3372d2f4-4afd-42ab-8f4d-3062a7efa0b0)

**4. 랜덤매칭**
- 주문 예정 시간을 지났지만 모집 인원을 채우지 못한 경우 랜덤추천 버튼 활성화
- 최대 3회 나눔 장소 주변에 거주 중인 유저를 추천받을 수 있으며, 매칭 신청 가능
![image](https://github.com/UMC-TogeDutch-Project/TogeDutch_BACKEND_JDBC/assets/97737822/fdb1e087-d4f3-4fdd-a997-b936cfdb529e)

**5. 친절도 평가**
- 공고자와 메이트는 약속 장소에서 만나 음식 전달 후 서로의 친절도를 평가
- 상대방의 프로필 클릭 시 친절도 수치와 텍스트 후기 확인 가능
- 앱 사용 시 사용자의 신뢰도를 판단하는 척도로 활용 가능
![image](https://github.com/UMC-TogeDutch-Project/TogeDutch_BACKEND_JDBC/assets/97737822/dfedb3a9-3f8a-4b4a-8df6-85f41fc01ed4)

**이외에도 알림 키워드 설정, 주변 맛집 검색, 광고 등록 등 다양한 기능을 구현했습니다.**

<br>

## 4️⃣ 가치더치의 POD
> **배달긱**
> 

- 배달긱은 1인 가구와 학생들을 타겟층으로
- 배달팁과 배달최소금액을 0원으로 제공
- **서비스 지역이 서울과 몇몇 대학교로 한정**
![image](https://github.com/UMC-TogeDutch-Project/TogeDutch_BACKEND_JDBC/assets/97737822/da166bd1-cced-411c-9190-dbf20a7f1e60)

<br>

## 5️⃣ 사용 기술
![Java Badge](https://img.shields.io/badge/Java-007396?style=flat&logo=Java&logoColor=white)&nbsp;
![SpringBoot Badge](https://img.shields.io/badge/Spring&nbsp;Boot-6DB33F?style=flat&logo=SpringBoot&logoColor=white)&nbsp;
![MySQL Badge](https://img.shields.io/badge/MySQL-4479A1?style=flat&logo=MySQL&logoColor=white)&nbsp;
![AWS EC2 Badge](https://img.shields.io/badge/Amazon&nbsp;EC2-FF9900?style=flat&logo=Amazon-EC2&logoColor=white)&nbsp; 
![AWS S3 Badge](https://img.shields.io/badge/Amazon&nbsp;S3-569A31?style=flat&logo=Amazon-S3&logoColor=white)&nbsp; 
![AWS RDS Badge](https://img.shields.io/badge/Amazon&nbsp;RDS-527FFF?style=flat&logo=Amazon-RDS&logoColor=white)&nbsp; 

![IntelliJ Badge](https://img.shields.io/badge/IntelliJ_IDEA-000000?style=flat&logo=IntelliJ-IDEA&logoColor=white)&nbsp; 
![Github Badge](https://img.shields.io/badge/github-181717?style=flat&logo=github&logoColor=white)&nbsp; 
![Postman](https://img.shields.io/badge/Postman-FF6C37?style=flat&logo=Postman&logoColor=white)&nbsp;
![Swagger](https://img.shields.io/badge/Swagger-85EA2D?style=flat&logo=Swagger&logoColor=white)&nbsp;
![Notion](https://img.shields.io/badge/Notion-000000?style=flat&logo=Notion&logoColor=white)&nbsp;

<br>

## 6️⃣ 시연 영상
https://www.youtube.com/watch?v=REyCEVnXyiY)](https://www.youtube.com/watch?v=REyCEVnXyiY

<br>

## 7️⃣ Commit Message Convention

> 커밋태그: 내용 #닉네임

ex. `feat : login 파일 추가 #jay'

| command | mean |
| --- | --- |
| feat | 새로운 기능 추가 |
| fix | 버그 수정 |
| docs | 문서 수정 |
| style | 코드 포맷 변경 |
| test | 테스트 코드 추가 |
| refactoring | 코드 리팩토링 |
| chore | 빌드, 패키지 매니저 수정 |
