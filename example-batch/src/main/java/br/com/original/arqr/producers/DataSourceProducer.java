package br.com.original.arqr.producers;

import br.com.original.fwcl.utils.SecurityUtil;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author Henrique Romão
 */

@Configuration
public class DataSourceProducer {

    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.hikari.pool-name}")
    private String poolName;

    @Value("${spring.datasource.hikari.minimum-idle}")
    private int minumunIdle;

    @Value("${spring.datasource.hikari.maximum-pool-size}")
    private int maximunPoolSize;

    @Value("${spring.datasource.hikari.connection-timeout}")
    private Long connectionTimeout;

    @Value("${spring.datasource.hikari.idle-timeout}")
    private Long idleTimeout;

    @Value("${spring.datasource.hikari.max-lifetime}")
    private Long maxLifetime;

    @Bean
    public DataSource getDataSourceArqr(SecurityUtil securityUtil) {
        HikariConfig config = new HikariConfig();
        config.setMinimumIdle(minumunIdle);
        config.setMaximumPoolSize(maximunPoolSize);
        config.setPoolName(poolName);
        config.setConnectionTimeout(connectionTimeout);
        config.setIdleTimeout(idleTimeout);
        config.setMaxLifetime(maxLifetime);
        config.setJdbcUrl(url);
        config.setDriverClassName(driverClassName);
        config.setUsername(username);

        //método da biblioteca original-util
        String passwordDecrypted = securityUtil.getCryptedValue("spring.datasource.password");
        config.setPassword(passwordDecrypted);

        //NUNCA DEIXAR A LINHA ABAIXO EM SEU CODIGO. DEIXADO AQUI APENAS PARA MELHOR ENTENDIMENTO
        System.out.println("PASSWORD: " + passwordDecrypted);
        return new HikariDataSource(config);
    }

}
