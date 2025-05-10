package com.mikepaskual.delivery.customer.service;

import com.mikepaskual.delivery.customer.dto.PackItem;
import com.mikepaskual.delivery.customer.dto.PackResume;
import com.mikepaskual.delivery.customer.exception.PackNotFoundException;
import com.mikepaskual.delivery.customer.model.Pack;
import com.mikepaskual.delivery.customer.model.PackRepository;
import com.mikepaskual.delivery.customer.dto.CreatePackRequest;
import com.mikepaskual.delivery.user.model.User;
import com.mikepaskual.delivery.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PackService {

    @Autowired
    private final CustomerService customerService;
    @Autowired
    private final PackRepository packRepository;
    @Autowired
    private final UserService userService;

    public PackService(CustomerService customerService, PackRepository packRepository, UserService userService) {
        this.customerService = customerService;
        this.packRepository = packRepository;
        this.userService = userService;
    }

    public void registerPack(Long customerId, CreatePackRequest request) {
        packRepository.save(Pack.builder()
                .setCreatedAt(LocalDateTime.now())
                .setDescription(request.getDescription())
                .setReceiver(customerService.getCustomerOrThrow(request.getReceiver()))
                .setSender(customerService.getCustomerOrThrow(customerId))
                .setTitle(request.getTitle())
                .setWeight(request.getWeight()).build());
    }

    public List<PackItem> getPacksBySender(Long customerId) {
        return packRepository.findBySenderId(customerId).stream()
                .map(pack -> {
                    User receiver = userService.getUserOrThrow(pack.getReceiver().getId());
                    return PackItem.builder()
                            .setCreatedAt(pack.getCreatedAt().toLocalDate())
                            .setDiscarded(pack.isDiscarded())
                            .setId(pack.getId())
                            .setReceiver(receiver.getFirstName() + " " + receiver.getLastName())
                            .setTitle(pack.getTitle()).build();
                })
                .collect(Collectors.toList());
    }

    public PackResume getPackResume(Long packId) {
        Pack pack = getPackOrThrow(packId);
        User user = userService.getUserOrThrow(pack.getReceiver().getId());
        return PackResume.builder()
                .setCreatedAt(pack.getCreatedAt())
                .setDescription(pack.getDescription())
                .setDiscarded(pack.isDiscarded())
                .setId(pack.getId())
                .setReceiver(user.getFirstName() + " " + user.getLastName())
                .setTitle(pack.getTitle())
                .setWeight(pack.getWeight()).build();
    }

    public Pack getPackOrThrow(Long packId) {
        return packRepository.findById(packId)
                .orElseThrow(() -> new PackNotFoundException(packId));
    }

    public void discardPack(Long packId) {
        Pack pack = getPackOrThrow(packId);
        pack.setDiscarded(true);
        packRepository.save(pack);
    }

}
