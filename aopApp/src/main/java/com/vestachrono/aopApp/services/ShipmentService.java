package com.vestachrono.aopApp.services;

public interface ShipmentService {
    String orderPackage(Long orderId);

    String trackPackage(Long orderId);
}