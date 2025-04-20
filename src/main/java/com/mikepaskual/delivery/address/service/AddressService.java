package com.mikepaskual.delivery.address.service;

import com.mikepaskual.delivery.address.dto.CreateAddressRequest;
import com.mikepaskual.delivery.address.exception.AddressNotFoundException;
import com.mikepaskual.delivery.address.model.Address;
import com.mikepaskual.delivery.address.model.AddressRepository;
import com.mikepaskual.delivery.customer.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AddressService {

    @Autowired
    private final AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public Address registerAddress(CreateAddressRequest request) {
        Address address = Address.builder()
                .setCity(request.getCity())
                .setCountry(request.getCountry())
                .setCreatedAt(request.getCreatedAt())
                .setCustomer(request.getCustomer())
                .setHidden(request.isHidden())
                .setMain(request.isMain())
                .setState(request.getState())
                .setStreet(request.getStreet())
                .setPostalCode(request.getPostalCode())
                .build();
        return addressRepository.save(address);
    }

    public Address findById(Long id) {
        return addressRepository.findById(id).orElseThrow(() -> new AddressNotFoundException(id));
    }

    public Address markAsHidden(Long id) {
        Address address = findById(id);
        address.setMain(false);
        address.setHidden(true);
        return addressRepository.save(address);
    }

    public Address update(Address address) {
        return addressRepository.save(address);
    }

    public List<Address> findNotHiddenByCustomer(Customer customer) {
        List<Address> addresses = new ArrayList<>();
        addressRepository.findByCustomer(customer).forEach(a -> {
            if (!a.isHidden()) {
                addresses.add(a);
            }
        });
        return addresses;
    }

    public Address markAsMain(Long id) {
        Address address = findById(id);
        List<Address> addresses = findNotHiddenByCustomer(address.getCustomer());
        addresses.forEach(a -> {
            if (a.getId().equals(id)) {
                a.setMain(true);
            } else {
                a.setMain(false);
            }
        });
        addressRepository.saveAll(addresses);
        return address;
    }

    public Address unmarkAsMain(Long id) {
        Address address = findById(id);
        address.setMain(false);
        addressRepository.save(address);
        return address;
    }

}
