package domain;

import java.util.Comparator;

import base.Contact;

public class ContactComparator implements Comparator<Contact> {
	@Override
	public int compare(Contact c1, Contact c2) {
		return c1.getName().compareTo(c2.getName());
	}
}
