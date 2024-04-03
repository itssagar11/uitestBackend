package com.uitest.backend.dto;

public record ChequeDto(long from_accountNo, String from_IFSC, long to_accountNo, String to_IFSC,int amount) {

}
