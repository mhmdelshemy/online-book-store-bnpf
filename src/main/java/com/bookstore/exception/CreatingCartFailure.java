package com.bookstore.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CreatingCartFailure {
    @JsonProperty(value = "code")
    String code;

    @JsonProperty(value = "message")
    String message;

}
