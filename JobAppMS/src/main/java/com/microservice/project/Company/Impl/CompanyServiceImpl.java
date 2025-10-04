package com.microservice.project.Company.Impl;

import com.microservice.project.Company.Company;
import com.microservice.project.Company.CompanyRepository;
import com.microservice.project.Company.CompanyService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyServiceImpl implements CompanyService
{
    private CompanyRepository companyRepository;

    public CompanyServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    @Override
    public boolean updateCompany(Company company, Long id) {

        Optional<Company> companyOptional = companyRepository.findById(id);
        if(companyOptional.isPresent()) {
            Company companyToUpdate = companyOptional.get();
            company.setDescription(companyToUpdate.getDescription());
            company.setName(companyToUpdate.getName());
            company.setJobs(companyToUpdate.getJobs());
            companyRepository.save(company);
            return true;
        }
        return false;
    }

    @Override
    public void createCompany(Company company) {
        companyRepository.save(company);
    }

    @Override
    public boolean deleteCompany(Long id) {

        if(companyRepository.existsById(id))
        {
            companyRepository.deleteById(id);
            return true;
        }else
        {
            return false;
        }
    }

    @Override
    public Company getCompanyById(Long id) {
        return companyRepository.findById(id).orElse(null);
    }

}
