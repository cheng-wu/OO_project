package consumer;

public class Stock {
	protected String stockName;
	protected double sum_bidPrice_multiplies_quantity;
	protected double sum_askPrice_multiplies_quantity;
	protected double sum_bidPrice_square_multiplies_quantity;
	protected double sum_askPrice_square_multiplies_quantity;
	protected int sum_bidQuantity;
	protected int sum_askQuantity;
	
	public Stock(String stockName){
		this.stockName = stockName;
		this.sum_bidPrice_multiplies_quantity = 0;
		this.sum_askPrice_multiplies_quantity = 0;
		this.sum_bidPrice_square_multiplies_quantity = 0;
		this.sum_askPrice_square_multiplies_quantity = 0;
		this.sum_bidQuantity = 0;
		this.sum_askQuantity = 0;
	}
	
	public double getStatistics(StatStrategy statType){
		return statType.calcStat(this);
	}
	
	public void addTicMsg(String msg){
		String[] msgInfo = msg.split("\t");
		
		double bidPrice = Double.valueOf(msgInfo[1].replaceAll("[^\\d.]", ""));
		int bidQuantity = Integer.valueOf(msgInfo[2].replaceAll("[^\\d.]", ""));
		double askPrice = Double.valueOf(msgInfo[3].replaceAll("[^\\d.]", ""));
		int askQuantity = Integer.valueOf(msgInfo[4].replaceAll("[^\\d.]", ""));
		
		this.sum_bidPrice_multiplies_quantity += bidPrice * bidQuantity;
		this.sum_askPrice_multiplies_quantity += askPrice * askQuantity;
		this.sum_bidPrice_square_multiplies_quantity += Math.pow(bidPrice,2) * bidQuantity;
		this.sum_askPrice_square_multiplies_quantity += Math.pow(askPrice,2) * askQuantity;;
		this.sum_bidQuantity += bidQuantity;
		this.sum_askQuantity += askQuantity;
	}

}
