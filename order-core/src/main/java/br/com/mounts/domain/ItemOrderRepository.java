package br.com.mounts.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemOrderRepository extends JpaRepository<ItemOrder, Long> {

  Page<ItemOrder> findAllPageByOrder(Order order, Pageable pageable);
}
