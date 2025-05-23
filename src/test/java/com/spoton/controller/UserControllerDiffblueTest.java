package com.spoton.controller;

import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spoton.dto.LoginDto;
import com.spoton.dto.PropertyUserDto;
import com.spoton.entity.PropertyUser;
import com.spoton.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {UserController.class})
@ExtendWith(SpringExtension.class)
class UserControllerDiffblueTest {
    @Autowired
    private UserController userController;

    @MockBean
    private UserService userService;

    /**
     * Method under test: {@link UserController#addUser(PropertyUserDto)}
     */
    @Test
    void testAddUser() throws Exception {
        // Arrange
        PropertyUser propertyUser = new PropertyUser();
        propertyUser.setEmail("jane.doe@example.org");
        propertyUser.setFirstName("Jane");
        propertyUser.setId(1L);
        propertyUser.setLastName("Doe");
        propertyUser.setPassword("iloveyou");
        propertyUser.setUserRole("User Role");
        propertyUser.setUsername("janedoe");
        when(userService.addUser(Mockito.<PropertyUserDto>any())).thenReturn(propertyUser);

        PropertyUserDto propertyUserDto = new PropertyUserDto();
        propertyUserDto.setEmail("jane.doe@example.org");
        propertyUserDto.setFirstName("Jane");
        propertyUserDto.setId(1L);
        propertyUserDto.setLastName("Doe");
        propertyUserDto.setPassword("iloveyou");
        propertyUserDto.setUserRole("User Role");
        propertyUserDto.setUsername("janedoe");
        String content = (new ObjectMapper()).writeValueAsString(propertyUserDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/users/addUser")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(userController).build().perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Registration is successful"));
    }

    /**
     * Method under test: {@link UserController#getCurrentUserProfile(PropertyUser)}
     */
    @Test
    void testGetCurrentUserProfile() throws Exception {
        // Arrange
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/users/profile");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"id\":null,\"firstName\":null,\"lastName\":null,\"email\":null,\"userRole\":null}"));
    }

    /**
     * Method under test: {@link UserController#getCurrentUserProfile(PropertyUser)}
     */
    @Test
    void testGetCurrentUserProfile2() throws Exception {
        // Arrange
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/users/profile");
        requestBuilder.contentType("https://example.org/example");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"id\":null,\"firstName\":null,\"lastName\":null,\"email\":null,\"userRole\":null}"));
    }

    /**
     * Method under test: {@link UserController#login(LoginDto)}
     */
    @Test
    void testLogin() throws Exception {
        // Arrange
        when(userService.verifyLogin(Mockito.<LoginDto>any())).thenReturn("Verify Login");

        LoginDto loginDto = new LoginDto();
        loginDto.setPassword("iloveyou");
        loginDto.setUsername("janedoe");
        String content = (new ObjectMapper()).writeValueAsString(loginDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"type\":\"Bearer\",\"token\":\"Verify Login\"}"));
    }
}
