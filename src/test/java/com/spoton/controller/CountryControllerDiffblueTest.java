package com.spoton.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {CountryController.class})
@ExtendWith(SpringExtension.class)
class CountryControllerDiffblueTest {
    @Autowired
    private CountryController countryController;

    /**
     * Method under test: {@link CountryController#addCountry()}
     */
    @Test
    void testAddCountry() throws Exception {
        // Arrange
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/countries/addCountry");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(countryController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("done"));
    }

//Prem testing pull request
    
    
    /**
     * Method under test: {@link CountryController#addCountry()}
     */
    @Test
    void testAddCountry2() throws Exception {
        // Arrange
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/countries/addCountry",
                "Uri Variables");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(countryController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("done"));
    }
}
