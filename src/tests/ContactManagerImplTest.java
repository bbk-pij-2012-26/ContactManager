package tests;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Test;

import base.Contact;
import base.ContactManager;

import domain.ContactManagerImpl;

public class ContactManagerImplTest {

	private final String DATAFILENAME = "contacts_test.txt";
	
	@Test
	public void loadAndSaveTest() {
		ContactManager cm = new ContactManagerImpl(DATAFILENAME);
		cm.flush();
	}
	
	@Test
	public void clearContentsTest()
	{
		ContactManagerImpl cm = new ContactManagerImpl(DATAFILENAME);
		cm.erase();
		cm.flush();
		
		ContactManagerImpl cm2 = new ContactManagerImpl(DATAFILENAME);
		if (cm2.hasData()) {
			fail("Data hasn't been deleted properly!");
		}
		
		System.out.println("Contacts saved!");
	}
	
	@Test
	public void ioDataTest()
	{
		//Save data
		ContactManager cm = new ContactManagerImpl(DATAFILENAME);
		for (int i = 0; i < 100; i++)
		{
			cm.addNewContact("My test contact "+ i, "blah blah blah");
		}
		
		cm.flush();
		
		//Return data from the saved file
		ContactManagerImpl cm2 = new ContactManagerImpl(DATAFILENAME);
		Set<Contact> loadedContacts = cm2.getContacts();
		
		if (loadedContacts.size() == 0)
			fail("getContacts() not working");
		
		for (Contact c : loadedContacts)
		{
			System.out.println(c.getId() + ":" + c.getName());
		}
		
		System.out.printf("-- Found %d contacts", loadedContacts.size());
		System.out.println();
	}
	
	@Test
	public void searchTest()
	{
		String nameQuery = "2";
		
		// Search for a contact by name
		ContactManagerImpl cm = new ContactManagerImpl(DATAFILENAME);
		Set<Contact> loadedContacts = cm.getContacts(nameQuery);
		
		if (loadedContacts.size() == 0)
			fail("getContacts(String name) not working");
		
		System.out.printf("-- Found %d contacts matching query '%s'"
				, loadedContacts.size(), nameQuery);
		System.out.println();
	}

}
