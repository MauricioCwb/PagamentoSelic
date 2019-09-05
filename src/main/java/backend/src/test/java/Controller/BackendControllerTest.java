package Controller;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import Model.CalculoEntrada;
import Model.CalculoSaida;
import ch.qos.logback.core.util.ContentTypeUtil;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BackendControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void getParcelas() throws Exception {    	
        mvc.perform(MockMvcRequestBuilders.post("/calculoSelic").contentType(MediaType.APPLICATION_JSON)
        		.content("{" + 
        				"    \"produto\": [" + 
        				"        {" + 
        				"            \"codigo\": 123," + 
        				"            \"nome\": \"Nome do Produto\"," + 
        				"            \"valor\": 100000" + 
        				"        }" + 
        				"    ]," + 
        				"    \"condicaoPagamento\": [" + 
        				"        {" + 
        				"            \"valorEntrada\": 0," + 
        				"            \"qtdeParcelas\": 2" + 
        				"        }" + 
        				"    ]" + 
        				"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("[{\"parcela\":1,\"valor\":51156.61,\"taxaJuros\":1.15},{\"parcela\":2,\"valor\":51156.61,\"taxaJuros\":1.15}]"));
    }
    
    @Test
    public void getPing() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("Sorry! My answers are limited. You have to ask the right questions!")));
    }
    
    @Test
    public void getTesting() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/test").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("Testing 1, 2, 3!")));
    }
}
