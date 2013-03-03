package domain;

import java.io.Serializable;
import java.util.Comparator;

import base.Contact;

public class ContactComparator implements Serializable, Comparator<Contact> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public int compare(Contact c1, Contact c2) {
		return c1.getName().compareTo(c2.getName());
	}
}
