package br.com.mounts.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface ItemOrderRepository extends JpaRepository<ItemOrder, Long> {}
