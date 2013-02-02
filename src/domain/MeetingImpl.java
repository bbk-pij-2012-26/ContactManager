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
		// TODO Auto-generated method stub
		return this.id;
	}

	@Override
	public Calendar getDate() {
		// TODO Auto-generated method stub
		return this.date;
	}

	@Override
	public Set<Contact> getContacts() {
		// TODO Auto-generated method stub
		return this.contacts;
	}
	
	private MeetingImpl()
	{
		this.id = this.hashCode();
	}

}
