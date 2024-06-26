package com.example.service;

import com.example.exception.ResourceNotFoundException;
import com.example.model.Documents;
import com.example.model.LoanApplication;
import com.example.repository.DocumentsRepository;
import com.example.repository.LoanApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class DocumentsService {

    @Autowired
    private DocumentsRepository documentsRepository;

    @Autowired
    private LoanApplicationRepository loanApplicationRepository;

    @Autowired
    private RestTemplate restTemplate;

    public List<Documents> getAllDocuments() {
        return documentsRepository.findAll();
    }

    public Documents getDocumentsById(long id) throws ResourceNotFoundException {
        return documentsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Document not found for the ID: " + id));
    }

    public Documents addDocuments(Documents documents) {
        long applicationId = documents.getLoanApplication().getApplication_id();
        try {
            LoanApplication loanApplication = loanApplicationRepository.findById(applicationId)
                    .orElseThrow(() -> new ResourceNotFoundException("Loan Application not found for the ID: " + applicationId));
            documents.setLoanApplication(loanApplication);
        } catch (ResourceNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return documentsRepository.save(documents);
    }

    public void updateDocuments(long id, Documents newDocuments) throws ResourceNotFoundException {
        Documents documents = documentsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Document not found for the ID: " + id));

        if (newDocuments.getDocument_type() != null) {
            documents.setDocument_type(newDocuments.getDocument_type());
        }
        if (newDocuments.getDocument_status() != null) {
            documents.setDocument_status(newDocuments.getDocument_status());
        }
        if (newDocuments.getDocument_url() != null) {
            documents.setDocument_url(newDocuments.getDocument_url());
        }
        if (newDocuments.getLoanApplication() != null) {
            long applicationId = newDocuments.getLoanApplication().getApplication_id();
            LoanApplication loanApplication = loanApplicationRepository.findById(applicationId)
                    .orElseThrow(() -> new ResourceNotFoundException("Loan Application not found for the ID: " + applicationId));
            documents.setLoanApplication(loanApplication);
        }

        documentsRepository.save(documents);
    }

    public void deleteDocuments(long id) throws ResourceNotFoundException {
        Documents documents = documentsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Document not found for the ID: " + id));
        documentsRepository.delete(documents);
    }

    public LoanApplication getLoanApplicationById(long id) throws ResourceNotFoundException {
        return loanApplicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Loan Application not found for the ID: " + id));
    }

    public void saveDocuments(long applicationId, String aadharUrl, String panUrl, String bankStatementUrl) throws Exception {
        LoanApplication loanApplication = loanApplicationRepository.findById(applicationId)
                .orElseThrow(() -> new ResourceNotFoundException("Loan Application not found for the ID: " + applicationId));

        if (aadharUrl != null && !aadharUrl.isEmpty()) {
            Documents aadharDoc = new Documents();
            aadharDoc.setDocument_type(Documents.DocumentType.Aadhar);
            aadharDoc.setDocument_status(Documents.DocumentStatus.NotOK);
            aadharDoc.setDocument_url(aadharUrl);
            aadharDoc.setLoanApplication(loanApplication);
            documentsRepository.save(aadharDoc);
        }

        if (panUrl != null && !panUrl.isEmpty()) {
            Documents panDoc = new Documents();
            panDoc.setDocument_type(Documents.DocumentType.PAN);
            panDoc.setDocument_status(Documents.DocumentStatus.NotOK);
            panDoc.setDocument_url(panUrl);
            panDoc.setLoanApplication(loanApplication);
            documentsRepository.save(panDoc);
        }

        if (bankStatementUrl != null && !bankStatementUrl.isEmpty()) {
            Documents bankStatementDoc = new Documents();
            bankStatementDoc.setDocument_type(Documents.DocumentType.Bank_Statement);
            bankStatementDoc.setDocument_status(Documents.DocumentStatus.NotOK);
            bankStatementDoc.setDocument_url(bankStatementUrl);
            bankStatementDoc.setLoanApplication(loanApplication);
            documentsRepository.save(bankStatementDoc);
        }
    }

    public List<Documents> getDocumentsByApplicationId(long applicationId) {
        return documentsRepository.findByLoanApplication_ApplicationId(applicationId);
    }

    public void deleteDocumentsByApplicationId(long applicationId) {
        List<Documents> documents = documentsRepository.findByLoanApplication_ApplicationId(applicationId);
        documentsRepository.deleteAll(documents);
    }

    public void updateDocumentUrl(long documentId, String documentUrl) throws ResourceNotFoundException {
        Documents documents = documentsRepository.findById(documentId)
                .orElseThrow(() -> new ResourceNotFoundException("Document not found for the ID: " + documentId));
        documents.setDocument_url(documentUrl);
        documentsRepository.save(documents);
    }
}

