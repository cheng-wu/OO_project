package consumer;

public class AskPriceStdDeviation extends StatStrategy {

	@Override
	public double calcStat(Stock stock) {
		// TODO Auto-generated method stub
		return Math.sqrt(stock.sum_askPrice_square_multiplies_quantity / stock.sum_askQuantity - Math.pow(stock.sum_askPrice_multiplies_quantity / stock.sum_askQuantity, 2));
	}

}
