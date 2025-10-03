package com.microservice.project.Job;

import java.util.List;

//it is used for loose coupling and have some modularity
public interface JobService
{
    List<Job> findAll();
    void createJob(Job job);
    Job getJobById(Long id);
    boolean deleteJobById(Long id);
    boolean updateJob(Long id, Job updatedJob);
}
