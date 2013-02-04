package domain;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Set;

import base.Contact;
import base.FutureMeeting;

public class FutureMeetingImpl implements FutureMeeting, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private Calendar date;
	private Set<Contact> contacts;
	
	@Override
	public int getId() {
		return this.id;
	}

	@Override
	public Calendar getDate() {
		return this.date;
	}

	@Override
	public Set<Contact> getContacts() {
		return this.contacts;
	}
	
	public FutureMeetingImpl(Set<Contact> contacts, Calendar date)
	{
		this.id = this.hashCode();
		this.contacts = contacts;
		this.date = date;
	}
}
