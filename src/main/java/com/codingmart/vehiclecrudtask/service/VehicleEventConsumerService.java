package com.codingmart.vehiclecrudtask.service;

import com.codingmart.vehiclecrudtask.entity.Vehicle;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class VehicleEventConsumerService {

    private final ObjectMapper objectMapper;

    @SneakyThrows
    @KafkaListener(topics = "vehicle-create-events", groupId = "vehicle-events")
    public void listenToVehicleCreateEvents(String eventMessage) {
        Vehicle vehicle = objectMapper.readValue(eventMessage, Vehicle.class);
        log.info("Received vehicle create event: {}", vehicle);
        System.out.println("Received vehicle create event: " + vehicle);
    }

    @SneakyThrows
    @KafkaListener(topics = "vehicle-update-events", groupId = "vehicle-events")
    public void listenToVehicleUpdateEvents(String eventMessage) {
        Vehicle vehicle = objectMapper.readValue(eventMessage, Vehicle.class);
        log.info("Received vehicle update event: {}", vehicle);
        System.out.println("Received vehicle update event: " + vehicle);
    }

    @SneakyThrows
    @KafkaListener(topics = "vehicle-delete-events", groupId = "vehicle-events")
    public void listenToVehicleDeleteEvents(String eventMessage) {
        Long vehicleId = objectMapper.readValue(eventMessage, Long.class);
        log.info("Received vehicle delete event for ID: {}", vehicleId);
        System.out.println("Received vehicle delete event for ID: " + vehicleId);
    }
}

