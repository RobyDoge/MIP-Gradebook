package org.example;

import java.util.Comparator;

public class Grade
{
    public Integer mark;
    public DateStruct date;

    public Grade(Integer mark, DateStruct date)
    {
        this.mark = mark;
        this.date = date;
    }
    public String toString()
    {
        return "Grade: " + mark + " Date: " + date.toString();
    }
    public Integer getMark()
    {
        return mark;
    }


}
class DateComparator implements Comparator<Grade>
{
    @Override
    public int compare(Grade grade1, Grade grade2) {
        int yearComparison = Integer.compare(grade1.date.year, grade2.date.year);
        if (yearComparison != 0) {
            return yearComparison;
        }

        int monthComparison = Integer.compare(grade1.date.month, grade2.date.month);
        if (monthComparison != 0) {
            return monthComparison;
        }

        int dayComparison = Integer.compare(grade1.date.day, grade2.date.day);
        if (dayComparison != 0) {
            return dayComparison;
        }

        return Integer.compare(grade1.mark, grade2.mark);
    }
}
