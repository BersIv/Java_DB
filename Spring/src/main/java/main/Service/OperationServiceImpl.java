package main.Service;

import main.Entity.Article;
import main.Entity.Balance;
import main.Entity.Operation;
import main.Exceptions.Duplicate;
import main.Exceptions.NotFound;
import main.Repositories.ArticleRepository;
import main.Repositories.OperationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OperationServiceImpl implements OperationService {
    @Autowired
    private OperationRepository operationRepository;
    @Autowired
    private ArticleRepository articleRepository;

    @Override
    public Operation addOperation(Article article, Balance balance) {
        if (operationRepository.existsOperationByBalanceId(balance.getId())) {
            throw new Duplicate("Can't create operation with used balance");
        }
        else {
            return operationRepository.save(new Operation(article, balance));
        }
    }

    @Override
    public void deleteById(Integer id) {
        if (operationRepository.existsById(id)) {
            operationRepository.deleteById(id);
        } else {
            throw new NotFound("No operation found by id");
        }
    }

    @Override
    public List<Operation> findAll() {
        return (List<Operation>) operationRepository.findAll();
    }

    @Override
    public Operation findById(Integer id) {
        Optional<Operation> optionalOperation = operationRepository.findById(id);
        if (optionalOperation.isPresent()) {
            return optionalOperation.get();
        } else {
            throw new NotFound("No operation found by id");
        }
    }

    @Override
    public List<Operation> findByName(String name) {
        if (!operationRepository.existsOperationByArticleName(name)) {
            throw new NotFound("No operation found by name");
        }
        return operationRepository.findByName(name);
    }

    @Override
    public void deleteByName(String name) {
        if (!operationRepository.existsOperationByArticleName(name)) {
            throw new NotFound("No operation found by name");
        }
        operationRepository.deleteByName(name);
    }

    @Override
    public void updateByName(String oldName, String newName) {
        if (!operationRepository.existsOperationByArticleName(oldName)) {
            throw new NotFound("No operation found by name");
        }
        if(!articleRepository.existsByName(newName)){
            articleRepository.save(new Article(newName));
            operationRepository.updateOperations(oldName, newName);
        }
        operationRepository.updateOperations(oldName, newName);
    }

    @Override
    public void updateById(Integer id, String newName) {
        if(!articleRepository.existsByName(newName)){
            articleRepository.save(new Article(newName));
            operationRepository.updateOperationById(id, newName);
        }
        operationRepository.updateOperationById(id, newName);
    }

    @Override
    public List<Operation> getAmountMoreThan(double amount) {
        List<Operation> operationList = operationRepository.getOperationsByAmountMoreThan(amount);
        if(operationList.isEmpty()){
            throw new NotFound("No operations with amount more than " + amount);
        }
        return operationList;
    }

    @Override
    public List<Operation> getAmountLessThan(double amount) {
        List<Operation> operationList = operationRepository.getOperationsByAmountLessThan(amount);
        if(operationList.isEmpty()){
            throw new NotFound("No operations with amount less than " + amount);
        }
        return operationList;
    }
}
