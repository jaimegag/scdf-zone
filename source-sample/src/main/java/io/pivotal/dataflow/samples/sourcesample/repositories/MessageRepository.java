package io.pivotal.dataflow.samples.sourcesample.repositories;

import io.pivotal.dataflow.samples.sourcesample.domain.Message;
import org.springframework.data.repository.CrudRepository;

public interface MessageRepository extends CrudRepository<Message, Long> {
}
