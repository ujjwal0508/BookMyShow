package com.scaler.bookmyshow.controller;

import com.scaler.bookmyshow.dtos.BookMovieResponseDTO;
import com.scaler.bookmyshow.dtos.BookMovieRequestDTO;
import com.scaler.bookmyshow.dtos.ResponseStatus;
import com.scaler.bookmyshow.models.Booking;
import com.scaler.bookmyshow.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller //@Service // Repository
// just a tag to tell spring this is a special class
public class BookingController {

    private BookingService bookingService;

    @Autowired //-> find an object of the params & send it here
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    public BookMovieResponseDTO bookMovie(BookMovieRequestDTO request){
        BookMovieResponseDTO bookMovieResponseDTO = new BookMovieResponseDTO();
        Booking booking;

        try{
            booking = bookingService.bookMovie(request.getUserId(), request.getShowSeatIds(), request.getShowId());
            bookMovieResponseDTO.setBookingId(booking.getId());
            bookMovieResponseDTO.setResponseStatus(ResponseStatus.SUCCESS);
            bookMovieResponseDTO.setAmount(booking.getAmount());
        }catch (Exception ex){
            bookMovieResponseDTO.setResponseStatus(ResponseStatus.FAILURE);
        }

        return bookMovieResponseDTO;
    }
}
