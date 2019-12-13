package com.trilogyed.creditcardservice.repo;

import com.trilogyed.creditcardservice.model.CreditCard;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CreditCardRepoTest {
    @Autowired
    private CreditCardRepo repo;

    @Before
    public void setUp() throws Exception {
        repo.findAll().stream().forEach(c -> repo.delete(c));
        repo.save(new CreditCard("4410", new BigDecimal("1000.00")));
    }

    @Test
    public void getByCCNum() {
        CreditCard expected = new CreditCard("4410", new BigDecimal("1000.00"));

        assertEquals(expected, repo.getByCreditCardNumber("4410"));
    }

    @Test
    public void deleteByCCNum() {
        repo.deleteByCreditCardNumber("4410");
        assertNull(repo.getByCreditCardNumber("4410"));
    }
}