package si.recek.wealthbuild;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import si.recek.wealthbuild.graphql.BankAccountQuery;

@SpringBootApplication
public class WealthBuildApplication {

	public static void main(String[] args) {
		SpringApplication.run(WealthBuildApplication.class, args);
	}

	/*@Bean
	public BankAccountQuery bankAccountQuery(){
		return new BankAccountQuery();
	}
*/
}

