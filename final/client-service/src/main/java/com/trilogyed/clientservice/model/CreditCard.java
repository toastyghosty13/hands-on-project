package com.trilogyed.clientservice.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Objects;


public class CreditCard {
    /*
    !!!!!!!!!!!!!!!!!!!!!!!
        DO
            NOT
        FORGET
            TO
        GENERATE
            EQUALS
        TOSTRING
            AND
        HASHCODE
    !!!!!!!!!!!!!!!!!!!!!!!
    */
    @NotNull
    @Size(min=4, max=4)
    private String creditCardNumber;
    @NotNull
    private BigDecimal balance;
    //@OneToMany(mappedBy = note_id, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    //private Set<Note> notes;

    public CreditCard() {
    }

    public CreditCard(@NotNull @Size(min = 4, max = 4) String creditCardNumber, @NotNull BigDecimal balance) {
        this.creditCardNumber = creditCardNumber;
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "CreditCard{" +
                "creditCardNumber='" + creditCardNumber + '\'' +
                ", balance=" + balance +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreditCard that = (CreditCard) o;
        return Objects.equals(creditCardNumber, that.creditCardNumber) &&
                Objects.equals(balance, that.balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(creditCardNumber, balance);
    }

    public String getcreditCardNumber() {
        return creditCardNumber;
    }

    public void setcreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    public BigDecimal getbalance() {
        return balance;
    }

    public void setbalance(BigDecimal balance) {
        this.balance = balance;
    }


}
