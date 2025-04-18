package com.mikepaskual.delivery.shared.init;

import com.mikepaskual.delivery.address.dto.CreateAddressRequest;
import com.mikepaskual.delivery.address.model.Address;
import com.mikepaskual.delivery.address.service.AddressService;
import com.mikepaskual.delivery.customer.dto.CreateCustomerRequest;
import com.mikepaskual.delivery.customer.model.Gender;
import com.mikepaskual.delivery.customer.service.CustomerService;
import com.mikepaskual.delivery.user.dto.CreateUserRequest;
import com.mikepaskual.delivery.user.model.User;
import com.mikepaskual.delivery.user.model.UserRole;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Component
public class DataSeed {

    @Autowired
    private UserService userService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private AddressService addressService;

    public DataSeed(UserService userService, CustomerService customerService, AddressService addressService) {
        this.addressService = addressService;
        this.customerService = customerService;
        this.userService = userService;
    }

    @PostConstruct
    public void init() {
        CreateUserRequest adminRequest = CreateUserRequest.builder()
                .setEmail("admin@delivery.edu")
                .setPassword("admin")
                .setUsername("admin")
                .setVerifyPassword("admin")
                .build();
        User admin = userService.registerUser(adminRequest);
        userService.changeRole(admin, UserRole.ADMIN);

        CreateUserRequest userRequest = CreateUserRequest.builder()
                .setEmail("user@delivery.edu")
                .setPassword("user")
                .setUsername("user")
                .setVerifyPassword("user")
                .build();
        User user = userService.registerUser(userRequest);

        loadAddressesAndCustomersFromCsv();
    }

    public void loadAddressesAndCustomersFromCsv() {
        Random random = new Random();
        List<Address> addresses = new ArrayList<>();
        try {
            CSVParser addressParser = CSVFormat.DEFAULT
                    .withFirstRecordAsHeader()
                    .parse(new InputStreamReader(new ClassPathResource("seed/addresses.csv").getInputStream()));
            for (CSVRecord addressRecord : addressParser) {
                CreateAddressRequest addressRequest = CreateAddressRequest.builder()
                        .setCity(addressRecord.get("city"))
                        .setCountry(addressRecord.get("country"))
                        .setState(addressRecord.get("state"))
                        .setStreetNumber(addressRecord.get("street_number"))
                        .setStreetName(addressRecord.get("street_address"))
                        .setStreetSuffix(addressRecord.get("street_suffix"))
                        .setPostalCode(addressRecord.get("postal_code"))
                        .setCreatedAt(LocalDateTime.now())
                        .build();
                addresses.add(addressService.registerAddress(addressRequest));
            }

            List<CSVRecord> customerRecords;
            try (CSVParser customerParser = CSVFormat.DEFAULT
                    .withFirstRecordAsHeader()
                    .parse(new InputStreamReader(new ClassPathResource("seed/customers.csv").getInputStream()))) {
                customerRecords = customerParser.getRecords();
            }

            int limit = Math.min(customerRecords.size(), addresses.size());
            Collections.shuffle(addresses);

            for (int i = 0; i < limit; i++) {
                CSVRecord record = customerRecords.get(i);
                Address assignedAddress = addresses.get(i);

                int numberOfAddresses = random.nextInt(4);
                List<Address> customerAddresses = new ArrayList<>();

                for (int j = 0; j < numberOfAddresses; j++) {
                    if (i + j < addresses.size()) {
                        customerAddresses.add(addresses.get(i + j));
                    }
                }

                CreateCustomerRequest request = CreateCustomerRequest.builder()
                        .setAddresses(customerAddresses)
                        .setFirstName(record.get("first_name"))
                        .setLastName(record.get("last_name"))
                        .setEmail(record.get("email"))
                        .setPhone(record.get("phone_number"))
                        .setGender(Gender.valueOf(record.get("gender").toUpperCase()))
                        .setBirthday(LocalDate.parse(record.get("date_of_birth")))
                        .setCreatedAt(LocalDateTime.of(LocalDate.parse(record.get("join_date")), LocalTime.now()))
                        .build();
                customerService.registerCustomer(request);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
