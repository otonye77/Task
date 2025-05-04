package com.dot.Dot.Task.entity;

import com.dot.Dot.Task.enums.TransactionStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String transactionReference;
    @NotNull(message = "Amount is required")
    @Min(value = 1, message = "Amount must be greater than 0")
    private BigDecimal amount;
    @NotNull(message = "Transaction fee is required")
    @Min(value = 0, message = "Transaction fee cannot be negative")
    private BigDecimal transactionFee;
    @NotNull(message = "Billed amount is required")
    @Min(value = 0, message = "Billed amount cannot be negative")
    private BigDecimal billedAmount;
    @NotBlank(message = "Description is required")
    private String description;

    private LocalDateTime createdDate;

    @Enumerated
    private TransactionStatus status;

    private String statusMessage;
    private boolean commisionWorthy;
    private BigDecimal commision;

    @NotBlank(message = "Account number is required")
    @Column(name = "from_account")
    private String fromAccount;

    @NotBlank(message = "Account number is required")
    @Column(name = "to_account")
    private String toAccount;


}
