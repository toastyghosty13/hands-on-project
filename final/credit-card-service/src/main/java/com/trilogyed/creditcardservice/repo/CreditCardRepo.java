package com.trilogyed.creditcardservice.repo;

//import com.trilogyed.creditcardservice.repo.model.CreditCard;

import com.trilogyed.creditcardservice.model.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditCardRepo extends JpaRepository<CreditCard, Integer> {
    CreditCard getByCreditCardNumber(String creditCardNumber);
    void deleteByCreditCardNumber(String creditCardNumber);
}
