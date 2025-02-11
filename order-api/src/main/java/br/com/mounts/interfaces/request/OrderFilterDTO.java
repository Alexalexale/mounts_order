package br.com.mounts.interfaces.request;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record OrderFilterDTO(
    UUID clientIdentify,
    LocalDate datOrder,
    BigDecimal minTotalAmount,
    BigDecimal maxTotalAmount) {}
