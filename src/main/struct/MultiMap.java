package main.struct;

import java.util.*;

public class MultiMap<K0, V0> {

	private Map<K0, Collection<V0>> multMap = new HashMap<>();

	/**
	 * Constructor
	 */
	public MultiMap() {

	}

	/**
	 * Put K0 Key and Collection V0
	 * @param k K0 key
	 * @param v V0 value
	 */
	public void put(K0 k, V0 v) {
		if(multMap.containsKey(k)) {
			multMap.get(k).add(v);
		}else{
			Collection<V0> vc = new HashSet<>();
			vc.add(v);
			multMap.put(k, vc);
		}
	}

	/**	public void setAlphabet(Set<Character> alphabet) {
		this.alphabet = alphabet;
	}
	 * Get Collection of V0 for given K0
	 * @param k K0 key
	 * @return Collection of V0 for key
	 */
	public Collection<V0> get(K0 k) {
		if(containsKey(k)) {
			return multMap.get(k);
		}else{
			return null;
		}
	}

	/**
	 * Get Set of all Keys
	 * @return Keyset of K0
	 */
	public Set<K0> getKey() {
		return multMap.keySet();
	}

	/**
	 * Methode to check if MultiMap contains a key
	 * @param k K0 key
	 * @return Boolean if map contains key
	 */
	public boolean containsKey(K0 k) {
		return multMap.containsKey(k);
	}

	/**
	 * Methode to check if a value of V0 is in a entry with Key key
	 * @param key key of K0
	 * @param value value of V0
	 * @return Boolean if map entry contains value
	 */
	public boolean containsValueForKey(K0 key, V0 value) {
		Collection<V0> valueSet = multMap.get(key);
		if(valueSet.contains(value)) {
			return true;
		}
		return false;
	}


}
