package com.mercadolibre.pf_be_java_hisp_w20_g07.config;


import com.mercadolibre.pf_be_java_hisp_w20_g07.dtos.MessageDto;
import com.mercadolibre.pf_be_java_hisp_w20_g07.dtos.response.ExceptionValidationDto;
import com.mercadolibre.pf_be_java_hisp_w20_g07.exceptions.*;
import com.newrelic.api.agent.NewRelic;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Basic handling for exceptions.
 */
@ControllerAdvice
public class ControllerExceptionHandler {

  private static final Logger LOGGER = LoggerFactory.getLogger(ControllerExceptionHandler.class);

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<String> notFoundException(NotFoundException e) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
  }

  /**
   * Handler for not found routes.
   *
   * @param req the incoming request.
   * @param ex  the exception thrown when route is not found.
   * @return {@link ResponseEntity} with 404 status code and the route that was not found in the body.
   */
  @ExceptionHandler(NoHandlerFoundException.class)
  public ResponseEntity<ApiError> noHandlerFoundException(
          HttpServletRequest req, NoHandlerFoundException ex) {
    ApiError apiError =
            new ApiError(
                    "route_not_found",
                    String.format("Route %s not found", req.getRequestURI()),
                    HttpStatus.NOT_FOUND.value());
    return ResponseEntity.status(apiError.getStatus()).body(apiError);
  }

  /**
   * Handler for external API exceptions.
   *
   * @param e the exception thrown during a request to external API.
   * @return {@link ResponseEntity} with status code and description provided for the handled exception.
   */
  @ExceptionHandler(ApiException.class)
  protected ResponseEntity<ApiError> handleApiException(ApiException e) {
    Integer statusCode = e.getStatusCode();
    boolean expected = HttpStatus.INTERNAL_SERVER_ERROR.value() > statusCode;
    NewRelic.noticeError(e, expected);
    if (expected) {
      LOGGER.warn("Internal Api warn. Status Code: " + statusCode, e);
    } else {
      LOGGER.error("Internal Api error. Status Code: " + statusCode, e);
    }

    ApiError apiError = new ApiError(e.getCode(), e.getDescription(), statusCode);
    return ResponseEntity.status(apiError.getStatus()).body(apiError);
  }

  /**
   * Handler for internal exceptions.
   *
   * @param e the exception thrown during request processing.
   * @return {@link ResponseEntity} with 500 status code and description indicating an internal error.
   */
  @ExceptionHandler(Exception.class)
  protected ResponseEntity<ApiError> handleUnknownException(Exception e) {
    LOGGER.error("Internal error", e);
    NewRelic.noticeError(e);

    ApiError apiError =
            new ApiError(
                    "internal_error", "Internal server error", HttpStatus.INTERNAL_SERVER_ERROR.value());
    return ResponseEntity.status(apiError.getStatus()).body(apiError);
  }


  @ExceptionHandler(ResourceNotFoundException.class)
  protected ResponseEntity<?> resourceNotFoundException(ResourceNotFoundException e) {

    return new ResponseEntity<>(new MessageDto(e.getMessage()), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(UserNotFoundException.class)
  protected ResponseEntity<?> userNotFoundException(UserNotFoundException e) {

    return new ResponseEntity<>(new MessageDto(e.getMessage()), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(BatchNotFoundException.class)
  protected ResponseEntity<?> batchNotFoundException(BatchNotFoundException e) {
    return new ResponseEntity<>(new MessageDto(e.getMessage()), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(RequestParamsException.class)
  protected ResponseEntity<?> RequestParamsException(RequestParamsException e) {

    return new ResponseEntity<>(new MessageDto(e.getMessage()), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(UnAuthorizeException.class)
  protected ResponseEntity<?> UnAuthorizeException(UnAuthorizeException e) {

    return new ResponseEntity<>(new MessageDto(e.getMessage()), HttpStatus.UNAUTHORIZED);
  }


  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ExceptionValidationDto> methodArgumentNotValidException(MethodArgumentNotValidException e, BindingResult r) {
    List<String> errosMessages = r.getAllErrors().stream().map(error -> error.getDefaultMessage()).collect(Collectors.toList());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionValidationDto("Invalid field", errosMessages));
  }

}



