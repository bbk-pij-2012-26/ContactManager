package domain;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Set;

import base.Contact;
import base.PastMeeting;

public class PastMeetingImpl extends MeetingImpl implements PastMeeting, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String notes;

	@Override
	public String getNotes() {
		// TODO Auto-generated method stub
		return this.notes;
	}
	
	public PastMeetingImpl(Set<Contact> contacts, Calendar date, String notes) 
	{
		super(contacts, date);		
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
