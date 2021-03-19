package unit.br.com.original.arqr.test;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

/**
 * @author Henrique Romão
 */
@SpringBatchTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class ReaderTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private JobRepositoryTestUtils jobRepositoryTestUtils;

    @After
    public void cleanUp() {
        jobRepositoryTestUtils.removeJobExecutions();
    }


    private JobParameters defaultJobParameters() {
        JobParametersBuilder paramsBuilder = new JobParametersBuilder();
        paramsBuilder.addString("name", "TESTE");
        paramsBuilder.addString("file.input", "sample-data-test.csv");
        return paramsBuilder.toJobParameters();
    }

    @Test
    public void launchJob() throws Exception {
        //testing a job
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(defaultJobParameters());
        //Testing a individual step
        //JobExecution jobExecution = jobLauncherTestUtils.launchStep("step1");
        assertEquals(BatchStatus.COMPLETED, jobExecution.getStatus());
    }
}
