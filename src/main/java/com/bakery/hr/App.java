package com.bakery.hr;

import com.bakery.hr.model.Employee;
import com.bakery.hr.service.EmployeeService;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class App {
    private static final EmployeeService service = new EmployeeService();

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            while (true) {
                System.out.println("\n=== Bakery HR Admin ===");
                System.out.println("1. View all active employees & salaries");
                System.out.println("2. Add employee");
                System.out.println("3. Update salary");
                System.out.println("4. Deactivate employee");
                System.out.println("5. Salary report");
                System.out.println("0. Exit");
                System.out.print("Choose: ");

                String choice = sc.nextLine().trim();
                switch (choice) {
	                case "1":
	                    viewAll();
	                    break;
	
	                case "2":
	                    add(sc);
	                    break;
	
	                case "3":
	                    updateSalary(sc);
	                    break;
	
	                case "4":
	                    deactivate(sc);
	                    break;
	
	                case "5":
	                    salaryReport();
	                    break;
	
	                case "0":
	                    System.out.println("Bye!");
	                    return;
	
	                default:
	                    System.out.println("Invalid choice.");
	                    break;
	            }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void viewAll() {
        try {
            List<Employee> list = service.listActive();
            System.out.println("\nID | Name                 | Role       | Salary");
            System.out.println("------------------------------------------------");
            for (Employee e : list) {
                System.out.printf("%-3d| %-20s| %-10s| %.2f%n",
                        e.getId(), e.getFullName(), e.getRole(), e.getBaseSalary());
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    static void add(Scanner sc) {
        try {
            System.out.print("Full name: ");
            String name = sc.nextLine().trim();
            System.out.print("Email: ");
            String email = sc.nextLine().trim();
            System.out.print("Role: ");
            String role = sc.nextLine().trim();
            System.out.print("Base salary: ");
            double salary = Double.parseDouble(sc.nextLine().trim());
            System.out.print("Hire date (YYYY-MM-DD, blank=today): ");
            String d = sc.nextLine().trim();
            LocalDate hire = d.isEmpty() ? LocalDate.now() : LocalDate.parse(d);

            service.onboard(name, email, role, salary, hire);
            System.out.println("Employee added.");
        } catch (Exception e) {
            System.out.println("Failed to add: " + e.getMessage());
        }
    }

    static void updateSalary(Scanner sc) {
        try {
            System.out.print("Employee ID: ");
            int id = Integer.parseInt(sc.nextLine().trim());
            System.out.print("New Salary: ");
            double salary = Double.parseDouble(sc.nextLine().trim());
            service.changeSalary(id, salary);
            System.out.println("Salary updated.");
        } catch (Exception e) {
            System.out.println("Failed to update: " + e.getMessage());
        }
    }

    static void deactivate(Scanner sc) {
        try {
            System.out.print("Employee ID: ");
            int id = Integer.parseInt(sc.nextLine().trim());
            service.deactivate(id);
            System.out.println("Employee deactivated.");
        } catch (Exception e) {
            System.out.println("Failed to deactivate: " + e.getMessage());
        }
    }

    static void salaryReport() {
        try {
            List<Employee> list = service.salaryReport();
            System.out.println("\n=== Salary Report (Active) ===");
            System.out.println("ID  | Name                 | Role       | Salary");
            System.out.println("-----------------------------------------------");
            for (Employee e : list) {
                System.out.printf("%-3d | %-20s | %-10s | %.2f%n",
                        e.getId(), e.getFullName(), e.getRole(), e.getBaseSalary());
            }
        } catch (Exception e) {
            System.out.println("Failed to load report: " + e.getMessage());
        }
    }
}
