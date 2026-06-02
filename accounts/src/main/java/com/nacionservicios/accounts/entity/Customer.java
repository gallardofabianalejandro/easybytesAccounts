package com.nacionservicios.accounts.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Entity
@ToString
@AllArgsConstructor
@NoArgsConstructor

public class Customer extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;

    @Size(max = 100)
    @NotNull
    private String name;

    @Size(max = 100)
    @NotNull
    private String email;

    @Size(max = 20)
    @NotNull
    private String mobileNumber;

}