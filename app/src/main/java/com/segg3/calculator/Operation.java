package com.segg3.calculator;

public class Operation {
    public final String identifier;
    public final int priority;
    public final int inputCount;
    public final OpImplementation implementation;

    public Operation(String identifier, int priority, int inputCount, OpImplementation implementation) {
        this.identifier = identifier;
        this.priority = priority;
        this.inputCount = inputCount;
        this.implementation = implementation;
    }
}
