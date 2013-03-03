package domain;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Set;

import base.Contact;
import base.FutureMeeting;

public class FutureMeetingImpl extends MeetingImpl implements FutureMeeting, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public FutureMeetingImpl(Set<Contact> contacts, Calendar date) 
	{
		super(contacts, date);
	}
}
