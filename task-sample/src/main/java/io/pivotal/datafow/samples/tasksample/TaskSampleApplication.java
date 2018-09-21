package io.pivotal.datafow.samples.tasksample;

import io.pivotal.datafow.samples.tasksample.domain.Species;
import io.pivotal.datafow.samples.tasksample.repositories.SpeciesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.task.configuration.EnableTask;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@EnableJpaRepositories
@EnableTask
public class TaskSampleApplication {

	@Bean
	public CommandLineRunner commandLineRunner() {
		return new JdbctoapiCommandLineRunner();
	}


	public static void main(String[] args) {
		SpringApplication.run(TaskSampleApplication.class, args);
	}

	public static class JdbctoapiCommandLineRunner implements CommandLineRunner {

		@Autowired
		RestTemplateBuilder restTemplateBuilder;

		@Autowired
		SpeciesRepository speciesRepo;

		@Override
		public void run (String... strings) throws Exception {
			System.out.println("Running task");
			RestTemplate restTemplate = restTemplateBuilder.build();
			Iterable<Species> speciesIterable = speciesRepo.findAll();
			System.out.println("Pulled species: " + speciesIterable.iterator().hasNext());
			for (Species species : speciesIterable) {
				System.out.println("Iterating in species: " + species.toString());
				HashMap<String, String> speciesMap = new HashMap<String, String>();
				speciesMap.put("id", species.getId().toString());
				speciesMap.put("county", species.getCounty());
				speciesMap.put("category", species.getCategory());
				speciesMap.put("taxonomy_g", species.getTaxonomy_g());
				speciesMap.put("taxonomy_sg", species.getTaxonomy_sg());
				speciesMap.put("sci_name", species.getSci_name());
				speciesMap.put("common_name", species.getCommon_name());
				HttpEntity<Map<String, String>> httpEntity = new HttpEntity<Map<String, String>>(speciesMap);
				//String response = restTemplate.postForObject("https://species-app.apps.pcfgcp.jagapps.co/species", httpEntity, String.class);
				String response = restTemplate.postForObject("http://localhost:8888/species", httpEntity, String.class);
				System.out.println("Response [" + response + "]");
			}
		}
	}
}
