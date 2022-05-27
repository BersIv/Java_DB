package main.Repositories;

import main.Entity.Balance;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BalanceRepository extends CrudRepository<Balance, Integer> {
    Balance findBalanceById(Integer id);

    @Query(value = "SELECT CASE WHEN count(*) > 0 THEN 1 ELSE 0 END FROM Balance B JOIN Operations O on O.BALANCE_ID = B.ID WHERE B.ID = :id", nativeQuery = true)
    Integer balanceInUse(@Param("id") Integer id);

    boolean existsBalanceById(Integer id);
}
