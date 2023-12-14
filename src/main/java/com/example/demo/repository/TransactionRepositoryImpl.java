package com.example.demo.repository;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.data.domain.Sort;

import com.example.demo.common.enums.PaymentMethodEnum;
import com.example.demo.common.enums.PaymentStatusEnum;
import com.example.demo.dto.tx.TxSearchDTO;
import com.example.demo.dto.tx.TxSearchResponse;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.Tuple;

public class TransactionRepositoryImpl {

	@PersistenceContext
	EntityManager em;

	String selectQuery = "SELECT tx.id as id, c.name as customer_name, tx.tx_time as tx_time, tx.nett_amt_paid as nett_amt_paid, tx.total_amt_paid as total_amt_paid, tx.total_tax_paid as total_tax_paid, tx.payment_status as payment_status, tx.payment_method as payment_method, tx.created_by as created_by FROM transaction tx INNER JOIN customer c on tx.customer_id = c.id";

	private List<TxSearchResponse> tupleToSearchResult(List<Tuple> tuples) {
		List<TxSearchResponse> results = new ArrayList<>();
		if (!Objects.isNull(tuples)) {
		    tuples.forEach(tuple -> results.add(TxSearchResponse
				.builder()
				.id(tuple.get(0, String.class))
				.customerName(tuple.get(1, String.class))
				.txTime(tuple.get(2, Timestamp.class).toInstant())
				.nettAmtPaid(tuple.get(3, BigDecimal.class))
				.totalAmtPaid(tuple.get(4, BigDecimal.class))
				.totalTaxPaid(tuple.get(5, BigDecimal.class))
				.paymentStatus(PaymentStatusEnum.valueOf(tuple.get(6, String.class)))
				.paymentMethod(PaymentMethodEnum.valueOf(tuple.get(7, String.class)))
				.createdBy(tuple.get(8, String.class))
				.build()
			));
		}
		return results;
	}

	public List<TxSearchResponse> searchWithFilters(TxSearchDTO searchDTO) {
		List<String> predicates = new ArrayList<>();
		Map<String, Object> parameters = new HashMap<>();
		if(Objects.nonNull(searchDTO.name())){
			predicates.add("c.name ILIKE :cname");
			parameters.put("cname", "%"+searchDTO.name()+"%");
		}
		if(Objects.nonNull(searchDTO.dateStart()) 
			&& Objects.nonNull(searchDTO.dateEnd())
			&& searchDTO.dateStart().compareTo(searchDTO.dateEnd()) < 0
			){
			predicates.add("tx.tx_time BETWEEN :dstart AND :dend");
			parameters.put("dstart", searchDTO.dateStart());
			parameters.put("dend", searchDTO.dateEnd());
		}
		if(Objects.nonNull(searchDTO.paymentStatus())){
			predicates.add("tx.payment_status IN :pstatus");
			parameters.put("pstatus", searchDTO.paymentStatus().stream().map(p -> p.toString()).toList()); 
		}
		if(Objects.nonNull(searchDTO.paymentMethod())){
			predicates.add("tx.payment_method IN :pmethod");
			parameters.put("pmethod",searchDTO.paymentMethod().stream().map(p -> p.toString()).toList()); 
		}
		if(Objects.nonNull(searchDTO.createdBy())){
			predicates.add("tx.created_by = :crby");
			parameters.put("crby", searchDTO.createdBy()); 
		}
		String direction = Objects.isNull(searchDTO.direction()) || searchDTO.direction().equals(Sort.Direction.ASC)?"ASC":"DESC";
		String orderBy = "ORDER BY tx.tx_time "+ direction;
		String andPredicates = String.join(" AND ", predicates);
		String mainQueryStr = String.join(" ",selectQuery,"WHERE",andPredicates,orderBy);
		Query mainQuery = em.createNativeQuery(mainQueryStr, Tuple.class);
		parameters.keySet().stream().forEach(k -> mainQuery.setParameter(k, parameters.get(k)));
		List<TxSearchResponse> resultList = tupleToSearchResult(mainQuery.getResultList());
		return resultList;
	}
}
