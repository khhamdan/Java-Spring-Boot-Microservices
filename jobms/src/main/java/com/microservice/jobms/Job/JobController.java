package com.microservice.jobms.Job;

import com.microservice.jobms.Job.external.Company;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
//sets the baseURL of all the controller, reduce the repevghryuryvjhyujtition of base url
@RequestMapping("/jobs")
public class JobController
{
    private JobService jobService;

//    this is injected and loosly coupled
    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @GetMapping
    public ResponseEntity<List<Job>> findAll(){
        RestTemplate restTemplate = new RestTemplate();
        Company company = restTemplate.getForObject("http://localhost:8081/companies/1", Company.class);
//        converting url response to this class
        System.out.println("Company:" + company.getName());
        System.out.println("Company:" + company.getId());
        return ResponseEntity.ok(jobService.findAll());
    }

    @PostMapping
    public ResponseEntity<String> createJob(@RequestBody Job job){
        jobService.createJob(job);
        return new ResponseEntity<>("Job added Successfully", HttpStatus.CREATED);
    }

// this is dynamic and url name
    @GetMapping("/{id}")
    private ResponseEntity<Job> findAll(@PathVariable Long id){
        Job job = jobService.getJobById(id);
        if(job == null)
            return new ResponseEntity<>(job, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteJob(@PathVariable Long id){
        boolean deleted = jobService.deleteJobById(id);
        if(deleted)
        {
            return new ResponseEntity<>("Job Deleted Successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateJob(@PathVariable Long id,@RequestBody Job updatedJob)
    {
        boolean updated = jobService.updateJob(id,updatedJob);
        if(updated)
        {
            return new ResponseEntity<>("Job updated Successfully",HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

}
