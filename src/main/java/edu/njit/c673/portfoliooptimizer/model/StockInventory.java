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
@Table(name = "STOCK_INVENTORY", schema = "tmp33")
public class StockInventory implements Serializable {

	private static final long serialVersionUID = -6329700262003901201L;

	@Id
	@Column(name = "STOCK_INVENTORY_ID")
	@GeneratedValue(generator = "STOCK_INVENTORY_SEQ", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "STOCK_INVENTORY_SEQ", sequenceName = "STOCK_INVENTORY_SEQ", schema = "tmp33", allocationSize = 1)
	private int stockId;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "STOCK_EXCHANGE_ID", foreignKey = @ForeignKey(name = "FK_STK_INV_EX_TYP") )
	private StockExchangeType stockExchangeType;

	@Column(name = "STOCK_SYMBOL")
	private String stockSymbol;

	@Column(name = "STOCK_NAME")
	private String stockName;

	public int getStockId() {
		return stockId;
	}

	public void setStockId(int stockId) {
		this.stockId = stockId;
	}

	public StockExchangeType getStockExchangeType() {
		return stockExchangeType;
	}

	public void setStockExchangeType(StockExchangeType stockExchangeType) {
		this.stockExchangeType = stockExchangeType;
	}

	public String getStockSymbol() {
		return stockSymbol;
	}

	public void setStockSymbol(String stockSymbol) {
		this.stockSymbol = stockSymbol;
	}

	public String getStockName() {
		return stockName;
	}

	public void setStockName(String stockName) {
		this.stockName = stockName;
	}

}
