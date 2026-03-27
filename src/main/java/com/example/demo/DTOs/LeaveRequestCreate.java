package com.example.demo.DTOs;

import java.time.LocalDate;

import jakarta.validation.constraints.FutureOrPresent;

public record LeaveRequestCreate(

    @FutureOrPresent(message ="start date shoud be now or in the future")
    LocalDate startDate,

    @FutureOrPresent(message ="end date shoud be now or in the future")
    LocalDate endDate,

    String reason






) 
{

}
