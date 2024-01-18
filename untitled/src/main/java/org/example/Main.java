package org.example;


import java.util.Set;

public class Main
{

    public static void main(String[] args)
    {
        System.out.println("Current Working Directory: " + System.getProperty("user.dir"));
        Menu menu = new Menu();
        menu.run();
    }
}