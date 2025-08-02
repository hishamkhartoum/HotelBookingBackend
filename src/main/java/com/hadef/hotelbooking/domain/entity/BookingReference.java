package com.hadef.hotelbooking.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class BookingReference {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false, unique = true)
    private String referenceNo;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        BookingReference that = (BookingReference) o;
        return Objects.equals(id, that.id) && Objects.equals(referenceNo, that.referenceNo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, referenceNo);
    }
}
