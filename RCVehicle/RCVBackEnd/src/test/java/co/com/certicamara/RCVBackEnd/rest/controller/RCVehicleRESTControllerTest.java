package co.com.certicamara.RCVBackEnd.rest.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("classpath:context/RCV-context.xml")
public class RCVehicleRESTControllerTest {
	
	public static final String API_VERSION = "/v1";
	public static final String MAP = "{\"x\":\"3\",\"y\":\"4\"}";
	
	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;
	
	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	@Test
	public void configMapTest() throws Exception{
		mockMvc.perform(post(API_VERSION + "/Map").content(MAP).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}
	
	@Test
	public void configMapWrongTest() throws Exception{
		String wrongMap = "{\"x\":\"3\",\"y\":\"-4\"}";
		mockMvc.perform(post(API_VERSION + "/Map").content(wrongMap).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotAcceptable());
	}
	
	@Test
	public void getMapTest() throws Exception{
		mockMvc.perform(get(API_VERSION + "/Map").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}
	
	@Test
	public void moveVehicleTest() throws Exception{
		String command = "2,N";
		mockMvc.perform(delete(API_VERSION + "/restartPosition").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
		mockMvc.perform(post(API_VERSION + "/Map").content(MAP).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
		mockMvc.perform(post(API_VERSION + "/moveVehicle").content(command) .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}
	
	@Test
	public void moveVehicleWithArrayTest() throws Exception{
		String command = "2,N;1,S;2,E;1,O";
		mockMvc.perform(delete(API_VERSION + "/restartPosition").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
		mockMvc.perform(post(API_VERSION + "/Map").content(MAP).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
		mockMvc.perform(post(API_VERSION + "/moveVehicle").content(command) .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}
	
	@Test
	public void moveVehicleWrongFormatTest() throws Exception{
		String command = "2,N;1S;2,E;1,O";
		mockMvc.perform(delete(API_VERSION + "/restartPosition").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
		mockMvc.perform(post(API_VERSION + "/Map").content(MAP).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
		mockMvc.perform(post(API_VERSION + "/moveVehicle").content(command) .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotAcceptable());
	}
	
	@Test
	public void moveVehicleOutOfMapTest() throws Exception{
		String command = "2,N;1,S;2,E;6,O";
		mockMvc.perform(delete(API_VERSION + "/restartPosition").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
		mockMvc.perform(post(API_VERSION + "/Map").content(MAP).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
		mockMvc.perform(post(API_VERSION + "/moveVehicle").content(command) .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotAcceptable());
	}
}
