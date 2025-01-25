package com.study.basics.service.impl;

import com.study.basics.dto.CompanyDTO;
import com.study.basics.entity.Company;
import com.study.basics.repositeory.CompanyRepository;
import com.study.basics.service.CompanyService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
@Service
public  class CompanyServiceImpl implements CompanyService {

    private  final CompanyRepository companyRepository;

    public CompanyServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public CompanyDTO mapToCompanyDTO(Company company){
        if (Objects.isNull(company)) {
           return null;
        }

        return CompanyDTO.builder()
                .id(company.getId())
                .name(company.getName())
                .domain(company.getDomain())
                .build();

    }

    public Company mapToCompany(CompanyDTO companyDTO){
        return Company.builder()
                .id(companyDTO.getId())
                .name(companyDTO.getName())
                .domain(companyDTO.getDomain())
                .build();
    }

    @Override
    public CompanyDTO createCompany(CompanyDTO companyDTO) {
        Company company = mapToCompany(companyDTO);
        Company savedCompany = companyRepository.save(company);
        return mapToCompanyDTO(savedCompany);
    }

    @Override
    public CompanyDTO getCompanyDTOById(Long id) throws Exception {
        Company company = companyRepository.findById(id).orElse(null);
        if(Objects.isNull(company)) {
            throw new Exception("Company with id: " + id + " could not be found");
        }
        return mapToCompanyDTO(company);
    }

    @Override
    public Company getCompanyById(Long id) throws Exception {
        Company company = companyRepository.findById(id).orElse(null);
        if(Objects.isNull(company)) {
            throw new Exception("Company with id: " + id + " could not be found");
        }
        return company;
    }

    @Override
    public CompanyDTO updateCompany(Long id,CompanyDTO companyDTO) {
        Optional<Company> optionalCompany = companyRepository.findById(id);
        if(optionalCompany.isPresent()){
            Company company = optionalCompany.get();
            company.setName(companyDTO.getName());
            company.setDomain(companyDTO.getDomain());
            Company updatedCompany = companyRepository.save(company);
            return mapToCompanyDTO(updatedCompany);
        }
        return null;

    }

    @Override
    public List<CompanyDTO> getAllCompany() {
        List<Company> companies = companyRepository.findAll();
        List<CompanyDTO>allCompanies=new ArrayList<>();
        for (int i = 0; i < companies.size(); i++) {
            CompanyDTO companyDTO = mapToCompanyDTO(companies.get(i));
            allCompanies.add(companyDTO);
        }
        return allCompanies;
    }

    @Override
    public void deleteById(Long id) {
        companyRepository.deleteById(id);
    }
}
