package com.movies.rest.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Table(name = "popularity")
@EntityListeners(AuditingEntityListener.class)
//@Audited
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Popularity {

    private static final long serialVersionUID = 7597260528858374412L;

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


    @Column(name = "likes", nullable = false)
    private boolean like;
}
