package com.trilogyed.clientservice.controller;

//import com.trilogyed.clientservice.controller.CreditCardClient;
//import com.trilogyed.clientservice.controller.CreditCard;

import com.trilogyed.clientservice.exception.InvalidCardNumber;
import com.trilogyed.clientservice.model.CreditCard;
import com.trilogyed.clientservice.util.feign.CreditCardClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class Controller {

    @Autowired
    private CreditCardClient client;

    @RequestMapping(value = "/creditcard", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public CreditCard saveCreditCard(@Valid @RequestBody CreditCard o) throws InvalidCardNumber {
        if(o.getcreditCardNumber().length() !=4){
            throw new InvalidCardNumber("invalid card number length");
        }
        return client.saveCreditCard(o);
    }

    @RequestMapping(value = "/creditcard/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public CreditCard getCreditCard(@PathVariable String id) throws InvalidCardNumber {
        try {
            return client.getCreditCard(id);
        } catch (NullPointerException n) {
            throw new InvalidCardNumber("invalid card number");
        }
    }


    @RequestMapping(value = "/creditcard/debitfunds", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void updateCreditCard(@Valid @RequestBody CreditCard o) throws InvalidCardNumber {
        try {
            client.updateCreditCard(o);
        } catch (NullPointerException e) {
            throw new InvalidCardNumber("invalid card number");
        }
    }
}
