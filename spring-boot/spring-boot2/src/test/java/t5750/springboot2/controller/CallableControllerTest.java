package t5750.springboot2.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

@RunWith(SpringRunner.class)
@WebMvcTest(CallableController.class)
public class CallableControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testHelloWorldController() throws Exception {
		MvcResult mvcResult = mockMvc.perform(get("/testCallable"))
				.andExpect(request().asyncStarted())
				.andDo(MockMvcResultHandlers.log()).andReturn();
		mockMvc.perform(asyncDispatch(mvcResult)).andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith("text/plain"))
				.andExpect(content().string("Test Callable !!"));
	}
}