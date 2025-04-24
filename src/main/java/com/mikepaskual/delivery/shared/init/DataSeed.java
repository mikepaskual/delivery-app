package com.mikepaskual.delivery.shared.init;

import com.mikepaskual.delivery.address.dto.CreateAddressRequest;
import com.mikepaskual.delivery.address.model.Address;
import com.mikepaskual.delivery.address.service.AddressService;
import com.mikepaskual.delivery.customer.dto.CreateCustomerRequest;
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

@Component
public class DataSeed {

    @Autowired
    private final AddressService addressService;
    @Autowired
    private final CustomerService customerService;
    @Autowired
    private final UserService userService;

    public DataSeed(AddressService addressService, CustomerService customerService, UserService userService) {
        this.addressService = addressService;
        this.customerService = customerService;
        this.userService = userService;
    }

    @PostConstruct
    public void init() {
        loadUsers();
        // loadAddressesAndCustomersFromCsv();
    }

    private void loadUsers() {
        CreateUserRequest createUserRequest1 = CreateUserRequest.builder()
                .setCreatedAt(LocalDateTime.now())
                .setEmail("user1@delivery.edu")
                .setPassword("123456")
                .setRole("DRIVER")
                .setUsername("user1")
                .setVerifyPassword("123456").build();
        User user1 = userService.registerUser(createUserRequest1);

        UpdateUserRequest updateUserRequest1 = UpdateUserRequest.builder()
                .setBirthday(LocalDate.of(1987, Month.APRIL, 17))
                .setGender(Gender.MALE.name())
                .setFirstName("MIGUEL ANGEL")
                .setPhone("666666666")
                .setLastName("PASCUAL GOLDARAZ").build();
        userService.updateUser(user1.getId(), updateUserRequest1);

        CreateUserRequest createUserRequest2 = CreateUserRequest.builder()
                .setCreatedAt(LocalDateTime.now())
                .setEmail("user2@delivery.edu")
                .setPassword("123456")
                .setRole("DRIVER")
                .setUsername("user2")
                .setVerifyPassword("123456").build();
        userService.registerUser(createUserRequest2);
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
