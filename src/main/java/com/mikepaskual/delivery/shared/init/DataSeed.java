package com.mikepaskual.delivery.shared.init;

import com.mikepaskual.delivery.address.dto.CreateAddressRequest;
import com.mikepaskual.delivery.address.model.Address;
import com.mikepaskual.delivery.address.service.AddressService;
import com.mikepaskual.delivery.customer.dto.CreateCustomerRequest;
import com.mikepaskual.delivery.driver.dto.UpdateDriverRequest;
import com.mikepaskual.delivery.driver.model.Driver;
import com.mikepaskual.delivery.driver.service.DriverService;
import com.mikepaskual.delivery.truck.dto.CreateTruckRequest;
import com.mikepaskual.delivery.truck.service.TruckService;
import com.mikepaskual.delivery.user.model.*;
import com.mikepaskual.delivery.customer.service.CustomerService;
import com.mikepaskual.delivery.user.dto.CreateUserRequest;
import com.mikepaskual.delivery.user.dto.UpdateUserRequest;
import com.mikepaskual.delivery.user.service.UserService;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import jakarta.annotation.PostConstruct;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
public class DataSeed {

    @Autowired
    private final AddressService addressService;
    @Autowired
    private final CustomerService customerService;
    @Autowired
    private final DriverService driverService;
    @Autowired
    private final RoleRepository roleRepository;
    @Autowired
    private final TruckService truckService;
    @Autowired
    private final UserService userService;
    private final UserRepository userRepository;
    @Autowired
    private final PasswordEncoder passwordEncoder;

    public DataSeed(AddressService addressService, CustomerService customerService, DriverService driverService,
                    RoleRepository roleRepository, TruckService truckService, UserService userService,
                    UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.addressService = addressService;
        this.customerService = customerService;
        this.driverService = driverService;
        this.roleRepository = roleRepository;
        this.truckService = truckService;
        this.userService = userService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void init() {
        loading_roles();
        loading_admin();
        //loadUsers();
        //loading(loading_users_from_csv(), loading_user_details_from_csv(), loading_trucks_from_csv());
        // loadAddressesAndCustomersFromCsv();
    }

    private void loading_roles() {
        Arrays.stream(UserRole.values())
                .forEach(role -> roleRepository.save(Role.builder().setName(role.name()).build()));
    }

    private void loading_admin() {
        Role adminRole = roleRepository.findByName(UserRole.ADMIN.name())
                .orElseThrow(() -> new IllegalStateException("ADMIN role not found"));
        userRepository.save(User.builder()
                .setCreatedAt(LocalDateTime.now())
                .setEmail("admin@deliveryapp.edu")
                .setPassword(passwordEncoder.encode("P@ssw0rd"))
                .setRoles(Set.of(adminRole))
                .setUsername("admin").build());
    }

    private void loading(List<CreateUserRequest> pUsers, List<UpdateUserRequest> pUserDetails, List<CreateTruckRequest> pTrucks) {
        int userCounter = 0;
        int truckCounter = 0;
        for (CreateUserRequest userRequest : pUsers) {
            if (userCounter == 100) {
                break;
            }
            User user = userService.updateUser(userService.registerUser(userRequest).getId(), pUserDetails.get(userCounter++));
            for (int i = 0; i < new Random().nextInt(4); i++) {
                Driver driver = driverService.findById(user.getId());
                LocalTime[] available = available();
                driverService.update(user.getId(), UpdateDriverRequest.builder()
                        .setAvailableFrom(available[0])
                        .setAvailableTo(available[1])
                        .setLicenseNumber(generateLicense()).build());
                CreateTruckRequest truck = pTrucks.get(truckCounter++);
                truck.setDriver(driver);
                truckService.registerTruck(truck);
            }
        }
    }

    public static LocalTime[] available() {
        final Random random = new Random();
        int horaInicio = 6 + random.nextInt(10);
        int minutoInicio = random.nextInt(60);
        LocalTime inicio = LocalTime.of(horaInicio, minutoInicio);

        int duracionHoras = 1 + random.nextInt(9);
        int duracionMinutos = random.nextInt(60);

        LocalTime fin = inicio.plusHours(duracionHoras).plusMinutes(duracionMinutos);

        if (fin.isBefore(inicio)) {
            fin = LocalTime.MAX;
        }
        return new LocalTime[] { inicio, fin };
    }

    public static String generateLicense() {
        final Random random = new Random();
        StringBuilder sb = new StringBuilder();
        sb.append("DL");
        for (int i = 0; i < 7; i++) {
            int digito = random.nextInt(10);
            sb.append(digito);
        }
        return sb.toString();
    }

    public static String generatePlate() {
        final String LETRAS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        final Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            char letra = LETRAS.charAt(random.nextInt(LETRAS.length()));
            sb.append(letra);
        }
        sb.append("-");
        for (int i = 0; i < 4; i++) {
            int digito = random.nextInt(10);
            sb.append(digito);
        }
        return sb.toString();
    }

    @SuppressWarnings("unchecked")
    private List<CreateUserRequest> loading_users_from_csv() {
        List<CreateUserRequest> users = new ArrayList<>();
        InputStream inputStream = getClass().getResourceAsStream("/seed/drivers.csv");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
             CSVReader csvReader = new CSVReader(reader)) {
            String[] fila;
            boolean esPrimeraLinea = true;
            while ((fila = csvReader.readNext()) != null) {
                if (esPrimeraLinea) {
                    esPrimeraLinea = false;
                    continue;
                }
                users.add(CreateUserRequest.builder()
                        .setUsername(fila[3].substring(0, fila[3].indexOf('@')))
                        .setEmail(fila[3])
                        .setPassword("P@ssw0rd")
//                        .setCreatedAt(LocalDateTime.of(LocalDate.parse(fila[6], DateTimeFormatter.ofPattern("yyyy-MM-dd")), LocalTime.now()))
                        .setRoles(Set.of("DRIVER")).build());
            }
        } catch (IOException | CsvValidationException e) {
            throw new RuntimeException("Error al leer el archivo CSV", e);
        }
        return users;
    }

    private List<UpdateUserRequest> loading_user_details_from_csv() {
        List<UpdateUserRequest> users = new ArrayList<>();
        InputStream inputStream = getClass().getResourceAsStream("/seed/drivers.csv");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
             CSVReader csvReader = new CSVReader(reader)) {
            String[] fila;
            boolean esPrimeraLinea = true;
            while ((fila = csvReader.readNext()) != null) {
                if (esPrimeraLinea) {
                    esPrimeraLinea = false;
                    continue;
                }
                users.add(UpdateUserRequest.builder()
                        .setPhone(fila[4])
                        .setFirstName(fila[0])
                        .setLastName(fila[1])
                        .setBirthday(LocalDate.parse(fila[5], DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                        .setGender(fila[2].toUpperCase()).build());
            }
        } catch (IOException | CsvValidationException e) {
            throw new RuntimeException("Error al leer el archivo CSV", e);
        }
        return users;
    }

    private List<CreateTruckRequest> loading_trucks_from_csv() {
        List<CreateTruckRequest> trucks = new ArrayList<>();
        InputStream inputStream = getClass().getResourceAsStream("/seed/trucks.csv");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
             CSVReader csvReader = new CSVReader(reader)) {
             String[] fila;
             boolean esPrimeraLinea = true;
             while ((fila = csvReader.readNext()) != null) {
                 if (esPrimeraLinea) {
                    esPrimeraLinea = false;
                    continue;
                 }
                 trucks.add(CreateTruckRequest.builder()
                         .setCapacity(Integer.valueOf(fila[7]))
                         .setColor(fila[4])
                         .setCreatedAt(LocalDateTime.now())
                         .setFuelType(fila[5].toUpperCase())
                         .setHeight(Double.valueOf(fila[10]))
                         .setLength(Double.valueOf(fila[8]))
                         .setMake(fila[0])
                         .setModel(fila[1])
                         .setTransmission(fila[6].toUpperCase())
                         .setPlate(generatePlate())
                         .setPurchaseDate(LocalDate.parse(fila[11], DateTimeFormatter.ofPattern("M/d/yyyy")))
                         .setStatus(fila[13].toUpperCase())
                         .setWidth(Integer.valueOf(fila[9]))
                         .setYear(Integer.valueOf(fila[2])).build());
            }
        } catch (IOException | CsvValidationException e) {
            throw new RuntimeException("Error al leer el archivo CSV", e);
        }
        return trucks;
    }

    @SuppressWarnings("unchecked")
    private void loadUsers() {
        User admin = userService.registerUser(CreateUserRequest.builder()
//                .setCreatedAt(LocalDateTime.now())
                .setEmail("admin@delivery.edu")
                .setPassword("P@ssw0rd")
                .setRoles(Set.of("ADMIN"))
                .setUsername("admin")
                .setRepeatPassword("P@ssw0rd").build());
        userService.updateUser(admin.getId(), UpdateUserRequest.builder()
                .setBirthday(LocalDate.of(1987, Month.APRIL, 17))
                .setGender(Gender.MALE.name())
                .setFirstName("MIGUEL ANGEL")
                .setPhone("666666666")
                .setLastName("PASCUAL GOLDARAZ").build());

        userService.registerUser(CreateUserRequest.builder()
//                .setCreatedAt(LocalDateTime.now())
                .setEmail("customer@delivery.edu")
                .setPassword("P@ssw0rd")
                .setRoles(Set.of("CUSTOMER"))
                .setUsername("customer")
                .setRepeatPassword("P@ssw0rd").build());

        User userDriver = userService.registerUser(CreateUserRequest.builder()
//                .setCreatedAt(LocalDateTime.now())
                .setEmail("driver@delivery.edu")
                .setPassword("P@ssw0rd")
                .setRoles(Set.of("DRIVER"))
                .setUsername("driver")
                .setRepeatPassword("P@ssw0rd").build());

        userService.registerUser(CreateUserRequest.builder()
//                .setCreatedAt(LocalDateTime.now())
                .setEmail("customerdriver@delivery.edu")
                .setPassword("P@ssw0rd")
                .setRoles(Set.of("DRIVER", "CUSTOMER"))
                .setUsername("customerdriver")
                .setRepeatPassword("P@ssw0rd").build());
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
