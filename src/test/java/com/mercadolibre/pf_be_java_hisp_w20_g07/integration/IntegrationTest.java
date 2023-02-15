package com.mercadolibre.pf_be_java_hisp_w20_g07.integration;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mercadolibre.pf_be_java_hisp_w20_g07.dtos.OrderStatusDTO;
import com.mercadolibre.pf_be_java_hisp_w20_g07.dtos.ProductDTO;
import com.mercadolibre.pf_be_java_hisp_w20_g07.dtos.request.PurchaseOrderRequestDTO;
import com.mercadolibre.pf_be_java_hisp_w20_g07.dtos.request.UserRequestDTO;
import com.mercadolibre.pf_be_java_hisp_w20_g07.exceptions.UserNotFoundException;
import com.mercadolibre.pf_be_java_hisp_w20_g07.service.impl.SesionServiceImpl;
import com.mercadolibre.restclient.mock.RequestMockHolder;
import com.mercadolibre.pf_be_java_hisp_w20_g07.Application;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

  @Autowired
  SesionServiceImpl service;

  protected IntegrationTest() {;
  }

  @AfterEach
  protected void afterEach() {
    RequestMockHolder.clear();
  }

  private ObjectWriter writer;

  @BeforeEach
  public void setUp() {
    writer = new ObjectMapper()
            .configure(SerializationFeature.WRAP_ROOT_VALUE,false)
            .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
            .registerModule(new JavaTimeModule())
            .writer();
  }

  //Log in
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

  //Requerimiento 2
  @Test
  void testGetListAllProducts() throws Exception{
    UserRequestDTO userRequestDTORepre = new UserRequestDTO("Tomas","tomas123");
    String token = service.login(userRequestDTORepre).getToken();

    this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/fresh-products/list")
                    .header("Authorization", "Bearer " + token))
            .andDo(print()).andExpect(status().isOk())
            .andExpect(content().contentType("application/json"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.[0].name").value("FRESA"));
  }

  @Test
  void testGetListWithoutProducts() throws Exception{
    UserRequestDTO userRequestDTORepre = new UserRequestDTO("Tomas","tomas123");
    String token = service.login(userRequestDTORepre).getToken();

    this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/fresh-products/list")
                    .header("Authorization", "Bearer " + token)
                    .param("code","TF"))
            .andDo(print()).andExpect(status().isNotFound())
            .andExpect(content().contentType("text/plain;charset=UTF-8"))
            .andExpect(MockMvcResultMatchers.jsonPath("$").value("No products"));

  }

  @Test
  void testGetListByCategory() throws Exception{
    UserRequestDTO userRequestDTORepre = new UserRequestDTO("Tomas","tomas123");
    String token = service.login(userRequestDTORepre).getToken();

    this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/fresh-products/list")
                    .header("Authorization", "Bearer " + token)
                    .param("code","FF"))
            .andDo(print()).andExpect(status().isOk())
            .andExpect(content().contentType("application/json"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.[0].name").value("PESCADO"));
  }

  @Test
  @DisplayName("Save Order")
  void saveOrderTest() throws Exception{
    UserRequestDTO userRequestDTORepre = new UserRequestDTO("Tomas","tomas123");
    String token = service.login(userRequestDTORepre).getToken();
    OrderStatusDTO orderStatus = new OrderStatusDTO("carrito");
    List<ProductDTO> productDTOList = Arrays.asList(new ProductDTO(1, 2),
            new ProductDTO(2,2));
    LocalDate date = LocalDate.parse("2023-01-24" );
    PurchaseOrderRequestDTO purchaseOrderRequestDTO = new PurchaseOrderRequestDTO(date,1,orderStatus,productDTOList);
    String payload = writer.writeValueAsString(purchaseOrderRequestDTO);
    this.mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/fresh-products/orders")
                    .header("Authorization", "Bearer " + token)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(payload))
            .andDo(print()).andExpect(status().isCreated())
            .andExpect(content().contentType("application/json"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.total_price").value("28000.0"));

  }

  @Test
  @DisplayName("Products by orderId")
  void testGetProductsByOrderId() throws Exception{
    UserRequestDTO userRequestDTORepre = new UserRequestDTO("Tomas","tomas123");
    String token = service.login(userRequestDTORepre).getToken();

    this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/fresh-products/orders/{orderId}","1")
                    .header("Authorization", "Bearer " + token))
            .andDo(print()).andExpect(status().isOk())
            .andExpect(content().contentType("application/json"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.[0].name").value("FRESA"));
  }

  @Test
  @DisplayName("Update Order")
  void updateOrderTest() throws Exception{
    UserRequestDTO userRequestDTORepre = new UserRequestDTO("Tomas","tomas123");
    String token = service.login(userRequestDTORepre).getToken();
    OrderStatusDTO orderStatus = new OrderStatusDTO("carrito");
    List<ProductDTO> productDTOList = Arrays.asList(new ProductDTO(1, 2),
            new ProductDTO(2,2));
    LocalDate date = LocalDate.parse("2023-01-24" );
    PurchaseOrderRequestDTO purchaseOrderRequestDTO = new PurchaseOrderRequestDTO(date,1,orderStatus,productDTOList);
    String payload = writer.writeValueAsString(purchaseOrderRequestDTO);
    this.mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/fresh-products/orders/{orderId}",2)
                    .header("Authorization", "Bearer " + token)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(payload))
            .andDo(print()).andExpect(status().isOk())
            .andExpect(content().contentType("text/plain;charset=UTF-8"))
            .andExpect(MockMvcResultMatchers.jsonPath("$").value("update"));
  }

  //Requerimiento 5
  @Test
  @DisplayName("finBatchesDueToExpireSoon Ok")
  void finBatchesDueToExpireSoon() throws Exception {

    UserRequestDTO userRequestDTO = new UserRequestDTO("Tomas","tomas123");

    String token = service.login(userRequestDTO).getToken();

    mockMvc.perform(get("/api/v1/fresh-products/batch/list/due-date/{dias}",3)
                    .header("Authorization",token)
            )
            .andDo(print()).andExpect(status().isOk())
            .andExpect(content().contentType("application/json"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.batch_stock.[0].batch_number").value(7))
            .andExpect(MockMvcResultMatchers.jsonPath("$.batch_stock.[1].batch_number").value(8))
            .andExpect(MockMvcResultMatchers.jsonPath("$.batch_stock.[2].batch_number").value(9));
  }

  @Test
  @DisplayName("finBatchesDueToExpireSoon UnAuthorized")
  void finBatchesDueToExpireSoonUnAuthorized() throws Exception {

    mockMvc.perform(get("/api/v1/fresh-products/batch/list/due-date/{dias}",3))
            .andDo(print()).andExpect(status().isForbidden());
  }

  @Test
  @DisplayName("finBatchesDueToExpireSoon order desc ok")
  void finBatchesDueToExpireSoonDesc() throws Exception {

    UserRequestDTO userRequestDTO = new UserRequestDTO("Tomas","tomas123");

    String token = service.login(userRequestDTO).getToken();

    mockMvc.perform(get("/api/v1/fresh-products/batch/list/due-date/{dias}",3)
                    .param("order","date_desc")
                    .param("category","FS")
                    .header("Authorization",token)
            )
            .andDo(print()).andExpect(status().isOk())
            .andExpect(content().contentType("application/json"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.batch_stock.[0].batch_number").value(9))
            .andExpect(MockMvcResultMatchers.jsonPath("$.batch_stock.[1].batch_number").value(7))
            .andExpect(MockMvcResultMatchers.jsonPath("$.batch_stock.[2].batch_number").value(8));

  }

  @Test
  @DisplayName("finBatchesDueToExpireSoon order asc ok")
  void finBatchesDueToExpireSoonAsc() throws Exception {

    UserRequestDTO userRequestDTO = new UserRequestDTO("Tomas","tomas123");

    String token = service.login(userRequestDTO).getToken();

    mockMvc.perform(get("/api/v1/fresh-products/batch/list/due-date/{dias}",3)
                    .param("order","date_asc")
                    .param("category","FS")
                    .header("Authorization",token)
            )
            .andDo(print()).andExpect(status().isOk())
            .andExpect(content().contentType("application/json"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.batch_stock.[0].batch_number").value(8))
            .andExpect(MockMvcResultMatchers.jsonPath("$.batch_stock.[1].batch_number").value(7))
            .andExpect(MockMvcResultMatchers.jsonPath("$.batch_stock.[2].batch_number").value(9));
  }

  @Test
  @DisplayName("finBatchesDueToExpireSoon invalid Params")
  void finBatchesDueToExpireSoonInvalidParams() throws Exception {

    UserRequestDTO userRequestDTO = new UserRequestDTO("Tomas","tomas123");

    String token = service.login(userRequestDTO).getToken();

    mockMvc.perform(get("/api/v1/fresh-products/batch/list/due-date/{dias}",3)
                    .param("order","date_asc")
                    .param("category","SF")
                    .header("Authorization",token)
            )
            .andDo(print()).andExpect(status().isBadRequest());
  }
}