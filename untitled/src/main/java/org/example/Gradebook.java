package org.example;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class Gradebook
{
    Comparator<Course> courseComparator = Comparator.comparing(Course::getName);
    private Set<Course> courses = new TreeSet<>(courseComparator);
    private Map<String, Set<String>> teachersAndCourses = new HashMap<>();
    private Set<String> students = new HashSet<>();

    void addStudent(String studentName)
    {
        students.add(studentName);
    }
    public Map<String, Set<String>> getTeachersAndCourses()
    {
        return Collections.unmodifiableMap(teachersAndCourses);
    }
    public Set<String> getStudents()
    {
        return Collections.unmodifiableSet(students);
    }
    public Set<Course> getCourses()
    {

        return Collections.unmodifiableSet(courses);
    }
    public Course getCourse(String courseName)
    {
        return courses.stream()
                .filter(course -> course.getName().equals(courseName))
                .findFirst()
                .orElse(null);
    }
    public void addTeacher(String teacherName)
    {
        Set<String> courses = new HashSet<>();
        teachersAndCourses.put(teacherName,courses);
    }

    public Float getStudentAverage(String studentName)
    {
        Float sum = 0.0f;
        Integer count = 0;
        for (Course course : courses)
        {
            if (course.containsStudent(studentName))
            {
                sum += course.getStudentAverage(studentName);
                count++;
            }
        }
        return (count == 0) ? 0.0f : sum / count;
    }
    public void addCourse(String courseName)
    {
        Course course = new Course(courseName);
        courses.add(course);
    }
    public void removeCourse(String courseName)
    {
        teachersAndCourses.values().forEach(courses -> courses.remove(courseName));
        courses.removeIf(course -> course.getName().equals(courseName));
    }
    public void removeTeacher(String teacherName)
    {
        teachersAndCourses.remove(teacherName);
    }
    public void addStudentToCourse(String studentName,String courseName)
    {
        courses.stream()
                .filter(course -> course.getName().equals(courseName))
                .findFirst()
                .ifPresent(course -> course.addStudent(studentName));
    }

    public void removeStudentFromCourse(String studentName,String courseName)
    {
        courses.stream()
                .filter(course -> course.getName().equals(courseName))
                .findFirst()
                .ifPresent(course -> course.removeStudent(studentName));
    }

    public void addGradeToStudent(String studentName,String courseName,Grade grade)
    {
           courses.stream()
                    .filter(course -> course.getName().equals(courseName))
                    .findFirst()
                    .ifPresent(course -> course.addGrade(grade,studentName));
    }

    public void removeGradeFromStudent(String studentName,String courseName,Grade grade)
    {
        courses.stream()
                .filter(course -> course.getName().equals(courseName))
                .findFirst()
                .ifPresent(course -> course.removeGrade(studentName,grade));
    }
    public void removeStudent(String studentName)
    {
        courses.forEach(course -> course.removeStudent(studentName));
    }
    public void removeTeacherFromCourse(String teacherName,String courseName)
    {
        teachersAndCourses.get(teacherName).remove(courseName);
    }
    public void addCourseToTeacher(String teacherName,String courseName)
    {
        teachersAndCourses.get(teacherName).add(courseName);
    }
    public boolean containsStudent(String userName)
    {
        return students.contains(userName);
    }

    public boolean containsTeacher(String userName)
    {
        return teachersAndCourses.containsKey(userName);
    }
}
