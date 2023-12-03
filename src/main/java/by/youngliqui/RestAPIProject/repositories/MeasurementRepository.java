package by.youngliqui.RestAPIProject.repositories;

import by.youngliqui.RestAPIProject.models.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeasurementRepository extends JpaRepository<Measurement, Integer> {
    int countMeasurementsByRaining(Boolean raining);
}
