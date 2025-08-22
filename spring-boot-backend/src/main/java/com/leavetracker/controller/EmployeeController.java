package com.leavetracker.controller;

import com.leavetracker.model.Employee;
import com.leavetracker.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        Employee saved = employeeService.saveEmployee(employee);
        return ResponseEntity.status(201).body(saved);
    }

    @PutMapping("/{name}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable String name, @RequestBody Employee employee) {
        Optional<Employee> updated = employeeService.updateEmployee(name, employee);
        return updated.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{associateId}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long associateId) {
        employeeService.deleteEmployeeByAssociateId(associateId);
        return ResponseEntity.noContent().build();
    }
}
