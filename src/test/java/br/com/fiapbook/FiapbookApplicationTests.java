package br.com.fiapbook;


import br.com.fiapbook.shared.annotation.ApiTest;
import br.com.fiapbook.shared.annotation.DatabaseTest;
import br.com.fiapbook.shared.annotation.IntegrationTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@ApiTest
@DatabaseTest
class FiapbookApplicationTests {

	private final ApplicationContext applicationContext;

	@Autowired
	FiapbookApplicationTests(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	@Test
	void contextLoads() {
		var fiapBookApplication = applicationContext.getBean(FiapbookApplication.class);
		Assertions.assertThat(fiapBookApplication).isNotNull();
	}

}
