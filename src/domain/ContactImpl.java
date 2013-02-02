package domain;
import java.io.Serializable;

import base.Contact;

public class ContactImpl implements Contact, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2L;
	
	private int id;
	public String name;
	public String notes;	
	
	public int getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public String getNotes() {
		return this.notes;
	}

	public void addNotes(String note) {
		if (this.notes == null) { 
			this.notes = new String();
		}
		
		this.notes += note + ";";
	}	 
	
	public ContactImpl(String name)
	{ 
		this.id = this.hashCode();
		this.name = name;
	}
	
}
