package edu.njit.cs673.portfoliooptimizer.optimizer;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.stat.correlation.Covariance;

import edu.njit.c673.portfoliooptimizer.model.PortfolioStock;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.Interval;

public class Optimizer {
	
	double portfolioExpectedReturn;
	
	private static final List<BigDecimal> getHistoricalAdjClose(Map.Entry<String, Stock> historicalPrices) {
		
		List<BigDecimal> historicalAdjClose = null;
		try {
			historicalAdjClose = historicalPrices.getValue().getHistory().stream().sorted((x, y) -> {
				return x.getDate().compareTo(y.getDate());
			}).map(e -> e.getAdjClose()).collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
		}

		return historicalAdjClose;
	}

	private static final List<BigDecimal> calculateMonthlyReturns(List<BigDecimal> adjCloseList) {
		List<BigDecimal> monthlyReturns = new ArrayList<BigDecimal>();

		boolean firstElement = true;

		BigDecimal previousMonthReturn = BigDecimal.ZERO;
		for (BigDecimal temp : adjCloseList) {

			if (firstElement == true) {
				previousMonthReturn = temp;
				firstElement = false;
				continue;
			}
			monthlyReturns.add(temp.subtract(previousMonthReturn)
								   .divide(previousMonthReturn, 6, RoundingMode.HALF_EVEN)
								   .multiply(new BigDecimal(100)));
			previousMonthReturn = temp;
		}

		return monthlyReturns;
	}			
	
	public static void main(String[] args) {
		
		PortfolioStock acn = new PortfolioStock();
		acn.setShareQuantity(new BigDecimal(10));
		acn.setStockSymbol("ACN");
		
		PortfolioStock ibm = new PortfolioStock();
		ibm.setShareQuantity(new BigDecimal(10));
		ibm.setStockSymbol("IBM");
	
		
		PortfolioStock acn1 = new PortfolioStock();
		acn1.setShareQuantity(new BigDecimal(5));
		acn1.setStockSymbol("ACN");
		
		List<PortfolioStock> stocks = new ArrayList<>();
		
		stocks.add(acn);
		stocks.add(ibm);
		stocks.add(acn1);
		new Optimizer().optimize(stocks);
		
	}
	
	public List<StockStatistics> optimize(List<PortfolioStock> stocks) {

		final Map<String,Integer> stockWeightMap = new HashMap<>(); 
		
		for(PortfolioStock stock:stocks){
		
			String symbol =stock.getStockSymbol();
			
			if(stockWeightMap.containsKey(symbol)){
				
				Integer stockQuantity = stockWeightMap.get(symbol);
				stockWeightMap.put(symbol,stockQuantity.intValue()+stock.getShareQuantity().intValue());
				
			}else{
				stockWeightMap.put(symbol,stock.getShareQuantity().intValue());
			}
		}
		
		
		Calendar from = Calendar.getInstance();
		from.add(Calendar.YEAR, -5);

		Calendar to = Calendar.getInstance();

		//String[] stockSymbols = { "ACN","IBM" };
		
		List<StockStatistics> statistics = new ArrayList<>();
		
		for(String stockSymbol:stocks.stream().map(e -> e.getStockSymbol()).distinct().collect(Collectors.toList())){
		
		StockStatistics tempStats = new StockStatistics();
			
		String[] tempStockSymbols = {stockSymbol};
			
		Map<String, Stock> historicalDataMap = null;
		try {
			historicalDataMap = YahooFinance.get(tempStockSymbols, from, to, Interval.MONTHLY);
		} catch (IOException e) {
			try {
				historicalDataMap = YahooFinance.get(new String[] {tempStockSymbols[0]+".NS"}, from, to, Interval.MONTHLY);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
		}				

		tempStats.setStockSymbol(stockSymbol);
		tempStats.setMonthlyReturns(historicalDataMap.entrySet().stream()
				 .map(Optimizer::getHistoricalAdjClose)
				 .map(Optimizer::calculateMonthlyReturns)
				 .collect(Collectors.toList())
				 .get(0));
		tempStats.setAverageMonthlyReturn(averageBigDecimal(tempStats.getMonthlyReturns()));						
		calculateExcessMonthlyReturns(tempStats);
		tempStats.setNoOfShares(stockWeightMap.get(stockSymbol));
		tempStats.setSuggestedNoOfShares(stockWeightMap.get(stockSymbol));
		statistics.add(tempStats);				
		}
		
		List<StockStatistics> statisticsClone = new ArrayList<>();
		
		statisticsClone.addAll(statistics);
		for(StockStatistics stat: statistics){
			
			for(StockStatistics otherStat: statisticsClone){								
				
				double[] thisStatMonthlyReturns = new double[stat.getMonthlyReturns().size()];
				double[] otherStatMonthlyReturns = new double[otherStat.getMonthlyReturns().size()];
				
				for(int i=0;i< stat.getMonthlyReturns().size();i++){
					thisStatMonthlyReturns[i] =stat.getMonthlyReturns().get(i).doubleValue();
				}
				
				for(int i=0;i< otherStat.getMonthlyReturns().size();i++){
					otherStatMonthlyReturns[i] =otherStat.getMonthlyReturns().get(i).doubleValue();
				}				
				
				Covariance covariance = new Covariance();
				
				double covarianceVal = covariance.covariance(thisStatMonthlyReturns, otherStatMonthlyReturns,false);
				
				System.out.println("Covarience between " + stat.getStockSymbol() +" and "+ otherStat.getStockSymbol()+ " is - " + covarianceVal);
				
				if(stat.getCovarience() == null){
					Map<String,Double> covarianceMap = new HashMap<>();									
					covarianceMap.put(otherStat.getStockSymbol(), covarianceVal);
					stat.setCovarience(covarianceMap);
				}else{
					stat.getCovarience().put(otherStat.getStockSymbol(), covarianceVal);
				}
			}
			
		}
		
		double[] averageMonthlyReturnsOfPortfolio = new double[statistics.size()];
		double[] weightsPerShare = new double[statistics.size()];
		
		int totalShares = 0;
		
		for(int i=0;i<statistics.size();i++){
			averageMonthlyReturnsOfPortfolio[i] = statistics.get(i).getAverageMonthlyReturn().doubleValue();
			//weightsPerShare[i] = statistics.get(i).getNoOfShares();
			
			totalShares +=statistics.get(i).getNoOfShares();
		}
		
		for(int i=0;i<statistics.size();i++){
			
			weightsPerShare[i] =(100*statistics.get(i).getNoOfShares())/totalShares;
		}
		
		RealMatrix m1 = new Array2DRowRealMatrix(averageMonthlyReturnsOfPortfolio);
		RealMatrix m2 = new Array2DRowRealMatrix(weightsPerShare);
		RealMatrix portfolioExpectedReturnMatrix = m2.transpose().multiply(m1);
		
		double[] row0 =portfolioExpectedReturnMatrix.getRow(0);
		portfolioExpectedReturn  =row0[0]/100;
		
		System.out.println(portfolioExpectedReturn);
				
		System.out.println("done");
		
		return statistics;
	}
	
	private BigDecimal averageBigDecimal(List<BigDecimal> datapoints){
		
		if(datapoints == null || datapoints.size()==0){
			return BigDecimal.ZERO;
		}
		
		BigDecimal size = new BigDecimal(datapoints.size());
		BigDecimal sum = BigDecimal.ZERO;
		for(BigDecimal datapoint: datapoints){
			sum = sum.add(datapoint);
		}
		
		return sum.divide(size, RoundingMode.CEILING);
	}
	
	private void calculateExcessMonthlyReturns(StockStatistics stats){
		
		stats.setExcessMonthlyReturns(new ArrayList<BigDecimal>());
		
		stats.getMonthlyReturns().stream()
								 .forEach(e -> stats.getExcessMonthlyReturns().add(e.subtract(stats.getAverageMonthlyReturn())));
	}
}
