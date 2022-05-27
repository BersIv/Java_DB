package main.web;

import main.Entity.Operation;
import main.Exceptions.Duplicate;
import main.Exceptions.NotFound;
import main.Service.ArticleService;
import main.Service.BalanceService;
import main.Service.OperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/operation")
public class OperationController {
    @Autowired
    private OperationService operationService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private BalanceService balanceService;

    @GetMapping("/get")
    public ResponseEntity<List<Operation>> getAllOperations() {
        List<Operation> list = operationService.findAll();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/getById-{id}")
    public ResponseEntity<Operation> getById(@PathVariable("id") Integer id) {
        try {
            return new ResponseEntity<>(operationService.findById(id), HttpStatus.OK);
        } catch (NotFound e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/getByName-{name}")
    public ResponseEntity<List<Operation>> getOperationWithName(@PathVariable("name") String name) {
        try {
            return new ResponseEntity<>(operationService.findByName(name), HttpStatus.OK);
        } catch (NotFound e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping("/add-{articleId}-{balanceId}")
    public ResponseEntity<Operation> add(@PathVariable(name = "articleId") Integer articleId,
                                         @PathVariable(name = "balanceId") Integer balanceId) {
        try {
            return new ResponseEntity<>(operationService.addOperation(articleService.findById(articleId),
                    balanceService.findById(balanceId)), HttpStatus.OK);
        } catch (Duplicate e) {
            throw new ResponseStatusException(HttpStatus.IM_USED, e.getMessage());
        } catch (NotFound e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping("/updateByNames-{oldName}-{newName}")
    public String update(@PathVariable("oldName") String oldName, @PathVariable("newName") String newName) {
        try {
            operationService.updateByName(oldName, newName);
        } catch (NotFound e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        return "Operations updated";
    }

    @PostMapping("/updateById-{id}-{newName}")
    public String update(@PathVariable("id") Integer id, @PathVariable("newName") String newName) {
        try {
            operationService.updateById(id, newName);
        } catch (NotFound e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        return "Operations updated";
    }

    @DeleteMapping("/deleteByName-{name}")
    public String deleteByName(@PathVariable("name") String name) {
        try {
            operationService.deleteByName(name);
        } catch (NotFound e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        return "Operation deleted";
    }

    @DeleteMapping("/deleteById-{id}")
    public String deleteById(@PathVariable("id") Integer id) {
        try {
            operationService.deleteById(id);
        } catch (NotFound e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        return "Operation deleted";
    }

    @GetMapping("/getMoreThan-{amount}")
    public ResponseEntity<List<Operation>> getAmountMoreThan(@PathVariable("amount") double amount) {
        try {
            return new ResponseEntity<>(operationService.getAmountMoreThan(amount), HttpStatus.OK);
        } catch (NotFound e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }

    }

    @GetMapping("/getLessThan-{amount}")
    public ResponseEntity<List<Operation>> getAmountLessThan(@PathVariable("amount") double amount) {
        try {
            return new ResponseEntity<>(operationService.getAmountLessThan(amount), HttpStatus.OK);
        } catch (NotFound e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

}
