package com.cg.sp.repository;

import com.cg.sp.entity.AcquiringPremiumEntity;
import com.cg.sp.model.converter.AcquiringPremiumDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface AcquiringPremiumRepository extends JpaRepository<AcquiringPremiumEntity, Integer> {
    @Query(value = "SELECT SUM(a.net_volume) AS totalVolume,"+
            "SUM(a.net_value) AS totalTransactionValue, " +
            "a.trans_date AS transactionDate, a.terminal_id AS terminalId, " +
            "a.city, a.region, a.location, a.mcc, a.mcc_description AS mccDescription\n" +
            "FROM acquiring_premium AS a\n" +
            "WHERE (a.cr_number = :crNumber)\n" +
            "AND\n" +
            "a.trans_date BETWEEN :startDate AND :endDate\n" +
            "GROUP BY a.terminal_id,a.trans_date, a.region, a.city, a.location, a.mcc, a.mcc_description\n",
            nativeQuery = true)
    List<AcquiringPremiumDTO> fetchPremiumDetailsWithCrNumber(@Param("crNumber") Long crNumber,
                                                             @Param("startDate") Date startDate,
                                                             @Param("endDate") Date endDate,
                                                             Pageable pageable);
}
