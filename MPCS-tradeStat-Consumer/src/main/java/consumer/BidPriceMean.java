package consumer;

public class BidPriceMean extends StatStrategy{

	@Override
	public double calcStat(Stock stock) {
		// TODO Auto-generated method stub
		return stock.sum_bidPrice_multiplies_quantity / stock.sum_bidQuantity;
	}

}
