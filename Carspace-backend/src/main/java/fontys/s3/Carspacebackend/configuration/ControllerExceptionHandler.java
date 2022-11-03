package fontys.s3.Carspacebackend.configuration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ControllerExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    ErrorResult handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        ErrorResult errorResult = new ErrorResult();
        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            errorResult.getFieldErrors()
                    .add(new FieldValidationError(fieldError.getField(),
                            fieldError.getDefaultMessage()));
        }
        return errorResult;
    }

    @Getter
    @NoArgsConstructor
    public static class ErrorResult {
        private final List<FieldValidationError> fieldErrors = new ArrayList<>();
        public ErrorResult(String field, String message){
            this.fieldErrors.add(new FieldValidationError(field, message));
        }
        public void add(String field, String message){
            this.fieldErrors.add(new FieldValidationError(field, message));
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FieldValidationError {
        private String field;
        private String message;
    }

}
