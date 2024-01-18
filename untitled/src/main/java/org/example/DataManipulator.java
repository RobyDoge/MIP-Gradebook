package org.example;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.File;

public class DataManipulator
{

    public static Gradebook loadData()
    {
        Gradebook gradebook = new Gradebook();
        try{
            File xmlFile = new File("D:/1FACULTATE/Anu II/MIP/MIP-Gradebook/untitled/src/main/java/org/example/gradebook.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document document = dBuilder.parse(xmlFile);
            gradebook = loadGradebookFromXML(document);

        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            return null;
        }
        return gradebook;
    }

    public static void saveData(Gradebook gradebook)
    {
        Gradebook gradebookToBeSaved = gradebook;
        try{
            File xmlFile = new File("D:/1FACULTATE/Anu II/MIP/MIP-Gradebook/untitled/src/main/java/org/example/gradebook.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document document = dBuilder.parse(xmlFile);

            Element rootElement = document.getDocumentElement();
            while (rootElement.hasChildNodes())
            {
                rootElement.removeChild(rootElement.getFirstChild());
            }

            saveGradebookFromXML(document,gradebookToBeSaved);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(xmlFile);
            transformer.transform(source, result);

            System.out.println("XML file modified successfully.");
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            return;
        }
    }

    private static void saveGradebookFromXML(Document document, Gradebook gradebookToBeSaved)
    {
        Element root = document.getDocumentElement();

        for(String studentName: gradebookToBeSaved.getStudents())
        {
            Element newStudent = document.createElement("student");
            newStudent.setAttribute("name",studentName);
            root.appendChild(newStudent);
        }

        for(String teacherName: gradebookToBeSaved.getTeachersAndCourses().keySet())
        {
            Element newTeacher = document.createElement("teacher");
            newTeacher.setAttribute("name",teacherName);
            root.appendChild(newTeacher);
        }

        for(String teacherName: gradebookToBeSaved.getTeachersAndCourses().keySet())
        {
            for(String courseName: gradebookToBeSaved.getTeachersAndCourses().get(teacherName))
            {
                Element newCourse = document.createElement("course");
                newCourse.setAttribute("name",courseName);
                newCourse.setAttribute("teacherName",teacherName);
                root.appendChild(newCourse);
            }
        }

        for(Course course: gradebookToBeSaved.getCourses())
        {
            for(String studentName: course.getStudents())
            {
                Element newStudentCourse = document.createElement("studentCourse");
                newStudentCourse.setAttribute("studentName",studentName);
                newStudentCourse.setAttribute("courseName", course.getName());
                root.appendChild(newStudentCourse);
            }
        }

        for(Course course: gradebookToBeSaved.getCourses())
        {
            for(String studentName: course.getStudents())
            {
                for(Grade grade: course.getStudentGrades(studentName))
                {
                    Element newGrade = document.createElement("grade");
                    newGrade.setAttribute("student",studentName);
                    newGrade.setAttribute("course",course.getName());
                    newGrade.setAttribute("value",grade.mark.toString());
                    newGrade.setAttribute("date",grade.date.toString());
                    root.appendChild(newGrade);
                }
            }
        }



    }


    private static Gradebook loadGradebookFromXML(Document document)
    {
        Gradebook gradebook = new Gradebook();

        NodeList teacherList = document.getElementsByTagName("teacher");
        for (int i = 0; i < teacherList.getLength(); i++)
        {
            Element teacherElement = (Element) teacherList.item(i);
            String teacherName = teacherElement.getAttribute("name");
            gradebook.addTeacher(teacherName);
        }
        NodeList courseList = document.getElementsByTagName("course");
        for (int i = 0; i < courseList.getLength(); i++)
        {
            Element courseElement = (Element) courseList.item(i);
            String courseName = courseElement.getAttribute("name");
            String teacherName = courseElement.getAttribute("teacherName");
            gradebook.addCourse(courseName);
            gradebook.addCourseToTeacher(teacherName, courseName);
        }

        NodeList studentList = document.getElementsByTagName("student");
        for (int i = 0; i < studentList.getLength(); i++)
        {
            Element studentElement = (Element) studentList.item(i);
            String studentName = studentElement.getAttribute("name");
            gradebook.addStudent(studentName);
        }
        NodeList studentCourseList = document.getElementsByTagName("studentCourse");
        for (int i = 0; i < studentCourseList.getLength(); i++)
        {
            Element studentCourseElement = (Element) studentCourseList.item(i);
            String studentName = studentCourseElement.getAttribute("studentName");
            String courseName = studentCourseElement.getAttribute("courseName");
            gradebook.addStudentToCourse(studentName, courseName);
        }
        // Extract grades
        NodeList gradeList = document.getElementsByTagName("grade");
        for (int i = 0; i < gradeList.getLength(); i++) {
            Element gradeElement = (Element) gradeList.item(i);
            String studentName = gradeElement.getAttribute("student");
            String courseName = gradeElement.getAttribute("course");
            String valueString = gradeElement.getAttribute("value");
            String dateString = gradeElement.getAttribute("date");
            String[] dateParts = dateString.split(" ");
            gradebook.addGradeToStudent(studentName, courseName, new Grade(
                    Integer.parseInt(valueString),
                    new DateStruct(
                            Integer.parseInt(dateParts[0]),
                            Integer.parseInt(dateParts[1]),
                            Integer.parseInt(dateParts[2]))
                    )
            );
        }

        return gradebook;
    }
}
