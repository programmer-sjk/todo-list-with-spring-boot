package example.todolist.health;

import example.todolist.AcceptanceTest;
import example.todolist.health.dto.HealthResponse;
import io.restassured.RestAssured;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HealthControllerTest extends AcceptanceTest {
	@Test
	@DisplayName("헬스체크를 통해 Application, DB 구동 상태를 확인할 수 있다.")
	void health() {
		// when
		HealthResponse response = RestAssured
				.given().log().all()
				.when().get("/api/health")
				.then().log().all()
				.extract()
				.as(HealthResponse.class);

		// then
		assertThat(response.applicationStatus()).isEqualTo("UP");
		assertThat(response.dbStatus()).isEqualTo("UP");
	}
}
