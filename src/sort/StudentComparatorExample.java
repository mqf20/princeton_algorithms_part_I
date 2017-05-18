package sort;

import java.util.Comparator;

/**
 * This class uses the example of a student to demonstrate how to implement a Comparator interface.
 * 
 * From Week 3, Coursera Algorithms, Part I (https://class.coursera.org/algs4partI-010)
 * 
 * @author Ming
 *
 */

public class StudentComparatorExample {
	
	public static final Comparator<StudentComparatorExample> BY_NAME = new ByName();
	public static final Comparator<StudentComparatorExample> BY_SUBJECT = new BySubject();
	public static final Comparator<StudentComparatorExample> BY_ID = new ByID();
	
	public String name;
	public String subject;
	public int ID;
	
	public StudentComparatorExample(String name, int ID, String subject) {
		this.name = name;
		this.ID = ID;
		this.subject = subject;
	}
	
	private static class ByName implements Comparator<StudentComparatorExample> {
		public int compare(StudentComparatorExample v, StudentComparatorExample w) {
			return v.name.compareTo(w.name);
		}
	}
	
	private static class BySubject implements Comparator<StudentComparatorExample> {
		public int compare(StudentComparatorExample v, StudentComparatorExample w) {
			return v.subject.compareTo(w.subject);
		}
	}
	
	private static class ByID implements Comparator<StudentComparatorExample> {
		public int compare(StudentComparatorExample v, StudentComparatorExample w) {
			return v.ID - w.ID;  // This technique works here because there is no risk of underflow
		}
	}

}
