package com.cipitech.tools.converters.exchange.error;

import com.cipitech.tools.converters.exchange.error.dto.ErrorResponseDTO;
import com.cipitech.tools.converters.exchange.error.exceptions.RecordNotFoundException;
import com.cipitech.tools.converters.exchange.error.exceptions.ServerErrorException;
import com.cipitech.tools.converters.exchange.error.exceptions.WrongParametersException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler
{
	@ExceptionHandler(RecordNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public final ErrorResponseDTO handleRecordNotFoundException(RecordNotFoundException ex)
	{
		return getErrorDTO(ex, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(WrongParametersException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public final ErrorResponseDTO handleWrongParametersException(WrongParametersException ex)
	{
		return getErrorDTO(ex, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ServerErrorException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public final ErrorResponseDTO handleWrongParametersException(ServerErrorException ex)
	{
		return getErrorDTO(ex, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public final ErrorResponseDTO handleOtherException(Exception ex)
	{
		return getErrorDTO(ex, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	private ErrorResponseDTO getErrorDTO(Exception ex, HttpStatus status)
	{
		return ErrorResponseDTO.builder().statusCode(status.value()).message(status.getReasonPhrase()).details(ex.getLocalizedMessage()).build();
	}
}
