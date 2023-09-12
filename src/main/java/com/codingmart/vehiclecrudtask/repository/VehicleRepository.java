package com.codingmart.vehiclecrudtask.repository;

import com.codingmart.vehiclecrudtask.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle,Long> {
}
