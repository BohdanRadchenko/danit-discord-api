package com.danit.discord.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String string;
    @Column
    private Boolean bool;
    @Column
    private Integer num;
}
