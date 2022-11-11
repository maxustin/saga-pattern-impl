package ru.ustinoff.orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ustinoff.orderservice.entity.PurchaseOrder;

@Repository
public interface OrderRepository extends JpaRepository<PurchaseOrder, Integer> {
}
