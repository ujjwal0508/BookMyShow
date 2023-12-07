package com.scaler.bookmyshow.repositories;

import com.scaler.bookmyshow.models.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Override
    Booking save(Booking entity);
}

// <ID, obj>
// JPARepository

// 1. Class -> Interface
//2. Extend JPARepository
