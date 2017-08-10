//package com.cityelf;
//
//import static org.mockito.Matchers.any;
//import static org.mockito.Mockito.doNothing;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import com.cityelf.controller.ShutdownReportController;
//import com.cityelf.model.Address;
//import com.cityelf.model.ShutdownReport;
//import com.cityelf.model.ShutdownReportRequest;
//import com.cityelf.service.ShutdownReportService;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.SerializationFeature;
//import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.time.LocalDateTime;
//
//@RunWith(SpringRunner.class)
//@WebMvcTest(value = ShutdownReportController.class, secure = false)
//public class ShutdownReportControllerTest {
//
//  @Autowired
//  private MockMvc mockMvc;
//
//  @MockBean
//  private ShutdownReportService shutdownReportService;
//  private ShutdownReportRequest shutdownReportRequest = new ShutdownReportRequest();
//  private ShutdownReport shutdownReport = new ShutdownReport();
//  private Address address = new Address();
//
//  public String objectToJson(Object o) throws Exception {
//    ObjectMapper mapper = new ObjectMapper();
//    mapper.registerModule(new JavaTimeModule());
//    mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
//    return mapper.writeValueAsString(o);
//  }
//
//  @Before
//  public void setUp() {
//    shutdownReport.setAddress(address);
//    shutdownReport.setStart(LocalDateTime.now());
//    shutdownReport.setForecastType("Water");
//    shutdownReportRequest.setShutdownReport(shutdownReport);
//    shutdownReportRequest.setUserId(2);
//  }
//
//  @Test
//  public void addNewReportShouldReturnHttpStatusOk200()
//      throws Exception {
//    doNothing().when(shutdownReportService).addNewReport(any());
//
//    mockMvc.perform(
//        post("/peoplereport/add")
//            .contentType(MediaType.APPLICATION_JSON_UTF8)
//            .content(objectToJson(shutdownReportRequest))
//    )
//        .andDo(print())
//        .andExpect(status().isOk());
//  }
//}
