package com.thewayhome.project.domain;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "etc_facility")
public class EtcFacility {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    // 화재경보기
    @Column(name = "fire_alarm", columnDefinition = "boolean default false")
    private Boolean fireAlarm;

    // 무인택배함
    @Column(name = "unmanned_parcel_box", columnDefinition = "boolean default false")
    private Boolean unmannedParcelBox;

    // 마당
    @Column(name = "yard", columnDefinition = "boolean default false")
    private Boolean yard;
}
