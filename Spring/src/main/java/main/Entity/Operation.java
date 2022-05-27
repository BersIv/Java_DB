package main.Entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Entity(name = "Operations")
public class Operation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Integer id;

    @Column(name = "debit")
    private double debit;

    @Column(name = "credit")
    private double credit;

    @Column(name = "create_time", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime time;

    @ManyToOne(cascade=CascadeType.MERGE)
    @JoinColumn(name = "article_id", referencedColumnName = "id")
    private Article article;

    @OneToOne(cascade=CascadeType.MERGE)
    @JoinColumn(name = "balance_id", referencedColumnName = "id")
    private Balance balance;

    public Operation() {
    }

    public Operation(Article article, Balance balance) {
        this.debit = balance.getDebit();
        this.credit = balance.getCredit();
        this.time = balance.getTime();
        time = time.truncatedTo(ChronoUnit.SECONDS);
        this.article = article;
        this.balance = balance;
    }

    public Operation(double debit, double credit,
                     Article article, Balance balance) {
        this.time = LocalDateTime.now();
        time = time.truncatedTo(ChronoUnit.SECONDS);
        this.debit = debit;
        this.credit = credit;
        this.article = article;
        this.balance = balance;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public Balance getBalance() {
        return balance;
    }

    public void setBalance(Balance balance) {
        this.balance = balance;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Operation{" +
                "id=" + id +
                ", debit=" + debit +
                ", credit=" + credit +
                ", time=" + time +
                ", article_id=" + article.getId() +
                ", balance_id=" + balance.getId() +
                '}';
    }
}
