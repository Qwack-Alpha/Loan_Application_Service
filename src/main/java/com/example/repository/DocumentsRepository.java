package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.model.Documents;

public interface DocumentsRepository extends JpaRepository<Documents, Long> {
	@Query("SELECT d FROM Documents d WHERE d.loanApplication.application_id = :applicationId")
    List<Documents> findByLoanApplication_ApplicationId(@Param("applicationId") long applicationId);

    @Modifying
    @Query("DELETE FROM Documents d WHERE d.loanApplication.application_id = :applicationId")
    void deleteByLoanApplication_ApplicationId(@Param("applicationId") long applicationId);
}