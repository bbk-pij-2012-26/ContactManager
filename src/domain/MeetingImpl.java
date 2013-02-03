package domain;

import java.util.Calendar;
import java.util.Set;

import base.Contact;
import base.Meeting;

public class MeetingImpl implements Meeting {

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
	
	private MeetingImpl()
	{
		this.id = this.hashCode();
	}
}
