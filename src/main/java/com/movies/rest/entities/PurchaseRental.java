package com.movies.rest.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.movies.rest.enums.Actions;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "purchase_rental")
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseRental {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Column(name = "quantity")
    private Long quantity;

    @Column(name = "type_action")
    @Enumerated(EnumType.STRING)
    private Actions typeAction;

    @Column(name = "delivery_date")
    private LocalDate deliveryDate;

    @Column(name = "delivery_at")
    private LocalDate deliveryAt;

    @Column(name = "penalty")
    private Boolean penalty;

    @Column(name = "created_at")
    @CreatedDate
    private LocalDateTime createdAt;

}
