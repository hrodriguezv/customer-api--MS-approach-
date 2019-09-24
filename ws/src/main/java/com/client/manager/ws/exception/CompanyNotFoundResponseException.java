package com.client.manager.ws.exception;

import com.client.manager.core.exception.CompanyNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CompanyNotFoundResponseException extends CompanyNotFoundException {
}
