package br.com.letscode.java.atividaderestcontroller.acoescsv;

import br.com.letscode.java.atividaderestcontroller.omdbclient.MovieMinimal;
import br.com.letscode.java.atividaderestcontroller.omdbclient.ResultSearch;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class CsvRepository {
    private final String path;
    private List<CsvRecord> recordList;

    public CsvRepository() {
        log.debug("Criando instância de CsvRecordRepository.");
        this.path = Optional.ofNullable(getClass().getClassLoader().getResource("cache.csv"))
                .map(url -> new File(url.getFile()).getPath())
                .orElseThrow();
        this.updateRecordList();
    }

    private void updateRecordList() {
        try (Stream<String> lines = Files.lines(Path.of(this.path))) {
            this.recordList = lines
                    .map(CsvRecord::fromLine)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            log.error("Não foi possível carregar o conteúdo de \"cache.csv\" para memória.", e);
        }
    }

    public List<MovieMinimal> search(String title) {
        log.debug("Buscando por {} no cache.", title);
        return this.recordList.stream()
                .filter(cr -> cr.getTitle().equalsIgnoreCase(title) && cr.getResponse())
                .map(CsvRecord::getMovieMinimal)
                .collect(Collectors.toList());
    }

    public void escrever(ResultSearch resultSearch) {
        resultSearch.getResultList()
                .forEach(num -> this.escreverLinha(num.getImdbId(), num.getTitle(), num.getYear()));
        this.updateRecordList();
    }

    private void escreverLinha(String imdbId, String movieTitle,
                               Integer year) {
        String linha = imdbId + ";" + movieTitle + ";" + year + "\n";
        try {
            Files.writeString(Path.of(this.path), linha, StandardOpenOption.APPEND);
        } catch (IOException e) {
            log.warn("Falha ao escrever o resultado");
        }
    }
}
