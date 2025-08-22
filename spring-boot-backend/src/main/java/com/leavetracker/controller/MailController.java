package com.leavetracker.controller;

import com.leavetracker.model.Employee;
import com.leavetracker.model.Leave;
import com.leavetracker.service.EmployeeService;
import com.leavetracker.service.MailService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@RequestMapping("/api/send-leave-email")
public class MailController {
    @Autowired
    private MailService mailService;
    @Autowired
    private EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<?> sendLeaveEmail(@RequestBody Map<String, Object> payload) {
        try {
            String to = (String) payload.get("to");
            String subject = (String) payload.get("subject");
            List<Map<String, Object>> employees = (List<Map<String, Object>>) payload.get("employees");
            List<Map<String, Object>> leaves = (List<Map<String, Object>>) payload.get("leaves");
            String weekStart = (String) payload.get("weekStart");
            String user = (String) payload.getOrDefault("user", to);
            String appPassword = (String) payload.get("appPassword");

            // Build week days (Mon-Fri)
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate start = LocalDate.parse(weekStart, formatter);
            List<LocalDate> weekDays = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                weekDays.add(start.plusDays(i));
            }

            // Build HTML table
            StringBuilder html = new StringBuilder();
            html.append("<h3>Leave update for this week</h3><table border=\"1\" cellpadding=\"6\" cellspacing=\"0\" style=\"border-collapse:collapse;font-family:Arial,sans-serif;font-size:15px;min-width:400px;text-align:center;\">");
            html.append("<thead><tr><th>Employee Name</th>");
            for (LocalDate d : weekDays) {
                html.append("<th>").append(d.format(DateTimeFormatter.ofPattern("dd MMM"))).append("</th>");
            }
            html.append("</tr></thead><tbody>");
            for (Map<String, Object> emp : employees) {
                html.append("<tr><td style='font-weight:600'>").append(emp.get("name")).append("</td>");
                for (LocalDate day : weekDays) {
                    String code = "";
                    for (Map<String, Object> leave : leaves) {
                        if (leave.get("employee").equals(emp.get("name")) &&
                            day.toString().equals(leave.get("date"))) {
                            String type = (String) leave.get("type");
                            if ("Planned".equals(type)) code = "PL";
                            else if ("Emergency".equals(type)) code = "EL";
                            else if ("Sick".equals(type)) code = "SL";
                        }
                    }
                    html.append("<td>").append(code).append("</td>");
                }
                html.append("</tr>");
            }
            html.append("</tbody></table>");

            mailService.sendHtmlMail(to, subject, html.toString(), user, appPassword);
            return ResponseEntity.ok(Collections.singletonMap("success", true));
        } catch (MessagingException | RuntimeException e) {
            return ResponseEntity.status(500).body(Collections.singletonMap("error", e.getMessage()));
        }
    }
}
