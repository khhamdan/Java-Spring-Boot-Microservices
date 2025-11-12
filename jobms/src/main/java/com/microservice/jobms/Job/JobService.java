package com.microservice.jobms.Job;

import com.microservice.jobms.Job.dto.JobDTO;

import java.util.List;

//it is used for loose coupling and have some modularity
public interface JobService
{
    List<JobDTO> findAll();
    void createJob(Job job);
    JobDTO getJobById(Long id);
    boolean deleteJobById(Long id);
    boolean updateJob(Long id, Job updatedJob);
}
