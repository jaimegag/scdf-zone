package io.pivotal.api.samples.speciesapi.species;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SpeciesController {

    private final SpeciesRepository speciesRepo;

    @Autowired
    public SpeciesController(SpeciesRepository speciesController) {
        this.speciesRepo = speciesController;
    }

    @GetMapping("/species")
    public List<Species> getAll() {
        return speciesRepo.findAll();
    }

    //@HystrixCommand
    @PostMapping("/species")
    public Species saveSpecies(@RequestBody Species species) {
        return speciesRepo.save(species);
    }

    //@HystrixCommand
    @GetMapping("/species/{id}")
    public Species getASpecies(@PathVariable Long id) {
        return speciesRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Species not found"));
    }
}
