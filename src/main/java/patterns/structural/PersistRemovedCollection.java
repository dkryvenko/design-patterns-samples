package patterns.structural;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * The example of Wrapper pattern.
 * 
 * An instance which implements Collection interface should be wrapped to change
 * method remove() to allow persist removing element in text file.
 */
public class PersistRemovedCollection<T> implements Collection<T> {

	// wrapped instance
	private Collection<T> collection;

	private Writer writer;

	// wrapped instance should be injected. wrapper without wrapped instance
	// has no sense
	private PersistRemovedCollection() {

	}

	public PersistRemovedCollection(Collection<T> collection, Writer writer) {
		this.collection = collection;
		this.writer = writer;
	}

	public int size() {
		return collection.size();
	}

	public boolean isEmpty() {
		return collection.isEmpty();
	}

	public boolean contains(Object o) {
		return collection.contains(o);
	}

	public Iterator<T> iterator() {
		return collection.iterator();
	}

	public Object[] toArray() {
		return collection.toArray();
	}

	public <T> T[] toArray(T[] a) {
		return collection.toArray(a);
	}

	public boolean add(T e) {
		return collection.add(e);
	}

	public boolean remove(Object o) {
		// additional behavior provided by the wrapper
		try {
			writer.write(o.toString());
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// original behavior of wrapped instance
		return collection.remove(o);
	}

	public boolean containsAll(Collection<?> c) {
		return collection.containsAll(c);
	}

	public boolean addAll(Collection<? extends T> c) {
		return collection.addAll(c);
	}

	public boolean removeAll(Collection<?> c) {
		return collection.removeAll(c);
	}

	public boolean retainAll(Collection<?> c) {
		return collection.retainAll(c);
	}

	public void clear() {
		collection.clear();
	}

	// test the wrapper
	public static void main(String[] args) throws IOException {
		// instance to be wrapped
		List<Integer> list = new LinkedList<Integer>();
		
		// wrap the list
		FileWriter writer = new FileWriter("coll.log");
		Collection<Integer> coll = new PersistRemovedCollection<Integer>(list, writer);
		
		// work with the wrapped list
		coll.add(3);
		coll.add(5);
		coll.add(4);
		coll.remove(3);
		coll.remove(4);
	}
}
