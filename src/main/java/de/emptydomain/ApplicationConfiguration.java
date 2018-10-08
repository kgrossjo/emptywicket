package de.emptydomain;
// from https://freiberufler-team.de/software-architektur/spring-3-hibernate-4-und-wicket-6-ein-starkes-gespann-1774.html

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.StringUtils;

@Configuration
@Import(PropertyConfiguration.class)
@ComponentScan(basePackages = { "backend" })
@EnableTransactionManagement
public class ApplicationConfiguration {

	@Value("${db.driverClassName}")
	private String dbDriverClassName;
	@Value("${db.url}")
	private String dbUrl;
	@Value("${db.username}")
	private String dbUsername;
	@Value("${db.password}")
	private String dbPassword;
	@Value("${db.showSql}")
	private boolean dbShowSQl;
	@Value("${db.generateDDL}")
	private boolean dbGenerateDdl;

	@Bean
	public DataSource dataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		if (!StringUtils.isEmpty(dbDriverClassName)) {
			dataSource.setDriverClassName(dbDriverClassName);
		}
		dataSource.setUrl(dbUrl);
		dataSource.setUsername(dbUsername);
		dataSource.setPassword(dbPassword);
		return dataSource;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setDataSource(dataSource());
		factory.setPersistenceUnitName("EmptyWicket");
		factory.setPackagesToScan("common.domain");

		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setDatabase(Database.HSQL);
		vendorAdapter.setShowSql(dbShowSQl);
		vendorAdapter.setGenerateDdl(dbGenerateDdl);

		factory.setJpaVendorAdapter(vendorAdapter);

		return factory;
	}

	@Bean
	public JpaTransactionManager transactionManager() {
		return new JpaTransactionManager(entityManagerFactory().getObject());
	}
}
