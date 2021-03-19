package br.com.original.rest.producers;

import br.com.original.fwcl.utils.SecurityUtil;
import br.com.original.rest.producers.DataSourceProducer;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.whenNew;

/**
 * @author Henrique Romão
 */
@RunWith(PowerMockRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@PrepareForTest(DataSourceProducer.class)
public class DataSourceProducerTest {

    @Mock
    private SecurityUtil securityUtil;

    @Test
    public void getDataSourceArqrShouldReturnDataSource() throws Exception {
        String expectedPassword = "1234";
        String testString = "test";
        Number testNumber = 1;
        HikariConfig config = mock(HikariConfig.class);

        HikariDataSource expectedDataSource = mock(HikariDataSource.class);

        whenNew(HikariConfig.class).withNoArguments().thenReturn(config);
        whenNew(HikariDataSource.class).withArguments(config).thenReturn(expectedDataSource);
        when(securityUtil.getCryptedValue(anyString())).thenReturn(expectedPassword);

        DataSourceProducer dataSourceProducer = new DataSourceProducer();

        ReflectionTestUtils.setField(dataSourceProducer, "driverClassName", testString);
        ReflectionTestUtils.setField(dataSourceProducer, "url", testString);
        ReflectionTestUtils.setField(dataSourceProducer, "username", testString);
        ReflectionTestUtils.setField(dataSourceProducer, "poolName", testString);
        ReflectionTestUtils.setField(dataSourceProducer, "minumunIdle", testNumber.intValue());
        ReflectionTestUtils.setField(dataSourceProducer, "maximunPoolSize", testNumber.intValue());
        ReflectionTestUtils.setField(dataSourceProducer, "connectionTimeout", testNumber.longValue());
        ReflectionTestUtils.setField(dataSourceProducer, "idleTimeout", testNumber.longValue());
        ReflectionTestUtils.setField(dataSourceProducer, "maxLifetime", testNumber.longValue());

        HikariDataSource returnedDataSource = (HikariDataSource) dataSourceProducer.getDataSourceArqr(securityUtil);

        assertEquals(expectedDataSource, returnedDataSource);   

        verify(config).setMinimumIdle(testNumber.intValue());
        verify(config).setMaximumPoolSize(testNumber.intValue());
        verify(config).setPoolName(testString);
        verify(config).setConnectionTimeout(testNumber.longValue());
        verify(config).setIdleTimeout(testNumber.longValue());
        verify(config).setMaxLifetime(testNumber.longValue());
        verify(config).setJdbcUrl(testString);
        verify(config).setDriverClassName(testString);
        verify(config).setUsername(testString);

        verify(securityUtil).getCryptedValue(anyString());

        verify(config).setPassword(expectedPassword);


    }
}
