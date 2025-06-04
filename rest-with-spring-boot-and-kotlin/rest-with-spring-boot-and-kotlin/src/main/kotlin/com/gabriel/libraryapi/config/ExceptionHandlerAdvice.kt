package com.gabriel.libraryapi.config

import com.gabriel.libraryapi.exception.ErrorResponse
import com.gabriel.libraryapi.exception.NotFoundException
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionHandlerAdvice {

    @ExceptionHandler(NotFoundException::class)
    fun handleNotFound(exception: NotFoundException, request: HttpServletRequest):
            ResponseEntity<ErrorResponse> {
        val response = ErrorResponse(
            status = HttpStatus.NOT_FOUND.value(),
            error = HttpStatus.NOT_FOUND.reasonPhrase,
            errorMessage = exception.message.orEmpty(),
            path = request.requestURI
        )
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response)

    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidation(exception: MethodArgumentNotValidException, request: HttpServletRequest):
            ResponseEntity<ErrorResponse> {
        val message = exception.bindingResult.fieldErrors.joinToString("; ") {
            "${it.field}: ${it.defaultMessage}"
        }
        val response = ErrorResponse(
            status = HttpStatus.BAD_REQUEST.value(),
            error = HttpStatus.BAD_REQUEST.reasonPhrase,
            errorMessage = exception.message.orEmpty(),
            path = request.requestURI
        )

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response)
    }

    @ExceptionHandler(Exception::class)
    fun handleGenericException(exception: Exception, request: HttpServletRequest):
            ResponseEntity<ErrorResponse> {
        val response = ErrorResponse(
            status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
            error = HttpStatus.INTERNAL_SERVER_ERROR.reasonPhrase,
            errorMessage = exception.message.orEmpty(),
            path = request.requestURI
        )

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response)
    }


}