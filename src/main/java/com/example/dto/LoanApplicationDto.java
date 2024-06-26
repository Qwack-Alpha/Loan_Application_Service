package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoanApplicationDto {
    private long user_id;
    private int amount_required;
    private int tenure;
    private long product_id;
    private long vendor_id;
    private String status;
    private String review_message;

}
