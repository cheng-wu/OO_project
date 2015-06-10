package subscriber;

public class TradingEngine {
	protected String engineName;
	protected Portfolio rootPortfolio;
	protected ReportEngine reportEngine;
	
	public TradingEngine(String engineName, Portfolio rootPortfolio){
		this.engineName = engineName;
		this.rootPortfolio = rootPortfolio;
	}
	
	public String report() {
		this.reportEngine = ReportEngine.getInstance();
		return reportEngine.report(this);
	}
	
	public void update(String msg){
		Iterator iterator = new Iterator(this.rootPortfolio);
		
		for(Component item = iterator.First();!iterator.IsDone();item = iterator.Next()){
			item.update(msg);
		}
	}

}
