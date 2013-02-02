package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import domain.ContactImpl;

import base.Contact;

public class ContactImplTest {

	@Test
	public void test() {
		Contact c = new ContactImpl("Contact 1");
		c.addNotes("note 1");
		c.addNotes("note 2");
		c.addNotes("note 3");
		
		if (c.getId() <= 0) 
			fail("Invalid ID!");
		
		if (c.getName().isEmpty())
			fail("Name not set!");
		
		if (c.getNotes().isEmpty())
			fail("Notes not set!");
		
		System.out.println(c.getId());
		System.out.println(c.getName());
		System.out.println(c.getNotes());
	}

}
