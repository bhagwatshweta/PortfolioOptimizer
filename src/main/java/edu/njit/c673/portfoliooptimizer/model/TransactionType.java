package edu.njit.c673.portfoliooptimizer.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "TRANSACTION_TYPE", schema = "tmp33")
public class TransactionType implements Serializable {

	private static final long serialVersionUID = -1553611478956249774L;

	@Id
	@Column(name = "TXN_TYPE_ID")
	@GeneratedValue(generator = "TXN_TYPE_SEQ", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "TXN_TYPE_SEQ", sequenceName = "TXN_TYPE_SEQ", schema = "tmp33", allocationSize = 1)
	private int transactionTypeId;

	@Column(name = "DESCRIPTION", length = 50)
	private String Description;
	
	public int getTransactionTypeId() {
		return transactionTypeId;
	}

	public void setTransactionTypeId(int transactionTypeId) {
		this.transactionTypeId = transactionTypeId;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}
}
