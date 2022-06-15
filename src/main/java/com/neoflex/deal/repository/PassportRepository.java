package com.neoflex.deal.repository;

import com.neoflex.deal.models.Passport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PassportRepository extends JpaRepository<Passport, Long> {
    Passport findTopByOrderByIdDesc();
}
