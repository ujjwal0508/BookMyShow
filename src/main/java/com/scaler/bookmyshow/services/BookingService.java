package com.scaler.bookmyshow.services;

import com.scaler.bookmyshow.models.*;
import com.scaler.bookmyshow.repositories.BookingRepository;
import com.scaler.bookmyshow.repositories.ShowRepository;
import com.scaler.bookmyshow.repositories.ShowSeatRepository;
import com.scaler.bookmyshow.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    private BookingRepository bookingRepository;
    private ShowRepository showRepository;
    private ShowSeatRepository showSeatRepository;
    private UserRepository userRepository;

    private PriceCalculator priceCalculator;

    @Autowired
    public BookingService(BookingRepository bookingRepository, ShowRepository showRepository, ShowSeatRepository showSeatRepository, UserRepository userRepository,PriceCalculator priceCalculator) {
        this.bookingRepository = bookingRepository;
        this.showRepository = showRepository;
        this.showSeatRepository = showSeatRepository;
        this.userRepository = userRepository;
        this.priceCalculator = priceCalculator;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Booking bookMovie(Long userId, List<Long> seatIds, Long showId){
        // -------- TODAY WE START TRANSACTION HERE ------
        // 1. get user from user id
        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isEmpty()){
            throw new RuntimeException();
        }
        User bookedBy = userOptional.get();

        // 2. get show from show id
        Optional<Show> showOptional = showRepository.findById(showId);
        if(showOptional.isEmpty()){
            throw new RuntimeException();
        }

        Show bookedFor = showOptional.get();

        // --------START TRANSACTION-------- // get status after they are locked
        // 3. get show seats from seat ids
        List<ShowSeat> showSeats = showSeatRepository.findAllById(seatIds);
        // 4. check if all show seats are available
        // 5. if no, throw error

        for(ShowSeat showSeat: showSeats){
            if(!(showSeat.getShowSeatStatus().equals(ShowSeatStatus.AVAILABLE) ||
                    (showSeat.getShowSeatStatus().equals(ShowSeatStatus.BLOCKED) &&
                            Duration.between(showSeat.getBlockedAt().toInstant(), new Date().toInstant()).toMinutes() > 15)
            )){
                throw new RuntimeException();
            }
        }


        // 6. if yes, mark status as locked
        // 7. save updated show seats to db
        List<ShowSeat> savedShowSeat = new ArrayList<>();
        for(ShowSeat showSeat: showSeats){
            showSeat.setShowSeatStatus(ShowSeatStatus.BLOCKED);
            showSeat.setBlockedAt(new Date());
            savedShowSeat.add(showSeatRepository.save(showSeat));
        }

        // ---------- END TRANSACTION----------
        // 8. create corresponding booking object
        Booking booking = new Booking();
        booking.setBookingStatus(BookingStatus.PENDING);
        booking.setShowSeats(savedShowSeat);
        booking.setUser(bookedBy);
        booking.setShow(bookedFor);
        booking.setBookedAt(new Date());
        booking.setAmount(priceCalculator.calculatePrice(savedShowSeat, bookedFor));

        Booking savedBooking = bookingRepository.save(booking);
         // --------- TODAY WE WILL END TRANSACTION HERE ----------

        return savedBooking;
    }
}
