package com.bakery.hr.model;

import java.time.LocalDate;

public class Employee {
    private int id;
    private String fullName;
    private String email;
    private String role;
    private double baseSalary;
    private LocalDate hireDate;
    private boolean active;

    // constructors
    public Employee() {}
    public Employee(String fullName, String email, String role, double baseSalary, LocalDate hireDate, boolean active) {
        this.fullName = fullName;
        this.email = email;
        this.role = role;
        this.baseSalary = baseSalary;
        this.hireDate = hireDate;
        this.active = active;
    }

    // getters/setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public double getBaseSalary() { return baseSalary; }
    public void setBaseSalary(double baseSalary) { this.baseSalary = baseSalary; }
    public LocalDate getHireDate() { return hireDate; }
    public void setHireDate(LocalDate hireDate) { this.hireDate = hireDate; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }

    @Override
    public String toString() {
        return String.format("#%d | %s | %s | %s | %.2f | %s | active=%s",
                id, fullName, email, role, baseSalary, hireDate, active);
    }
}
