package com.microservice.companyms.Company.Impl;

import com.microservice.companyms.Company.Company;
import com.microservice.companyms.Company.CompanyRepository;
import com.microservice.companyms.Company.CompanyService;
import com.microservice.companyms.Company.client.ReviewClient;
import com.microservice.companyms.Company.dto.ReviewMessage;
import jakarta.ws.rs.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyServiceImpl implements CompanyService
{
    private CompanyRepository companyRepository;
    private ReviewClient reviewClient;

    public CompanyServiceImpl(CompanyRepository companyRepository, ReviewClient reviewClient) {
        this.companyRepository = companyRepository;
        this.reviewClient = reviewClient;
    }

    @Override
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    @Override
    public boolean updateCompany(Company UpdatedCompany, Long id) {

        Optional<Company> companyOptional = companyRepository.findById(id);

        if(companyOptional.isPresent()) {
            Company companyToUpdate = companyOptional.get();

            companyToUpdate.setName(UpdatedCompany.getName());
            companyToUpdate.setDescription(UpdatedCompany.getDescription());

            companyRepository.save(companyToUpdate);
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

    @Override
    public void updateCompanyRating(ReviewMessage reviewMessage) {
        System.out.println(reviewMessage.getDescription());
        if (reviewMessage.getCompanyId() == null) {
            System.out.println("Skipping message with null companyId");
            return;
        }
        Company company = companyRepository.findById(reviewMessage.getCompanyId())
                .orElseThrow(()-> new NotFoundException("Company not found"+ reviewMessage.getCompanyId()));

        double averageRating = reviewClient.getAverageRatingForCompany(reviewMessage.getCompanyId());
        company.setRating(averageRating);
        companyRepository.save(company);
    }

}
