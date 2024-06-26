package com.example.controller;

import com.example.model.Documents;
import com.example.service.DocumentsService;
import com.example.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/documents")
public class DocumentsController {

    @Autowired
    private DocumentsService documentsService;

    @GetMapping("/read")
    public List<Documents> read() {
        return documentsService.getAllDocuments();
    }

    @GetMapping("/readOne/{id}")
    public Documents readOne(@PathVariable("id") long id) throws ResourceNotFoundException {
        return documentsService.getDocumentsById(id);
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody List<Documents> documentsList) {
        for (Documents documents : documentsList) {
            documentsService.addDocuments(documents);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public void update(@RequestBody Documents documents, @PathVariable("id") long id) throws ResourceNotFoundException {
        documentsService.updateDocuments(id, documents);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable("id") long id) throws ResourceNotFoundException {
        documentsService.deleteDocuments(id);
    }
    
    @GetMapping("/byApplication/{applicationId}")
    public List<Documents> getDocumentsByApplicationId(@PathVariable("applicationId") long applicationId) {
        return documentsService.getDocumentsByApplicationId(applicationId);
    }

    @DeleteMapping("/deleteByApplication/{applicationId}")
    public ResponseEntity<?> deleteDocumentsByApplicationId(@PathVariable("applicationId") long applicationId) {
        documentsService.deleteDocumentsByApplicationId(applicationId);
        return ResponseEntity.ok().build();
    }
    @PutMapping("/updateUrl")
    public ResponseEntity<?> updateDocumentUrl(@RequestParam("document_id") long documentId,
                                               @RequestParam("document_url") String documentUrl) {
        try {
            documentsService.updateDocumentUrl(documentId, documentUrl);
            return ResponseEntity.ok("Document URL updated successfully");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
