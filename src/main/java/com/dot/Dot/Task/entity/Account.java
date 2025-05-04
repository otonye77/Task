package com.dot.Dot.Task.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.math.BigDecimal;
import java.util.Set;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    @Id
    private String accountNumber;
    @NotBlank(message = "Account name is required")
    private String accountName;
    private BigDecimal balance;

    @OneToMany(mappedBy = "fromAccount")
    private Set<Transaction> sentTransactions;

    @OneToMany(mappedBy = "toAccount")
    private Set<Transaction> receivedTransactions;
}
