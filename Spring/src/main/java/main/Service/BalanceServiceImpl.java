package main.Service;

import main.Entity.Balance;
import main.Exceptions.NotFound;
import main.Exceptions.StillInUse;
import main.Repositories.BalanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BalanceServiceImpl implements BalanceService{
    @Autowired
    private BalanceRepository balanceRepository;

    @Override
    public Balance addBalance(double debit, double credit) {
        return balanceRepository.save(new Balance(debit, credit));
    }

    @Override
    public Balance addBalance(Balance balance) {
        return balanceRepository.save(new Balance(balance));
    }

    @Override
    public void deleteById(Integer id) {
        if(balanceRepository.balanceInUse(id) == 1){
            throw new StillInUse("Balance is still in use");
        }
        if(!balanceRepository.existsBalanceById(id)){
            throw  new NotFound("Balance not found by id");
        }
        balanceRepository.deleteById(id);
    }

    @Override
    public List<Balance> findAll() {
        return (List<Balance>) balanceRepository.findAll();
    }

    @Override
    public Balance findById(Integer id) {
        if(!balanceRepository.existsById(id)){
            throw new NotFound("No balance found by id");
        }
        return balanceRepository.findBalanceById(id);
    }
}
