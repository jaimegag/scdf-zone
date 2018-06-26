package io.pivotal.dataflow.samples.sourcesample;

import io.pivotal.dataflow.samples.sourcesample.repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
@EnableBinding(Source.class)
public class SourceSampleApplication implements CommandLineRunner {

	@Autowired
	MessageRepository repo;

	public static void main(String[] args) {
		SpringApplication.run(SourceSampleApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		//do something

	}
}
