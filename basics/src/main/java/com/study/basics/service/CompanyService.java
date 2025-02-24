package com.study.basics.service;

import com.study.basics.dto.CompanyDTO;
import com.study.basics.entity.Company;

import java.util.List;

public interface CompanyService {
     CompanyDTO createCompany(CompanyDTO companyDTO);
     CompanyDTO getCompanyDTOById(Long id) throws Exception;
     Company getCompanyById(Long id) throws RuntimeException;
     CompanyDTO updateCompany(Long id,CompanyDTO updatedCompany);
     List<CompanyDTO> getAllCompany();
     void deleteById(Long id);

    CompanyDTO mapToCompanyDTO(Company company);
}
