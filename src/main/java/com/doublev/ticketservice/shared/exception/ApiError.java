package com.doublev.ticketservice.shared.exception;

public record ApiError(int status, String message) {}
