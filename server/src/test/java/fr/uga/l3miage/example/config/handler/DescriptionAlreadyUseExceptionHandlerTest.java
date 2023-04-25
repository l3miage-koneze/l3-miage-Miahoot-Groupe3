package fr.uga.l3miage.example.config.handler;

import fr.uga.l3miage.example.error.AlreadyUseErrorResponse;
import fr.uga.l3miage.example.error.ErrorResponse;
import fr.uga.l3miage.example.exception.rest.AlreadyUseRestException;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Permet de tester le handler de l'exception d'API {@link AlreadyUseRestException}
 */
class AlreadyUseExceptionHandlerTest {
    @Test
    void testHandle() {
        AlreadyUseExceptionHandler handler = new AlreadyUseExceptionHandler();
        AlreadyUseRestException exception = new AlreadyUseRestException("Already use",-1L);
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("TestConfigWithProperties");
        ResponseEntity<AlreadyUseErrorResponse> expected = ResponseEntity.status(exception.getHttpStatus())
                .body(AlreadyUseErrorResponse.builder()
                        .errorMessage(exception.getMessage())
                        .uri(request.getRequestURI())
                        .errorCode(exception.getErrorCode())
                        .httpStatus(exception.getHttpStatus())
                        .id(exception.getId())
                        .build());

        ResponseEntity<ErrorResponse> response = handler.handle(request, exception);
        assertThat(response).usingRecursiveComparison().isEqualTo(expected);
    }
}
