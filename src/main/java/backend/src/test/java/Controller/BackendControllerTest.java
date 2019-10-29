package Controller;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BackendControllerTest {

    @Autowired
    private MockMvc mvc;

    @Before
    public void initCarrega() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/produtos/carregar")
        		.accept(MediaType.TEXT_PLAIN)
        		.content("\"body\": \"C:\\Projetos\\Produto\\src\\main\\java\\backend\\products_teste_webdev_leroy.xlsx\""))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testConsulta() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/produtos/consultar")
        		.accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testProdutos() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/produtos/produto")
        		.accept(MediaType.APPLICATION_JSON))
        		.andExpect(status().isOk());
    }

    @Test
    public void testProduto9999() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/produtos/produto/9999")
        		.accept(MediaType.APPLICATION_JSON))
        		.andExpect(status().isBadRequest());
    }

    @Test
    public void testDeleteProduto1010() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/produtos/produto/1010")
        		.accept(MediaType.APPLICATION_JSON))
        		.andExpect(status().isBadRequest());
    }

    @Test
    public void testPing() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/")
        		.accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("Sorry! My answers are limited. You have to ask the right questions!")));
    }
    
    @Test
    public void testTesting() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/test")
        		.accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("Testing 1, 2, 3!")));
    }
}
