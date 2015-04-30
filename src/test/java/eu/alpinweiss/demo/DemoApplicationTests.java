package eu.alpinweiss.demo;

import eu.alpinweiss.demo.domain.Planet;
import eu.alpinweiss.demo.domain.PlanetRepository;
import org.hamcrest.Matchers;
import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.matchers.NotNull;
import org.mockito.internal.matchers.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.internal.matchers.Null.NULL;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DemoApplication.class)
@WebAppConfiguration
public class DemoApplicationTests {

	@Autowired
	private PlanetRepository planetRepository;

	@Test
	public void shouldReturnFirstSolarSystemPlanet() throws Exception {
		Planet mercury = planetRepository.findOne(1L);
		assertThat(mercury.getName(), is("Mercury"));
	}

	@Test
	public void shouldRemovePlanetFromDB() throws Exception {
		Planet mercury = planetRepository.findOne(1L);
		planetRepository.delete(mercury);
		mercury = planetRepository.findOne(1L);
		assertThat(mercury, NULL);
	}

	@Test
	public void shouldSavePlanetAndReturnTenPlanetsInSolarSystem() throws Exception {
		Planet planet = new Planet("Phaeton", 1.0, "Alpinweiss Team", 180304.0, true, "Mystical planet");
		planetRepository.save(planet);
		List<Planet> planets = planetRepository.findAll();
		assertThat(planets.size(), is(10));
	}

}
