package com.bakery.hr.service;

import com.bakery.hr.dao.EmployeeDao;
import com.bakery.hr.model.Employee;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class EmployeeService {

    private final EmployeeDao dao = new EmployeeDao();

    public List<Employee> listActive() throws SQLException {
        return dao.findAllActive();
    }

    public void onboard(String name, String email, String role, double salary, LocalDate hireDate) throws SQLException {
        Employee e = new Employee(name, email, role, salary, hireDate, true);
        dao.insert(e);
    }

    public void changeSalary(int id, double newSalary) throws SQLException {
        dao.updateSalary(id, newSalary);
    }

    public void deactivate(int id) throws SQLException {
        dao.softDelete(id);
    }

    public List<Employee> salaryReport() throws SQLException {
        return dao.salaryReport();
    }
}
