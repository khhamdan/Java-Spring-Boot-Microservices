package com.microservice.reviewms.Review;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long>
{

//    this handle on runtime
    List<Review> findByCompanyId(Long companyId);
}
