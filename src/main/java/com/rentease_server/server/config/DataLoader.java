/*
package com.rentease_server.server.config;

import com.rentease_server.server.entities.Landlord;
import com.rentease_server.server.entities.Property;
import com.rentease_server.server.repositories.LandlordRepo;
import com.rentease_server.server.repositories.PropertyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Configuration
public class DataLoader {

    @Autowired
    private LandlordRepo landlordRepo;

    @Autowired
    private PropertyRepo propertyRepo;

    @Bean
    public CommandLineRunner loadData(){
        return args -> {

            // Load Landlord Data:
            Optional<Landlord> landlordOpt1 = landlordRepo
                    .findById("932c3a21-257c-4452-ae0d-db90e4589df6");
            Optional<Landlord> landlordOpt2 = landlordRepo
                    .findById("bc6e4ca2-2ce5-464a-9c2e-ea008caff877");

            if (landlordOpt1.isEmpty() || landlordOpt2.isEmpty()) {
                throw new RuntimeException("Required landlords not found in the database.");
            }

            Landlord landlord1 = landlordOpt1.get();
            Landlord landlord2 = landlordOpt2.get();

            // Create Property instances
            List<Property> properties = Arrays.asList(
                    Property.builder()
                            .propertyId(UUID.randomUUID().toString())
                            .name("Luxury Apartment")
                            .address("101 Pine Road")
                            .city("Hyderabad")
                            .state("Telangana")
                            .pinCode(500001)
                            .tenantType("Family")
                            .dateListed(LocalDateTime.now().toString())
                            .description("Luxury 4 BHK apartment with modern finishes and great amenities.")
                            .floor("15th of 20 Floors")
                            .size("4 BHK")
                            .propertyAge("2 years")
                            .propertyType("Apartment")
                            .availabilityStatus("Available")
                            .photos(new String[]{"https://example.com/photo7.jpg", "https://example.com/photo8.jpg"})
                            .rent(60000.00)
                            .deposit(180000.00)
                            .configuration("4 Bedrooms, 3 Bathrooms, 1 Hall, 1 Kitchen")
                            .furnishedStatus("Fully Furnished")
                            .furnishedAmenities(new String[]{"Air conditioning", "Modular Kitchen", "Sofa Set", "Dining Table"})
                            .otherAmenities(new String[]{"Swimming Pool", "Gym", "Clubhouse", "24/7 Security"})
                            .landlord(landlord1) // assuming landlord1 is a predefined object
                            .build(),
                    Property.builder()
                            .propertyId(UUID.randomUUID().toString())
                            .name("Oak Avenue Villa")
                            .address("789 Oak Avenue")
                            .city("Pune")
                            .state("Maharashtra")
                            .pinCode(411001)
                            .tenantType("Family")
                            .dateListed(LocalDateTime.now().toString())
                            .description("Spacious 3 BHK villa with a private garden and excellent connectivity.")
                            .floor("Ground Floor")
                            .size("3 BHK")
                            .propertyAge("3 years")
                            .propertyType("House")
                            .availabilityStatus("Available")
                            .photos(new String[]{"https://example.com/photo5.jpg", "https://example.com/photo6.jpg"})
                            .rent(45000.00)
                            .deposit(135000.00)
                            .configuration("3 Bedrooms, 2 Bathrooms, 1 Hall, 1 Kitchen, Garden")
                            .furnishedStatus("Unfurnished")
                            .furnishedAmenities(new String[]{})
                            .otherAmenities(new String[]{"Garden", "Lift", "Parking", "Swimming Pool"})
                            .landlord(landlord2) // assuming landlord2 is a predefined object
                            .build(),
                    Property.builder()
                            .propertyId(UUID.randomUUID().toString())
                            .name("Maple Street Studio")
                            .address("202 Maple Street")
                            .city("Chennai")
                            .state("Tamil Nadu")
                            .pinCode(600001)
                            .tenantType("Bachelor")
                            .dateListed(LocalDateTime.now().toString())
                            .description("Cozy studio apartment in a central location with easy access to public transport.")
                            .floor("7th of 12 Floors")
                            .size("2 BHK")
                            .propertyAge("1 year")
                            .propertyType("Apartment")
                            .availabilityStatus("Available")
                            .photos(new String[]{"https://example.com/photo9.jpg", "https://example.com/photo10.jpg"})
                            .rent(15000.00)
                            .deposit(45000.00)
                            .configuration("2 Bedroom, 2 Bathroom, 1 Kitchen")
                            .furnishedStatus("Semi Furnished")
                            .furnishedAmenities(new String[]{"Basic Furniture", "Washing Machine"})
                            .otherAmenities(new String[]{"Rooftop Terrace", "Laundry Facilities", "Security Surveillance"})
                            .landlord(landlord1)
                            .build(),
                    Property.builder()
                            .propertyId(UUID.randomUUID().toString())
                            .name("Royal Residency")
                            .address("Rajendra Nagar")
                            .city("Patna")
                            .state("Bihar")
                            .pinCode(800016)
                            .tenantType("Family")
                            .dateListed(LocalDateTime.now().toString())
                            .description("Premium 4 BHK apartment in the upscale Rajendra Nagar area.")
                            .floor("7th of 10 Floors")
                            .size("4 BHK")
                            .propertyAge("3 years")
                            .propertyType("Apartment")
                            .availabilityStatus("Available")
                            .photos(new String[]{"image1.jpg"})
                            .rent(700000.00)
                            .deposit(1400000.00)
                            .configuration("4 Bedroom, 4 Bathroom, 1 Study Room, 2 Balconies")
                            .furnishedStatus("Fully Furnished")
                            .furnishedAmenities(new String[]{"Modular Kitchen", "Designer Furniture", "Wardrobes", "Air Conditioning", "Smart Home Automation"})
                            .otherAmenities(new String[]{"Rooftop Garden", "Swimming Pool", "Clubhouse", "24/7 Power Backup", "Dedicated Covered Parking"})
                            .landlord(landlord2) // assuming landlord4 is a predefined object
                            .build(),
                    Property.builder()
                            .propertyId(UUID.randomUUID().toString())
                            .name("Spacious Apartment")
                            .address("101, MG Road")
                            .city("Bengaluru")
                            .state("Karnataka")
                            .pinCode(560001)
                            .tenantType("Bachelor")
                            .dateListed(LocalDateTime.now().toString())
                            .description("A spacious 2 BHK apartment located in the heart of Bengaluru.")
                            .floor("5th Floor")
                            .size("2 BHK")
                            .propertyAge("3 years")
                            .propertyType("Apartment")
                            .availabilityStatus("Available")
                            .photos(new String[]{"https://example.com/photos/1.jpg", "https://example.com/photos/2.jpg"})
                            .rent(30000.00)
                            .deposit(90000.00)
                            .configuration("2 Bedroom and 2 Bathroom")
                            .furnishedStatus("Fully Furnished")
                            .furnishedAmenities(new String[]{"Sofa", "Dining Table", "Wardrobes", "Beds", "AC", "Refrigerator"})
                            .otherAmenities(new String[]{"Parking", "Gym", "Swimming Pool", "24/7 Security", "Power Backup"})
                            .landlord(landlord1) // assuming landlord5 is a predefined object
                            .build(),
                    Property.builder()
                            .propertyId(UUID.randomUUID().toString())
                            .name("Modern Apartment")
                            .address("456 Elm Street")
                            .city("Bengaluru")
                            .state("Karnataka")
                            .pinCode(560001)
                            .tenantType("Family")
                            .dateListed(LocalDateTime.now().toString())
                            .description("A modern 1 BHK apartment in a well-maintained complex with excellent amenities.")
                            .floor("2nd of 5 Floors")
                            .size("1 BHK")
                            .propertyAge("5 years")
                            .propertyType("Apartment")
                            .availabilityStatus("Available")
                            .photos(new String[]{"https://example.com/photo3.jpg", "https://example.com/photo4.jpg"})
                            .rent(20000.00)
                            .deposit(60000.00)
                            .configuration("1 Bedroom, 1 Bathroom, 1 Hall, 1 Kitchen")
                            .furnishedStatus("Semi Furnished")
                            .furnishedAmenities(new String[]{"Ceiling Fan", "Modular Kitchen", "Wardrobe"})
                            .otherAmenities(new String[]{"Clubhouse", "Playground", "24/7 Security"})
                            .landlord(landlord1) // assuming landlord6 is a predefined object
                            .build(),
                    Property.builder()
                            .propertyId(UUID.randomUUID().toString())
                            .name("Cozy Studio")
                            .address("Ananadpur")
                            .city("Kolkata")
                            .state("West Bengal")
                            .pinCode(600001)
                            .tenantType("Family")
                            .dateListed(LocalDateTime.now().toString())
                            .description("Cozy studio apartment in a central location with easy access to public transport.")
                            .floor("3rd of 12 Floors")
                            .size("1 BHK")
                            .propertyAge("1 year")
                            .propertyType("Apartment")
                            .availabilityStatus("Available")
                            .photos(new String[]{"https://example.com/photo9.jpg", "https://example.com/photo10.jpg"})
                            .rent(10000.00)
                            .deposit(20000.00)
                            .configuration("1 Bedroom, 1 Hall, 1 Kitchen, 1 Bathroom")
                            .furnishedStatus("Semi Furnished")
                            .furnishedAmenities(new String[]{"Basic Furniture", "Washing Machine"})
                            .otherAmenities(new String[]{"Rooftop Terrace", "Laundry Facilities", "Security Surveillance"})
                            .landlord(landlord1) // assuming landlord7 is a predefined object
                            .build(),
                    Property.builder()
                            .propertyId(UUID.randomUUID().toString())
                            .name("Ganga View Villa")
                            .address("Ganga Road")
                            .city("Bhagalpur")
                            .state("Bihar")
                            .pinCode(813210)
                            .tenantType("Family")
                            .dateListed(LocalDateTime.now().toString())
                            .description("Luxury 4 BHK villa with a stunning view of the Ganges, providing a serene living experience. The villa is equipped with premium amenities and offers a spacious layout for a comfortable lifestyle.")
                            .floor("Ground Floor of 2 Floors")
                            .size("4 BHK")
                            .propertyAge("5 years")
                            .propertyType("House")
                            .availabilityStatus("Available")
                            .photos(new String[]{"https://example.com/photo23.jpg", "https://example.com/photo24.jpg"})
                            .rent(180000.00)
                            .deposit(540000.00)
                            .configuration("4 Bedroom, 5 Bathroom, 2 Balconies, Private Garden")
                            .furnishedStatus("Semi Furnished")
                            .furnishedAmenities(new String[]{"Modular Kitchen", "Wardrobes", "Luxury Bathroom Fittings", "High-End Electricals"})
                            .otherAmenities(new String[]{"Private Garden", "Riverside View", "Parking for 3 Cars", "Servant Quarters", "24/7 Security"})
                            .landlord(landlord1) // assuming landlord8 is a predefined object
                            .build(),
                    Property.builder()
                            .propertyId(UUID.randomUUID().toString())
                            .name("Shanti Apartment")
                            .address("123 Main Street")
                            .city("Mumbai")
                            .state("Maharashtra")
                            .pinCode(400001)
                            .tenantType("Family")
                            .dateListed(LocalDateTime.now().toString())
                            .description("A beautiful 2 BHK apartment located in the heart of the city.")
                            .floor("5th of 6 Floors")
                            .size("2 BHK")
                            .propertyAge("10 years")
                            .propertyType("Apartment")
                            .availabilityStatus("Available")
                            .photos(new String[]{"img1.png"})
                            .rent(45000.00)
                            .deposit(90000.00)
                            .configuration("2 Bedroom, 3 Bathroom, 1 Hall, 1 Kitchen")
                            .furnishedStatus("Fully Furnished")
                            .furnishedAmenities(new String[]{"Air Conditioning", "Refrigerator", "Washing Machine"})
                            .otherAmenities(new String[]{"Swimming Pool", "Gym", "Parking"})
                            .landlord(landlord2) // assuming landlord9 is a predefined object
                            .build(),
                    Property.builder()
                            .propertyId(UUID.randomUUID().toString())
                            .name("Sudha Rani Apartment")
                            .address("Madurdaha Hussainpur")
                            .city("Kolkata")
                            .state("West Bengal")
                            .pinCode(600001)
                            .tenantType("Bachelor")
                            .dateListed(LocalDateTime.now().toString())
                            .description("Cozy apartment in a central location with easy access to public transport.")
                            .floor("1st of 3 Floors")
                            .size("2 BHK")
                            .propertyAge("10 years")
                            .propertyType("Apartment")
                            .availabilityStatus("Available")
                            .photos(new String[]{"image.jpg"})
                            .rent(2400.00)
                            .deposit(4800.00)
                            .configuration("2 Bedroom, 2 Bathroom")
                            .furnishedStatus("Semi Furnished")
                            .furnishedAmenities(new String[]{"Basic Furniture", "Washing Machine"})
                            .otherAmenities(new String[]{"Parking", "Laundry Facilities", "Security Surveillance"})
                            .landlord(landlord1) // assuming landlord10 is a predefined object
                            .build()
            );

            propertyRepo.saveAll(properties);

        };
    }

}

 */
