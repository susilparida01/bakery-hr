package com.bakery.hr.dao;

import com.bakery.hr.config.DBConfig;
import com.bakery.hr.model.Employee;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDao {

    public List<Employee> findAllActive() throws SQLException {
        String sql = "SELECT id, full_name, email, role, base_salary, hire_date, is_active FROM employees WHERE is_active=1 ORDER BY full_name";
        List<Employee> list = new ArrayList<>();
        try (Connection con = DBConfig.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Employee e = mapRow(rs);
                list.add(e);
            }
        }
        return list;
    }

    public Employee findById(int id) throws SQLException {
        String sql = "SELECT id, full_name, email, role, base_salary, hire_date, is_active FROM employees WHERE id=?";
        try (Connection con = DBConfig.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapRow(rs) : null;
            }
        }
    }

    public int insert(Employee e) throws SQLException {
        String sql = "INSERT INTO employees(full_name, email, role, base_salary, hire_date, is_active) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = DBConfig.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, e.getFullName());
            ps.setString(2, e.getEmail());
            ps.setString(3, e.getRole());
            ps.setDouble(4, e.getBaseSalary());
            ps.setDate(5, Date.valueOf(e.getHireDate()));
            ps.setBoolean(6, e.isActive());
            int rows = ps.executeUpdate();
            if (rows == 1) {
                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (keys.next()) e.setId(keys.getInt(1));
                }
            }
            return rows;
        }
    }

    public int updateSalary(int id, double newSalary) throws SQLException {
        String sql = "UPDATE employees SET base_salary=? WHERE id=?";
        try (Connection con = DBConfig.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDouble(1, newSalary);
            ps.setInt(2, id);
            return ps.executeUpdate();
        }
    }

    public int softDelete(int id) throws SQLException {
        String sql = "UPDATE employees SET is_active=0 WHERE id=?";
        try (Connection con = DBConfig.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate();
        }
    }

    public List<Employee> salaryReport() throws SQLException {
        String sql = "SELECT id, full_name, role, base_salary FROM v_salary_report";
        List<Employee> list = new ArrayList<>();
        try (Connection con = DBConfig.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Employee e = new Employee();
                e.setId(rs.getInt("id"));
                e.setFullName(rs.getString("full_name"));
                e.setRole(rs.getString("role"));
                e.setBaseSalary(rs.getDouble("base_salary"));
                
                // these columns are not in the view; keep them unset/derived if needed
                e.setHireDate(null); // not in the view; set a placeholder if needed
                e.setActive(true);
                e.setEmail(null); // nullable in view, handled above
               
                list.add(e);
            }
        }
        return list;
    }

    private Employee mapRow(ResultSet rs) throws SQLException {
        Employee e = new Employee();
        e.setId(rs.getInt("id"));
        e.setFullName(rs.getString("full_name"));
        e.setEmail(rs.getString("email"));
        e.setRole(rs.getString("role"));
        e.setBaseSalary(rs.getDouble("base_salary"));
        Date d = rs.getDate("hire_date");
        e.setHireDate(d != null ? d.toLocalDate() : null);
        e.setActive(rs.getBoolean("is_active"));
        return e;
    }
}
