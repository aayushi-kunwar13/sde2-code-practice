package com.practice.phonepe.questions.elevatorsystem.strategy;

import com.practice.phonepe.questions.elevatorsystem.Elevator;
import com.practice.phonepe.questions.elevatorsystem.models.Request;

import java.util.List;
import java.util.Optional;

public interface ElevatorSelectionStrategy {
    Optional<Elevator> selectElevator(List<Elevator> elevators, Request request);
}
