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
@Table(name = "ACCOUNTS")
public class Account extends BaseEntity {
    @Id
    @Column(name = "ACCOUNT_NUMBER", nullable = false)
    private Long accountNumber;

    @NotNull
    @Column(name = "CUSTOMER_ID", nullable = false)
    private Long customerId;

    @Size(max = 100)
    @NotNull
    @Column(name = "ACCOUNT_TYPE", nullable = false, length = 100)
    private String accountType;

    @Size(max = 200)
    @NotNull
    @Column(name = "BRANCH_ADDRESS", nullable = false, length = 200)
    private String branchAddress;

}