package by.youngliqui.RestAPIProject.dto;

import by.youngliqui.RestAPIProject.models.Sensor;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

public class MeasurementDTO {

    @Min(value = -100, message = "Value should be greater than -100")
    @Max(value = 100)
    private float value;

    private boolean raining;

    private SensorDTO sensor;

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public boolean isRaining() {
        return raining;
    }

    public void setRaining(boolean raining) {
        this.raining = raining;
    }

    public SensorDTO getSensor() {
        return sensor;
    }

    public void setSensor(SensorDTO sensor) {
        this.sensor = sensor;
    }
}
