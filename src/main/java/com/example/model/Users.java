package com.example.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Users {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long user_id;

	// @NotNull
	@Size(min = 0, max = 50)
	private String email;

	// @NotNull
	@Size(min = 0, max = 50)
	private String password;

	// @NotNull
	@Size(min = 0, max = 50)
	private String first_name;

	// @NotNull
	@Size(min = 0, max = 50)
	private String last_name;

	// @NotNull
	@Size(min = 0, max = 15)
	private String phone;

	// @NotNull
	@Size(min = 0, max = 200)
	private String address;

	// @NotNull
	@Size(min = 0, max = 10)
	private String pin;

	// @NotNull
	@Enumerated(EnumType.STRING)
	private SecurityQuestion securityQuestion;

	// @NotNull
	@Size(min = 0, max = 100)
	private String securityAnswer;

	// @NotNull
	@Size(min = 0, max = 10)
	private String pan;

	// @NotNull
	private LocalDate dob;

	// @NotNull
	private long annualIncome;

	// @NotNull
	@Enumerated(EnumType.STRING)
	private RoleEnum role;

	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt;

	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

	@OneToMany(mappedBy = "user")
	private Set<LoanApplication> loanApplications; // New field

	@PrePersist
	protected void onCreate() {
		createdAt = LocalDateTime.now();
	}

	@PreUpdate
	protected void onUpdate() {
		updatedAt = LocalDateTime.now();
	}

	public enum SecurityQuestion {
		FIRST_SCHOOL, GRANDMOTHER_NAME, FAVOURITE_FOOD
	}

	public enum RoleEnum {
		CUSTOMER, ADMIN, LOAN_OFFICER
	}
}
