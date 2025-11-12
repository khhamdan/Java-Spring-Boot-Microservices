package com.microservice.jobms.Job;

import com.microservice.jobms.Job.dto.JobDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<List<JobDTO>> findAll(){
        return ResponseEntity.ok(jobService.findAll());
    }

    // this is dynamic and url name
    @GetMapping("/{id}")
    private ResponseEntity<JobDTO> findAll(@PathVariable Long id){
        JobDTO jobDTO = jobService.getJobById(id);
        if(jobDTO == null)
            return new ResponseEntity<>(jobDTO, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<String> createJob(@RequestBody Job job){
        jobService.createJob(job);
        return new ResponseEntity<>("Job added Successfully", HttpStatus.CREATED);
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
