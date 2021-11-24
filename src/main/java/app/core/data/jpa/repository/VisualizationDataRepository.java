package app.core.data.jpa.repository;

import app.core.data.jpa.persistance.VisualizationData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VisualizationDataRepository extends CrudRepository<VisualizationData, Long> {

}
