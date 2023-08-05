package com.mahrukh.timesheet.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class TimesheetTemplateController {
    @GetMapping("/templates")
    public String getTimesheetTemplate(Model model) {

        List<String> days = List.of("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday");

        model.addAttribute("name", "Aamir Latif");
        model.addAttribute("days", days);
        return "timesheetTemplate";
    }
}
