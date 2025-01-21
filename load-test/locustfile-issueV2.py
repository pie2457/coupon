import random
from locust import task, FastHttpUser, constant


class CouponIssueV1(FastHttpUser):
    connection_timeout = 5.0
    network_timeout = 5.0
    wait_time = constant(0)  # 대기 시간 없음 (RPS 최대화)

    @task
    def issue(self):
        payload = {
            "userId": random.randint(1, 1000000),  # 범위를 1~100만으로 축소 (캐싱 가능성 높임)
            "couponId": 1
        }
        self.client.post("/v2/issue", json=payload)  # 응답 검사 없이 단순 요청
