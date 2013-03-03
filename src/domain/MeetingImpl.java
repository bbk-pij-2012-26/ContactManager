package domain;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Set;

import base.Contact;
import base.Meeting;

public class MeetingImpl implements Meeting, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected int id;
	protected Calendar date;
	protected Set<Contact> contacts;
	
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
	
	public MeetingImpl()
	{
		this.id = this.hashCode();
	}
	
	public MeetingImpl(Set<Contact> contacts, Calendar date)
	{
		this.id = this.hashCode();
		this.contacts = contacts;
		this.date = date;
	}
	
}
