package consumer;

public class AskPriceMean extends StatStrategy{

	@Override
	public double calcStat(Stock stock) {
		// TODO Auto-generated method stub
		return stock.sum_askPrice_multiplies_quantity / stock.sum_askQuantity;
	}

}
