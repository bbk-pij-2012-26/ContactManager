package tests;

import static org.junit.Assert.*;

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
	}
	
	@Test
	public void addDataTest()
	{
		ContactManager cm = new ContactManagerImpl(DATAFILENAME);
	
		for (int i = 0; i < 100000; i++)
		{
			cm.addNewContact("My test contact "+ i, "blah blah blah");
		}
		
		cm.flush();
	}
	
	@Test
	public void loadDataTest()
	{
		ContactManagerImpl cm = new ContactManagerImpl(DATAFILENAME);
		for (Contact c : cm.getAllContacts())
		{
			System.out.println(c.getId() + ":" + c.getName());
		}
		
		System.out.printf("-- Found %d contacts", cm.getAllContacts().size());
	}

}
