package org.example;

import java.util.Date;
import java.util.Scanner;

public class Menu
{
    private Gradebook gradebook = new Gradebook();
    private Scanner scanner = new Scanner(System.in);
    public void run()
    {
        gradebook = DataManipulator.loadData();
        String menu = "Introduceti numele de utilizator: ";
        System.out.println(menu);
        String userName = scanner.nextLine();
        if(gradebook.containsStudent(userName))
        {
            runStudentMenu(userName);
        }
        else if(gradebook.containsTeacher(userName))
        {
            runTeacherMenu(userName);

        }
        else
        {
            System.out.println("Nume de utilizator invalid.");
        }

        DataManipulator.saveData(gradebook);

    }

    private void runStudentMenu(String studentName)
    {
        final String MENU = """
                Selecteaza o obtiune:
                1. Vizualizare note pentru o disciplina.
                2. Vizualizare medie pentru o disciplina.
                3. Vizualizare medie generala.
                0. Iesire.
                """;

        System.out.println(MENU);
        int option = scanner.nextInt();
        scanner.nextLine();
        while (option!=0)
        {
            String courseName;
            Course course;
            switch (option)
            {
                case 1:
                    System.out.println("Introduceti numele disciplinei:");
                    courseName = scanner.nextLine();
                    course = gradebook.getCourse(courseName);
                    if(course == null)
                    {
                        System.out.println("Disciplina nu exista.");
                        break;
                    }
                    System.out.println("Notele la disciplina " + courseName + " sunt: ");
                    for(Grade grade : course.getStudentGrades(studentName))
                    {
                        System.out.println(grade.date.toString() + " " + grade.mark.toString());
                    }
                    break;
                case 2:
                    System.out.println("Introduceti numele disciplinei: ");
                    courseName = scanner.nextLine();
                    course = gradebook.getCourse(courseName);
                    if(course == null)
                    {
                        System.out.println("Disciplina nu exista.");
                        break;
                    }
                    System.out.println("Media la disciplina " + courseName + " este: " + course.getStudentAverage(studentName));
                    break;
                case 3:
                    System.out.println("Media generala este: " + gradebook.getStudentAverage(studentName));
                    break;
                default:
                    System.out.println("Optiune invalida.");
                    break;
            }
            System.out.println(MENU);
            option = scanner.nextInt();
            scanner.nextLine();
        }
    }
    private void runTeacherMenu(String teacherName)
    {
        final String MENU = """
                Selecteaza o obtiune:
                1. Vizualizare note pentru o disciplina.
                2. Vizualizare medie pentru o disciplina.
                3. Adaugare nota unui student.
                4. Sorteaza si afiseaza disciplinele alfabetic.
                5. Sorteaza si afiseaza elevi alfabetic.
                6. Sorteaza si afiseaza notele unei discipline in functie de data calendaristica.
                7. Sorteaza si afiseaza notele unui elev in functie de data calendaristica.
                8. Sterge nota unui student la o disciplina.
                0. Iesire.
                """;
        System.out.println(MENU);
        int option = scanner.nextInt();
        scanner.nextLine();
        while (option != 0)
        {
            String courseName;
            Course course;
            String studentName;
            int mark;

            switch (option)
            {
                case 1:
                    System.out.println("Introduceti numele disciplinei: ");
                    courseName = scanner.nextLine();
                    course = gradebook.getCourse(courseName);
                    if (course == null)
                    {
                        System.out.println("Disciplina nu exista.");
                        break;
                    }
                    System.out.println("Notele la disciplina " + courseName + " sunt: ");
                    for(Grade grade : course.getAllGrades())
                    {
                        System.out.println(grade.toString());
                    }
                    break;
                case 2:
                    System.out.println("Introduceti numele disciplinei: ");
                    courseName = scanner.nextLine();
                    course = gradebook.getCourse(courseName);
                    if (course == null)
                    {
                        System.out.println("Disciplina nu exista.");
                        break;
                    }
                    System.out.println("Media la disciplina " + courseName + " este: " + course.getCourseAverage());
                    break;
                case 3:
                    System.out.println("Introduceti numele disciplinei: ");
                    courseName = scanner.nextLine();
                    course = gradebook.getCourse(courseName);
                    if (course == null)
                    {
                        System.out.println("Disciplina nu exista.");
                        break;
                    }
                    if (!gradebook.getTeachersAndCourses().get(teacherName).contains(courseName))
                    {
                        System.out.println("Nu sunteti profesor la aceasta disciplina.");
                        break;
                    }

                    System.out.println("Introduceti numele studentului: ");
                    studentName = scanner.nextLine();
                    if (!course.containsStudent(studentName))
                    {
                        System.out.println("Studentul nu exista.");
                        break;
                    }
                    System.out.println("Introduceti nota: ");
                    mark = scanner.nextInt();
                    scanner.nextLine();
                    Date currentDate = new Date();
                    DateStruct date = new DateStruct(currentDate.getDate(), currentDate.getMonth() + 1, currentDate.getYear() + 1900);
                    gradebook.addGradeToStudent(studentName, courseName,new Grade(mark,date));
                    break;
                case 4:
                    System.out.println("Disciplinele sunt: ");
                    for (Course sortedCourse : gradebook.getCourses())
                    {
                        System.out.println(sortedCourse.getName());
                    }
                    break;
                case 5:
                    System.out.println("Elevii sunt: ");
                    for (String student : gradebook.getStudents())
                    {
                        System.out.println(student);
                    }
                    break;
                case 6:
                    System.out.println("Introdu disciplina: ");
                    courseName = scanner.nextLine();
                    course = gradebook.getCourse(courseName);
                    if (course == null)
                    {
                        System.out.println("Disciplina nu exista.");
                        break;
                    }
                    System.out.println("Notele sunt: ");

                    for (Grade grade : course.getAllGrades())
                    {
                        System.out.println(grade.toString());
                    }
                case 7:
                    System.out.println("Introdu numele studentului: ");
                    studentName = scanner.nextLine();
                    if (!gradebook.getStudents().contains(studentName))
                    {
                        System.out.println("Studentul nu exista.");
                        break;
                    }
                    System.out.println("Notele sunt: ");
                    for (Course sortedCourse : gradebook.getCourses())
                    {
                        if (sortedCourse.containsStudent(studentName))
                        {
                            for (Grade grade : sortedCourse.getStudentGrades(studentName))
                            {
                                System.out.println(grade.date.toString() + " " + grade.mark.toString());
                            }
                        }
                    }
                    break;
                case 8:
                    System.out.println("Introduceti numele disciplinei: ");
                    courseName = scanner.nextLine();
                    course = gradebook.getCourse(courseName);
                    if (course == null)
                    {
                        System.out.println("Disciplina nu exista.");
                        break;
                    }
                    if (!gradebook.getTeachersAndCourses().get(teacherName).contains(courseName))
                    {
                        System.out.println("Nu sunteti profesor la aceasta disciplina.");
                        break;
                    }

                    System.out.println("Introduceti numele studentului: ");
                    studentName = scanner.nextLine();
                    if (!course.containsStudent(studentName))
                    {
                        System.out.println("Studentul nu exista.");
                        break;
                    }
                    System.out.println("Introduceti nota: ");
                    mark = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Introduceti data: ");
                    Integer day = scanner.nextInt();
                    scanner.nextLine();
                    Integer month = scanner.nextInt();
                    scanner.nextLine();
                    Integer year = scanner.nextInt();
                    scanner.nextLine();
                    gradebook.removeGradeFromStudent(studentName, courseName, new Grade(mark, new DateStruct(day,month,year)));
                    break;
                default:
                    System.out.println("Optiune invalida.");
                    break;
            }
            System.out.println(MENU);
            option = scanner.nextInt();
            scanner.nextLine();
        }
    }
}
