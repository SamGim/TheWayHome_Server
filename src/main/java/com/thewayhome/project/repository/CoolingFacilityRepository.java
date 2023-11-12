package com.thewayhome.project.repository;

import com.thewayhome.project.domain.CoolingFacility;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoolingFacilityRepository extends JpaRepository<CoolingFacility, Long> {
}