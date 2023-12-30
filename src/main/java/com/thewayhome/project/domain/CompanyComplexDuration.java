package com.thewayhome.project.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "company_complex_duration")
public class CompanyComplexDuration {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ccd_id")
    private Long ccdId;
    @Column(name = "min_duration")
    private Integer minDuration;
    @Column(name = "max_duration")
    private Integer maxDuration;

}
