package com.codingmart.vehiclecrudtask.service;

import com.codingmart.vehiclecrudtask.entity.Vehicle;
import com.codingmart.vehiclecrudtask.exception.NotFoundException;
import com.codingmart.vehiclecrudtask.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;
    private final VehicleEventProducerService vehicleEventProducerService;


    @Override
    public Vehicle createVehicle(Vehicle vehicle) {
        // Before saving the vehicle, log that the create operation is being performed
        log.info("Creating a new vehicle: {}", vehicle);

        Vehicle createdVehicle = vehicleRepository.save(vehicle);

        // After successfully creating the vehicle, log the result
        log.info("Vehicle created successfully: {}", createdVehicle);

        vehicleEventProducerService.produceVehicleCreatedEvent(vehicle);


        return createdVehicle;
    }

    @Override
    public Vehicle getVehicleById(Long id) {
        // Log that the get by ID operation is being performed
        log.info("Fetching vehicle by ID: {}", id);

        if (vehicleRepository.findById(id).isEmpty()) {
            // Log a not-found message
            log.warn("Vehicle with ID {} not found", id);
            throw new NotFoundException("Vehicle Id not Found");
        }

        Vehicle vehicle = vehicleRepository.findById(id).get();

        // Log the retrieved vehicle
        log.info("Found vehicle: {}", vehicle);

        return vehicle;
    }

    @Override
    public List<Vehicle> getAllVehicles() {
        // Log that the get all vehicles operation is being performed
        log.info("Fetching all vehicles");

        List<Vehicle> vehicles = vehicleRepository.findAll();

        // Log the list of retrieved vehicles
        log.info("Found {} vehicles", vehicles.size());

        return vehicles;
    }

    @Override
    public Vehicle updateVehicle(Vehicle vehicle, Long id) {
        // Log that the update operation is being performed
        log.info("Updating vehicle with ID {}: {}", id, vehicle);

        if (vehicleRepository.findById(id).isEmpty()) {
            // Log a not-found message
            log.warn("Vehicle with ID {} not found", id);
            throw new NotFoundException("Vehicle Id not Found");
        }

        vehicle.setId(id);
        Vehicle updatedVehicle = vehicleRepository.save(vehicle);

        // Log the updated vehicle
        log.info("Vehicle updated successfully: {}", updatedVehicle);

        vehicleEventProducerService.produceVehicleUpdatedEvent(vehicle);

        return updatedVehicle;
    }

    @Override
    public String deleteVehicle(Long id) {
        // Log that the delete operation is being performed
        log.info("Deleting vehicle with ID: {}", id);

        if (vehicleRepository.findById(id).isEmpty()) {
            // Log a not-found message
            log.warn("Vehicle with ID {} not found", id);
            throw new NotFoundException("Vehicle Id not Found");
        }

        vehicleRepository.deleteById(id);

        // Log a success message
        log.info("Vehicle with ID {} deleted successfully", id);

        vehicleEventProducerService.produceVehicleDeletedEvent(id);

        return "Vehicle deleted Successfully";
    }
}