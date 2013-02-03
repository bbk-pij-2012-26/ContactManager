package domain;

import java.util.Calendar;
import java.util.Set;

import base.Contact;
import base.FutureMeeting;

public class FutureMeetingImpl implements FutureMeeting {

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
