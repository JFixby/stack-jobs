
package com.jfixby.sofj.go;

public class Category implements Comparable<Category> {

	public String category;
	public int value;

	@Override
	public String toString () {
		return "<" + this.category + "> popularity=(" + this.value + ")";
	}

	@Override
	public int compareTo (final Category o) {
		return -Integer.compare(this.value, o.value);
	}

}
