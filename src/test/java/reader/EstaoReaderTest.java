package reader;

import com.google.gson.JsonSyntaxException;
import entity.Estado;
import entity.Regiao;
import org.junit.Test;
import org.junit.jupiter.api.Order;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class EstaoReaderTest {
    @Test
    @Order(1)
    public void lerUmaRegiaoTest() throws IOException {
        Estado estado = EstadoReader.readOneFrom("./static/Tocantins.json");
        assertNotNull(estado);
        assertTrue(estado instanceof Estado);
        assertNotNull(estado.getRegiao());
    }

    @Test(expected = NoSuchFileException.class)
    @Order(2)
    public void lerUmaRegiaoPathErradoTest() throws IOException {
        RegiaoReader.readOneFrom("./static/Toca.json");
    }

    @Test
    @Order(3)
    public void lerListaRegioes() throws IOException {
        List<Estado> estados = EstadoReader.readListFrom("./static/Estados.json");
        assertNotNull(estados);
        assertFalse(estados.isEmpty());
        estados.forEach(item -> {
            assertTrue(item instanceof Estado);
            assertNotNull( item);
            assertNotNull(item.getNome());
        });
    }

    @Test(expected = NoSuchFileException.class)
    @Order(4)
    public void lerListaRegioesPathErradoTest() throws IOException {
        RegiaoReader.readListFrom("./static/Regiaos.json");
    }

    @Test(expected = JsonSyntaxException.class)
    @Order(5)
    public void lerListaRegioesJsonErradoTest() throws IOException {
        RegiaoReader.readListFrom("./static/Tocantins.json");
    }
}
