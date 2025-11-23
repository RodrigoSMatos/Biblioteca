package com.biblioteca.controller;

import com.biblioteca.model.Categoria;
import com.biblioteca.service.CategoriaService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

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
@WebMvcTest(CategoriaController.class)
class CategoriaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoriaService categoriaService;

    @Autowired
    private ObjectMapper objectMapper;

    // ---------------------------
    // POST /categorias
    // ---------------------------
    @Test
    void deveCriarCategoriaComSucesso() throws Exception {
        Categoria categoria = Categoria.builder()
                .id(1L)
                .nome("Fantasia")
                .build();

        when(categoriaService.criar(any(Categoria.class))).thenReturn(categoria);

        mockMvc.perform(post("/categorias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoria)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("Fantasia"));

        verify(categoriaService, times(1)).criar(any(Categoria.class));
    }

    // ---------------------------
    // GET /categorias
    // ---------------------------
    @Test
    void deveListarCategorias() throws Exception {
        Categoria categoria = Categoria.builder()
                .id(1L)
                .nome("Fantasia")
                .build();

        when(categoriaService.listar()).thenReturn(List.of(categoria));

        mockMvc.perform(get("/categorias"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Fantasia"));

        verify(categoriaService, times(1)).listar();
    }

    // ---------------------------
    // GET /categorias/{id}
    // ---------------------------
    @Test
    void deveBuscarCategoriaPorId() throws Exception {
        Categoria categoria = Categoria.builder()
                .id(1L)
                .nome("Fantasia")
                .build();

        when(categoriaService.buscarPorId(1L)).thenReturn(categoria);

        mockMvc.perform(get("/categorias/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Fantasia"));

        verify(categoriaService, times(1)).buscarPorId(1L);
    }

    // ---------------------------
    // PUT /categorias/{id}
    // ---------------------------
    @Test
    void deveAtualizarCategoria() throws Exception {
        Categoria entrada = Categoria.builder()
                .nome("Ficção Científica")
                .build();

        Categoria retorno = Categoria.builder()
                .id(1L)
                .nome("Ficção Científica")
                .build();

        when(categoriaService.atualizar(eq(1L), any(Categoria.class))).thenReturn(retorno);

        mockMvc.perform(put("/categorias/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(entrada)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Ficção Científica"));

        verify(categoriaService, times(1)).atualizar(eq(1L), any(Categoria.class));
    }

    // ---------------------------
    // DELETE /categorias/{id}
    // ---------------------------
    @Test
    void deveDeletarCategoria() throws Exception {
        doNothing().when(categoriaService).deletar(1L);

        mockMvc.perform(delete("/categorias/1"))
                .andExpect(status().isNoContent());

        verify(categoriaService, times(1)).deletar(1L);
    }
}
