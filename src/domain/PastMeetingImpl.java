package domain;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Set;

import base.Contact;
import base.PastMeeting;

public class PastMeetingImpl implements PastMeeting, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private Calendar date;
	private Set<Contact> contacts;
	private String notes;
	
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

	@Override
	public String getNotes() {
		// TODO Auto-generated method stub
		return this.notes;
	}
	
	public PastMeetingImpl(Set<Contact> contacts, Calendar date, String notes) {
		this.id = this.hashCode();
		this.date = date;
		this.contacts = contacts;
		this.notes = notes;
	}
	
	/**
	 * Adds notes to the meeting
	 * @param notes the notes to be added
	 */
	public void addNotes(String notes)
	{
		this.notes += notes + ";";
	}
}
