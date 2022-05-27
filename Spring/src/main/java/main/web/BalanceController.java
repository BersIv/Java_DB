package main.web;

import main.Entity.Balance;
import main.Exceptions.NotFound;
import main.Exceptions.StillInUse;
import main.Service.BalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/balance")
public class BalanceController {
    @Autowired
    private BalanceService balanceService;

    @GetMapping(value = "/get")
    public ResponseEntity<List<Balance>> getArticles() {
        return new ResponseEntity<>(balanceService.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/getById-{id}")
    public ResponseEntity<Balance> getById(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(balanceService.findById(id), HttpStatus.OK);
    }

    @PostMapping(value = "/add")
    public ResponseEntity<Balance> addBalance(@RequestBody Balance balance) {
        return new ResponseEntity<>(balanceService.addBalance(balance), HttpStatus.OK);
    }

    @PostMapping(value = "/add-{debit}-{credit}")
    public ResponseEntity<Balance> addBalance(@PathVariable("debit") double debit,
                           @PathVariable("credit") double credit) {
        return new ResponseEntity<>(balanceService.addBalance(debit, credit), HttpStatus.OK);
    }

    @DeleteMapping("/deleteById-{id}")
    public String deleteById(@PathVariable("id") Integer id) {
        try {
            balanceService.deleteById(id);
        } catch (NotFound e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        catch (StillInUse e){
            throw new ResponseStatusException(HttpStatus.IM_USED, e.getMessage());
        }
        return "Balance deleted";
    }

}