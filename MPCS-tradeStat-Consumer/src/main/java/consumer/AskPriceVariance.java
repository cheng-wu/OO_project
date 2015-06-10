package consumer;

public class AskPriceVariance extends StatStrategy{

	@Override
	public double calcStat(Stock stock) {
		// TODO Auto-generated method stub
		return stock.sum_askPrice_square_multiplies_quantity / stock.sum_askQuantity - Math.pow(stock.sum_askPrice_multiplies_quantity/stock.sum_askQuantity,2);
	}

}
