package ru.ustinoff.paymentservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ustinoff.paymentservice.entity.UserBalance;

@Repository
public interface UserBalanceRepository extends JpaRepository<UserBalance, Integer> {
}
