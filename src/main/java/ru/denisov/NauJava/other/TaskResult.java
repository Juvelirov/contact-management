package ru.denisov.NauJava.other;

public class TaskResult<T> {
    private final T result;
    private final long executionTime;

    public TaskResult(T result, long executionTime) {
        this.result = result;
        this.executionTime = executionTime;
    }

    public T getResult() {
        return result;
    }

    public long getExecutionTime() {
        return executionTime;
    }
}