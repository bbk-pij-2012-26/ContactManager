package tests;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;
import junit.framework.Assert;
import org.junit.Test;
import domain.ContactImpl;
import domain.MeetingImpl;
import base.Contact;
import base.Meeting;


public class MeetingImplTest {

	@Test
	public void MainTest() {

		//create a set of contacts
		Set<Contact> contacts1 = new HashSet<Contact>();
		contacts1.add(new ContactImpl("contact 1"));
		contacts1.add(new ContactImpl("contact 2"));
		contacts1.add(new ContactImpl("contact 3"));
		
		//get current date for the meeting
		Calendar date1 = Calendar.getInstance();
		
		Meeting m1 = new MeetingImpl(contacts1, date1);
		
		Assert.assertTrue(m1.getContacts().size() == contacts1.size());
		Assert.assertTrue(m1.getDate().equals(date1));	
	}
	
}
