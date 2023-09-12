package com.codingmart.vehiclecrudtask.service;

import com.codingmart.vehiclecrudtask.entity.Vehicle;

public interface VehicleEventProducerService {

    void produceVehicleCreatedEvent(Vehicle vehicle);

    void produceVehicleUpdatedEvent(Vehicle vehicle);

    void produceVehicleDeletedEvent(Long vehicleId);

}
