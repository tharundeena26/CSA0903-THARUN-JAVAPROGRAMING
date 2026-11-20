import java.io.IOException;

class ExamException extends Exception {
    ExamException(String message) { super(message); }
    ExamException(String message, Throwable cause) { super(message, cause); }
}

class SubmissionStorageException extends ExamException {
    SubmissionStorageException(String message, Throwable cause) {
        super(message, cause);
    }
}

class ExamWindowClosedException extends ExamException {
    ExamWindowClosedException(String message) { super(message); }
}

class StudentNotRegisteredException extends ExamException {
    StudentNotRegisteredException(String message) { super(message); }
}

class FileTooLargeException extends ExamException {
    FileTooLargeException(String message) { super(message); }
}

class Storage {
    void save(String fileName, long sizeKb) throws SubmissionStorageException {
        try {
            if (sizeKb > 500) throw new IOException("Disk quota exceeded");
            System.out.println("Saved " + fileName);
        } catch (IOException e) {
            throw new SubmissionStorageException("Could not store " + fileName, e);
        }
    }
}

class ExamService {
    private final Storage storage = new Storage();

    void submit(String file, long sizeKb, boolean windowOpen, boolean registered)
            throws ExamException {
        if (!windowOpen) throw new ExamWindowClosedException("Exam window has closed");
        if (!registered) throw new StudentNotRegisteredException("Student is not registered");
        if (sizeKb > 1000) throw new FileTooLargeException("Answer file exceeds 1000 KB");
        storage.save(file, sizeKb);
    }
}

public class StorageDemo {
    public static void main(String[] args) {
        ExamService service = new ExamService();
        try {
            service.submit("answer01.pdf", 120, true, true);
            service.submit("answer02.pdf", 800, true, true);
        } catch (ExamException e) {
            System.out.println("Error: " + e.getMessage());
            if (e.getCause() != null) {
                System.out.println("Cause: " + e.getCause().getMessage());
            } else {
                System.out.println("Cause: none");
            }
        }
    }
}