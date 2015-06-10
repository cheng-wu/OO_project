package subscriber;

public abstract class Component {
	protected String componentName;
	
	public Component(String componentName){
		this.componentName = componentName;
	}
	
	abstract public void add(Component component);
	abstract public void update(String msg);
	abstract public String getName();
	abstract public String reportStat();
}
