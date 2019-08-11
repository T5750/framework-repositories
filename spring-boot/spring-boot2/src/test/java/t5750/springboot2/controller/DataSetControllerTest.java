package t5750.springboot2.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigInteger;
import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import t5750.springboot2.model.DataSet;
import t5750.springboot2.service.DataSetService;

@RunWith(SpringRunner.class)
@WebMvcTest(DataSetController.class)
public class DataSetControllerTest {
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private DataSetService dataSetService;

	@Test
	public void foo() throws Exception {
		Mockito.when(dataSetService.findAll()).thenReturn(
				Arrays.asList(new DataSet(BigInteger.valueOf(1), "data")));
		MvcResult mvcResult = mockMvc.perform(get("/fetch-data-sets"))
				.andExpect(request().asyncStarted())
				.andDo(MockMvcResultHandlers.log()).andReturn();
		mockMvc.perform(asyncDispatch(mvcResult))
				.andDo(MockMvcResultHandlers.log()).andExpect(status().isOk())
				.andExpect(content().json("{\"id\":1,\"name\":\"data\"}"));
	}
}
