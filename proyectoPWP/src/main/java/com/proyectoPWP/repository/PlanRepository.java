package com.proyectoPWP.repository;

import com.proyectoPWP.domain.plan.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanRepository extends JpaRepository<Plan, Long> {
}
