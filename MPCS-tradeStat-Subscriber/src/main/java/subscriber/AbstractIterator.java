package subscriber;

public abstract class AbstractIterator {
	//base iterator functionality
	abstract public Component First();
	abstract public Component Next();
	abstract public boolean IsDone();
	abstract public Component CurrentItem();
}
