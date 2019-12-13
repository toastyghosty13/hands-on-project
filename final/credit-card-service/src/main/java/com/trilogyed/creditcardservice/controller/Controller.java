package com.trilogyed.creditcardservice.controller;

//import com.trilogyed.creditcardservice.controller.CreditCardRepo;
//import com.trilogyed.creditcardservice.controller.CreditCard;

import com.trilogyed.creditcardservice.model.CreditCard;
import com.trilogyed.creditcardservice.repo.CreditCardRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.constraints.Null;
import java.util.List;

@RestController
public class Controller {

    @Autowired
    private CreditCardRepo repo;

    @Transactional
    @RequestMapping(value = "/creditcard", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public CreditCard saveCreditCard(@RequestBody CreditCard o) {
        return repo.save(o);
    }

    @RequestMapping(value = "/creditcard/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public CreditCard getCreditCard(@PathVariable String id) throws NullPointerException {
            return repo.getByCreditCardNumber(id);
    }

    @RequestMapping(value = "/creditcard", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<CreditCard> getAllCreditCards() {
        return repo.findAll();
    }

    @Transactional
    @RequestMapping(value = "/creditcard/debitfunds", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void updateCreditCard(@RequestBody CreditCard o) throws NullPointerException {
        repo.save(o);
    }

    @Transactional
    @RequestMapping(value = "/creditcard/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String deleteCreditCard(@PathVariable String id) throws NullPointerException{
        try {
            repo.deleteByCreditCardNumber(id);
            return "Delete: Success";
        } catch (Exception e) {
            return "Delete: Fail";
        }
    }
}
