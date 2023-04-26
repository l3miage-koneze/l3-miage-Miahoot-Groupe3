package fr.uga.l3miage.example.exception.rest;

import fr.uga.l3miage.example.error.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class NotTheSameIdRestException extends RuntimeException {
    private final Long id1;
    private final Long id2;


    public NotTheSameIdRestException(String message, Long id1, Long id2) {
        super(message);
        this.id1 = id1;
        this.id2 = id2;
    }

    public NotTheSameIdRestException(String message, Long id1, Long id2, Throwable cause) {
        super(message, cause);
        this.id1 = id1;
        this.id2 = id2;
    }

    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }

    public ErrorCode getErrorCode(){return ErrorCode.ALREADY_USE_ERROR;}
}
