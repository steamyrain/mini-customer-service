package com.example.demo.repository;

import java.time.Instant;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.dto.tx.ITxGenCusReportBetween;
import com.example.demo.dto.tx.TxSearchDTO;
import com.example.demo.dto.tx.TxSearchResponse;
import com.example.demo.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction,String> {
	public List<TxSearchResponse> searchWithFilters(TxSearchDTO searchDTO);

	@Query(nativeQuery = true, value = "SELECT c.name as customerName, tx.customer_id as customerId, SUM(tx.total_amt_paid) as sumTxTotalAmtPaid FROM transaction tx INNER JOIN customer c on tx.customer_id = c.id WHERE tx.tx_time BETWEEN :dateStart AND :dateEnd AND tx.customer_id = :customerId GROUP BY tx.customer_id, c.name")
	public ITxGenCusReportBetween genCusReportBetween(@Param("customerId") String customerId, @Param("dateStart") Instant dateStart, @Param("dateEnd") Instant dateEnd);

	@Query(nativeQuery = true, value = "SELECT c.name as customerName, tx.customer_id as customerId, SUM(tx.total_amt_paid) as sumTxTotalAmtPaid FROM transaction tx INNER JOIN customer c on tx.customer_id = c.id WHERE tx.customer_id = :customerId GROUP BY tx.customer_id, c.name")
	public ITxGenCusReportBetween genCusReport(@Param("customerId") String customerId); 
}
