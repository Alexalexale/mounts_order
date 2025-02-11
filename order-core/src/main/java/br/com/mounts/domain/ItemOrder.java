package br.com.mounts.domain;

import static java.math.MathContext.DECIMAL32;
import static java.math.RoundingMode.HALF_UP;
import static lombok.AccessLevel.MODULE;
import static lombok.AccessLevel.PUBLIC;

import br.com.mounts.infrascruture.UUIDToStringConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Entity
@ToString
@Table(name = "ITEM_ORDER")
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor(access = MODULE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ItemOrder {

  public ItemOrder(
      @NonNull UUID itemIdentify,
      @NonNull Order order,
      @NonNull BigDecimal amount,
      @NonNull Long qtde) {
    this.itemIdentify = itemIdentify;
    this.order = order;
    this.amount = amount;
    this.qtde = qtde;
    this.totalAmount = amount.multiply(BigDecimal.valueOf(qtde), DECIMAL32).setScale(2, HALF_UP);
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ID_ITEM_ORDER")
  Long id;

  @Setter
  @ManyToOne
  @Getter(value = PUBLIC)
  @JoinColumn(name = "ID_ORDER", nullable = false)
  Order order;

  @Convert(converter = UUIDToStringConverter.class)
  @Getter(value = PUBLIC)
  @Column(name = "IDT_ITEM", nullable = false)
  @EqualsAndHashCode.Include
  UUID itemIdentify;

  @Getter(value = PUBLIC)
  @Column(name = "NUM_AMOUNT", nullable = false)
  @EqualsAndHashCode.Include
  BigDecimal amount;

  @Getter(value = PUBLIC)
  @Column(name = "NUM_QTDE", nullable = false)
  Long qtde;

  @Getter(value = PUBLIC)
  @Column(name = "NUM_TOTAL_AMOUNT", nullable = false)
  BigDecimal totalAmount;
}
