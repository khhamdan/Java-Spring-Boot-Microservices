package com.microservice.companyms.Company;

import com.microservice.companyms.Company.dto.ReviewMessage;

import java.util.List;

public interface CompanyService
{
    List<Company> getAllCompanies();
    boolean updateCompany(Company company, Long id);
    void createCompany(Company company);
    boolean deleteCompany(Long id);
    Company getCompanyById(Long id);
    public void updateCompanyRating(ReviewMessage reviewMessage);

}
