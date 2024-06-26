package com.example.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Documents {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long document_id;

    @Enumerated(EnumType.STRING)
    private DocumentType document_type;

    @Enumerated(EnumType.STRING)
    private DocumentStatus document_status;

    private String document_url;

    private LocalDateTime updated_at;

    @ManyToOne
    @JoinColumn(name = "application_id")
    private LoanApplication loanApplication;

    @PrePersist
    protected void onCreate() {
        document_status = DocumentStatus.OK;
    }

    @PreUpdate
    protected void onUpdate() {
        updated_at = LocalDateTime.now();
    }

    public enum DocumentType {
        Aadhar,
        PAN,
        Bank_Statement
    }

    public enum DocumentStatus {
        OK,
        NotOK
    }
}
