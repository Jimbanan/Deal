package com.neoflex.deal.models.employment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmploymentRepository extends JpaRepository<Employment, Long> {
}
