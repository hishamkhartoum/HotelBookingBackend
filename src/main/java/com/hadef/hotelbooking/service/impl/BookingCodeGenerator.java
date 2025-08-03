package com.hadef.hotelbooking.service.impl;

import com.hadef.hotelbooking.domain.entity.BookingReference;
import com.hadef.hotelbooking.repository.BookingReferenceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class BookingCodeGenerator {

    private final BookingReferenceRepository bookingReferenceRepository;

    public String generateBookingReference(){
        String bookingReference;

        // keep generating until a unique code is found
        do{
            bookingReference = generateRandomAlphaNumericCode(); //genrate code of length 10

        }while (isBookingReferenceExist(bookingReference)); //check if the code already exist. if it does't, exit

        saveBookingReferenceToDatabase(bookingReference); //save the code to database

        return bookingReference;
    }
    private String generateRandomAlphaNumericCode(){

        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ123456789";
        Random random = new Random();

        StringBuilder stringBuilder = new StringBuilder(10);

        for (int i = 0; i < 10; i++){
            int index = random.nextInt(characters.length());
            stringBuilder.append(characters.charAt(index));
        }
        return stringBuilder.toString();
    }

    private boolean isBookingReferenceExist(String bookingReference){
        return bookingReferenceRepository.findByReferenceNo(bookingReference).isPresent();
    }

    private void saveBookingReferenceToDatabase(String bookingReference){
        BookingReference newBookingReference = BookingReference.builder().referenceNo(bookingReference).build();
        bookingReferenceRepository.save(newBookingReference);
    }
}
