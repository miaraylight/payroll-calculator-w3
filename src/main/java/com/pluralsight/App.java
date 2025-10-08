package com.pluralsight;

import java.io.*;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // Create an array to hold up to 8 employees
        Employee[] employees = new Employee[8];
        int counter = 0;  // Counter to track how many employees are added

        System.out.println("Enter the name of the file to process:");
        String file = scanner.nextLine().trim();

        System.out.println("Enter the name of the payroll file to create:");
        String payroll = scanner.nextLine().trim();

        // Read employee data from the CSV file
        try (
                BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(payroll, true));
        ) {

            String line;

            // Skip the header line
            bufferedReader.readLine();

            // Read each line of the file and parse employee data
            while ((line = bufferedReader.readLine()) != null) {
                // Split the line into parts using pipe symbol |
                String[] parts = line.split("\\|");

                // Parse individual fields
                int id = Integer.parseInt(parts[0]);         // Employee ID
                String name = parts[1];                      // Employee Name
                double hours = Double.parseDouble(parts[2]); // Hours Worked
                double rate = Double.parseDouble(parts[3]);  // Hourly Rate

                // Create an Employee object and add it to the array
                Employee employee = new Employee(id, name, hours, rate);
                employees[counter++] = employee;

                String report = parts[0]+"|"+name+"|"+employee.getGrossPay()+"\n";
                bufferedWriter.write(report);
            }

        } catch (IOException e) {
            System.out.println("Error reading employee data:");
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.out.println("Error parsing numeric values from file:");
            e.printStackTrace();
        }

        // Print all employee information
        System.out.println("\nEmployee List:");
        for (Employee employee : employees) {
            if (employee != null) {
                System.out.println("==========================================================");
                System.out.println(employee);
                String message = String.format("%s gross pay is: %.2f", employee.getName(), employee.getGrossPay());
                System.out.println(message);

            }
        }
    }
}
