package com.mikepaskual.delivery.shared.init;

import com.mikepaskual.delivery.address.model.Address;
import com.mikepaskual.delivery.address.model.AddressRepository;
import com.mikepaskual.delivery.driver.model.Driver;
import com.mikepaskual.delivery.driver.model.DriverRepository;
import com.mikepaskual.delivery.truck.model.*;
import com.mikepaskual.delivery.user.exception.RoleNotFoundException;
import com.mikepaskual.delivery.user.model.*;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;

@Component
public class DataSeed {

    @Autowired
    private final AddressRepository addressRepository;
    @Autowired
    private final DriverRepository driverRepository;
    @Autowired
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private final RoleRepository roleRepository;
    @Autowired
    private final TruckRepository truckRepository;
    @Autowired
    private final UserRepository userRepository;

    public DataSeed(AddressRepository addressRepository, DriverRepository driverRepository,
                    PasswordEncoder passwordEncoder, RoleRepository roleRepository, TruckRepository truckRepository,
                    UserRepository userRepository) {
        this.addressRepository = addressRepository;
        this.driverRepository = driverRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.truckRepository = truckRepository;
        this.userRepository = userRepository;
    }

    @PostConstruct
    public void init() {
        loading_roles();
        loading_admin();
        loading_driver();
    }

    private void loading_roles() {
        Arrays.stream(UserRole.values())
                .forEach(role -> roleRepository.save(Role.builder().setName(role.name()).build()));
    }

    @SuppressWarnings("unchecked")
    private void loading_admin() {
        Role adminRole = roleRepository.findByName(UserRole.ADMIN.name())
                .orElseThrow(() -> new RoleNotFoundException(UserRole.ADMIN.name()));
        userRepository.save(User.builder()
                .setBirthday(LocalDate.of(1987, Month.APRIL, 17))
                .setCreatedAt(LocalDateTime.now())
                .setEmail("admin@deliveryapp.edu")
                .setFirstName("ADMIN")
                .setGender(Gender.MALE)
                .setLastName("ADMIN")
                .setPassword(passwordEncoder.encode("P@ssw0rd"))
                .setPhone("000000000")
                .setRoles(Set.of(adminRole))
                .setUsername("admin").build());
    }

    @SuppressWarnings("unchecked")
    private void loading_driver() {
        Role driverRole = roleRepository.findByName(UserRole.DRIVER.name())
                .orElseThrow(() -> new RoleNotFoundException(UserRole.DRIVER.name()));

        Address address1 = addressRepository.save(Address.builder()
                .setCity("Pamplona-Iruña")
                .setCreatedAt(LocalDateTime.now())
                .setCountry("España")
                .setMain(false)
                .setPostalCode("31015")
                .setState("Navarra-Nafarroa")
                .setStreet("73 Aberg Place").build());

        Address address2 = addressRepository.save(Address.builder()
                .setCity("Pamplona-Iruña")
                .setCreatedAt(LocalDateTime.now())
                .setCountry("España")
                .setMain(true)
                .setPostalCode("31015")
                .setState("Navarra-Nafarroa")
                .setStreet("384 East Point").build());

        User user = User.builder()
                .setAddresses(List.of(address1, address2))
                .setCreatedAt(LocalDateTime.now())
                .setEmail("driver@deliveryapp.edu")
                .setPassword(passwordEncoder.encode("P@ssw0rd"))
                .setRoles(Set.of(driverRole))
                .setUsername("driver").build();

        Truck truck = truckRepository.save(Truck.builder()
                .setCapacity(500)
                .setColor("White")
                .setCreatedAt(LocalDateTime.now())
                .setFuelType(FuelType.GASOLINE)
                .setHeight(2.9)
                .setLength(6.3)
                .setMake("Mitsubishi")
                .setModel("Lancer Evolution")
                .setPlate(PlateGenerator.generatePlate())
                .setPurchaseDate(LocalDate.of(2019, Month.JANUARY, 15))
                .setStatus(StatusTruck.ACTIVE)
                .setTransmission(Transmission.AUTOMATIC)
                .setWidth(2.6)
                .setYear(2002).build());

        Driver driver = Driver.builder()
                .setAvailableFrom(LocalTime.of(14, 0))
                .setAvailableTo(LocalTime.of(18, 0))
                .setLicenseNumber(LicenseNumberGenerator.generateLicenseNumber())
                .setTrucks(List.of(truck))
                .setUser(user).build();
        driverRepository.save(driver);
    }

}
