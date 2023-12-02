package by.youngliqui.RestAPIProject.repositories;

import by.youngliqui.RestAPIProject.models.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SensorRepository extends JpaRepository<Sensor, Integer> {
    Optional<Sensor> findOneByName(String name);
}
