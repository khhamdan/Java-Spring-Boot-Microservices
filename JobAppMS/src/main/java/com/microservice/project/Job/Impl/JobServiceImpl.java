package com.microservice.project.Impl;


import com.microservice.project.Job.Job;
import com.microservice.project.Job.JobRepository;
import com.microservice.project.Job.JobService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


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
    public List<Job> findAll()
    {
        return jobRepository.findAll();
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
    public boolean deleteJobById(Long id) {
        Iterator<Job> iterator = jobs.iterator();
        while(iterator.hasNext())
        {
            Job job = iterator.next();
            if(job.getId().equals(id))
            {
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean updateJob(Long id, Job updatedJob) {
        for(Job job: jobs)
        {
            if(job.getId().equals(id))
            {
                job.setTitle(updatedJob.getTitle());
                job.setDescription(updatedJob.getDescription());
                job.setMinSalary(updatedJob.getMinSalary());
                job.setMaxSalary(updatedJob.getMaxSalary());
                job.setLocation(updatedJob.getLocation());
                return true;
            }
        }

        return false;
    }

}
