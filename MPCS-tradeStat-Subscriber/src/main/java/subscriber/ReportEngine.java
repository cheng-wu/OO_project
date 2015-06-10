package subscriber;

public class ReportEngine {
	private static ReportEngine instance;
	
	private ReportEngine(){}
	
	public static ReportEngine getInstance(){
		if(instance == null){
			instance = new ReportEngine();
		}
		return instance;
	}
	
	public String report(TradingEngine tradingEngine){
		StringBuilder reportMsg = new StringBuilder();
		reportMsg.append("TradingEngine: "+tradingEngine.engineName+" : ");
		reportMsg.append(tradingEngine.rootPortfolio.reportStat());
		return  reportMsg.toString();
	}

}
