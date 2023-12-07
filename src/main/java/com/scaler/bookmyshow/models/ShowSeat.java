package com.scaler.bookmyshow.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
public class ShowSeat extends BaseModel {
    @ManyToOne
    private Seat seat;
    @ManyToOne
    private Show show;

    @Enumerated(EnumType.ORDINAL)
    private ShowSeatStatus showSeatStatus;
    private Date blockedAt;
}
