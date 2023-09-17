package br.com.fiapbook;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
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
