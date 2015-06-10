package subscriber;

import java.util.ArrayList;

public abstract class Composite extends Component{
	protected ArrayList<Component> componentList;

	public Composite(String compositeName) {
		super(compositeName);
		this.componentList = new ArrayList<Component>();
	}
	
	@Override
	public void add(Component component) {
		this.componentList.add(component);
		
	}
}
