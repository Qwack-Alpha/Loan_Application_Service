package com.example.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Vendors {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long vendor_id;

	private String vendor_name;
	private String contact_phone;
	private String contact_email;
	private String vendor_logo;

}
