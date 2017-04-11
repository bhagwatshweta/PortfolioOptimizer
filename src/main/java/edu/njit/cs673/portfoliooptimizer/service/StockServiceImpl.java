package edu.njit.cs673.portfoliooptimizer.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.njit.c673.portfoliooptimizer.model.PortfolioStock;
import edu.njit.c673.portfoliooptimizer.model.StockInventory;
import edu.njit.c673.portfoliooptimizer.model.StockPerformance;
import edu.njit.cs673.portfoliooptimizer.dao.StockInventoryDao;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.quotes.stock.StockQuote;

@Service
@Transactional
public class StockServiceImpl implements StockService {

	private static final Logger log = Logger.getLogger(StockServiceImpl.class);

	@Autowired
	StockInventoryDao stockinventory;
	
	@Override
	public Map<String, Stock> getStockQuotes(String[] stockSymbols) {
		try {
			return YahooFinance.get(stockSymbols);
		} catch (IOException e) {		
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<StockPerformance> getStockPerformance(List<PortfolioStock> portfolioStocks) throws IOException {

		Map<String, BigDecimal> stockBuyPriceMap1 = new HashMap<String, BigDecimal>();
		Map<String, BigDecimal> stockBuyPriceMap2 = new HashMap<String, BigDecimal>();
		Map<String, BigDecimal> stockshareQuantityMap = new HashMap<String,BigDecimal>();
		int PortfolioID=0;
		for (PortfolioStock stock : portfolioStocks) {
			if(stock.getStockExchangeType().getStockExchangeId() == 1){
			stockBuyPriceMap1.put(stock.getStockSymbol(), stock.getPurchasePrice());
			}else if(stock.getStockExchangeType().getStockExchangeId() == 2){
			stockBuyPriceMap2.put(stock.getStockSymbol(), stock.getPurchasePrice());
			}
			stockshareQuantityMap.put(stock.getStockSymbol(), stock.getShareQuantity());
			PortfolioID =stock.getPortfolio().getPortfolioId();
		}
		
		

		Map<String, Stock> stockMap = new HashMap<String,Stock>();

		List<StockPerformance> stockPerformanceList = new ArrayList<StockPerformance>();

		if(stockBuyPriceMap1.keySet().size() > 0) 
		{
			stockMap = YahooFinance.get(stockBuyPriceMap1.keySet().toArray(new String[stockBuyPriceMap1.keySet().size()]));
		}
		stockMap.putAll(getHistoricalDataForNSEStock(stockBuyPriceMap2.keySet()));
		
		
		stockBuyPriceMap1.putAll(stockBuyPriceMap2);
		
		for (Map.Entry<String, Stock> entry : stockMap.entrySet()) {
			StockPerformance stockPerformance = new StockPerformance();

			for (PortfolioStock stock : portfolioStocks) {

				if (stock.getStockSymbol().equals(entry.getKey())) {
					stockPerformance.setStock(stock);
					break;
				}
			}

			if (entry.getValue().getQuote() != null) {
				stockPerformance.setStockSymbol(entry.getKey());				
				stockPerformance.setLastPrice(entry.getValue().getQuote().getPreviousClose());
				// stockPerformance.setCostBasis(new BigDecimal(0));
				// stockPerformance.setMarketValue(new BigDecimal(0));

				BigDecimal buyPrice = stockBuyPriceMap1.get(entry.getKey());				
				BigDecimal stockQuantity = stockshareQuantityMap.get(entry.getKey());
				BigDecimal currPrice = entry.getValue().getQuote().getPrice();
				
				BigDecimal gain = null;
				BigDecimal gainper = null;
				BigDecimal costBasis = null;
				BigDecimal mktvalue = null;
						
				if (currPrice != null) {
					//gain = currPrice.subtract(buyPrice);
					//costBasis = buyPrice.multiply(stockQuantity);
					
					costBasis = getCostBases(entry.getKey(),PortfolioID);
					log.debug("The costBasis calculated as: "+costBasis );
					stockPerformance.setCostBasis(costBasis);
					mktvalue = currPrice.multiply(stockQuantity);
					stockPerformance.setMarketValue(mktvalue);
					
					gain = mktvalue.add(costBasis.negate());
					gainper = gain.multiply(new BigDecimal(100));
					gainper = gainper.divide(costBasis,2, RoundingMode.HALF_UP);
					stockPerformance.setGain(gain);
					stockPerformance.setChange(entry.getValue().getQuote().getChange());
					//gainper = (gain.divide(buyPrice,RoundingMode.HALF_UP).multiply(new BigDecimal(100)));
					stockPerformance.setGainPercentage(gainper);
					stockPerformance.setOverallReturn(gainper);
					stockPerformance.setShares(stockQuantity);
					
				}												

				stockPerformanceList.add(stockPerformance);
			} else {
				log.error("Invalid stock symbol - " + entry.getKey());
			}
		}

		return stockPerformanceList;
	}

	public BigDecimal getCostBases(String StockSymbol,int PortfolioID)
	{
		BigDecimal CostBasis = stockinventory.getCostBases(StockSymbol, PortfolioID);
		return CostBasis;
	}
	
	public void addCash(int portfolioId, BigDecimal cash){
		
	}


	@Override
	public List<StockInventory> getStockFromInventory() {
		
		return stockinventory.getAllStockInventory();
	}

	

	@Override
	public Map<String,Stock> getHistoricalDataForNSEStock(Set<String> stockQuotes) {

		Map<String, Stock> returnList = new HashMap<String, Stock>();
		
		for(String stockQuote: stockQuotes){
		
			if(stockQuote == null || stockQuote.trim().isEmpty()){
				continue;
			}
		StockQuote quote = new StockQuote(stockQuote);
		Stock stock = new Stock(stockQuote);

		URL url = null;

		try {
			 Date date = new Date(); // your date
			    Calendar cal = Calendar.getInstance();
			    cal.setTime(date);
			    int f = cal.get(Calendar.YEAR);
			    int d = cal.get(Calendar.MONTH);
			    int e = cal.get(Calendar.DAY_OF_MONTH);
			    d+=1;
			    Calendar calendar = Calendar.getInstance(); // this would default to now
			    calendar.add(Calendar.DAY_OF_MONTH, -90);
			    int c = calendar.get(Calendar.YEAR);
			    int a = calendar.get(Calendar.MONTH);
			    int b = calendar.get(Calendar.DAY_OF_MONTH);
			    a+=1;
			url = new URL("http://chart.finance.yahoo.com/table.csv?s=" + stockQuote.trim()
					+ ".NS&a="+8+"&b="+12+"&c="+2016+"&d="+8+"&e="+12+"&f="+2016+"&g=d&ignore=.csv");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		File temp = new File(System.getProperty("java.io.tmpdir") + File.separator + "NSE_STOCK_PRICES.csv");

		try {
			FileUtils.copyURLToFile(url, temp);
		} catch (IOException e) {
			e.printStackTrace();
		}

		CSVParser parser = null;

		try {
			parser = new CSVParser(new FileReader(temp), CSVFormat.DEFAULT);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {

			CSVRecord record = parser.getRecords().get(1);

			stock.setCurrency("U+20B9");
			quote.setOpen(new BigDecimal(record.get(1)));
			quote.setPrice(new BigDecimal(record.get(6)));
			quote.setDayHigh(new BigDecimal(record.get(2)));
			quote.setDayLow(new BigDecimal(record.get(3)));
			quote.setPreviousClose(new BigDecimal(record.get(4)));
			quote.setVolume(new Long(record.get(5)));
			stock.setQuote(quote);

			returnList.put(stockQuote, stock);
		} catch (IOException e) {
			e.printStackTrace();
		}
		}
		
		return returnList;
		
	}
}

