package com.microservice.jobms.Job.Impl;


import com.microservice.jobms.Job.Job;
import com.microservice.jobms.Job.JobRepository;
import com.microservice.jobms.Job.JobService;
import com.microservice.jobms.Job.clients.CompanyClient;
import com.microservice.jobms.Job.clients.ReviewClient;
import com.microservice.jobms.Job.dto.JobDTO;
import com.microservice.jobms.Job.external.Company;
import com.microservice.jobms.Job.external.Review;
import com.microservice.jobms.Job.mapper.JobMapper;
import org.apache.http.client.methods.HttpGet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.view.freemarker.FreeMarkerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
//it will inject it and make it available runtime and it do the loose couple
public class JobServiceImpl implements JobService
{
    private final JobRepository jobRepository;

    JobRepository JobRepository;

    @Autowired
    RestTemplate restTemplate;

    private CompanyClient companyClient;
    private ReviewClient reviewClient;

    public JobServiceImpl(JobRepository jobRepository, CompanyClient companyClient,ReviewClient reviewClient) {
        this.jobRepository = jobRepository;
        this.companyClient = companyClient;
        this.reviewClient = reviewClient;
    }

//    private List<Job> jobs = new ArrayList<>();

    @Override
    public List<JobDTO> findAll() {
        List<Job> jobs = jobRepository.findAll();
        List<JobDTO> jobDTOList = new ArrayList<>();

        return jobs.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    private JobDTO convertToDto(Job job)
    {
        Company company = companyClient.getCompany(job.getCompanyId());

        List<Review> reviews = reviewClient.getReviews(job.getCompanyId());

        JobDTO jobDTO = JobMapper.mapJobToJobWithCompanyDTO(job, company, reviews);

            return jobDTO;
    }

    @Override
    public void createJob(Job job) {
        jobRepository.save(job);
      }

      @Override
    public JobDTO getJobById(Long id) {
         Job job = jobRepository.findById(id).orElse(null);
          return convertToDto(job);
    }

    @Override
    public boolean deleteJobById(Long id)
    {
        try {
            jobRepository.deleteById(id);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean updateJob(Long id, Job updatedJob)
    {
        Optional<Job> jobOptional = jobRepository.findById(id);
        if(jobOptional.isPresent()) {
            Job job = jobOptional.get();
            job.setTitle(updatedJob.getTitle());
            job.setDescription(updatedJob.getDescription());
            job.setMinSalary(updatedJob.getMinSalary());
            job.setMaxSalary(updatedJob.getMaxSalary());
            job.setLocation(updatedJob.getLocation());
            jobRepository.save(job);
            return true;
        }
        return false;
    }

}
