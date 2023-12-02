package by.youngliqui.RestAPIProject.services;

import by.youngliqui.RestAPIProject.models.Measurement;
import by.youngliqui.RestAPIProject.models.Sensor;
import by.youngliqui.RestAPIProject.repositories.MeasurementRepository;
import by.youngliqui.RestAPIProject.repositories.SensorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class MeasurementsService {
    private final MeasurementRepository measurementRepository;
    private final SensorRepository sensorRepository;

    public MeasurementsService(MeasurementRepository measurementRepository, SensorRepository sensorRepository) {
        this.measurementRepository = measurementRepository;
        this.sensorRepository = sensorRepository;
    }

    public Optional<Sensor> findSensorByName(String name) {
        return sensorRepository.findOneByName(name);
    }

    @Transactional
    public void save(Measurement measurement) {
        enrichMeasurement(measurement);
        measurementRepository.save(measurement);
    }

    private void enrichMeasurement(Measurement measurement) {
        Optional<Sensor> sensor = findSensorByName(measurement.getSensor().getName());

        measurement.setSensor(sensor.get());
        measurement.setCreatedAt(LocalDateTime.now());
        measurement.setUpdatedAt(LocalDateTime.now());
        measurement.setCreatedWho("ADMIN");
    }
}
