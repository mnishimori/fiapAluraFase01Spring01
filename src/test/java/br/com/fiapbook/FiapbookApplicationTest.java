package br.com.fiapbook;


import br.com.fiapbook.shared.annotation.DatabaseTest;
import br.com.fiapbook.shared.annotation.IntegrationTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

@IntegrationTest
@DatabaseTest
class FiapbookApplicationTest {

	private final ApplicationContext applicationContext;

	@Autowired
	FiapbookApplicationTest(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	@Test
	void contextLoads() {
		var fiapBookApplication = applicationContext.getBean(FiapbookApplication.class);
		Assertions.assertThat(fiapBookApplication).isNotNull();
	}

}
