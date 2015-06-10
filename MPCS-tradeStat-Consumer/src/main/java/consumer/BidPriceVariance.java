package consumer;

public class BidPriceVariance extends StatStrategy{

	@Override
	public double calcStat(Stock stock) {
		// TODO Auto-generated method stub
		return stock.sum_bidPrice_square_multiplies_quantity / stock.sum_bidQuantity - Math.pow(stock.sum_bidPrice_multiplies_quantity/stock.sum_bidQuantity,2);
	}

}
