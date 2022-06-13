package com.neoflex.deal.models.credit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditRepository extends JpaRepository<Credit, Long> {

    Credit findTopByOrderByIdDesc();

}
