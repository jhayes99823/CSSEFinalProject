package main;
import java.util.ArrayList;

public class MyPriorityQueue<T> extends ArrayList<Graph.WrapperDestination> {

	public MyPriorityQueue() {
		super();
	}

	public boolean add(Graph.WrapperDestination element) {
		if (element == null) {
			throw new NullPointerException();
		}
		super.add(element);
		up();
		return true;
	}

	public void up() {
		int spot = size() - 1;
		int parentSpot = (size() - 2) / 2;
		while (parentSpot > -1) {
			if (get(spot).getValue()<get(parentSpot).getValue()) {
				Graph.WrapperDestination parent = get(parentSpot);
				set(parentSpot, get(spot));
				set(spot, parent);
				spot = parentSpot;
				parentSpot = parentSpot / 2;
			} else {
				break;
			}
		}
	}

	public boolean offer(Graph.WrapperDestination element) {
		return add(element);
	}

	public Graph.WrapperDestination poll() {
		if (isEmpty()) {
			return null;
		}
		Graph.WrapperDestination element = get(0);
		remove(element);
		return element;
	}

	public Graph.WrapperDestination peek() {
		return size() > 0 ? get(0) : null;
	}

	public boolean remove(Graph.WrapperDestination element) {
		if (contains(element)) {
			int spot = super.indexOf(element);
			Graph.WrapperDestination temp = get(spot);
			set(spot, get(size() - 1));
			set(size() - 1, temp);
			super.remove(size() - 1);
			up();
			int leftChild = spot * 2 + 1;
			int rightChild = spot * 2 + 2;
			while (leftChild < size()) {
				Graph.WrapperDestination smallestElement = get(leftChild);
				int smallIndex = leftChild;
				if (rightChild < size() && smallestElement.getValue()>get(rightChild).getValue()) {
					smallestElement = get(rightChild);
					smallIndex = rightChild;
				}
				if (super.get(spot).getValue()>smallestElement.getValue()) {
					Graph.WrapperDestination parent = get(spot);
					set(spot, get(smallIndex));
					set(smallIndex, parent);
					spot = smallIndex;
					leftChild = spot * 2 + 1;
					rightChild = spot * 2 + 2;
				} else {
					break;
				}
			}
			return true;
		}
		return false;
	}
}