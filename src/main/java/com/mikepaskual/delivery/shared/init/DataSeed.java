package com.mikepaskual.delivery.shared.init;

import com.mikepaskual.delivery.address.model.Address;
import com.mikepaskual.delivery.address.model.AddressRepository;
import com.mikepaskual.delivery.customer.model.Customer;
import com.mikepaskual.delivery.customer.model.CustomerRepository;
import com.mikepaskual.delivery.driver.model.Driver;
import com.mikepaskual.delivery.driver.model.DriverRepository;
import com.mikepaskual.delivery.driver.model.DriverStatus;
import com.mikepaskual.delivery.truck.model.*;
import com.mikepaskual.delivery.user.exception.RoleNotFoundException;
import com.mikepaskual.delivery.user.model.*;
import jakarta.annotation.PostConstruct;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class DataSeed {

    @Autowired
    private final AddressRepository addressRepository;
    @Autowired
    private final CustomerRepository customerRepository;
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

    public DataSeed(AddressRepository addressRepository, CustomerRepository customerRepository,
                    DriverRepository driverRepository, PasswordEncoder passwordEncoder,
                    RoleRepository roleRepository, TruckRepository truckRepository,
                    UserRepository userRepository) {
        this.addressRepository = addressRepository;
        this.customerRepository = customerRepository;
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
        loading_customer();
        //loading_driver();

        loading_from_csv();
    }

    private void loading_from_csv() {
        loading_customers_and_drivers();
        loading_addresses();
        loading_trucks();
    }

    private void loading_trucks() {
        List<Truck> trucks = new ArrayList<>();

        try {
            for (CSVRecord record : CSVFormat.DEFAULT
                    .withFirstRecordAsHeader()
                    .parse(new InputStreamReader(new ClassPathResource("seed/trucks.csv").getInputStream()))) {
                trucks.add(Truck.builder()
                        .setCapacity(Integer.parseInt(record.get("weight_capacity")))
                        .setColor(record.get("color"))
                        .setFuelType(FuelType.valueOf(record.get("fuel_type").toUpperCase()))
                        .setHeight(Double.parseDouble(record.get("height")))
                        .setLength(Double.parseDouble(record.get("length")))
                        .setMake(record.get("truck_make"))
                        .setModel(record.get("truck_model"))
                        .setPlate(PlateGenerator.generatePlate())
                        .setPurchaseDate(LocalDate.parse(record.get("purchase_date"), DateTimeFormatter.ofPattern("M/d/yyyy")))
                        .setStatus(StatusTruck.valueOf(record.get("status").toUpperCase()))
                        .setTransmission(Transmission.valueOf(record.get("transmission").toUpperCase()))
                        .setWidth(Double.parseDouble(record.get("width")))
                        .setYear(Integer.parseInt(record.get("truck_year"))).build());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Random random = new Random();
        AtomicInteger trucksCounter = new AtomicInteger(0);
        driverRepository.findAll().forEach(driver -> {
            for (int i = 0; i < random.nextInt(5); i++) {
                Truck truck = trucks.get(trucksCounter.incrementAndGet());
                truck.setCreatedAt(driver.getCreatedAt());
                truck.setDriver(driver);
                truckRepository.save(truck);
            }
        });
    }

    private void loading_addresses() {
        List<Address> addresses = new ArrayList<>();

        try {
            for (CSVRecord record : CSVFormat.DEFAULT
                    .withFirstRecordAsHeader()
                    .parse(new InputStreamReader(new ClassPathResource("seed/addresses.csv").getInputStream()))) {
                addresses.add(Address.builder()
                        .setCity(record.get("city"))
                        .setCountry(record.get("country"))
                        .setPostalCode(record.get("postal_code"))
                        .setState(record.get("state"))
                        .setStreet(record.get("street_address")).build());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Random random = new Random();
        AtomicInteger addressesCounter = new AtomicInteger(0);
        userRepository.findAll().stream()
                .filter(user -> user.getRoles().stream()
                        .noneMatch(role -> UserRole.ADMIN.name().equals(role.getName()))
                ).forEach(user -> {
                    int indexToMain = random.nextInt(4);
                    for (int i = 0; i < random.nextInt(4); i++) {
                        Address address = addresses.get(addressesCounter.incrementAndGet());
                        if (indexToMain == i) {
                            address.setMain(true);
                        }
                        address.setCreatedAt(user.getCreatedAt());
                        address.setUser(user);
                        addressRepository.save(address);
                    }
                });
    }

    private void loading_customers_and_drivers() {
        loading_customers();
        //loading_drivers();
    }

    @SuppressWarnings("unchecked")
    private void loading_customers() {
        Role customerRole = roleRepository.findByName(UserRole.CUSTOMER.name())
                .orElseThrow(() -> new RoleNotFoundException(UserRole.CUSTOMER.name()));

        List<Customer> customers = new ArrayList<>();

        try {
            for (CSVRecord record : CSVFormat.DEFAULT
                    .withFirstRecordAsHeader()
                    .parse(new InputStreamReader(new ClassPathResource("seed/customers.csv").getInputStream()))) {
                User user = User.builder()
                        .setBirthday(LocalDate.parse(record.get("date_of_birth"), DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                        .setCreatedAt(LocalDateTime.of(LocalDate.parse(record.get("join_date"),
                                DateTimeFormatter.ofPattern("yyyy-MM-dd")), LocalTime.now()))
                        .setEmail(record.get("email"))
                        .setFirstName(record.get("first_name"))
                        .setGender(Gender.valueOf(record.get("gender").toUpperCase()))
                        .setLastName(record.get("last_name"))
                        .setPassword(passwordEncoder.encode("P@ssw0rd"))
                        .setPhone(record.get("phone_number"))
                        .setRoles(Set.of(customerRole))
                        .setUsername(record.get("email").substring(0, record.get("email").indexOf('@'))).build();
                Customer customer = Customer.builder()
                        .setCreatedAt(user.getCreatedAt())
                        .setUser(user).build();
                customers.add(customer);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        customerRepository.saveAll(customers);
    }

    @SuppressWarnings("unchecked")
    private void loading_drivers() {
        Role driverRole = roleRepository.findByName(UserRole.DRIVER.name())
                .orElseThrow(() -> new RoleNotFoundException(UserRole.DRIVER.name()));

        List<Driver> drivers = new ArrayList<>();

        try {
            for (CSVRecord record : CSVFormat.DEFAULT
                    .withFirstRecordAsHeader()
                    .parse(new InputStreamReader(new ClassPathResource("seed/drivers.csv").getInputStream()))) {
                User user = User.builder()
                        .setBirthday(LocalDate.parse(record.get("date_of_birth"), DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                        .setCreatedAt(LocalDateTime.of(LocalDate.parse(record.get("join_date"),
                                DateTimeFormatter.ofPattern("yyyy-MM-dd")), LocalTime.now()))
                        .setEmail(record.get("email"))
                        .setFirstName(record.get("first_name"))
                        .setGender(Gender.valueOf(record.get("gender").toUpperCase()))
                        .setLastName(record.get("last_name"))
                        .setPassword(passwordEncoder.encode("P@ssw0rd"))
                        .setPhone(record.get("phone_number"))
                        .setRoles(Set.of(driverRole))
                        .setUsername(record.get("email").substring(0, record.get("email").indexOf('@'))).build();
                LocalTime[] range = LocalTimeRangeGenerator.generateLocalTimeRange();
                Driver driver = Driver.builder()
                        .setAvailableFrom(range[0])
                        .setAvailableTo(range[1])
                        .setCreatedAt(user.getCreatedAt())
                        .setLicenseNumber(LicenseNumberGenerator.generateLicenseNumber())
                        .setStatus(DriverStatus.ACTIVE)
                        .setUser(user).build();
                drivers.add(driver);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        driverRepository.saveAll(drivers);
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
    private void loading_customer() {
        Role customerRole = roleRepository.findByName(UserRole.CUSTOMER.name())
                .orElseThrow(() -> new RoleNotFoundException(UserRole.CUSTOMER.name()));

        User user = User.builder()
                .setCreatedAt(LocalDateTime.now())
                .setEmail("customer@deliveryapp.edu")
                .setPassword(passwordEncoder.encode("P@ssw0rd"))
                .setRoles(Set.of(customerRole))
                .setUsername("customer").build();

        Customer customer = Customer.builder()
                .setUser(user)
                .setCreatedAt(LocalDateTime.now()).build();
        customerRepository.save(customer);
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
