package br.com.letscode.java.atividaderestcontroller.omdbclient;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Arrays;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class MovieMinimal {

    @JsonProperty("imdbID")
    private String imdbid;
    @JsonProperty("Title")
    private String title;
    private Integer year;

    public MovieMinimal(MovieMinimal movieMinimal) {
    }

    @JsonProperty("Year")
    public void setYear(String year){
        this.year = convertYear(year);
    }

    private int convertYear(String year) {
        if (year.matches("\\d+")){
            return Integer.parseInt(year);
        }
        return Arrays.stream(year.split("\\D"))
                .map(Integer::parseInt)
                .findFirst()
                .orElseThrow();
    }

    public static MovieMinimal fromLine(String line) {
        String[] split = line.split(", ");
        return new MovieMinimal(
                split[0],
                split[1],
                Integer.parseInt(split[2])
        );
    }

    public String toCsv() {
        return this.imdbid + ",\"" + this.title + ",\"" + this.year + "\n";
    }

    public String getImdbid() {
        return imdbid;
    }

    public String getTitle() {
        return title;
    }

    public Integer getYear() {
        return year;
    }
}
