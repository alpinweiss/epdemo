package eu.alpinweiss.demo.service;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.MessageSource;
import org.springframework.http.converter.HttpMessageNotReadableException;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.internal.matchers.NotNull.NOT_NULL;

@RunWith(MockitoJUnitRunner.class)
public class MessageServiceImplTest {

    public static final String FIELD_1 = "field1";

    @Mock
    private InvalidFormatException exception;
    @Mock
    private HttpMessageNotReadableException httpMessageNotReadableException;
    @Mock
    private MessageSource messageSource;
    @InjectMocks
    private MessageService messageService = new MessageServiceImpl();

    @Before
    public void setUp() throws Exception {
        when(httpMessageNotReadableException.getRootCause()).thenReturn(exception);
        when(messageSource.getMessage(anyString(), Matchers.<Object[]>any(), any(Locale.class))).thenReturn(MessageServiceImpl.VALIDATION_NUMERIC_MESSAGE);
    }

    @Test
    public void shouldReturnErrorMessage_CaseNotANumber() throws Exception {
        List<JsonMappingException.Reference> path = new ArrayList<>();
        path.add(new JsonMappingException.Reference(null, FIELD_1));
        when(exception.getPath()).thenReturn(path);
        when(exception.getValue()).thenReturn("Not a number!");

        Map<String, String> userReadableMessage = messageService.createUserReadableMessage(httpMessageNotReadableException);

        assertThat(userReadableMessage, NOT_NULL);
        assertThat(userReadableMessage.size(), is(1));
        assertThat(userReadableMessage.get(FIELD_1), equalTo(MessageServiceImpl.VALIDATION_NUMERIC_MESSAGE));

        when(exception.getValue()).thenReturn("123s");

        userReadableMessage = messageService.createUserReadableMessage(httpMessageNotReadableException);

        assertThat(userReadableMessage, NOT_NULL);
        assertThat(userReadableMessage.size(), is(1));
        assertThat(userReadableMessage.get(FIELD_1), equalTo(MessageServiceImpl.VALIDATION_NUMERIC_MESSAGE));
    }

    @Test
    public void shouldPassValidation() throws Exception {
        List<JsonMappingException.Reference> path = new ArrayList<>();
        path.add(new JsonMappingException.Reference(null, FIELD_1));
        when(exception.getPath()).thenReturn(path);
        when(exception.getValue()).thenReturn(12.0);

        Map<String, String> userReadableMessage = messageService.createUserReadableMessage(httpMessageNotReadableException);

        assertThat(userReadableMessage, NOT_NULL);
        assertThat(userReadableMessage.size(), is(0));

        when(exception.getValue()).thenReturn(12);

        userReadableMessage = messageService.createUserReadableMessage(httpMessageNotReadableException);

        assertThat(userReadableMessage, NOT_NULL);
        assertThat(userReadableMessage.size(), is(0));
    }

    @Test
    public void shouldReturnErrorMessage_CaseOnlyPositiveNumbersAllowed() throws Exception {
        List<JsonMappingException.Reference> path = new ArrayList<>();
        path.add(new JsonMappingException.Reference(null, FIELD_1));
        when(exception.getPath()).thenReturn(path);
        when(exception.getValue()).thenReturn(-12.0);

        Map<String, String> userReadableMessage = messageService.createUserReadableMessage(httpMessageNotReadableException);

        assertThat(userReadableMessage, NOT_NULL);
        assertThat(userReadableMessage.size(), is(1));
        assertThat(userReadableMessage.get(FIELD_1), equalTo(MessageServiceImpl.VALIDATION_NUMERIC_MESSAGE));

        when(exception.getValue()).thenReturn(-12);

        userReadableMessage = messageService.createUserReadableMessage(httpMessageNotReadableException);

        assertThat(userReadableMessage, NOT_NULL);
        assertThat(userReadableMessage.size(), is(1));
        assertThat(userReadableMessage.get(FIELD_1), equalTo(MessageServiceImpl.VALIDATION_NUMERIC_MESSAGE));
    }

}