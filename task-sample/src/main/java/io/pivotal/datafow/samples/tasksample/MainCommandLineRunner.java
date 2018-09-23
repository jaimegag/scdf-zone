package io.pivotal.datafow.samples.tasksample;

import io.pivotal.datafow.samples.tasksample.domain.Species;
import io.pivotal.datafow.samples.tasksample.repositories.SpeciesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class MainCommandLineRunner implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(MainCommandLineRunner.class);

    private SpeciesRepository repository;

    private List<DataSource> dataSources;

    @Autowired
    RestTemplateBuilder restTemplateBuilder;

    @Autowired
    public MainCommandLineRunner(SpeciesRepository speciesRepository, List<DataSource> dataSources) {
        this.repository = speciesRepository;
        this.dataSources = dataSources;
    }

    @Override
    public void run (String... strings) throws Exception {
        log.info("Running task");
        log.info("Found " + dataSources.size() + " data sources.");
        RestTemplate restTemplate = restTemplateBuilder.build();
        Iterable<Species> speciesIterable = repository.findAll();
        log.info("Pulled species: " + speciesIterable.iterator().hasNext());
        for (Species species : speciesIterable) {
            log.info("Iterating in species: " + species.toString());
            HashMap<String, String> speciesMap = new HashMap<String, String>();
            speciesMap.put("id", species.getId().toString());
            speciesMap.put("county", species.getCounty());
            speciesMap.put("category", species.getCategory());
            speciesMap.put("taxonomy_g", species.getTaxonomy_g());
            speciesMap.put("taxonomy_sg", species.getTaxonomy_sg());
            speciesMap.put("sci_name", species.getSci_name());
            speciesMap.put("common_name", species.getCommon_name());
            HttpEntity<Map<String, String>> httpEntity = new HttpEntity<Map<String, String>>(speciesMap);
            String response = restTemplate.postForObject("https://species-app.apps.pcfgcp.jagapps.co/species", httpEntity, String.class);
            //String response = restTemplate.postForObject("http://localhost:8888/species", httpEntity, String.class);
            log.info("Response [" + response + "]");
        }
    }
}
