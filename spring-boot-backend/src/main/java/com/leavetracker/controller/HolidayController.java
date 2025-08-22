package com.leavetracker.controller;

import com.leavetracker.model.Holiday;
import com.leavetracker.service.HolidayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/holidays")
public class HolidayController {
    @Autowired
    private HolidayService holidayService;

    @GetMapping
    public List<Holiday> getAllHolidays() {
        return holidayService.getAllHolidays();
    }
}
