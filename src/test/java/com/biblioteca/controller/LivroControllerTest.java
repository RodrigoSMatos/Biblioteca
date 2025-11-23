package com.biblioteca.controller;

import com.biblioteca.dto.LivroCreateUpdateDTO;
import com.biblioteca.dto.LivroDTO;
import com.biblioteca.dto.CategoriaDTO;
import com.biblioteca.service.LivroService;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(LivroController.class)
class LivroControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LivroService livroService;

    @Autowired
    private ObjectMapper objectMapper;

    // ---------------------------
    // TESTE: POST /livros
    // ---------------------------
    @Test
    void deveCriarLivroComSucesso() throws Exception {

        LivroCreateUpdateDTO dto = LivroCreateUpdateDTO.builder()
                .titulo("O Hobbit")
                .autor("Tolkien")
                .isbn("1234567890")
                .anoPublicacao(1937)
                .quantidadeExemplares(3)
                .categoriaId(1L)
                .build();

        LivroDTO retorno = LivroDTO.builder()
                .id(1L)
                .titulo("O Hobbit")
                .autor("Tolkien")
                .isbn("1234567890")
                .anoPublicacao(1937)
                .quantidadeExemplares(3)
                .categoria(CategoriaDTO.builder().id(1L).nome("Fantasia").build())
                .build();

        when(livroService.criar(any(LivroCreateUpdateDTO.class))).thenReturn(retorno);

        mockMvc.perform(post("/livros")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.titulo").value("O Hobbit"))
                .andExpect(jsonPath("$.categoria.id").value(1));

        verify(livroService, times(1)).criar(any(LivroCreateUpdateDTO.class));
    }

    // ---------------------------
    // TESTE: GET /livros
    // ---------------------------
    @Test
    void deveListarLivros() throws Exception {

        LivroDTO livro = LivroDTO.builder()
                .id(1L)
                .titulo("O Hobbit")
                .autor("Tolkien")
                .isbn("123")
                .categoria(CategoriaDTO.builder().id(1L).nome("Fantasia").build())
                .build();

        when(livroService.listar()).thenReturn(List.of(livro));

        mockMvc.perform(get("/livros"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].titulo").value("O Hobbit"));

        verify(livroService, times(1)).listar();
    }

    // ---------------------------
    // TESTE: GET /livros/{id}
    // ---------------------------
    @Test
    void deveBuscarLivroPorId() throws Exception {

        LivroDTO livro = LivroDTO.builder()
                .id(1L)
                .titulo("O Hobbit")
                .autor("Tolkien")
                .isbn("123")
                .categoria(CategoriaDTO.builder().id(1L).nome("Fantasia").build())
                .build();

        when(livroService.buscarPorId(1L)).thenReturn(livro);

        mockMvc.perform(get("/livros/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value("O Hobbit"));

        verify(livroService, times(1)).buscarPorId(1L);
    }

    // ---------------------------
    // TESTE: PUT /livros/{id}
    // ---------------------------
    @Test
    void deveAtualizarLivro() throws Exception {

        LivroCreateUpdateDTO dto = LivroCreateUpdateDTO.builder()
                .titulo("Atualizado")
                .autor("Tolkien")
                .isbn("999")
                .anoPublicacao(1937)
                .quantidadeExemplares(10)
                .categoriaId(1L)
                .build();

        LivroDTO retorno = LivroDTO.builder()
                .id(1L)
                .titulo("Atualizado")
                .autor("Tolkien")
                .isbn("999")
                .anoPublicacao(1937)
                .quantidadeExemplares(10)
                .categoria(CategoriaDTO.builder().id(1L).nome("Fantasia").build())
                .build();

        when(livroService.atualizar(eq(1L), any(LivroCreateUpdateDTO.class))).thenReturn(retorno);

        mockMvc.perform(put("/livros/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value("Atualizado"));

        verify(livroService, times(1)).atualizar(eq(1L), any());
    }

    // ---------------------------
    // TESTE: DELETE /livros/{id}
    // ---------------------------
    @Test
    void deveDeletarLivro() throws Exception {

        doNothing().when(livroService).deletar(1L);

        mockMvc.perform(delete("/livros/1"))
                .andExpect(status().isNoContent());

        verify(livroService, times(1)).deletar(1L);
    }
}
