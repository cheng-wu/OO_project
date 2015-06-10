package subscriber;

public class Portfolio extends Composite{

	public Portfolio(String compositeName) {
		super(compositeName);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return this.componentName;
	}

	@Override
	public void update(String msg) {
		Iterator iterator = new Iterator(this);
		for(Component item = iterator.First();!iterator.IsDone();item = iterator.Next()){
			item.update(msg);
		}
	}

	@Override
	public String reportStat() {
		StringBuilder portfolioReport = new StringBuilder();
		portfolioReport.append("\nportfolio name: "+this.componentName + " : ");
		
		Iterator iterator = new Iterator(this);
		for(Component item = iterator.First();!iterator.IsDone();item = iterator.Next()){
			portfolioReport.append(item.reportStat());
		}
		return portfolioReport.toString();
	}
}
