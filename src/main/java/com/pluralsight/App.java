package com.pluralsight;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class App {
    public static void main(String[] args) {
        Employee[] employees = new Employee[8];
        int counter = 0;


        try {
            FileReader fileReader = new FileReader("employees.csv");

            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line;

            bufferedReader.readLine();

            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
                String[] parts = line.split("\\|");  // Double backslash to escape the pipe
                int id = Integer.parseInt(parts[0]);
                String name = parts[1];
                double hours = Double.parseDouble(parts[2]);
                double rate = Double.parseDouble(parts[3]);
                Employee employee = new Employee(id, name, hours, rate);
                employees[counter] = employee;
                counter++;
            }

            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (Employee employee: employees) {
            System.out.println(employee.toString());
        }
    }
}
