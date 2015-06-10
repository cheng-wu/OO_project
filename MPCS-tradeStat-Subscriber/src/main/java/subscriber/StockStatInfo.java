package subscriber;

public class StockStatInfo extends Component{
	protected String statType;
	protected double statValue;
	
	public StockStatInfo(String stockName, String statType) {
		super(stockName);
		this.statType = statType;
		this.statValue = 0;
	}

	@Override
	public void add(Component component) {
		System.out.println("Cannot add for stock stat info");
		
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return this.componentName;
	}
	
	@Override
	public void update(String msg) {
		String[] msgInfo = msg.split("\t");
		
		System.out.println("updating*** "+msgInfo[0]+" stock***");
		
		if(this.componentName.equalsIgnoreCase(msgInfo[0])){
			if(this.statType.equalsIgnoreCase("bidMean")){
				this.statValue = Double.valueOf(msgInfo[1].replaceAll("[^\\d.]", ""));
			}else if(this.statType.equalsIgnoreCase("bidVariance")){
				this.statValue=Double.valueOf(msgInfo[2].replaceAll("[^\\d.]", ""));
			}else if(this.statType.equalsIgnoreCase("bidStdDev")){
				this.statValue = Double.valueOf(msgInfo[3].replaceAll("[^\\d.]", ""));
			}else if(this.statType.equalsIgnoreCase("askMean")){
				this.statValue = Double.valueOf(msgInfo[4].replaceAll("[^\\d.]", ""));
			}else if(this.statType.equalsIgnoreCase("askVariance")){
				this.statValue = Double.valueOf(msgInfo[5].replaceAll("[^\\d.]", ""));
			}else if(this.statType.equalsIgnoreCase("askStdDev")){
				this.statValue = Double.valueOf(msgInfo[6].replaceAll("[^\\d.]", ""));
			}
		}
		// TODO Auto-generated method stub
		
	}

	@Override
	public String reportStat() {
		StringBuilder statReport = new StringBuilder();
		statReport.append(this.componentName+"\t"+this.statType+"\t"+String.valueOf(this.statValue)+"\t|\t");
		return statReport.toString();
	}
}
