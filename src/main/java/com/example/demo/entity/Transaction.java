package com.example.demo.entity;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.id.UUIDGenerator;

import com.example.demo.common.enums.PaymentMethodEnum;
import com.example.demo.common.enums.PaymentStatusEnum;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "transaction")
public class Transaction extends BaseAuditedEntity {
	@Id
	@GenericGenerator(name = "uuid", type = UUIDGenerator.class)
	@GeneratedValue(generator = "uuid")
	private String id;

	@Column(name = "nett_amt_paid") 
	private BigDecimal nettAmountPaid;

	@Column(name = "total_amt_paid") 
	private BigDecimal totalAmountPaid;

	@Column(name = "total_tax_paid") 
	private BigDecimal totalTaxPaid;

	@Column(name = "tx_time") 
	private Instant txTime;

	@Enumerated(EnumType.STRING)
	@Column(name = "payment_status")
	private PaymentStatusEnum paymentStatus;

	@Enumerated(EnumType.STRING)
	@Column(name = "payment_method")
	private PaymentMethodEnum paymentMethod;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customer_id")
	private Customer customer;

	@JsonManagedReference
	@OneToMany(mappedBy = "transaction", fetch = FetchType.LAZY)
	private List<TransactionItem> transactionItems;
}
