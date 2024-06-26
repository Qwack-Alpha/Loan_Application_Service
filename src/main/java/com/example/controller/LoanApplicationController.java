package com.example.controller;

import com.example.model.LoanApplication;
import com.example.model.LoanProducts;
import com.example.service.LoanApplicationService;
import com.example.dto.LoanApplicationDto;
import com.example.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/loan-applications")
public class LoanApplicationController {

    @Autowired
    private LoanApplicationService loanApplicationService;

    @GetMapping("/read")
    public List<LoanApplication> read() {
        return loanApplicationService.getAllLoanApplications();
    }

    @GetMapping("/readOne/{id}")
    public LoanApplication readOne(@PathVariable("id") long id) throws ResourceNotFoundException {
        return loanApplicationService.getLoanApplicationById(id);
    }

    
    @PostMapping("/add")
    public ResponseEntity<LoanApplication> addLoanApplication(@RequestBody LoanApplicationDto loanApplicationDto) {
        LoanApplication savedApplication = loanApplicationService.addLoanApplication(loanApplicationDto);
        return new ResponseEntity<>(savedApplication, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public void update(@RequestBody LoanApplication loanApplication, @PathVariable("id") long id) throws ResourceNotFoundException {
        loanApplicationService.updateLoanApplication(id, loanApplication);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable("id") long id) throws ResourceNotFoundException {
        loanApplicationService.deleteLoanApplication(id);
    }
    
    @PutMapping("/updateStatus/{id}")
    public ResponseEntity<String> updateStatus(@PathVariable("id") long id, @RequestParam("status") String status) {
        try {
            loanApplicationService.updateApplicationStatus(id, status);
            return ResponseEntity.ok("Status updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating status: " + e.getMessage());
        }
    }
    

    @PatchMapping("/partial-update/{id}")
    public ResponseEntity<String> partialUpdateLoanApplication(@PathVariable("id") long id, @RequestBody Map<String, Object> updates) {
        try {
            loanApplicationService.partialUpdateLoanApplication(id, updates);
            return ResponseEntity.ok("Loan Application updated successfully");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating loan application: " + e.getMessage());
        }
    }

    
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    @GetMapping("/loan-products/byProductName")
    public List<LoanProducts> getLoanProductsByProductName(@RequestParam("productName") String productName) {
        return loanApplicationService.getLoanProductsByProductName(productName);
    }


}
