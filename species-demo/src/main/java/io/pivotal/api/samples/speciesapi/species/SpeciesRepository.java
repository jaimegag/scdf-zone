package io.pivotal.api.samples.speciesapi.species;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpeciesRepository extends JpaRepository<Species, Long> {

    // TODO add some custom queries...

}
