package ru.ustinoff.paymentservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ustinoff.paymentservice.entity.UserTransaction;

@Repository
public interface UserTransactionRepository extends JpaRepository<UserTransaction, Integer> {
}
