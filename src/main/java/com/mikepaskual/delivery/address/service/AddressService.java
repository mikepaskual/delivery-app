package com.mikepaskual.delivery.address.service;

import com.mikepaskual.delivery.address.dto.CreateAddressRequest;
import com.mikepaskual.delivery.address.exception.AddressNotFoundException;
import com.mikepaskual.delivery.address.model.Address;
import com.mikepaskual.delivery.address.model.AddressRepository;
import com.mikepaskual.delivery.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AddressService {

    @Autowired
    private final AddressRepository addressRepository;
    @Autowired
    private final UserService userService;

    public AddressService(AddressRepository addressRepository, UserService userService) {
        this.addressRepository = addressRepository;
        this.userService = userService;
    }

    public Address registerAddress(Long userId, CreateAddressRequest request) {
        return addressRepository.save(Address.builder()
                .setCity(request.getCity())
                .setCountry(request.getCountry())
                .setCreatedAt(LocalDateTime.now())
                .setState(request.getState())
                .setStreet(request.getStreet())
                .setPostalCode(request.getPostalCode())
                .setUser(userService.getUserOrThrow(userId)).build());
    }

    public Address getAddressOrThrow(Long addressId) {
        return addressRepository.findById(addressId)
                .orElseThrow(() -> new AddressNotFoundException(addressId));
    }

    public List<Address> getAddressesByUser(Long userId) {
        return addressRepository.findByUser(userService.getUserOrThrow(userId));
    }

    public void update(Long addressId, Long userId) {
        Address target = getAddressOrThrow(addressId);
        if (!target.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("Address does not belong to user");
        }
        if (target.isMain()) {
            target.setMain(false);
            addressRepository.save(target);
        } else {
            List<Address> addresses = addressRepository.findByUserAndDeletedFalse(userService.getUserOrThrow(userId));
            for (Address address : addresses) {
                address.setMain(address.getId().equals(addressId));
            }
            addressRepository.saveAll(addresses);
        }
    }

}
