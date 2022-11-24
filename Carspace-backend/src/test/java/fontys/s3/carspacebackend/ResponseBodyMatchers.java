package fontys.s3.carspacebackend;

import com.fasterxml.jackson.databind.ObjectMapper;
import fontys.s3.carspacebackend.configuration.ControllerExceptionHandler.*;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ResponseBodyMatchers {
    private ObjectMapper objectMapper = new ObjectMapper();

    public <T> ResultMatcher containsObjectAsJson(
            Object expectedObject,
            Class<T> targetClass) {
        return mvcResult -> {
            String json = mvcResult.getResponse().getContentAsString();
            T actualObject = objectMapper.readValue(json, targetClass);
            assertTrue(new ReflectionEquals(expectedObject).matches(actualObject));
        };
    }

    public ResultMatcher containsError(
            String expectedFieldName,
            String expectedMessage) {
        return mvcResult -> {
            String json = mvcResult.getResponse().getContentAsString();
            ErrorResult errorResult = objectMapper.readValue(json, ErrorResult.class);
            List<FieldValidationError> fieldErrors = errorResult.getFieldErrors().stream()
                    .filter(fieldError -> fieldError.getField().equals(expectedFieldName))
                    .filter(fieldError -> fieldError.getMessage().equals(expectedMessage))
                    .collect(Collectors.toList());

            assertEquals(1, fieldErrors.size());
        };
    }
    public static ResponseBodyMatchers responseBody(){
        return new ResponseBodyMatchers();
    }
}
