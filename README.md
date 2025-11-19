# 개발 기술 스택

## Frontend

Framework: 리액트 - 바이트

그 외 Styled-components로 스타일링을 하였고

HTTP Client는 Axios로 하여 백엔드와 소통하였습니다.

## Backend

Framework: Spring Boot

gradle을 사용하였습니다.

## 파싱방법

파싱은 프론트로부터 SQL문을 string으로 받고 한 글자씩 문자 단위로 순회하며 토큰을 분류하고 분류한 것을 List로 하여 프론트에 전달하였습니다.

특히, 파싱로직에서는 String대신 StringBuilder를 사용하여 반복적인 문자열 연산시 발생하는 메모리낭비를 줄이고자 하였습니다.

## UI

* 입력인터페이스
* 출력인터페이스

이렇게 두 개로 나눠서 구성했습니다. 출력시에는 백엔드에서 받은 json형태의 데이터를 가공하여 요구사항과 동일한 출력 형태로 만들었습니다.

## 실제 페이지

<img width="1287" height="571" alt="image" src="https://github.com/user-attachments/assets/0b1fc776-fa11-40f2-acec-5fb45aa0745c" />

---

<img width="1285" height="975" alt="image" src="https://github.com/user-attachments/assets/e276804e-3063-4f9c-b6ef-610726582d30" />

