package br.com.letscode.java.atividaderestcontroller.acoescsv;

import br.com.letscode.java.atividaderestcontroller.omdbclient.MovieMinimal;
//import br.com.letscode.java.atividaderestcontroller.omdbclient.ResultSearch;
import br.com.letscode.java.atividaderestcontroller.omdbclient.ResultSearch;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.opencsv.CSVWriter;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class ManipulacaoCsv {

    @JsonProperty("Search")
    public List<MovieMinimal> movies;
    public List<ResultSearch> results;

    public void EscreverCsv(ResultSearch filmes) {
        String filePath = getFilePathResourceAsString();
        this.movies = filmes.getResultList();
        try {
            for (MovieMinimal mfilm : this.movies) {
                Files.writeString(Path.of(filePath), mfilm.toCsv());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public long ChecarOcorrenciaCsv(String titulo) {//Testar e verificar necessidade
        return this.movies.stream()
                //.anyMatch(item -> item.getTitulo().equals(titulo));
                .filter(p -> p.getTitle().equals(titulo))
                .count();
        /*this.filmesCsv.stream()
                .filter(p -> p.getTitulo().equals(titulo))
                .collect(Collectors.toSet())
                .forEach(System.out::println);*/
    }

    /*public ResultSearch ConsultaFilmeCsv(String titulo) {//Descobrir como chamar e ver se tem q ser void ou nao
        return this.movies.stream()
                .filter(p -> p.getTitle().equals(titulo))
                //.collect(Collectors.toSet())
                //.collect(toList());
                //.forEach(System.out::println);
    }*/

    public void leituraArquivo() {
        String filePath = getFilePathResourceAsString();
        try (Stream<String> lines = Files.lines(Path.of(filePath))) {
            this.movies = lines
                    .map(MovieMinimal::fromLine)
                    .collect(toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getFilePathResourceAsString() {
        URL url = getClass().getClassLoader().getResource("cache.csv");
        File file = new File(Objects.requireNonNull(url).getFile());
        return file.getPath();
    }

    public List<MovieMinimal> getMovies() {
        return movies;
    }

    public List<ResultSearch> getResults() {
        return results;
    }
}
