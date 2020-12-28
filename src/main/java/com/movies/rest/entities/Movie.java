package com.movies.rest.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.envers.Audited;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "movies")
@EntityListeners(AuditingEntityListener.class)
//@Audited
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Size(max = 250)
    @Column(name = "title", nullable = false, unique = true)
    private String title;

    @Size(max = 500)
    @Column(name = "description", nullable = false, length = 500)
    private String description;

    @Column(name = "stock", nullable = false)
    private Long stock;


    @Column(name = "rental_price", nullable = false)
    private BigDecimal rentalPrice;

    @DecimalMin("1.00")
    @Column(name = "sale_price", nullable = false)
    private BigDecimal salePrice;

    private boolean availability;

    @Min(0)
    @Column(name = "no_likes", nullable = false)
    private Long noLikes;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "movie_images", joinColumns = @JoinColumn(name = "movie_id"))
    @Column(name = "image", nullable = false)
    private Set<Image> images = new HashSet<>();

    @EqualsAndHashCode.Exclude @ToString.Exclude
    @JsonManagedReference
    @Builder.Default
    @OneToMany(mappedBy = "movie")
    private Set<Popularity> popularities  = new HashSet<>();

    @EqualsAndHashCode.Exclude @ToString.Exclude
    @JsonManagedReference
    @Builder.Default
    @OneToMany(mappedBy = "movie", fetch = FetchType.EAGER)
    private Set<PurchaseRental> purchaseRentals  = new HashSet<>();

    @Column(name = "created_at")
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(name = "created_by")
    @CreatedBy
    private String createdBy;

    @Column(name = "modified_by")
    @CreatedBy
    private String modifiedBy;
}
