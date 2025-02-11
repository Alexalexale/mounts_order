package br.com.mounts.domain;

import static java.math.BigDecimal.ZERO;
import static java.math.RoundingMode.HALF_UP;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Entity
@ToString
@Table(name = "ORDERS")
@NoArgsConstructor(access = AccessLevel.MODULE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Order {

  public Order(
      @NonNull UUID orderIdentify, @NonNull UUID clientIdentify, @NonNull LocalDateTime datOrder) {
    this.orderIdentify = orderIdentify;
    this.clientIdentify = clientIdentify;
    this.datOrder = datOrder;
    this.items = new HashSet<>();
  }

  @Id
  @Getter(value = AccessLevel.PUBLIC)
  @Column(name = "IDT_ORDER", nullable = false)
  @EqualsAndHashCode.Include
  private UUID orderIdentify;

  @Getter(value = AccessLevel.PUBLIC)
  @Column(name = "IDT_CLIENT", nullable = false)
  private UUID clientIdentify;

  @Getter(value = AccessLevel.PUBLIC)
  @Column(name = "NUM_TOTAL_AMOUNT", nullable = false)
  private BigDecimal totalAmount;

  @ToString.Exclude
  @OneToMany(
      fetch = FetchType.LAZY,
      mappedBy = "order",
      cascade = CascadeType.ALL,
      orphanRemoval = true)
  private Set<ItemOrder> items;

  @Getter(value = AccessLevel.PUBLIC)
  @Column(name = "DAT_ORDER", nullable = false)
  private LocalDateTime datOrder;

  @CreatedDate
  @Column(name = "DAT_CREATION", nullable = false)
  private LocalDateTime datCreation = LocalDateTime.now();

  @LastModifiedDate
  @Column(name = "DAT_UPDATE", nullable = false)
  private LocalDateTime lastModifiedDate = LocalDateTime.now();

  public Set<ItemOrder> getItems() {
    return Set.copyOf(items);
  }

  public void addItem(final ItemOrder item) {
    items.add(item);
  }

  public void calculateTotalAmount() {
    this.totalAmount =
        items.parallelStream()
            .map(ItemOrder::getTotalAmount)
            .reduce(ZERO, BigDecimal::add)
            .setScale(2, HALF_UP);
  }
}
