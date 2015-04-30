package eu.alpinweiss.demo.service;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Service
public class MessageServiceImpl implements MessageService {

    static final String VALIDATION_NUMERIC_MESSAGE = "validation.numeric.message";

    @Autowired
    private MessageSource messageSource;

    @Override
    public Map<String, String> createUserReadableMessage(HttpMessageNotReadableException ex) {
        Map<String, String> fieldErrorMap = new HashMap<>();

        InvalidFormatException rootCause = (InvalidFormatException) ex.getRootCause();
        List<JsonMappingException.Reference> path = rootCause.getPath();
        for (JsonMappingException.Reference fieldError : path) {
            String fieldName = fieldError.getFieldName();
            if (!isPositiveNumeric(rootCause.getValue().toString())) {
                fieldErrorMap.put(fieldName, messageSource.getMessage(VALIDATION_NUMERIC_MESSAGE, null, Locale.US));
            }
        }
        return fieldErrorMap;
    }

    @Override
    public Map<String, String> createUserReadableMessage(MethodArgumentNotValidException ex) {
        Map<String, String> fieldErrorMap = new HashMap<>();

        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        fieldErrors.forEach(p -> fieldErrorMap.put(p.getField(), p.getDefaultMessage()));

        return fieldErrorMap;
    }

    private boolean isPositiveNumeric(String str) {
        return str.matches("^\\d+(\\.\\d+)?$");
    }
}
