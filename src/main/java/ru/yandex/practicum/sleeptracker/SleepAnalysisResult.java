package ru.yandex.practicum.sleeptracker;

public class SleepAnalysisResult {

    private final String functionDescription;
    private final Object result;

    public SleepAnalysisResult(String functionDescription, Object result) {
        this.functionDescription = functionDescription;
        this.result = result;
    }

    public String getFunctionDescription() {
        return functionDescription;
    }

    public Object getResult() {
        return result;
    }

    @Override
    public String toString() {
        return functionDescription + ": " + result;
    }
}