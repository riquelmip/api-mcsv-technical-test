package com.microservices.microservicegeneral.service.implementation;


import com.microservices.microservicegeneral.model.AddressEntity;
import com.microservices.microservicegeneral.repository.AddressRepository;
import com.microservices.microservicegeneral.service.IAddressService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AddressServiceImpl implements IAddressService {
    private final AddressRepository addressRepository;

    public AddressServiceImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public List<AddressEntity> findByCustomerId(Long customerId) {
        return addressRepository.findByCustomerId(customerId);
    }

    @Override
    public AddressEntity create(AddressEntity address) {
        AddressEntity createdAddress = addressRepository.save(address);

        // Validar si el estado lo trae true, entonces debe poner en false a los demas
        if (createdAddress.isStatus()) {
            List<AddressEntity> otherAddresses = addressRepository.findByCustomerId(createdAddress.getCustomer().getId());
            for (AddressEntity addr : otherAddresses) {
                if (!addr.getId().equals(createdAddress.getId())) {
                    addr.setStatus(false);
                    addressRepository.save(addr);
                }
            }
        }

        return createdAddress;
    }

    @Override
    public AddressEntity update(Long id, AddressEntity address) {
        AddressEntity existing = addressRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Address not found"));
        existing.setStreet(address.getStreet());
        existing.setCity(address.getCity());
        existing.setState(address.getState());
        existing.setPostalCode(address.getPostalCode());
        existing.setCountry(address.getCountry());
        return addressRepository.save(existing);
    }

    @Override
    public void delete(Long id) {
        addressRepository.deleteById(id);
    }

    @Override
    public Optional<AddressEntity> findById(Long id) {
        return addressRepository.findById(id);
    }
}
