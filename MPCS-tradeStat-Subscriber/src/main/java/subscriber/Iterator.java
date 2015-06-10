package subscriber;

public class Iterator extends AbstractIterator{
	private Composite composite;
	private int current;
	
	public Iterator(Composite composite){
		this.composite = composite;
		this.current = 0;
	}

	@Override
	public Component First() {
		// TODO Auto-generated method stub
		return this.composite.componentList.get(0);
	}

	@Override
	public Component Next() {
		// TODO Auto-generated method stub
		this.current ++;
		if(!IsDone()){
			return this.composite.componentList.get(this.current);
		}else{
			return null;
		}
	}

	@Override
	public boolean IsDone() {
		if(this.current >= this.composite.componentList.size())
			return true;
		else
			return false;
	}

	@Override
	public Component CurrentItem() {
		// TODO Auto-generated method stub
		return this.composite.componentList.get(this.current);
	}

}
