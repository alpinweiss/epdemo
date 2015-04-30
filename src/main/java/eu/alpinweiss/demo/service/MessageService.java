package eu.alpinweiss.demo.service;

import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Map;

public interface MessageService {

    Map<String, String> createUserReadableMessage(HttpMessageNotReadableException ex);

    Map<String, String> createUserReadableMessage(MethodArgumentNotValidException ex);

}
