package com.hadef.hotelbooking.repository;

import com.hadef.hotelbooking.domain.entity.BookingReference;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface BookingReferenceRepository extends JpaRepository<BookingReference, UUID> {
    Optional<BookingReference> findByReferenceNo(String referenceNo);
}
