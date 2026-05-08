package mk.finki.db.hotel_management.service.impl;

import mk.finki.db.hotel_management.model.Service;
import mk.finki.db.hotel_management.repository.ServiceRepository;
import mk.finki.db.hotel_management.service.ServiceService;



import java.util.List;

@org.springframework.stereotype.Service
public class ServiceServiceImpl implements ServiceService {

    private final ServiceRepository serviceRepository;

    public ServiceServiceImpl(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    @Override
    public List<Service> findAllServices() {
        return serviceRepository.findAll();
    }
}
