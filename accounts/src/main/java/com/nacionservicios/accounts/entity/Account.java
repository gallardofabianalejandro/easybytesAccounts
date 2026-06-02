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
public class Account extends BaseEntity {
    @Id
    private Long accountNumber;

    @NotNull
    private Long customerId;

    @Size(max = 100)
    @NotNull
    private String accountType;

    @Size(max = 200)
    @NotNull
    private String branchAddress;

}