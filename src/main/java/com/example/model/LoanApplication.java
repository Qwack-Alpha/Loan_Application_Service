package com.example.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "loan_application", indexes = {
    @Index(name = "idx_user_id", columnList = "user_id"),
    @Index(name = "idx_product_id", columnList = "product_id"),
    @Index(name = "idx_vendor_id", columnList = "vendor_id")
})
public class LoanApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long application_id;

    private int amount_required;
    private int tenure;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
    private String review_message;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private Users user;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "product_id")
    private LoanProducts product;

    @ManyToOne
    @JoinColumn(name = "vendor_id", referencedColumnName = "vendor_id")
    private Vendors vendor;

    @PrePersist
    protected void onCreate() {
        created_at = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updated_at = LocalDateTime.now();
    }

    public enum Status {
        Application_Submitted,
        Submit_Docs,
        Docs_Submitted,
        Resubmit_Docs,
        Docs_Resubmitted,
        Loan_Approved,
        Final
    }
}
