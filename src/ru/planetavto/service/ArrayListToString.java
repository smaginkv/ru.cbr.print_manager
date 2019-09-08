package ru.planetavto.service;

import java.util.ArrayList;

public class  ArrayListToString <T> extends ArrayList<T> {
	private static final long serialVersionUID = 1L;

	public ArrayListToString() {
		super();
	}
	
	public ArrayListToString(int length) {
		super(length);
	}

	@Override
	public String toString() {
		if (this.size() == 0) {
			return "[Empty]";
		}
		StringBuilder result = new StringBuilder();

		for (T document : this) {
			if (result.length() != 0) {
				result.append(", ");
			}
			result.append(document);
		}
		return "[" + result.toString() + "]";
	}
}
