package com.mikepaskual.delivery.shared.init;

import com.mikepaskual.delivery.address.dto.CreateAddressRequest;
import com.mikepaskual.delivery.address.model.Address;
import com.mikepaskual.delivery.address.service.AddressService;
import com.mikepaskual.delivery.customer.dto.CreateCustomerRequest;
import com.mikepaskual.delivery.driver.dto.UpdateDriverRequest;
import com.mikepaskual.delivery.driver.model.Driver;
import com.mikepaskual.delivery.driver.service.DriverService;
import com.mikepaskual.delivery.truck.dto.CreateTruckRequest;
import com.mikepaskual.delivery.truck.model.FuelType;
import com.mikepaskual.delivery.truck.model.Truck;
import com.mikepaskual.delivery.truck.service.TruckService;
import com.mikepaskual.delivery.user.model.Gender;
import com.mikepaskual.delivery.customer.service.CustomerService;
import com.mikepaskual.delivery.user.dto.CreateUserRequest;
import com.mikepaskual.delivery.user.dto.UpdateUserRequest;
import com.mikepaskual.delivery.user.model.User;
import com.mikepaskual.delivery.user.service.UserService;
import jakarta.annotation.PostConstruct;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

@Component
public class DataSeed {

    @Autowired
    private final AddressService addressService;
    @Autowired
    private final CustomerService customerService;
    @Autowired
    private final DriverService driverService;
    @Autowired
    private final TruckService truckService;
    @Autowired
    private final UserService userService;

    public DataSeed(AddressService addressService, CustomerService customerService, DriverService driverService,
                    TruckService truckService, UserService userService) {
        this.addressService = addressService;
        this.customerService = customerService;
        this.driverService = driverService;
        this.truckService = truckService;
        this.userService = userService;
    }

    @PostConstruct
    public void init() {
        loadUsers();
        // loadAddressesAndCustomersFromCsv();
    }

    @SuppressWarnings("unchecked")
    private void loadUsers() {
        User admin = userService.registerUser(CreateUserRequest.builder()
                .setCreatedAt(LocalDateTime.now())
                .setEmail("admin@delivery.edu")
                .setPassword("P@ssw0rd")
                .setRoles(Set.of("ADMIN"))
                .setUsername("admin")
                .setVerifyPassword("P@ssw0rd").build());
        userService.updateUser(admin.getId(), UpdateUserRequest.builder()
                .setBirthday(LocalDate.of(1987, Month.APRIL, 17))
                .setGender(Gender.MALE.name())
                .setFirstName("MIGUEL ANGEL")
                .setPhone("666666666")
                .setLastName("PASCUAL GOLDARAZ").build());

        userService.registerUser(CreateUserRequest.builder()
                .setCreatedAt(LocalDateTime.now())
                .setEmail("customer@delivery.edu")
                .setPassword("P@ssw0rd")
                .setRoles(Set.of("CUSTOMER"))
                .setUsername("customer")
                .setVerifyPassword("P@ssw0rd").build());

        User userDriver = userService.registerUser(CreateUserRequest.builder()
                .setCreatedAt(LocalDateTime.now())
                .setEmail("driver@delivery.edu")
                .setPassword("P@ssw0rd")
                .setRoles(Set.of("DRIVER"))
                .setUsername("driver")
                .setVerifyPassword("P@ssw0rd").build());

        userService.registerUser(CreateUserRequest.builder()
                .setCreatedAt(LocalDateTime.now())
                .setEmail("customerdriver@delivery.edu")
                .setPassword("P@ssw0rd")
                .setRoles(Set.of("DRIVER", "CUSTOMER"))
                .setUsername("customerdriver")
                .setVerifyPassword("P@ssw0rd").build());
    }

    private void loadAddressesAndCustomersFromCsv() {
        Random random = new Random();

        // reading addresses from csv file
        List<CreateAddressRequest> addressesRequest = new ArrayList<>();
        CSVParser addressCsvParser = null;
        try {
            addressCsvParser = CSVFormat.DEFAULT
                    .withFirstRecordAsHeader()
                    .parse(new InputStreamReader(new ClassPathResource("seed/addresses.csv").getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for (CSVRecord addressRecord : addressCsvParser) {
            addressesRequest.add(CreateAddressRequest.builder()
                    .setCity(addressRecord.get("city"))
                    .setCountry(addressRecord.get("country"))
                    .setHidden(false)
                    .setState(addressRecord.get("state"))
                    .setStreet(addressRecord.get("street_address"))
                    .setPostalCode(addressRecord.get("postal_code"))
                    .build());
        }

        // reading customers from csv file
        List<CSVRecord> customerRecords;
        try (CSVParser customerParser = CSVFormat.DEFAULT
                .withFirstRecordAsHeader()
                .parse(new InputStreamReader(new ClassPathResource("seed/customers.csv").getInputStream()))) {
            customerRecords = customerParser.getRecords();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        int indexAddress = 0;
        List<Address> addresses;
        for (int i = 0; i < customerRecords.size(); i++) {
            CreateCustomerRequest customerRequest = CreateCustomerRequest.builder()
                    .setFirstName(customerRecords.get(i).get("first_name"))
                    .setLastName(customerRecords.get(i).get("last_name"))
                    .setEmail(customerRecords.get(i).get("email"))
                    .setPhone(customerRecords.get(i).get("phone_number"))
                    .setGender(Gender.valueOf(customerRecords.get(i).get("gender").toUpperCase()))
                    .setBirthday(LocalDate.parse(customerRecords.get(i).get("date_of_birth")))
                    .setCreatedAt(LocalDateTime.of(LocalDate.parse(customerRecords.get(i).get("join_date")),
                            LocalTime.of(random.nextInt(24), random.nextInt(60), random.nextInt(60))))
                    .build();

            int numberOfAddresses = random.nextInt(4);
            addresses = new ArrayList<>(numberOfAddresses);
            boolean markAsMain = random.nextBoolean();

            for (int j = 0; j < numberOfAddresses; j++) {
                CreateAddressRequest addressRequest = addressesRequest.get(indexAddress);
                indexAddress++;
                addressRequest.setCreatedAt(customerRequest.getCreatedAt());
                if (markAsMain && random.nextInt(4) == j) {
                    addressRequest.setMain(markAsMain);
                }
                addresses.add(addressService.registerAddress(addressRequest));
            }

            customerRequest.setAddresses(addresses);
            customerService.registerCustomer(customerRequest);
        }
    }
}
