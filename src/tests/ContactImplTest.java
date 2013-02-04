package tests;

import junit.framework.Assert;

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
		
		Assert.assertTrue(c.getId() > 0);
		Assert.assertFalse(c.getName().isEmpty());
		Assert.assertFalse(c.getNotes().isEmpty());
	}

}
