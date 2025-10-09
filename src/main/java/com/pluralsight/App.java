package com.pluralsight;

import java.io.*;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the name of the file to process:");
        String file = scanner.nextLine().trim();

        System.out.println("Enter the name of the payroll file to create:");
        String payrollName = scanner.nextLine().trim();

        Employee[] employees = readEmployees(file);

        displayEmployees(employees);

        writeReport(employees, payrollName);

    }

    private static Employee[] readEmployees(String file) {
        // Create an array to hold up to 8 employees
        Employee[] employees = new Employee[8];

        int counter = 0;  // Counter to track how many employees are added

        // Read employee data from the CSV file
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
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
            }

        } catch (IOException e) {
            System.out.println("Error reading employee data:");
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.out.println("Error parsing numeric values from file:");
            e.printStackTrace();
        }
        return employees;
    }

    /**
     * Writes a payroll report to a file in either JSON or CSV format.
     *
     * @param employees   Array of Employee objects to include in the report
     * @param payrollName The output file name (e.g., "payroll.json" or "payroll.csv")
     * @throws IOException If there is an error writing to the file
     */
    private static void writeReport(Employee[] employees, String payrollName) throws IOException {
        // Extract file extension to determine format (e.g., "json" or "csv")
        String[] fileName = payrollName.split("\\.");
        String format = fileName[1];

        // Create a writer to output to the specified payroll file
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(payrollName));

        String report = "";  // Will hold the full report content

        // Handle JSON format
        if (format.equalsIgnoreCase("json")) {
            report = "[\n";  // Start of JSON array

            for (Employee employee : employees) {
                // Format each employee as a JSON object
                                String fmtStr = String.format(
                        "{\"id\": %d, \"name\": \"%s\", \"grossPay\": %.2f}, \n",
                        employee.getEmployeeId(), employee.getName(), employee.getGrossPay()
                );

                report += fmtStr;  // Append to the report string
            }

            report += "\n]";  // End of JSON array

            // Write JSON report to file
            bufferedWriter.write(report);
            bufferedWriter.close();

        } else if (format.equalsIgnoreCase("csv")) {
            // Handle CSV format
            for (Employee employee : employees) {
                // Format each employee as a pipe-separated line
                report += employee.getEmployeeId() + "|" + employee.getName() + "|" + employee.getGrossPay() + "\n";
            }

            // Write CSV report to file
            bufferedWriter.write(report);
            bufferedWriter.close();
        }
    }


    private static void displayEmployees(Employee[] employees) {
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
