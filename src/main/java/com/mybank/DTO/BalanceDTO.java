package com.mybank.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@Builder
public class BalanceDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("amount")
    private Double amount;
}
