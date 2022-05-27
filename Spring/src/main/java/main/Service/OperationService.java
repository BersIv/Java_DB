package main.Service;

import main.Entity.Article;
import main.Entity.Balance;
import main.Entity.Operation;

import java.util.List;

public interface OperationService {
    Operation addOperation(Article article, Balance balance);
    void deleteById(Integer id);
    List<Operation> findAll();
    Operation findById(Integer id);
    List<Operation> findByName(String name);
    void deleteByName(String name);
    void updateByName(String oldName, String newName);
    void updateById(Integer id, String newName);
    List<Operation> getAmountMoreThan(double amount);
    List<Operation> getAmountLessThan(double amount);
}
