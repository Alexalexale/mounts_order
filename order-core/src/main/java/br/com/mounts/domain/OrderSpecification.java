package br.com.mounts.domain;

import jakarta.persistence.criteria.Predicate;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;

@UtilityClass
public class OrderSpecification {

  public static Specification<Order> filterOrders(
      UUID clientIdentify,
      LocalDate datOrder,
      BigDecimal minTotalAmount,
      BigDecimal maxTotalAmount) {

    return (root, query, criteriaBuilder) -> {
      List<Predicate> predicates = new ArrayList<>();

      if (clientIdentify != null) {
        predicates.add(criteriaBuilder.equal(root.get("clientIdentify"), clientIdentify));
      }

      if (datOrder != null) {
        LocalDateTime startOfDay = datOrder.atStartOfDay();
        LocalDateTime endOfDay = datOrder.atTime(23, 59, 59);
        predicates.add(criteriaBuilder.between(root.get("datOrder"), startOfDay, endOfDay));
      }

      if (minTotalAmount != null) {
        predicates.add(
            criteriaBuilder.greaterThanOrEqualTo(root.get("totalAmount"), minTotalAmount));
      }

      if (maxTotalAmount != null) {
        predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("totalAmount"), maxTotalAmount));
      }

      return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    };
  }
}
