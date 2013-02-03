package domain;

import java.util.Comparator;

import base.Meeting;

public class MeetingComparator implements Comparator<Meeting> {
	
	@Override
	public int compare(Meeting m1, Meeting m2) {
		return m1.getDate().compareTo(m2.getDate());
	}
}
