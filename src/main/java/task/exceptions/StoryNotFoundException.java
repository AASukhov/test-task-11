package task.exceptions;

public class StoryNotFoundException extends RuntimeException {
    public StoryNotFoundException(String message) {
        super(message);
    }
}
