package com.cg.sp.repository;

import com.cg.sp.entity.AcquiringStandardEntity;
import com.cg.sp.model.converter.AcquiringStandardDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface AcquiringStandardRepository extends JpaRepository<AcquiringStandardEntity,Integer> {

    @Query(value = "SELECT SUM(a.net_volume) AS totalVolume, " +
            "SUM(a.net_value) AS totalTransactionValue, a.terminal_id AS terminalId" +
            " FROM acquiring_standard AS a" +
            " WHERE (a.cr_number = :crNumber)" +
            " AND" +
            " a.trans_date BETWEEN :startDate AND :endDate AND a.channel = 'PoS'" +
            " GROUP BY a.terminal_id; ", nativeQuery = true)
    List<AcquiringStandardDTO> fetchStandardDetailsWithCrNumber(@Param("crNumber") Long crNumber,
                                                               @Param("startDate") Date startDate,
                                                               @Param("endDate") Date endDate,
                                                                Pageable pageable);
}
