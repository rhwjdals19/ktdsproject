package com.ktds.KimGo_Project.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
public class BoardDTO {
        private Long id;
        private String title;
        private String content;
        private String location;
        private String phone;
        private String rating;
        private String imagePath;
        private LocalDate date;
        private String type;
        private String foodType;



        // Getters and Setters
    }