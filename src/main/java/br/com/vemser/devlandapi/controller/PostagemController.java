package br.com.vemser.devlandapi.controller;

import br.com.vemser.devlandapi.dto.PostagemComentDTO;
import br.com.vemser.devlandapi.dto.PostagemCreateDTO;
import br.com.vemser.devlandapi.dto.PostagemDTO;
import br.com.vemser.devlandapi.exceptions.RegraDeNegocioException;
import br.com.vemser.devlandapi.service.PostagemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/postagem")
@Validated
public class PostagemController {

    @Autowired
    private PostagemService postagemService;

    @Operation(summary = "Listar todas as postagens", description = "Realizará a listagem de todas as postagens do banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Sucesso! A listagem das postagens foi realizada com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Permissão negada! Você não possui permissão para utilizar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Erro! Durante a execução, foi gerada uma exceção")
            }
    )
    @GetMapping
    public ResponseEntity<List<PostagemDTO>> list() throws RegraDeNegocioException {
        return new ResponseEntity<>(postagemService.list(), HttpStatus.OK);
    }

    @Operation(summary = "Listar postagens por tipo", description = "Realizará a listagem de todas as postagens do banco de dados pelo tipo")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Sucesso! A listagem das postagens pelo tipo foi realizada com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Permissão negada! Você não possui permissão para utilizar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Erro! Durante a execução, foi gerada uma exceção")
            }
    )
    @GetMapping("/{tipoPostagem}/tipo")
    public ResponseEntity<List<PostagemDTO>> litByTipo(@PathVariable("tipoPostagem") Integer tipoPostagem) throws RegraDeNegocioException {
        return new ResponseEntity<>(postagemService.listByTipo(tipoPostagem), HttpStatus.OK);
    }

    @GetMapping("/{idPostagem}/comentarios")
    public ResponseEntity<PostagemComentDTO> listByIdPostagem(@PathVariable("idPostagem") Integer idPostagem) throws RegraDeNegocioException {
        return new ResponseEntity<>(postagemService.listById(idPostagem), HttpStatus.OK);
    }

    @Operation(summary = "Adicionar postagem", description = "Adicionará uma nova postagem ao banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Sucesso! Nova postagem adicionada com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Permissão negada! Você não possui permissão para utilizar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Erro! Durante a execução, foi gerada uma exceção")
            }
    )
    @PostMapping("/{idUsuario}")
    public ResponseEntity<PostagemDTO> post(@PathVariable("idUsuario") Integer idUsuario,
                                            @RequestBody @Valid PostagemCreateDTO postagemCreateDTO) throws RegraDeNegocioException {
        return new ResponseEntity<>(postagemService.post(idUsuario, postagemCreateDTO), HttpStatus.CREATED);
    }

    @Operation(summary = "Adicionar curtida à postagem", description = "Adicionará uma curtida à postagem no banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Sucesso! Novo like adicionado à postagem com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Permissão negada! Você não possui permissão para utilizar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Erro! Durante a execução, foi gerada uma exceção")
            }
    )
    @PostMapping("/{idPostagem}/curtir")
    public ResponseEntity<PostagemDTO> curtir(@PathVariable("idPostagem") Integer idPostagem) throws RegraDeNegocioException {
        return new ResponseEntity<>(postagemService.curtir(idPostagem), HttpStatus.OK);
    }

    @Operation(summary = "Atualizar postagem", description = "Realizará a atualização da postagem no banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Sucesso! A postagem selecionada foi atualizada com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Permissão negada! Você não possui permissão para utilizar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Erro! Durante a execução, foi gerada uma exceção")
            }
    )
    @PutMapping("/{idPostagem}")
    public ResponseEntity<PostagemDTO> update(@PathVariable() Integer idPostagem,
                                           @RequestBody @Valid PostagemCreateDTO postagemCreateDTO) throws RegraDeNegocioException {
        return new ResponseEntity<>(postagemService.update(idPostagem, postagemCreateDTO), HttpStatus.OK);
    }

    @Operation(summary = "Deletar postagem", description = "Deletará a postagem do banco de dados com base na sua identificação")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Sucesso! A postagem foi removida com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Permissão negada! Você não possui permissão para utilizar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Erro! Durante a execução, foi gerada uma exceção")
            }
    )
    @DeleteMapping("/{idPostagem}")
    public ResponseEntity<Void> delete(@PathVariable("idPostagem") Integer idPostagem) throws RegraDeNegocioException {
        postagemService.delete(idPostagem);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
