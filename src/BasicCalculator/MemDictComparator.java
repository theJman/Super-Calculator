package BasicCalculator;

import java.io.Serializable;
import java.util.Comparator;

public class MemDictComparator implements Serializable, Comparator<String> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2919772686332469018L;

	public MemDictComparator() {
		
	}

	/**
	 * sort by key length then by natural ordering if length is the same
	 */
	@Override
	public int compare(String o1, String o2) {
		if(o1.length() < o2.length())
			return 1;
		else if(o1.length() > o2.length())
			return -1;
		return o1.compareTo(o2);
	}

}
