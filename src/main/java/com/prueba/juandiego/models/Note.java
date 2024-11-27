package com.prueba.juandiego.models;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;


@Data
@Entity
@Table(name = "notes")

public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 5000)
    private String content;

    @Column(nullable = false)
    private boolean archived = false;

    //Foreign key with Users
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;




    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
