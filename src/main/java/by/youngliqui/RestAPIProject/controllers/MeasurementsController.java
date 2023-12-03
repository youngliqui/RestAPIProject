package by.youngliqui.RestAPIProject.controllers;

import by.youngliqui.RestAPIProject.dto.MeasurementDTO;
import by.youngliqui.RestAPIProject.models.Measurement;
import by.youngliqui.RestAPIProject.services.MeasurementsService;
import by.youngliqui.RestAPIProject.utils.*;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/measurements")
public class MeasurementsController {
    private final MeasurementsService measurementsService;
    private final ModelMapper modelMapper;
    private final MeasurementValidation measurementValidation;

    @Autowired
    public MeasurementsController(MeasurementsService measurementsService, ModelMapper modelMapper, MeasurementValidation measurementValidation) {
        this.measurementsService = measurementsService;
        this.modelMapper = modelMapper;
        this.measurementValidation = measurementValidation;
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> add(@RequestBody @Valid MeasurementDTO measurementDTO,
                                          BindingResult bindingResult) {

        Measurement measurement = convertToMeasurement(measurementDTO);
        measurementValidation.validate(measurement, bindingResult);

        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();

            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMsg.append(error.getField())
                        .append(" - ")
                        .append(error.getDefaultMessage())
                        .append(";");
            }

            throw new MeasurementNotCreatedException(errorMsg.toString());
        }

        measurementsService.save(measurement);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping
    public List<MeasurementDTO> getMeasurements() {
        return measurementsService.findAll().stream()
                .map(this::convertToMeasurementDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/rainyDaysCount")
    public int getRainyDaysCount() {
        return measurementsService.getRainyCount();
    }

    @ExceptionHandler
    private ResponseEntity<MeasurementErrorResponse> handleException(MeasurementNotCreatedException e) {
        MeasurementErrorResponse response = new MeasurementErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private Measurement convertToMeasurement(MeasurementDTO measurementDTO) {
        return modelMapper.map(measurementDTO, Measurement.class);
    }

    private MeasurementDTO convertToMeasurementDTO(Measurement measurement) {
        return modelMapper.map(measurement, MeasurementDTO.class);
    }
}
