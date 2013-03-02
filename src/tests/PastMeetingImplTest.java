package tests;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;
import junit.framework.Assert;
import org.junit.Test;
import domain.ContactImpl;
import domain.PastMeetingImpl;
import base.Contact;
import base.PastMeeting;


public class PastMeetingImplTest {

	@Test
	public void MainTest() {

		//create a set of contacts
		Set<Contact> contacts1 = new HashSet<Contact>();
		contacts1.add(new ContactImpl("contact 1"));
		contacts1.add(new ContactImpl("contact 2"));
		contacts1.add(new ContactImpl("contact 3"));
		
		//get current date for the meeting
		Calendar date1 = Calendar.getInstance();
		
		PastMeeting pm1 = new PastMeetingImpl(contacts1, date1, "notes from this meeting");
		
		Assert.assertTrue(pm1.getContacts().size() == contacts1.size());
		Assert.assertTrue(pm1.getDate().equals(date1));	
		Assert.assertTrue(pm1.getNotes().equals("notes from this meeting"));
	}
	
}
