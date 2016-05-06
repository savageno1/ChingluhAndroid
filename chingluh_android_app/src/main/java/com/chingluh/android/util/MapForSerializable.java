package com.chingluh.android.util;

import java.io.Serializable;
import java.util.Map;

public class MapForSerializable implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 4953198047736791689L;
	private Map<String, ?> map;

	public Map<String, ?> getMap() {
		return this.map;
	}

	public void setMap(Map<String, ?> map) {
		this.map = map;
	}
}
