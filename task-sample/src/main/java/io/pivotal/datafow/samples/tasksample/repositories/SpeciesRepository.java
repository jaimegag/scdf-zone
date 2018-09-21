package io.pivotal.datafow.samples.tasksample.repositories;

import io.pivotal.datafow.samples.tasksample.domain.Species;
import org.springframework.data.repository.CrudRepository;

public interface SpeciesRepository extends CrudRepository<Species, Integer> {
}
