package com.trilogyed.clientservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trilogyed.clientservice.exception.InvalidCardNumber;
import com.trilogyed.clientservice.model.CreditCard;
import com.trilogyed.clientservice.util.feign.CreditCardClient;
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
    private CreditCardClient client;

    private static final BigDecimal BD = new BigDecimal("1000.00");
    private static final BigDecimal BDU = new BigDecimal("1500.00");

    private static final CreditCard CreditCard_ID = new CreditCard("4410",BD);
    private static final CreditCard CreditCard_UPDATED = new CreditCard("4410", BDU);
    private static final CreditCard CreditCard_BAD_UPDATE = new CreditCard("4420", BDU);
    private static final CreditCard CreditCard_BAD_SAVE_SHORT = new CreditCard("442", BDU);
    private static final CreditCard CreditCard_BAD_SAVE_LONG = new CreditCard("44244", BDU);


    private static final String SUCCESS = "Success";
    private static final String FAIL = "Fail";

    private ObjectMapper mapper = new ObjectMapper();

    @Before
    public void setUpMock() { //might need to be changed to fit with jpa methods
        when(client.saveCreditCard(CreditCard_ID)).thenReturn(CreditCard_ID);
        when(client.getCreditCard("4410")).thenReturn(CreditCard_ID);

        //success and failure messages sent from service layer if applicable
        //when(client.updateCreditCard(CreditCard_UPDATED)).thenReturn("Update: "+ SUCCESS);
        //when(client.deleteCreditCard(1)).thenReturn("Delete: " + SUCCESS);
        //when(client.updateCreditCard(CreditCard_BAD_UPDATE)).thenReturn("Update: "+ FAIL);
        //when(client.deleteCreditCard(1)).thenReturn("Delete: " + FAIL);

        //exceptions
        when(client.updateCreditCard(CreditCard_BAD_UPDATE)).thenThrow(new InvalidCardNumber("invalid card number"));
        when(client.saveCreditCard(CreditCard_BAD_SAVE_SHORT)).thenThrow(new InvalidCardNumber("invalid card number length"));
        when(client.saveCreditCard(CreditCard_BAD_SAVE_LONG)).thenThrow(new InvalidCardNumber("invalid card number length"));
        when(client.getCreditCard("4420")).thenThrow(new InvalidCardNumber("invalid card number"));

    }

    @Test
    public void saveCreditCard() throws Exception {
        String output_json = mapper.writeValueAsString(CreditCard_ID);

        mvc.perform(post("/creditcard")
                .content(output_json)
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

    //exception test
    @Test
    public void exceptionTestPut() throws Exception {
        String input_json = mapper.writeValueAsString(CreditCard_BAD_UPDATE);
        mvc.perform(put("/creditcard/debitfunds")
                .content(input_json)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isUnprocessableEntity()) //or whatever status code you set your exception to be, this is a default value
                .andExpect(content().string(containsString("invalid card number")));
    }

    //have to use bad request bc it is caught by the JSR 303 when trying to create the object above as a private static final CreditCard
    @Test
    public void exceptionTestPostShort() throws Exception {
        String input_json = mapper.writeValueAsString(CreditCard_BAD_SAVE_SHORT);
        mvc.perform(post("/creditcard")
                .content(input_json)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isBadRequest()); //or whatever status code you set your exception to be, this is a default value
                //.andExpect(content().string(containsString("invalid card number length")));
    }

    //have to use bad request bc it is caught by the JSR 303 when trying to create the object above as a private static final CreditCard
    @Test
    public void exceptionTestPostLong() throws Exception {
        String input_json = mapper.writeValueAsString(CreditCard_BAD_SAVE_LONG);
        mvc.perform(post("/creditcard")
                .content(input_json)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isBadRequest()) ;//or whatever status code you set your exception to be, this is a default value
                //.andExpect(content().string(containsString("invalid card number length")));
    }

    @Test
    public void exceptionTestGet() throws Exception {
        mvc.perform(get("/creditcard/4420")
        )
                .andDo(print())
                .andExpect(status().isUnprocessableEntity()) //or whatever status code you set your exception to be, this is a default value
                .andExpect(content().string(containsString("invalid card number")));
    }
}
