package br.com.letscode.java.atividaderestcontroller.omdbclient;

import br.com.letscode.java.atividaderestcontroller.acoescsv.ConversorData;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.*;

@Value
public class MovieMinimal {

    String imdbId;
    String title;
    Integer year;

    @JsonCreator
    public MovieMinimal(String imdbId, String title, String year) {
        this.imdbId = imdbId;
        this.title = title;
        this.year = ConversorData.convertYear(year);
    }
}
