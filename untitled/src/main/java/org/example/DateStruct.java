package org.example;

import java.util.Objects;
public class
DateStruct
{
    public Integer day;
    public Integer month;
    public Integer year;

    public String toString()
    {
        return day + " " + month + " " + year;
    }

    DateStruct(Integer day, Integer month, Integer year)
    {
        this.day = day;
        this.month = month;
        this.year = year;
    }
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        DateStruct that = (DateStruct) object;
        return Objects.equals(year, that.year) &&
                Objects.equals(month, that.month) &&
                Objects.equals(day, that.day);
    }

    @Override
    public int hashCode() {
        return Objects.hash(year, month, day);
    }
}
