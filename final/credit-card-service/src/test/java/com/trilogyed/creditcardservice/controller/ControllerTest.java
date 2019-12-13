package com.trilogyed.creditcardservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trilogyed.creditcardservice.model.CreditCard;
import com.trilogyed.creditcardservice.repo.CreditCardRepo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(Controller.class)
public class ControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private CreditCardRepo repo;

    private static final BigDecimal BD = new BigDecimal("1000.00");
    private static final CreditCard CreditCard_ID = new CreditCard("4410", BD);
    private static final List<CreditCard> CreditCard_LIST = new ArrayList<>(Arrays.asList(CreditCard_ID));
    private static final CreditCard CreditCard_UPDATED = new CreditCard("4410", BD);
    private static final CreditCard CreditCard_BAD_UPDATE = new CreditCard("aaa", BD);
    private static final String SUCCESS = "Success";
    private static final String FAIL = "Fail";

    private ObjectMapper mapper = new ObjectMapper();

    @Before
    public void setUpMock() { //might need to be changed to fit with jpa methods
        when(repo.save(CreditCard_ID)).thenReturn(CreditCard_ID);
        when(repo.getByCreditCardNumber("4410")).thenReturn(CreditCard_ID);
        when(repo.findAll()).thenReturn(CreditCard_LIST);

        //success and failure messages sent from service layer if applicable
        //when(repo.updateCreditCard(CreditCard_UPDATED)).thenReturn("Update: "+ SUCCESS);
        //when(repo.deleteCreditCard(1)).thenReturn("Delete: " + SUCCESS);
        //when(repo.updateCreditCard(CreditCard_BAD_UPDATE)).thenReturn("Update: "+ FAIL);
        //when(repo.deleteCreditCard(1)).thenReturn("Delete: " + FAIL);

        //exceptions
        //when(repo.updateCreditCard(CreditCard_BAD_UPDATE)).thenThrow(new ("bad thing"));
        //when(repo.deleteCreditCard(7)).thenThrow(new ("bad thing"));        
    }

    @Test
    public void saveCreditCard() throws Exception {
        String input_json = mapper.writeValueAsString(CreditCard_ID);
        String output_json = mapper.writeValueAsString(CreditCard_ID);

        mvc.perform(post("/creditcard")
                .content(input_json)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(output_json));
    }

    @Test
    public void getCreditCard() throws Exception {
        String output_json = mapper.writeValueAsString(CreditCard_ID);

        mvc.perform(get("/creditcard/{id}", "4410"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(output_json));
    }

    @Test
    public void getAllCreditCards() throws Exception {
        String output_json = mapper.writeValueAsString(CreditCard_LIST);

        mvc.perform(get("/creditcard"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(output_json));
    }

    @Test
    public void updateCreditCard() throws Exception {
        String input_json = mapper.writeValueAsString(CreditCard_UPDATED);

        mvc.perform(put("/creditcard/debitfunds")
                .content(input_json)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk());

        //for things with random or json parsing errors
        //.andExpect(jsonPath("$.id").value("" + REAL_LOCATION.getId()))
        //.andExpect(jsonPath("$.description").value(REAL_LOCATION.getDescription()))
        //.andExpect(jsonPath("$.location").value(REAL_LOCATION.getLocation()));
    }

    @Test
    public void deleteCreditCard() throws Exception {
        mvc.perform(delete("/creditcard/{id}", 1))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    //exception test
    /*
    @Test
    public void exceptionTest() throws  throws Exception {
        String input_json = mapper.writeValueAsString(CreditCard_BAD_UPDATE);
        mvc.perform(put("/creditcard")
                .content(input_json)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isUnprocessableEntity()) //or whatever status code you set your exception to be, this is a default value
                .andExpect(content().string(containsString("bad thing")));
    }
    */
}
