package com.cipitech.tools.converters.exchange.error;

import com.cipitech.tools.converters.exchange.error.dto.ErrorResponseDTO;
import com.cipitech.tools.converters.exchange.error.exceptions.RecordNotFoundException;
import com.cipitech.tools.converters.exchange.error.exceptions.ServerErrorException;
import com.cipitech.tools.converters.exchange.error.exceptions.WrongParametersException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler
{
	@ExceptionHandler(RecordNotFoundException.class)
	public final ResponseEntity<ErrorResponseDTO> handleRecordNotFoundException(RecordNotFoundException ex)
	{
		return new ResponseEntity<>(getErrorDTO(ex, HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(WrongParametersException.class)
	public final ResponseEntity<ErrorResponseDTO> handleWrongParametersException(WrongParametersException ex)
	{
		return new ResponseEntity<>(getErrorDTO(ex, HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ServerErrorException.class)
	public final ResponseEntity<ErrorResponseDTO> handleWrongParametersException(ServerErrorException ex)
	{
		return new ResponseEntity<>(getErrorDTO(ex, HttpStatus.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<ErrorResponseDTO> handleOtherException(Exception ex)
	{
		return new ResponseEntity<>(getErrorDTO(ex, HttpStatus.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	private ErrorResponseDTO getErrorDTO(Exception ex, HttpStatus status)
	{
		return ErrorResponseDTO.builder().statusCode(status.value()).message(status.getReasonPhrase()).details(ex.getLocalizedMessage()).build();
	}
}
