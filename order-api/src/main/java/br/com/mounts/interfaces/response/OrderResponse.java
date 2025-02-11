package br.com.mounts.interfaces.response;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record OrderResponse(
    UUID orderIdentify,
    UUID clientIdentify,
    BigDecimal totalAmount,
    LocalDateTime datOrder,
    int numItems)
    implements Serializable {}
