package br.com.letscode.java.atividaderestcontroller.rest;

import br.com.letscode.java.atividaderestcontroller.acoescsv.CsvRepository;
import br.com.letscode.java.atividaderestcontroller.omdbclient.MovieMinimal;
import br.com.letscode.java.atividaderestcontroller.omdbclient.MovieMinimalRestRepository;
import br.com.letscode.java.atividaderestcontroller.omdbclient.ResultSearch;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;


@Log4j2
@RestController
public class SearchRestController {

    private final MovieMinimalRestRepository restRepository;
    private final CsvRepository csvRepository;

    public SearchRestController(MovieMinimalRestRepository restRepository) {
        this.restRepository = restRepository;
        this.csvRepository = new CsvRepository();
    }

    @GetMapping("/search")
    public ResultSearch search(@RequestParam String title) {
        final List<MovieMinimal> movieMinimalList = this.csvRepository.search(title);
        if (movieMinimalList.isEmpty()) {
            log.info("Não foram encontrados resultados localmente para {}. Buscando na API.", title);
            final ResultSearch resultSearch = this.restRepository.search(title);
            if (resultSearch.getResponse()) {
                log.debug("Atualizando arquivo de cache com os resultados da API.");
                this.csvRepository.escrever(resultSearch);
            }
            return resultSearch;
        } else {
            return new ResultSearch(movieMinimalList, movieMinimalList.size(), true);
        }
    }
}
