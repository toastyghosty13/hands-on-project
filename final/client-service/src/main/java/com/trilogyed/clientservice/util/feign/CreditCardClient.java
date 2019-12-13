package com.trilogyed.clientservice.util.feign;

import com.trilogyed.clientservice.model.CreditCard;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@FeignClient(name = "creditcard-service")
public interface CreditCardClient {
    @RequestMapping(value = "/creditcard", method = RequestMethod.POST)
    public CreditCard saveCreditCard(@RequestBody CreditCard o);

    @RequestMapping(value = "/creditcard/{id}", method = RequestMethod.GET)
    public CreditCard getCreditCard(@PathVariable String id) throws NullPointerException;

    @RequestMapping(value = "/creditcard/debitfunds", method = RequestMethod.PUT)
    public String updateCreditCard(@RequestBody CreditCard o) throws NullPointerException;

}
