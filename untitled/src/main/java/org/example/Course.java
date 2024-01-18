package org.example;

import java.util.*;
import java.util.stream.Collectors;

public class Course
{
    private String name;
    private Map<String,Set<Grade>> studentAndGrades = new HashMap<>();
    public Course(String name)
    {
        this.name = name;
    }
    public String getName()
    {
        return this.name;
    }

    public Set<Grade> getStudentGrades(String studentName)
    {
        return studentAndGrades.get(studentName);
    }
    public Vector<Grade> getAllGrades()
    {
        return studentAndGrades.values().stream()
                .flatMap(Set::stream)
                .collect(Collectors.toCollection(Vector::new));
    }
    public Boolean containsStudent(String studentName)
    {
        return studentAndGrades.containsKey(studentName);
    }

    public Float getStudentAverage(String studentName)
    {
        Set<Grade> defaultGrades = new TreeSet<>(new DateComparator());
        Set<Grade> gradesVector = studentAndGrades.getOrDefault(studentName, defaultGrades);
        return (float)gradesVector.stream()
                .mapToInt(Grade::getMark)
                .average()
                .orElse(0.0);
    }


    public Float getCourseAverage()
    {
        return (float) studentAndGrades.values().stream()
                .flatMap(Set::stream)
                .mapToInt(Grade::getMark)
                .average()
                .orElse(0.0);
    }

    public void addGrade(Grade grade, String studentName)
    {
        this.studentAndGrades.get(studentName).add(grade);
    }

    public void removeGrade(String studentName, Grade grade)
    {
        this.studentAndGrades.get(studentName).remove(grade);
    }
    public void removeStudent(String studentName)
    {
        if(!this.studentAndGrades.containsKey(studentName))
        {
            return;
        }
        this.studentAndGrades.remove(studentName);
    }
    public void addStudent(String studentName)
    {
        Set<Grade> grades = studentAndGrades.computeIfAbsent(studentName, k -> new TreeSet<>(new DateComparator()));
        this.studentAndGrades.put(studentName, grades);
    }


    public String[] getStudents()
    {
        return studentAndGrades.keySet().toArray(new String[0]);
    }
}
