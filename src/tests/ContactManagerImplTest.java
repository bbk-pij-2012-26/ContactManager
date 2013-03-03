package tests;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;
import org.junit.Test;
import junit.framework.Assert;
import base.Contact;
import base.PastMeeting;
import domain.ContactManagerImpl;

public class ContactManagerImplTest {

	private final String DATAFILENAME = "contacts_test.txt";
	private ContactManagerImpl mContactManager;
	
	@Test
	/**
	 * Tests:
	 * getContacts()
	 * getFutureMeetingList(contact)
	 * getPastMeetingList(contact)
	 * getPastMeeting(id)
	 */
	public void MainTest() 
	{
		this.mContactManager = new ContactManagerImpl(DATAFILENAME);	
		this.mContactManager.erase(); //clear existing file
		
		this.contactsTest();
		this.futureMeetingsTest();
		this.pastMeetingsTest();		
		this.addMeetingNotesTest();				
		
		this.mContactManager.flush();
	}
	
	/**
	 * Test methods: 
	 * getContacts()
	 * getContacts(int...)
	 * getContacts(string)
	 */
	private void contactsTest()
	{		
		//test addNewContact()
		this.mContactManager.addNewContact("Contact 1", "notes for contact 1");
		this.mContactManager.addNewContact("Contact 2", "notes for contact 2");
		this.mContactManager.addNewContact("Contact 3", "notes for contact 3");
		this.mContactManager.addNewContact("Contact 4", "notes for contact 4");
		this.mContactManager.addNewContact("Contact 5", "notes for contact 5");
		
		//test getContacts()
		Assert.assertTrue(this.mContactManager.getContacts().size() == 5);
		
		//test getContacts(int...)
		Object[] contacts = this.mContactManager.getContacts().toArray();
		int contactId1 = ((Contact)contacts[0]).getId();
		int contactId2 = ((Contact)contacts[1]).getId();
		
		//return the first couple of contacts
		Object[] contactsById = this.mContactManager.getContacts(contactId1, contactId2).toArray();		
		Assert.assertTrue("Wrong number of contacts returned!", contactsById.length == 2); //ensure only 2 contacts are returned
		
		//ensure the contacts returned have the same ids which were requested
		for (int i = 0; i < 2; i++)
		{		
			int contactId = ((Contact)contactsById[0]).getId();
			Assert.assertTrue("Wrong id of contacts returned!", contactId == contactId1 || contactId == contactId2);
		}		
		
		//test getContacts(string)
		Set<Contact> contactsByName = this.mContactManager.getContacts("Contact 1");
		Assert.assertTrue("Should return 1 contact", contactsByName.size() == 1);
	}	
	
	/**
	 * Test methods:
	 * addFutureMeeting()
	 * getFutureMeetingList(calendar)
	 * getFutureMeetingList(contact)
	 */
	private void futureMeetingsTest()
	{	
		Set<Contact> meetingContacts = new HashSet<Contact>();
		meetingContacts.addAll(this.mContactManager.getContacts("Contact 1")); //should only add "Contact 1"  
		meetingContacts.addAll(this.mContactManager.getContacts("Contact 2")); //should only add "Contact 2"
		
		Assert.assertTrue("The Set should only contain 2 contacts", meetingContacts.size() == 2);
		
		//get a future date in 2 months time
		Calendar date = Calendar.getInstance();
		date.add(Calendar.MONTH, 2);
		
		this.mContactManager.addFutureMeeting(meetingContacts, date);
		
		Assert.assertTrue("It should return at least one contact!", 
				this.mContactManager.getFutureMeetingList(date).size() == 1);
		
		Contact c = (Contact) meetingContacts.toArray()[0]; //get first contact of the meeting
		
		Assert.assertTrue("It should return at least one contact!", 
				this.mContactManager.getFutureMeetingList(c).size() == 1);
		
	}	
	
	/**
	 * Test methods:
	 * addNewPastMeeting()	 * 
	 * getPastMeetingList(contact)
	 */
	private void pastMeetingsTest()
	{	
		Set<Contact> meetingContacts = new HashSet<Contact>();
		meetingContacts.addAll(this.mContactManager.getContacts("Contact 3")); //should only add "Contact 3"  
		meetingContacts.addAll(this.mContactManager.getContacts("Contact 4")); //should only add "Contact 4"
		
		Assert.assertTrue("The Set should only contain 2 contacts", meetingContacts.size() == 2);
		
		//get a past date 2 months ago
		Calendar date = Calendar.getInstance();
		date.add(Calendar.MONTH, -2);
		
		this.mContactManager.addNewPastMeeting(meetingContacts, date, "these are notes from the meeting");
			
		Contact c = (Contact) meetingContacts.toArray()[0]; //get first contact of the meeting
		
		Assert.assertTrue("It should return at least one contact!", 
				this.mContactManager.getPastMeetingList(c).size() == 1);
		
	}
	
	/**
	 * Test methods:
	 * addMeetingNotes(int)
	 */
	private void addMeetingNotesTest()
	{
		Contact c = (Contact) this.mContactManager.getContacts("Contact 3").toArray()[0];
		
		PastMeeting pm = (PastMeeting) this.mContactManager.getPastMeetingList(c).toArray()[0];
		Assert.assertTrue("Couldn't find a past meeting with Contact 3", pm != null);
		
		int pastMeetingId = pm.getId();
		
		this.mContactManager.addMeetingNotes(pastMeetingId, "these are some other notes from the meeting!");
				
		
		PastMeeting pm2 = this.mContactManager.getPastMeeting(pastMeetingId);
		Assert.assertTrue(pm2.getNotes().contains("these are some other notes"));
	}
}
