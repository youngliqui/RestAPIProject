package by.youngliqui.RestAPIProject.utils;

import by.youngliqui.RestAPIProject.models.Measurement;
import by.youngliqui.RestAPIProject.models.Sensor;
import by.youngliqui.RestAPIProject.services.MeasurementsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;


@Component
public class MeasurementValidation implements Validator {

    private final MeasurementsService measurementsService;

    @Autowired
    public MeasurementValidation(MeasurementsService measurementsService) {
        this.measurementsService = measurementsService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Measurement.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Measurement measurement = (Measurement) target;

        Optional<Sensor> showSensorByName = measurementsService.findSensorByName(measurement.getSensor().getName());

        if (showSensorByName.isEmpty()) {
            errors.rejectValue("sensor", "", "Sensor with this name was not found");
        }
    }
}
