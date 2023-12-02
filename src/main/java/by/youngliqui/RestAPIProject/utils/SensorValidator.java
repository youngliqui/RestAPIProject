package by.youngliqui.RestAPIProject.utils;

import by.youngliqui.RestAPIProject.models.Sensor;
import by.youngliqui.RestAPIProject.services.SensorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
public class SensorValidator implements Validator {

    private final SensorsService sensorsService;

    @Autowired
    public SensorValidator(SensorsService sensorsService) {
        this.sensorsService = sensorsService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Sensor.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Sensor sensor = (Sensor) target;

        Optional<Sensor> showSensorByName = sensorsService.findOneByName(sensor.getName());

        if (showSensorByName.isPresent()) {
            errors.rejectValue("name", "", "This name is already taken");
        }
    }
}
