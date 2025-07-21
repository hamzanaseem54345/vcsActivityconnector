package com.clanx.vcsactivityconnector;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(exclude = {
		DataSourceAutoConfiguration.class,
		HibernateJpaAutoConfiguration.class,
		LiquibaseAutoConfiguration.class
})
@EnableFeignClients(basePackages = "com.clanx.vcsactivityconnector.client")
public class VcsActivityConnectorApplication {

	public static void main(String[] args) {
		SpringApplication.run(VcsActivityConnectorApplication.class, args);
	}

}

