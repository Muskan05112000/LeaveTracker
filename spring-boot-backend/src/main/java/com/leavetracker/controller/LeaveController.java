package com.leavetracker.controller;

import com.leavetracker.model.Leave;
import com.leavetracker.service.LeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/leaves")
public class LeaveController {
    @Autowired
    private LeaveService leaveService;

    @GetMapping
    public List<Leave> getAllLeaves() {
        return leaveService.getAllLeaves();
    }

    @PostMapping
    public ResponseEntity<Leave> createLeave(@RequestBody Leave leave) {
        Leave saved = leaveService.saveLeave(leave);
        return ResponseEntity.status(201).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Leave> updateLeave(@PathVariable String id, @RequestBody Leave leave) {
        Optional<Leave> updated = leaveService.getLeaveById(id);
        if (updated.isPresent()) {
            Leave l = updated.get();
            l.setDate(leave.getDate());
            l.setEmployee(leave.getEmployee());
            l.setType(leave.getType());
            return ResponseEntity.ok(leaveService.saveLeave(l));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLeave(@PathVariable String id) {
        leaveService.deleteLeaveById(id);
        return ResponseEntity.noContent().build();
    }
}
