package com.example.etutorbackend.model.entity;

import lombok.Getter;

@Getter
public enum RatingValue {
    VERY_LOW(1), LOW(2), NORMAL(3), HIGH(4), VERY_HIGH(5);

    private final int ratingValue;


    RatingValue(int i) {
        this.ratingValue = i;
    }




}
