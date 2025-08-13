package com.leavetracker.service;

import com.leavetracker.model.Employee;
import com.leavetracker.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Optional<Employee> getEmployeeByAssociateId(Long associateId) {
        return employeeRepository.findByAssociateId(associateId);
    }

    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public void deleteEmployeeByAssociateId(Long associateId) {
        employeeRepository.deleteByAssociateId(associateId);
    }

    public Optional<Employee> updateEmployee(String name, Employee updatedEmployee) {
        Optional<Employee> existing = employeeRepository.findByName(name);
        if (existing.isPresent()) {
            Employee emp = existing.get();
            emp.setName(updatedEmployee.getName());
            emp.setAssociateId(updatedEmployee.getAssociateId());
            emp.setLocation(updatedEmployee.getLocation());
            emp.setTeam(updatedEmployee.getTeam());
            emp.setRole(updatedEmployee.getRole());
            return Optional.of(employeeRepository.save(emp));
        }
        return Optional.empty();
    }
}
