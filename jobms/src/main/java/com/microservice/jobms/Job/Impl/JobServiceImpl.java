package com.microservice.jobms.Job.Impl;


import com.microservice.jobms.Job.Job;
import com.microservice.jobms.Job.JobRepository;
import com.microservice.jobms.Job.JobService;
import com.microservice.jobms.Job.dto.JobWithCompanyDTO;
import com.microservice.jobms.Job.external.Company;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
//it will inject it and make it available runtime and it do the loose couple
public class JobServiceImpl implements JobService
{
    private final JobRepository jobRepository;
    JobRepository JobRepository;

    public JobServiceImpl(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

//    private List<Job> jobs = new ArrayList<>();

    @Override
    public List<JobWithCompanyDTO> findAll() {
        List<Job> jobs = jobRepository.findAll();
        List<JobWithCompanyDTO> jobWithCompanyDTOList = new ArrayList<>();

        return jobs.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    private JobWithCompanyDTO convertToDto(Job job)
    {
            JobWithCompanyDTO jobWithCompanyDTO = new JobWithCompanyDTO();
            jobWithCompanyDTO.setJob(job);
            RestTemplate restTemplate = new RestTemplate();

            Company company = restTemplate.getForObject("http://localhost:8081/companies/" + job.getCompanyId() , Company.class);

            jobWithCompanyDTO.setCompany(company);
            return jobWithCompanyDTO;
    }

    @Override
    public void createJob(Job job) {
        jobRepository.save(job);
      }

      @Override
    public Job getJobById(Long id) {
        return jobRepository.findById(id).orElse(null);
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
