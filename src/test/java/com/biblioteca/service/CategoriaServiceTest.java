package com.biblioteca.service;

import com.biblioteca.exception.ResourceNotFoundException;
import com.biblioteca.model.Categoria;
import com.biblioteca.repository.CategoriaRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoriaServiceTest {

    @Mock
    private CategoriaRepository categoriaRepository;

    @InjectMocks
    private CategoriaService categoriaService;

    private Categoria categoria;

    @BeforeEach
    void setup() {
        categoria = Categoria.builder()
                .id(1L)
                .nome("Fantasia")
                .build();
    }

    // -----------------------------
    // CRIAR
    // -----------------------------
    @Test
    void deveCriarCategoriaComSucesso() {
        when(categoriaRepository.save(any(Categoria.class))).thenReturn(categoria);

        Categoria criada = categoriaService.criar(categoria);

        assertNotNull(criada);
        assertEquals("Fantasia", criada.getNome());
        verify(categoriaRepository, times(1)).save(categoria);
    }

    // -----------------------------
    // LISTAR
    // -----------------------------
    @Test
    void deveListarTodasAsCategorias() {
        when(categoriaRepository.findAll()).thenReturn(List.of(categoria));

        List<Categoria> lista = categoriaService.listar();

        assertEquals(1, lista.size());
        assertEquals("Fantasia", lista.get(0).getNome());
    }

    // -----------------------------
    // BUSCAR POR ID (sucesso)
    // -----------------------------
    @Test
    void deveBuscarCategoriaPorIdComSucesso() {
        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(categoria));

        Categoria resultado = categoriaService.buscarPorId(1L);

        assertNotNull(resultado);
        assertEquals("Fantasia", resultado.getNome());
    }

    // -----------------------------
    // BUSCAR POR ID (não encontrado)
    // -----------------------------
    @Test
    void deveLancarErroAoBuscarCategoriaPorIdInexistente() {
        when(categoriaRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> categoriaService.buscarPorId(1L));
    }

    // -----------------------------
    // ATUALIZAR
    // -----------------------------
    @Test
    void deveAtualizarCategoriaComSucesso() {
        Categoria atualizada = Categoria.builder()
                .nome("Ficção Científica")
                .build();

        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(categoria));
        when(categoriaRepository.save(any(Categoria.class))).thenReturn(categoria);

        Categoria resultado = categoriaService.atualizar(1L, atualizada);

        assertNotNull(resultado);
        verify(categoriaRepository, times(1)).save(categoria);
        // aqui poderíamos testar se o nome foi setado, dependendo da sua implementação
    }

    // -----------------------------
    // DELETAR
    // -----------------------------
    @Test
    void deveDeletarCategoriaComSucesso() {
        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(categoria));

        assertDoesNotThrow(() -> categoriaService.deletar(1L));
        verify(categoriaRepository, times(1)).delete(categoria);
    }
}
