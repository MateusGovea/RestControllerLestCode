package br.com.letscode.java.atividaderestcontroller.rest;

import br.com.letscode.java.atividaderestcontroller.acoescsv.ManipulacaoCsv;
import br.com.letscode.java.atividaderestcontroller.omdbclient.MovieMinimal;
import br.com.letscode.java.atividaderestcontroller.omdbclient.MovieMinimalRestRepository;
//import br.com.letscode.java.atividaderestcontroller.omdbclient.ResultSearch;
import br.com.letscode.java.atividaderestcontroller.omdbclient.ResultSearch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
public class SearchRestController {

    private final MovieMinimalRestRepository restRepository;

    public SearchRestController(MovieMinimalRestRepository restRepository) {
        this.restRepository = restRepository;
    }

    /*@GetMapping("/search")
    public ResultSearch search(@RequestParam String title){
        return this.restRepository.search(title);
    }*/

    @GetMapping("/search")
    public ResultSearch search(@RequestParam String title){
        ManipulacaoCsv movie = new ManipulacaoCsv();
        ResultSearch filmes = this.restRepository.search(title);//pesquisas
        //List<MovieMinimal> filmess = filmes.getResultList();//filmes
        //movie.getResults().add(filmes);
        movie.EscreverCsv(filmes);

        return this.restRepository.search(title);



        //return movie.ConsultaFilmeCsv(title);
    }

    /*@GetMapping("/searchcsv")
    public MovieMinimal searchCsv(@RequestParam String title){
        ManipulacaoCsv arquivo = new ManipulacaoCsv();
        arquivo.leituraArquivo();
        List<MovieMinimal> consulta = arquivo.getMovies();
        if (arquivo.ChecarOcorrenciaCsv(title) == 0){
        arquivo.EscreverCsv();//null-pointer
        return this.restRepository.searchCsv(title);
        } else {
        arquivo.ConsultaFilmeCsv(title);
        return this.restRepository.searchCsv(title);
        }
    }*/

    /*@GetMapping("/{imdbid}")
    public ResultSearch searchId(@RequestParam int imdbid){
        ManipulacaoCsv arquivo = new ManipulacaoCsv();
        arquivo.leituraArquivo();
        List<MovieMinimal> consulta = arquivo.getMovies();
        if (arquivo.ChecarOcorrenciaCsv(Integer.toString(imdbid)) == 0){
            arquivo.EscreverCsv();//null-pointer
            return this.restRepository.search(Integer.toString(imdbid));
        } else {
            arquivo.ConsultaFilmeCsv(Integer.toString(imdbid));
            return this.restRepository.search(Integer.toString(imdbid));
        }
    }*/
}

//Este metodo retorna ResultSearch, dessa forma ele só está retornando a pesquisa na API
//Eu tenho que arrumar um jeito escolher qual executar, sobre o MESMO COMANDO
//Talvez a resposta seja unificar o tipo de saída dos dois métodos

//EscreverCsv() esta dando NullPointer, é provável que eu tenha que pesquisar outras formas de fazer aquilo
//Talvez ele esteja assim por estar sendo executado ANTES do Return do metodo
//Uma solução seria fazer ele ocorrer dps do método, na msm chamada, ou encontrar uma que tbm dê certo quando precisar