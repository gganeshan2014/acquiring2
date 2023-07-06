package com.cg.sp.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ACQUIRING_PREMIUM")
@Builder
public class AcquiringPremiumEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "TERMINAL_ID")
    private String terminalId;

    @Temporal(value = TemporalType.DATE)
    @JsonFormat(pattern = "dd-MM-yyyy", shape = JsonFormat.Shape.STRING)
    @Column(name = "TRANS_DATE")
    private Date transactionDate;

    private String channel;

    @Column(name = "NET_VOLUME")
    private Integer volume;

    @Column(name = "NET_VALUE")
    private Double value;


    private String region;
    private String city;
    private String location;
    private String mcc;

    @Column(name = "MCC_DESCRIPTION")
    private String mccDescription;

    @Column(name = "CR_NUMBER")
    private Long crNumber;
}
