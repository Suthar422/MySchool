package com.myschool.announcement.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/announcements")
public class AnnouncementController {

    @GetMapping("/")
    public String getAnnouncement() {
        return "Hello World!";
    }

    @PostMapping("/createAnnouncement")
    public String postAnnouncement() {
        return "Hello World!";
    }
}
