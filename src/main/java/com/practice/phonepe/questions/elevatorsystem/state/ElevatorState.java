package com.practice.phonepe.questions.elevatorsystem.state;

import com.practice.phonepe.questions.elevatorsystem.Elevator;
import com.practice.phonepe.questions.elevatorsystem.enums.Direction;
import com.practice.phonepe.questions.elevatorsystem.models.Request;

public interface ElevatorState {
    void move(Elevator elevator);
    void addRequest(Elevator elevator, Request request);
    Direction getDirection();
}
