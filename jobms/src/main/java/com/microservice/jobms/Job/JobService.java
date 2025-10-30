package com.microservice.jobms.Job;

import com.microservice.jobms.Job.dto.JobWithCompanyDTO;

import java.util.List;

//it is used for loose coupling and have some modularity
public interface JobService
{
    List<JobWithCompanyDTO> findAll();
    void createJob(Job job);
    Job getJobById(Long id);
    boolean deleteJobById(Long id);
    boolean updateJob(Long id, Job updatedJob);
}
