package br.com.acme.sw.apis.planetapi.fixtures;

import br.com.acme.sw.apis.planetapi.client.model.SwPageDTO;
import br.com.acme.sw.apis.planetapi.client.model.SwPlanetDTO;
import br.com.acme.sw.apis.planetapi.model.PlanetRequest;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class FixtureLoader implements TemplateLoader {

    @PostConstruct
    public void initialize() {
        load();
    }

    @Override
    public void load() {
        loadSwApiFixtures();
        loadPlanetRequests();
    }

    private void loadSwApiFixtures() {
        Fixture.of(SwPlanetDTO.class).addTemplate("tatooine", new Rule() {{
            add("climate", "arid");
            add("terrain", "desert");
            add("name", "Tatooine");
            add("films", IntStream.of(1, 3, 4, 5, 6).mapToObj(x -> MessageFormat.format("https://swapi.co/api/films/{0}/", x)).collect(Collectors.toList()));
        }});

        Fixture.of(SwPageDTO.class).addTemplate("default", new Rule() {{
            add("count", 0L);
            add("next", "");
            add("previous", "");
            add("results", Collections.emptyList());
        }});

        Fixture.of(SwPageDTO.class).addTemplate("tatooine").inherits("default", new Rule() {{
            add("count", 1L);
            add("results", has(1).of(SwPlanetDTO.class, "tatooine"));
        }});
    }

    private void loadPlanetRequests() {
        Fixture.of(PlanetRequest.class).addTemplate("valid", new Rule() {{
            add("name", "Tatooine");
            add("climate", "Arid");
            add("terrain", "Desert");
        }});

        Fixture.of(PlanetRequest.class).addTemplate("invalid").inherits("valid", new Rule() {{
            add("terrain", null);
        }});

        Fixture.of(PlanetRequest.class).addTemplate("valid.earth", new Rule() {{
            add("terrain", "Grasslands, Mountains");
            add("climate", "Temperate");
            add("name", "Earth");
        }});
    }
}
