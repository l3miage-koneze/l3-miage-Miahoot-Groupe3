package fr.uga.l3miage.example.exception.technical;

public class NotTheSameIdException extends Exception{
    private final Long id1;
    private final Long id2;


    public NotTheSameIdException(String message, Long id1, Long id2) {
        super(message);
        this.id1 = id1;
        this.id2 = id2;
    }

    public NotTheSameIdException(String message, Long id1, Long id2, Throwable cause) {
        super(message, cause);
        this.id1 = id1;
        this.id2 = id2;
    }
}
