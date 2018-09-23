package io.pivotal.datafow.samples.tasksample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.task.configuration.EnableTask;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTask
@EnableTransactionManagement
@EnableJpaRepositories
@EntityScan("io.pivotal.datafow.samples.tasksample.domain")
public class TaskSampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskSampleApplication.class, args);
	}

}
