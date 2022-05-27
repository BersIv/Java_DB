package main.Entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Entity(name = "Balance")
public class Balance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Integer id;

    private LocalDateTime time;
    private double debit;
    private double credit;
    private double amount;

/*
    @OneToOne(mappedBy = "balance", cascade=CascadeType.REMOVE)
    private Operation operation;
*/

    public Balance() {
        this.time = LocalDateTime.now();
        this.time = time.truncatedTo(ChronoUnit.SECONDS);
        this.amount = debit - credit;
    }

    public Balance(double debit, double credit) {
        this.time = LocalDateTime.now();
        this.time = this.time.truncatedTo(ChronoUnit.SECONDS);
        this.debit = debit;
        this.credit = credit;
        amount = debit - credit;
    }

    public Balance(Balance balance){
        this.id = balance.getId();
        this.time = LocalDateTime.now();
        this.time = this.time.truncatedTo(ChronoUnit.SECONDS);
        this.debit = balance.getDebit();
        this.credit = balance.getCredit();
        this.amount = debit - credit;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public double getDebit() {
        return debit;
    }

    public void setDebit(double debit) {
        this.debit = debit;
    }

    public double getCredit() {
        return credit;
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Balance{" +
                "id=" + id +
                ", time=" + time +
                ", debit=" + debit +
                ", credit=" + credit +
                ", amount=" + amount +
                '}';
    }
}
