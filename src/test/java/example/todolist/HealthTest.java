package example.todolist;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HealthTest extends AcceptanceTest {
	@Test
	@DisplayName("헬스체크를 통해 Application, DB 구동 상태를 확인할 수 있다.")
	void health() {
		// when
		JsonPath response = RestAssured
				.given().log().all()
				.when().get("/api/health")
				.then().log().all()
				.extract()
				.jsonPath();

		// then
		String applicationStatus = response.getString("status");
		String dbStatus = response.getString("components.db.status");

		assertThat(applicationStatus).isEqualTo("UP");
		assertThat(dbStatus).isEqualTo("UP");
	}
}
