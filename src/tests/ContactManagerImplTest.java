//package tests;
//
//import static org.junit.Assert.*;
//
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Random;
//import java.util.Set;
//import org.junit.Test;
//import base.Contact;
//import base.ContactManager;
//import base.Meeting;
//import base.PastMeeting;
//import domain.ContactManagerImpl;
//import domain.FutureMeetingImpl;
//import domain.PastMeetingImpl;
//
//public class ContactManagerImplTest {
//
//	private final String DATAFILENAME = "contacts_test.txt";
//		
//	@Test
//	/**
//	 * Tests:
//	 * addNewContact(name, notes)
//	 */
//	public void addContactsTest()
//	{				
//		ContactManager cm = new ContactManagerImpl(DATAFILENAME);
//		int noOfContacts = getRandomInt(20, 100);		
//		
//		//add some contacts
//		for (int i = 0; i < noOfContacts; i++)
//		{
//			cm.addNewContact("My test contact "+ i, "blah blah blah");
//		}		
//
//		cm.flush();
//	}
//	
//	@Test
//	/**
//	 * Tests:
//	 * addFutureMeeting(contacts, date)
//	 */
//	public void addFutureMeetingsTest()
//	{
//		ContactManager cm = new ContactManagerImpl(DATAFILENAME);
//		
//		List<Meeting> meetings = getRandomMeetings(true);
//		for (Meeting m : meetings)
//		{
//			cm.addFutureMeeting(m.getContacts(), m.getDate());
//		}		
//		
//		cm.flush();
//	}
//	
//	@Test
//	/**
//	 * Tests:
//	 * addNewPastMeeting(contacts, date, notes)
//	 */
//	public void addPastMeetingsTest()
//	{
//		ContactManager cm = new ContactManagerImpl(DATAFILENAME);
//		
//		List<Meeting> meetings = getRandomMeetings(false);
//		for (Meeting m : meetings)
//		{
//			PastMeeting pm = (PastMeeting)m;
//			cm.addNewPastMeeting(pm.getContacts(), pm.getDate(), pm.getNotes());
//		}		
//		
//		cm.flush();
//	}
//	
//	@Test
//	/**
//	 * Tests:
//	 * getContacts()
//	 * getFutureMeetingList(contact)
//	 * getPastMeetingList(contact)
//	 * getPastMeeting(id)
//	 */
//	public void dataLoadTest() 
//	{
//		ContactManagerImpl cmCleaner = new ContactManagerImpl(DATAFILENAME);
//		cmCleaner.erase();
//		cmCleaner.flush();
//
//		//Return data from the saved file
//		ContactManagerImpl cm = new ContactManagerImpl(DATAFILENAME);
//		Set<Contact> loadedContacts = cm.getContacts();	
//		
//		if (loadedContacts.size() == 0)
//			fail("getContacts() not working");
//		
//		int futureMeetingsCount = 0; //counts all FUTURE meetings retrieved for all contacts scanned
//		int pastMeetingsCount = 0; //counts all PAST meetings retrieved for all contacts scanned
//		
//		for (Contact c : loadedContacts)
//		{
//			//test getFutureMeetingList(contact)
//			List<Meeting> futureMeetings = cm.getFutureMeetingList(c);
//			futureMeetingsCount += futureMeetings.size();
//			
//			//test getFutureMeeting(id)
//			for (Meeting fm : futureMeetings) {
//				Meeting returnedMeeting = cm.getFutureMeeting(fm.getId());
//				if (fm.getId() != returnedMeeting.getId())
//					fail("getFutureMeeting(id) - didn't return the same meeting");
//			}
//			
//			//test getPastMeetingList(contact)
//			List<PastMeeting> pastMeetings = cm.getPastMeetingList(c);
//			pastMeetingsCount += pastMeetings.size();		
//			
//			//test getPastMeeting(id)
//			for (PastMeeting pm : pastMeetings) {
//				PastMeeting returnedMeeting = cm.getPastMeeting(pm.getId());
//				if (pm.getId() != returnedMeeting.getId())
//					fail("getPastMeeting(id) - didn't return the same meeting");
//			}
//			
//			System.out.printf("%d : %s [FutureMeetings: %d|PastMeetings: %d"
//					, c.getId(), c.getName(), futureMeetings.size(), pastMeetings.size());
//			System.out.println();
//		}
//		
//		if (futureMeetingsCount == 0)
//			fail("no FutureMeetings returned");
//		
//		if (pastMeetingsCount == 0)
//			fail("no PastMeetings returned");
//		
//		System.out.printf("-- Found %d contacts", loadedContacts.size());
//		System.out.println();
//	}
//	
//	@Test
//	/**
//	 * Tests:
//	 * getContacts(name)
//	 */
//	public void getContactsByNameTest()
//	{
//		String nameQuery = "2";
//		
//		// Search for a contact by name
//		ContactManagerImpl cm = new ContactManagerImpl(DATAFILENAME);
//		Set<Contact> loadedContacts = cm.getContacts(nameQuery);
//		
//		if (loadedContacts.size() == 0)
//			fail("getContacts(String name) not working");
//		
//		System.out.printf("-- Found %d contacts matching query '%s'"
//				, loadedContacts.size(), nameQuery);
//		System.out.println();
//	}
//	
//	
//	private int getRandomInt(int min, int max) {
//		Random rnd = new Random();
//		int randomInt = min;
//		while (randomInt <= min) {
//			randomInt = rnd.nextInt(max+1);
//		}
//		return randomInt;
//	}
//	
//	private List<Meeting> getRandomMeetings(boolean future)
//	{
//		int noOfMeetings = getRandomInt(20, 100);
//		
//		ContactManager cm = new ContactManagerImpl(DATAFILENAME);
//		List<Contact> contactList = new ArrayList<Contact>();
//		contactList.addAll(cm.getContacts());
//		
//		List<Meeting> meetings = new ArrayList<Meeting>();
//		
//		//get some future meetings
//		for (int i = 0; i < noOfMeetings; i++) {
//			//get a random date
//			int daysToAdd = getRandomInt(1, 100);
//			if (!future) daysToAdd *= -1; //get some days in the past if needed
//			
//			Calendar rndDate = Calendar.getInstance();
//			rndDate.add(Calendar.DATE, daysToAdd); 
//			
//			//get a random number of contacts (between 0 and 5)
//			//from a random position in the contact list created above	
//			//and add it to the list of contacts for this meeting
//			int contactListStartIndex = getRandomInt(0, contactList.size()-10);
//			int contactListEndIndex = contactListStartIndex + getRandomInt(1, 10);
//			
//			Set<Contact> meetingContacts = new HashSet<Contact>();
//			for (int j = contactListStartIndex; j < contactListEndIndex; j++) {			
//				meetingContacts.add(contactList.get(j));				
//			}
//			
//			if (future) { 
//				meetings.add(new FutureMeetingImpl(meetingContacts, rndDate));
//			}
//			else {
//				meetings.add(new PastMeetingImpl(meetingContacts, rndDate, "test notes"));
//			}
//		
//		}	
//		
//		return meetings;
//	}
//
//}
