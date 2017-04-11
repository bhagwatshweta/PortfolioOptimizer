package edu.njit.c673.portfoliooptimizer.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "STOCK_EXCHANGE_TYPE", schema = "tmp33")
public class StockExchangeType implements Serializable {

	private static final long serialVersionUID = 8140980795821186400L;

	@Id
	@Column(name = "STOCK_EXCHANGE_ID")
	@GeneratedValue(generator = "STK_EX_ID_SEQ", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "STK_EX_ID_SEQ", sequenceName = "STK_EX_ID_SEQ", allocationSize = 1)
	private int stockExchangeId;

	@Column(name = "STOCK_EXCHANGE_NAME", length = 100, nullable = false)
	private String stockExchangeName;

	@Column(name = "CURRENCY_NAME", length = 20, nullable = false)
	private String currencyName;

	@Column(name = "CURRENCY_SYMBOL", length = 3, nullable = false)
	private String currencySymbol;

	public int getStockExchangeId() {
		return stockExchangeId;
	}

	public void setStockExchangeId(int stockExchangeId) {
		this.stockExchangeId = stockExchangeId;
	}

	public String getStockExchangeName() {
		return stockExchangeName;
	}

	public void setStockExchangeName(String stockExchangeName) {
		this.stockExchangeName = stockExchangeName;
	}

	public String getCurrencyName() {
		return currencyName;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}

	public String getCurrencySymbol() {
		return currencySymbol;
	}

	public void setCurrencySymbol(String currencySymbol) {
		this.currencySymbol = currencySymbol;
	}

}
