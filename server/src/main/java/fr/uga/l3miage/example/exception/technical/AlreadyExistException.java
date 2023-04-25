package fr.uga.l3miage.example.exception.technical;

import lombok.Getter;

/**
 * Exception technique<br>
 * Les annotations :
 * <ul>
 *     <li>{@link Getter} permet de créer tout les getters de tous les attributs. Voir la doc <a href="https://projectlombok.org/features/GetterSetter">projetlombok.org/features/Getter</a></li>
 * </ul>
 */
@Getter
public class AlreadyExistException extends Exception {
    private final Long id;

    public AlreadyExistException(String message, Long id) {
        super(message);
        this.id = id;
    }

    public AlreadyExistException(String message, Long id, Throwable cause) {
        super(message, cause);
        this.id = id;
    }
}
