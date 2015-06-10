package consumer;

public class BidPriceStdDeviation extends StatStrategy{

	@Override
	public double calcStat(Stock stock) {
		// TODO Auto-generated method stub
		return Math.sqrt(stock.sum_bidPrice_square_multiplies_quantity / stock.sum_bidQuantity - Math.pow(stock.sum_bidPrice_multiplies_quantity / stock.sum_bidQuantity, 2));
	}

}
