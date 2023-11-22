package com.thewayhome.project.repository;

import com.thewayhome.project.domain.HeatingFacility;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HeatingFacilityRepository extends JpaRepository<HeatingFacility, Long> {
}