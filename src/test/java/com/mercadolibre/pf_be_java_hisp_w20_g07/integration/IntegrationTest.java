package com.mercadolibre.pf_be_java_hisp_w20_g07.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mercadolibre.pf_be_java_hisp_w20_g07.dtos.request.UserRequestDTO;
import com.mercadolibre.pf_be_java_hisp_w20_g07.exceptions.UserNotFoundException;
import com.mercadolibre.restclient.mock.RequestMockHolder;
import com.mercadolibre.pf_be_java_hisp_w20_g07.Application;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = Application.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {"SCOPE_SUFFIX = integration_test"})
public class IntegrationTest {

  @Autowired
  MockMvc mockMvc;

  protected IntegrationTest() {;
  }

  @AfterEach
  protected void afterEach() {
    RequestMockHolder.clear();
  }


  @Test
  @DisplayName("Login Ok")
  void loginOk() throws Exception {

    UserRequestDTO userRequestDTO = new UserRequestDTO("Tomas","tomas123");

    ObjectWriter writer = new ObjectMapper()
            .configure(SerializationFeature.WRAP_ROOT_VALUE,false)
            .registerModule(new JavaTimeModule())
            .writer();

    String payloadDto = writer.writeValueAsString(userRequestDTO);

    System.out.println(payloadDto);

    mockMvc.perform(post("/api/v1/log-in")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(payloadDto))
            .andDo(print()).andExpect(status().isOk())
            .andExpect(content().contentType("application/json"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.user_name").value("Tomas"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.token").isNotEmpty());

  }

  @Test
  @DisplayName("Login Not Ok")
  void loginNotOk() throws Exception {

    UserRequestDTO userRequestDTO = new UserRequestDTO("Tomas11","tomas123");

    ObjectWriter writer = new ObjectMapper()
            .configure(SerializationFeature.WRAP_ROOT_VALUE,false)
            .registerModule(new JavaTimeModule())
            .writer();

    String payloadDto = writer.writeValueAsString(userRequestDTO);

    System.out.println(payloadDto);

    mockMvc.perform(post("/api/v1/log-in")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(payloadDto))
            .andDo(print()).andExpect(status().isUnauthorized())
            .andReturn();

  }


}