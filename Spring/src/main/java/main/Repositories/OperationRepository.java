package main.Repositories;

import main.Entity.Operation;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface OperationRepository extends CrudRepository<Operation, Integer> {

    @Query(value = "SELECT Operations.*, Articles.NAME FROM Operations INNER JOIN Articles ON article_id = Articles.id " +
            "WHERE Articles.name = :name", nativeQuery = true)
    List<Operation> findByName(@Param("name") String name);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM Operations WHERE article_id IN (SELECT id FROM Articles WHERE name = :name)", nativeQuery = true)
    void deleteByName(String name);

    @Modifying
    @Transactional
    @Query(value = "UPDATE Operations O SET O.ARTICLE_ID = (SELECT ID FROM Articles A WHERE A.NAME = :newName)\n" +
            "WHERE O.ARTICLE_ID IN (SELECT ID FROM Articles A WHERE A.NAME = :oldName)", nativeQuery = true)
    void updateOperations(@Param("oldName") String oldName, @Param("newName") String newName);

    @Modifying
    @Transactional
    @Query(value = "UPDATE Operations SET article_id = (SELECT id FROM Articles a WHERE a.name = :newName)\n" +
            "WHERE Operations.Id = :id", nativeQuery = true)
    void updateOperationById(@Param("id") Integer id, @Param("newName") String newName);

    @Query(value = "SELECT * FROM Operations O JOIN Balance B on B.ID = O.BALANCE_ID where AMOUNT > :amount", nativeQuery = true)
    List<Operation> getOperationsByAmountMoreThan(@Param("amount") double amount);

    @Query(value = "SELECT * FROM Operations O JOIN Balance B on B.ID = O.BALANCE_ID where AMOUNT < :amount", nativeQuery = true)
    List<Operation> getOperationsByAmountLessThan(@Param("amount") double amount);

    boolean existsOperationByBalanceId(Integer id);
    boolean existsOperationByArticleName(String name);
}
