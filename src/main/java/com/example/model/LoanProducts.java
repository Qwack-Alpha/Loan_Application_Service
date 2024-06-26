package com.example.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoanProducts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long product_id;

    @Enumerated(EnumType.STRING)
    private ProductName product_name;

    private float product_interest_rate;
    private float product_processing_fee;

    @ManyToOne
    @JoinColumn(name = "vendor_id")
    private Vendors vendor;

    private int product_prepayment_charge;  // New field
    private String product_prepayment_conditions;  // New field

    public enum ProductName {
        Personal, Gold, Home
    }
}
