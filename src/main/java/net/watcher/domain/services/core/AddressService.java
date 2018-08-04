package net.watcher.domain.services.core;

import net.watcher.domain.entities.Address;
import net.watcher.domain.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AddressService {
    @Autowired
    private AddressRepository addressRepository;

    @Transactional(readOnly = true)
    public Address findOrCreateAddress(String country) {
        return addressRepository.findAddressByCountry(country);
    }

}
