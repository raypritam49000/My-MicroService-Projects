package com.shipment.service.app.command.api.repository;

import com.shipment.service.app.command.api.data.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShipmentRepository extends JpaRepository<Shipment, String> {
}
