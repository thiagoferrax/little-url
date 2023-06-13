package com.thiagoferraz.littleurl.resources.exceptions;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.thiagoferraz.littleurl.dto.UrlDTO;
import com.thiagoferraz.littleurl.services.exceptions.UrlNotFoundException;

public class ResourceExceptionHandlerTest {

	@Mock
	HttpServletRequest httpServletRequest;

	@Mock
	BindingResult bindingResult;

	@InjectMocks
	ResourceExceptionHandler handler;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void whenHandlingANotFoundExceptionReturnsStatusNotFoundAndExpectedMessage() {
		// Given
		String message = "Url not found exception.";
		UrlNotFoundException e = new UrlNotFoundException(message);
		
		//When
		ResponseEntity<StandardError> urlNotFound = handler.urlNotFound(e, httpServletRequest);

		//Then
		Assert.assertEquals(HttpStatus.NOT_FOUND, urlNotFound.getStatusCode());
		Assert.assertEquals(message, urlNotFound.getBody().getMessage());
		Assert.assertEquals(Integer.valueOf(HttpStatus.NOT_FOUND.value()), urlNotFound.getBody().getStatus());
	}
	
	@Test
	public void whenHandlingAMethodArgumentNotValidExceptionReturnsStatusBadRequestAndExpectedMessage() throws NoSuchMethodException, SecurityException {
		// Given
		Method method = UrlDTO.class.getMethod("setLongUrl", String.class);
		int parameterIndex = 0;
		MethodParameter parameter = new MethodParameter(method, parameterIndex);
		
		List<FieldError> errors = new ArrayList<FieldError>();
		String fieldName = "longUrl";
		String defaultMessage = "is not a valid URL";
		errors.add(new FieldError("UrlDTO", fieldName, defaultMessage));
		Mockito.when(bindingResult.getFieldErrors()).thenReturn(errors);
		
		MethodArgumentNotValidException e = new MethodArgumentNotValidException(parameter, bindingResult);
		
		//When
		ResponseEntity<ValidationError> validationError = handler.validation(e, httpServletRequest);

		//Then
		Assert.assertEquals(HttpStatus.BAD_REQUEST, validationError.getStatusCode());
		Assert.assertEquals(Integer.valueOf(HttpStatus.BAD_REQUEST.value()), validationError.getBody().getStatus());
		Assert.assertEquals(fieldName, validationError.getBody().getErrors().get(0).getFieldName());
		Assert.assertEquals(defaultMessage, validationError.getBody().getErrors().get(0).getMessage());
	}

}
