package sort;

public class TestStudentComparatorExample {

    public static void main(String[] args) {
        
        System.out.println(">> Sorting by ID");
        
        StudentComparatorExample s1 = new StudentComparatorExample("jay", 2, "java");
        StudentComparatorExample s2 = new StudentComparatorExample("ming", 1, "magic");
        StudentComparatorExample s3 = new StudentComparatorExample("ken", 3, "C");

        StudentComparatorExample[] s = {s1, s2, s3};
        
//        SelectionSort.sort(s, StudentComparatorExample.BY_ID);
        ShellSort.sort(s, StudentComparatorExample.BY_ID);
        for (StudentComparatorExample student : s) {
            System.out.printf("ID %s, name %s, subject %s\n", student.ID, student.name, 
                    student.subject);
        }
        System.out.println();

        System.out.println(">> Sorting by subject");
        InsertionSort.sort(s, StudentComparatorExample.BY_SUBJECT);
        for (StudentComparatorExample student : s) {
            System.out.printf("ID %s, name %s, subject %s\n", student.ID, student.name, 
                    student.subject);
        }
        System.out.println();
        
    }

}
