package main.Service;

import main.Entity.Balance;

import java.util.List;

public interface BalanceService {
    Balance addBalance(double debit, double credit);
    Balance addBalance(Balance balance);
    void deleteById(Integer id);
    List<Balance> findAll();
    Balance findById(Integer id);
}
