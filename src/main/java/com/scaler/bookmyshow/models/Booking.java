package com.scaler.bookmyshow.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
public class Booking extends BaseModel {
    private BookingStatus bookingStatus;

    @ManyToMany
    private List<ShowSeat> showSeats;// b:ss -> 1 : M => M: 1

    @ManyToOne
    private User user;
    private Date bookedAt;

    @ManyToOne
    private Show show;
    private int amount;
    @OneToMany
    private List<Payment> payments;
}
