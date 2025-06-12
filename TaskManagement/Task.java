package TaskManagement;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * Represents a personal task.
 */
public class Task {
    private static int NEXT_ID = 1;

    private final int id;
    private final String description;
    private final int priority; // 1 (high) - 5 (low)
    private final LocalDate dueDate;
    private boolean completed = false;

    public Task(String description, int priority, String dueDateStr) throws InvalidPriorityException, DateParseException {
        if (priority < 1 || priority > 5) throw new InvalidPriorityException("Priority must be 1-5");
        this.description = description;
        this.priority = priority;
        try {
            this.dueDate = LocalDate.parse(dueDateStr);
        } catch (DateTimeParseException e) {
            throw new DateParseException("Invalid date format (YYYY-MM-DD)");
        }
        this.id = NEXT_ID++;
    }

    public int getId() { return id; }
    public String getDescription() { return description; }
    public int getPriority() { return priority; }
    public LocalDate getDueDate() { return dueDate; }
    public boolean isCompleted() { return completed; }

    public void markComplete() throws TaskAlreadyCompletedException {
        if (completed) throw new TaskAlreadyCompletedException("Task already completed");
        completed = true;
    }

    public boolean isOverdue() {
        return !completed && dueDate.isBefore(LocalDate.now());
    }

    @Override
    public String toString() {
        return String.format("#%d | %s | Priority:%d | Due:%s | %s", id, description, priority, dueDate, completed ? "Done" : "Pending");
    }
}

class InvalidPriorityException extends Exception { public InvalidPriorityException(String m){super(m);} }
class DateParseException extends Exception { public DateParseException(String m){super(m);} }
class TaskAlreadyCompletedException extends Exception { public TaskAlreadyCompletedException(String m){super(m);} }
class TaskNotFoundException extends Exception { public TaskNotFoundException(String m){super(m);} }
