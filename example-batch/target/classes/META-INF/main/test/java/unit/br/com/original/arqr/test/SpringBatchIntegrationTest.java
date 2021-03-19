//package unit.br.com.original.arqr.test;
//
//import br.com.original.arqr.config.BatchConfiguration;
//import br.com.original.arqr.producers.DataSourceProducer;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.batch.core.*;
//import org.springframework.batch.test.JobLauncherTestUtils;
//import org.springframework.batch.test.JobRepositoryTestUtils;
//import org.springframework.batch.test.context.SpringBatchTest;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.test.annotation.DirtiesContext;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.TestExecutionListeners;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
//import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
//
//import static org.hamcrest.core.Is.is;
//import static org.junit.Assert.assertThat;
//
///**
// * @author Henrique Romão
// */
//@RunWith(SpringRunner.class)
//@SpringBatchTest
//@ComponentScan("br.com.original")
//@SpringBootTest
//@EnableAutoConfiguration
//@ContextConfiguration(classes = {BatchConfiguration.class, DataSourceProducer.class})
//@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class})
//@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
//@ActiveProfiles("test")
//public class SpringBatchIntegrationTest {
//
//    @Autowired
//    private JobLauncherTestUtils jobLauncherTestUtils;
//
//    @Autowired
//    private JobRepositoryTestUtils jobRepositoryTestUtils;
//
//    @After
//    public void cleanUp() {
//        jobRepositoryTestUtils.removeJobExecutions();
//    }
//
//    @Before
//    public void setUp(){
//        System.setProperty("password", "Original01");
//        System.setProperty("algorithm", "PBEWITHMD5ANDDES");
//    }
//
//    private JobParameters defaultJobParameters() {
//        JobParametersBuilder paramsBuilder = new JobParametersBuilder();
//        paramsBuilder.addString("name", "TESTE");
//        paramsBuilder.addString("file.input", "sample-data-test.csv");
//        return paramsBuilder.toJobParameters();
//    }
//
//    @Test
//    public void givenReferenceOutput_whenJobExecuted_thenSuccess() throws Exception {
//
//        // when
//        JobExecution jobExecution = jobLauncherTestUtils.launchJob(defaultJobParameters());
//        JobInstance actualJobInstance = jobExecution.getJobInstance();
//        ExitStatus actualJobExitStatus = jobExecution.getExitStatus();
//
//        // then
//        assertThat(actualJobInstance.getJobName(), is("importUserJob"));
//        assertThat(actualJobExitStatus.getExitCode(), is("COMPLETED"));
////        AssertFile.assertFileEquals(expectedResult, actualResult);
//    }
//
//
//
//}
