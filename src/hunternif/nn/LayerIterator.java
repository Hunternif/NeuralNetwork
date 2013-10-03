package hunternif.nn;

import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class LayerIterator implements ListIterator<List<? extends Neuron>> {
	private int position = -1;
	private NNetwork network;
	public LayerIterator(NNetwork network) {
		this.network = network;
	}
	@Override
	public boolean hasNext() {
		return position == -1 || position >= 0 && position < network.numberOfLayers() - 1;
	}
	@Override
	public int nextIndex() {
		return position + 1;
	}
	@Override
	public List<? extends Neuron> next() {
		position = nextIndex();
		try {
			return network.getLayer(position);
		} catch (IndexOutOfBoundsException e) {
			throw new NoSuchElementException();
		}
	}
	
	@Override
	public boolean hasPrevious() {
		return position == -1 || position > 0 && position <= network.numberOfLayers() - 1;
	}
	@Override
	public int previousIndex() {
		return position == -1 ? -1 : position - 1;
	}
	@Override
	public List<? extends Neuron> previous() {
		position = previousIndex();
		if (position == -1) {
			position = network.numberOfLayers() - 1;
		}
		try {
			return network.getLayer(position);
		} catch (IndexOutOfBoundsException e) {
			throw new NoSuchElementException();
		}
	}
	
	@Override
	public void add(List<? extends Neuron> arg0) {
		throw new UnsupportedOperationException();
	}
	@Override
	public void set(List<? extends Neuron> arg0) {
		throw new UnsupportedOperationException();
	}
	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}
}
