package mk.finki.db.hotel_management.service;

import mk.finki.db.hotel_management.model.Offer;
import mk.finki.db.hotel_management.model.Service;

import java.util.List;


public interface ServiceService {
    List<Service> findAllServices();
}
