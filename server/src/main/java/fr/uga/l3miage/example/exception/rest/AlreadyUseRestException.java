package fr.uga.l3miage.example.exception.rest;

import fr.uga.l3miage.example.error.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;


/**
 * Correspond à l'exception d'API d'une description déjà utilisée<br>
 * Les annotations :
 * <ul>
 *     <li>{@link Getter} permet de créer tout les getters de tous les attributs. Voir la doc <a href="https://projectlombok.org/features/GetterSetter">projetlombok.org/features/Getter</a></li>
 * </ul>
 */
@Getter
public class AlreadyUseRestException extends RuntimeException {
    private final Long id;

    public AlreadyUseRestException(String message, Long id) {
        super(message);
        this.id = id;
    }

    public AlreadyUseRestException(String message, Long id, Throwable cause) {
        super(message, cause);
        this.id = id;
    }

    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }

    public ErrorCode getErrorCode(){return ErrorCode.ALREADY_USE_ERROR;}
}
