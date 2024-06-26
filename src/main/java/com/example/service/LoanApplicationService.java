package com.example.service;

import com.example.dto.LoanApplicationDto;
import com.example.exception.ResourceNotFoundException;
import com.example.model.LoanApplication;
import com.example.model.Users;
import com.example.model.LoanProducts;
import com.example.model.Vendors;
import com.example.repository.LoanApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class LoanApplicationService {

    @Autowired
    private LoanApplicationRepository loanApplicationRepository;

    @Autowired
    private RestTemplate restTemplate;

    private final String USER_SERVICE_URL = "http://localhost:8082/users/readOne/";
    private final String ADMIN_SERVICE_URL = "http://localhost:8084/";

    public List<LoanApplication> getAllLoanApplications() {
        return loanApplicationRepository.findAll();
    }

    public LoanApplication getLoanApplicationById(long id) throws ResourceNotFoundException {
        return loanApplicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Loan Application not found for the ID: " + id));
    }

    public LoanApplication addLoanApplication(LoanApplicationDto loanApplicationDto) {
        LoanApplication loanApplication = new LoanApplication();

        loanApplication.setAmount_required(loanApplicationDto.getAmount_required());
        loanApplication.setTenure(loanApplicationDto.getTenure());
        loanApplication.setStatus(LoanApplication.Status.Application_Submitted);
        loanApplication.setReview_message(loanApplicationDto.getReview_message());

        // Fetch user details from User Management Microservice
        Users user = restTemplate.getForObject(USER_SERVICE_URL + loanApplicationDto.getUser_id(), Users.class);
        if (user == null) {
            throw new RuntimeException("User not found with ID: " + loanApplicationDto.getUser_id());
        }
        loanApplication.setUser(user);

        // Fetch product details from Admin Management Service
        LoanProducts product = restTemplate.getForObject(ADMIN_SERVICE_URL + "loan-products/readOne/" + loanApplicationDto.getProduct_id(), LoanProducts.class);
        if (product == null) {
            throw new RuntimeException("Product not found with ID: " + loanApplicationDto.getProduct_id());
        }
        loanApplication.setProduct(product);

        // Fetch vendor details from Admin Management Service
        Vendors vendor = restTemplate.getForObject(ADMIN_SERVICE_URL + "vendors/readOne/" + loanApplicationDto.getVendor_id(), Vendors.class);
        if (vendor == null) {
            throw new RuntimeException("Vendor not found with ID: " + loanApplicationDto.getVendor_id());
        }
        loanApplication.setVendor(vendor);

        return loanApplicationRepository.save(loanApplication);
    }

    public void updateLoanApplication(long id, LoanApplication newLoanApplication) throws ResourceNotFoundException {
        LoanApplication loanApplication = loanApplicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Loan Application not found for the ID: " + id));

        loanApplication.setAmount_required(newLoanApplication.getAmount_required());
        loanApplication.setTenure(newLoanApplication.getTenure());
        loanApplication.setReview_message(newLoanApplication.getReview_message());
        loanApplication.setStatus(newLoanApplication.getStatus());

        // Fetch user details from User Management Microservice
        Users user = restTemplate.getForObject(USER_SERVICE_URL + newLoanApplication.getUser().getUser_id(), Users.class);
        if (user == null) {
            throw new RuntimeException("User not found with ID: " + newLoanApplication.getUser().getUser_id());
        }
        loanApplication.setUser(user);

        // Fetch product details from Admin Management Service
        LoanProducts product = restTemplate.getForObject(ADMIN_SERVICE_URL + "loan-products/readOne/" + newLoanApplication.getProduct().getProduct_id(), LoanProducts.class);
        if (product == null) {
            throw new RuntimeException("Product not found with ID: " + newLoanApplication.getProduct().getProduct_id());
        }
        loanApplication.setProduct(product);

        // Fetch vendor details from Admin Management Service
        Vendors vendor = restTemplate.getForObject(ADMIN_SERVICE_URL + "vendors/readOne/" + newLoanApplication.getVendor().getVendor_id(), Vendors.class);
        if (vendor == null) {
            throw new RuntimeException("Vendor not found with ID: " + newLoanApplication.getVendor().getVendor_id());
        }
        loanApplication.setVendor(vendor);

        loanApplicationRepository.save(loanApplication);
    }

    public void deleteLoanApplication(long id) throws ResourceNotFoundException {
        LoanApplication loanApplication = loanApplicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Loan Application not found for the ID: " + id));
        loanApplicationRepository.delete(loanApplication);
    }

    public void updateApplicationStatus(long applicationId, String status) throws ResourceNotFoundException {
        LoanApplication loanApplication = loanApplicationRepository.findById(applicationId)
                .orElseThrow(() -> new ResourceNotFoundException("Loan Application not found for the ID: " + applicationId));
        loanApplication.setStatus(LoanApplication.Status.valueOf(status));
        loanApplicationRepository.save(loanApplication);
    }
    
    public void partialUpdateLoanApplication(long id, Map<String, Object> updates) throws ResourceNotFoundException {
      LoanApplication loanApplication = loanApplicationRepository.findById(id)
              .orElseThrow(() -> new ResourceNotFoundException("Loan Application not found for the ID: " + id));

      if (updates.containsKey("amount_required")) {
          loanApplication.setAmount_required((Integer) updates.get("amount_required"));
      }
      if (updates.containsKey("tenure")) {
          loanApplication.setTenure((Integer) updates.get("tenure"));
      }
      if (updates.containsKey("review_message")) {
          loanApplication.setReview_message((String) updates.get("review_message"));
      }
      if (updates.containsKey("status")) {
          loanApplication.setStatus(LoanApplication.Status.valueOf((String) updates.get("status")));
      }
      if (updates.containsKey("user")) {
          Users user = new Users();
          user.setUser_id(((Number) updates.get("user")).longValue());
          loanApplication.setUser(user);
      }
      if (updates.containsKey("product")) {
          LoanProducts product = new LoanProducts();
          product.setProduct_id(((Number) updates.get("product")).longValue());
          loanApplication.setProduct(product);
      }
      if (updates.containsKey("vendor")) {
          Vendors vendor = new Vendors();
          vendor.setVendor_id(((Number) updates.get("vendor")).longValue());
          loanApplication.setVendor(vendor);
      }

      loanApplicationRepository.save(loanApplication);
  }
    
 /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public List<LoanProducts> getLoanProductsByProductName(String productName) {
        String url = ADMIN_SERVICE_URL + "loan-products/byProductName?productName=" + productName;
        LoanProducts[] loanProducts = restTemplate.getForObject(url, LoanProducts[].class);
        return List.of(loanProducts);
    }
}

