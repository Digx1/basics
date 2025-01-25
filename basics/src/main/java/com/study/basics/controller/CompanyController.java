package com.study.basics.controller;

import com.study.basics.dto.CompanyDTO;
import com.study.basics.service.CompanyService;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Transactional
@RequestMapping("/company")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }
    @PostMapping
    public ResponseEntity<CompanyDTO> createCompany( @RequestBody CompanyDTO companyDTO){
        CompanyDTO company =  companyService.createCompany(companyDTO);
        return new ResponseEntity<>(company, HttpStatus.CREATED);

    }
    @GetMapping({"{id}"})
    public ResponseEntity<CompanyDTO> getCompanyById(@PathVariable(value = "id") Long id) throws Exception {
        CompanyDTO companyDTO = companyService.getCompanyDTOById(id);
        return ResponseEntity.ok(companyDTO);
    }

    @PutMapping({"{id}"})
    public ResponseEntity<CompanyDTO> updateCompany(@PathVariable(value = "id") Long id, @RequestBody CompanyDTO companyDTO){
        CompanyDTO company = companyService.updateCompany(id,companyDTO);
        return ResponseEntity.ok(company);
    }
    @GetMapping
    public  ResponseEntity<List<CompanyDTO>> getAllCompanies(){
        List<CompanyDTO> allCompanies = companyService.getAllCompany();
        return ResponseEntity.ok(allCompanies);
    }
   @DeleteMapping({"{id}"})
    public ResponseEntity<String> deleteById(@PathVariable(value = "id") Long id){
        companyService.deleteById(id);
        return ResponseEntity.ok("company deleted");

    }
}
