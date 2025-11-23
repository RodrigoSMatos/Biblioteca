package com.biblioteca.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GlobalExceptionHandlerTest {

  private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

  private HttpServletRequest mockRequest(String uri) {
    HttpServletRequest req = mock(HttpServletRequest.class);
    when(req.getRequestURI()).thenReturn(uri);
    return req;
  }

  // -----------------------------
  // NOT FOUND
  // -----------------------------
  @Test
  void deveLidarComNotFound() {
    HttpServletRequest req = mockRequest("/livros/1");
    ResourceNotFoundException ex = new ResourceNotFoundException("Livro não encontrado");

    ResponseEntity<ApiError> resposta = handler.handleNotFound(ex, req);

    assertEquals(HttpStatus.NOT_FOUND, resposta.getStatusCode());
    assertEquals("Livro não encontrado", resposta.getBody().getMessage());
  }

  // -----------------------------
  // CONSTRAINT VIOLATION
  // -----------------------------
  @Test
  void deveLidarComConstraintViolation() {
    HttpServletRequest req = mockRequest("/categorias");
    ConstraintViolationException ex = new ConstraintViolationException("Campo inválido", null);

    ResponseEntity<ApiError> resposta = handler.handleConstraintViolation(ex, req);

    assertEquals(HttpStatus.BAD_REQUEST, resposta.getStatusCode());
  }

  // -----------------------------
  // ILLEGAL ARGUMENT
  // -----------------------------
  @Test
  void deveLidarComIllegalArgument() {
    HttpServletRequest req = mockRequest("/acao");
    IllegalArgumentException ex = new IllegalArgumentException("Regra violada");

    ResponseEntity<ApiError> resposta = handler.handleIllegalArgument(ex, req);

    assertEquals(HttpStatus.BAD_REQUEST, resposta.getStatusCode());
  }

  // -----------------------------
  // METHOD ARGUMENT NOT VALID
  // -----------------------------
  @Test
  void deveLidarComMethodArgumentNotValid() {
    // mock do BindingResult com um erro de campo
    BindingResult bindingResult = mock(BindingResult.class);
    FieldError fieldError = new FieldError("livroDTO", "titulo", "não pode ser vazio");
    when(bindingResult.getAllErrors()).thenReturn(List.of(fieldError));

    // mock da exception com o BindingResult dentro
    MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
    when(ex.getBindingResult()).thenReturn(bindingResult);

    WebRequest request = mock(WebRequest.class);
    when(request.getDescription(false)).thenReturn("uri=/livros");

    ResponseEntity<Object> resposta = handler.handleMethodArgumentNotValid(
            ex,
            new HttpHeaders(),
            HttpStatus.BAD_REQUEST,
            request
    );

    assertEquals(HttpStatus.BAD_REQUEST, resposta.getStatusCode());
    assertNotNull(resposta.getBody());
  }

  // -----------------------------
  // EXCEPTION GENÉRICA
  // -----------------------------
  @Test
  void deveLidarComErroGenerico() {
    HttpServletRequest req = mockRequest("/qualquer");
    Exception ex = new Exception("Falha inesperada");

    ResponseEntity<ApiError> resposta = handler.handleGeneric(ex, req);

    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, resposta.getStatusCode());
    assertEquals("Falha inesperada", resposta.getBody().getMessage());
  }
}
