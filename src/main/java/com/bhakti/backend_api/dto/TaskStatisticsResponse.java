package com.bhakti.backend_api.dto;

public class TaskStatisticsResponse {

    private long totalTasks;

    private long todo;

    private long inProgress;

    private long done;

    private Double completionRate;

    public TaskStatisticsResponse() {
    }

    public long getTotalTasks() {
        return totalTasks;
    }

    public void setTotalTasks(
            long totalTasks
    ) {
        this.totalTasks = totalTasks;
    }

    public long getTodo() {
        return todo;
    }

    public void setTodo(
            long todo
    ) {
        this.todo = todo;
    }

    public long getInProgress() {
        return inProgress;
    }

    public void setInProgress(
            long inProgress
    ) {
        this.inProgress = inProgress;
    }

    public long getDone() {
        return done;
    }

    public void setDone(
            long done
    ) {
        this.done = done;
    }

    public Double getCompletionRate() {
    return completionRate;
}

public void setCompletionRate(
        Double completionRate
) {
    this.completionRate = completionRate;
}
}