# 이벤트 선착순 쿠폰 시스템
## Background
선착순 쿠폰 이벤트는 한정된 수량의 쿠폰을 먼저 신청한 사용자에게 제공하는 이벤트입니다.
## Architecture
### 비동기 쿠폰 발급 요청 처리 구조
![image](https://github.com/user-attachments/assets/4db412bb-d2f9-4056-b308-7b1cba2bf08a)

### Tech Stack
**Infra** </br>
Aws EC2, Aws RDS, Aws Elastic Cache

**Server** </br>
Java 17, Spring Boot 3.1, Spring Mvc, JPA, QueryDsl

**Database** </br>
Mysql, Redis, H2

**Monitoring** </br>
Aws Cloud Watch, Spring Actuator, Promethous, Grafana

**Etc** </br>
Locust, Gradle, Docker


## Main Feature
### 쿠폰 발급 검증
- 발급 기한
- 발급 수량
- 중복 발급
### 쿠폰 발급 수량 관리
- Redis Set기반 재고 관리
### 비동기 쿠폰 발급
- Redis List (발급 Queue)
- Queue Polling Scheduler

## Result
#### server spec
- EC2
  - coupon-api, coupon-consumer
  - t2.micro
- Mysql
  - cache.t2.micro
- Redis
  - cache.t2.micro
### Load Test
#### Metric 
![image](https://github.com/user-attachments/assets/7ff83678-23ab-494e-9209-25b9d95194db)

