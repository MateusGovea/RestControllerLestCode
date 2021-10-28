package br.com.letscode.java.atividaderestcontroller.omdbclient;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Value;

import java.util.List;

import static java.lang.Boolean.valueOf;
import static java.lang.Integer.parseInt;

@Value
@AllArgsConstructor
public class ResultSearch {

    List<MovieMinimal> resultList;
    Integer total;
    Boolean response;

    @JsonCreator
    public ResultSearch(
            @JsonProperty("Search") List<MovieMinimal> resultList,
            @JsonProperty("totalResults") String total,
            @JsonProperty("Response") String response) {
        this(resultList, parseInt(total), valueOf(response));
    }
}
