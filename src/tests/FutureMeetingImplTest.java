package tests;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;
import junit.framework.Assert;
import org.junit.Test;
import domain.ContactImpl;
import domain.FutureMeetingImpl;
import base.FutureMeeting;
import base.Contact;


public class FutureMeetingImplTest {

	@Test
	public void MainTest() {

		//create a set of contacts
		Set<Contact> contacts1 = new HashSet<Contact>();
		contacts1.add(new ContactImpl("contact 1"));
		contacts1.add(new ContactImpl("contact 2"));
		contacts1.add(new ContactImpl("contact 3"));
		
		//get current date for the meeting
		Calendar date1 = Calendar.getInstance();
		
		FutureMeeting fm1 = new FutureMeetingImpl(contacts1, date1);
		
		Assert.assertTrue(fm1.getContacts().size() == contacts1.size());
		Assert.assertTrue(fm1.getDate().equals(date1));	
	}
	
}
