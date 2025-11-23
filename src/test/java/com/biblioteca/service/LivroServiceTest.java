package com.biblioteca.service;

import com.biblioteca.dto.LivroCreateUpdateDTO;
import com.biblioteca.dto.LivroDTO;
import com.biblioteca.exception.ResourceNotFoundException;
import com.biblioteca.mapper.LivroMapper;
import com.biblioteca.model.Categoria;
import com.biblioteca.model.Livro;
import com.biblioteca.repository.CategoriaRepository;
import com.biblioteca.repository.LivroRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class LivroServiceTest {

    @Mock
    private LivroRepository livroRepository;

    @Mock
    private CategoriaRepository categoriaRepository;

    @InjectMocks
    private LivroService livroService;

    private Categoria categoria;
    private Livro livro;
    private LivroCreateUpdateDTO dto;

    @BeforeEach
    void setup() {
        categoria = Categoria.builder()
                .id(1L)
                .nome("Fantasia")
                .build();

        livro = Livro.builder()
                .id(1L)
                .titulo("O Hobbit")
                .autor("Tolkien")
                .isbn("123456")
                .anoPublicacao(1937)
                .quantidadeExemplares(3)
                .categoria(categoria)
                .build();

        dto = LivroCreateUpdateDTO.builder()
                .titulo("O Hobbit")
                .autor("Tolkien")
                .isbn("123456")
                .anoPublicacao(1937)
                .quantidadeExemplares(3)
                .categoriaId(1L)
                .build();
    }

    // -----------------------------------
    // TESTE: CRIAR LIVRO (sucesso)
    // -----------------------------------
    @Test
    void deveCriarLivroComSucesso() {
        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(categoria));
        when(livroRepository.save(any(Livro.class))).thenReturn(livro);

        LivroDTO resultado = livroService.criar(dto);

        assertNotNull(resultado);
        assertEquals("O Hobbit", resultado.getTitulo());
        verify(livroRepository, times(1)).save(any(Livro.class));
    }

    // -----------------------------------
    // TESTE: CRIAR LIVRO COM CATEGORIA INEXISTENTE
    // -----------------------------------
    @Test
    void deveLancarErroQuandoCategoriaNaoExisteAoCriar() {
        when(categoriaRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> livroService.criar(dto));
    }

    // -----------------------------------
    // TESTE: BUSCAR POR ID (sucesso)
    // -----------------------------------
    @Test
    void deveBuscarLivroPorIdComSucesso() {
        when(livroRepository.findById(1L)).thenReturn(Optional.of(livro));

        LivroDTO resultado = livroService.buscarPorId(1L);

        assertNotNull(resultado);
        assertEquals("O Hobbit", resultado.getTitulo());
    }

    // -----------------------------------
    // TESTE: BUSCAR POR ID (não encontrado)
    // -----------------------------------
    @Test
    void deveLancarErroAoBuscarPorIdInexistente() {
        when(livroRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> livroService.buscarPorId(1L));
    }

    // -----------------------------------
    // TESTE: LISTAR LIVROS
    // -----------------------------------
    @Test
    void deveListarTodosOsLivros() {
        when(livroRepository.findAll()).thenReturn(List.of(livro));

        List<LivroDTO> lista = livroService.listar();

        assertEquals(1, lista.size());
        assertEquals("O Hobbit", lista.get(0).getTitulo());
    }

    // -----------------------------------
    // TESTE: ATUALIZAR LIVRO
    // -----------------------------------
    @Test
    void deveAtualizarLivroComSucesso() {
        when(livroRepository.findById(1L)).thenReturn(Optional.of(livro));
        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(categoria));
        when(livroRepository.save(any(Livro.class))).thenReturn(livro);

        LivroDTO resultado = livroService.atualizar(1L, dto);

        assertNotNull(resultado);
        assertEquals("O Hobbit", resultado.getTitulo());
    }

    // -----------------------------------
    // TESTE: DELETAR LIVRO
    // -----------------------------------
    @Test
    void deveDeletarLivroComSucesso() {
        when(livroRepository.findById(1L)).thenReturn(Optional.of(livro));

        assertDoesNotThrow(() -> livroService.deletar(1L));
        verify(livroRepository, times(1)).delete(livro);
    }
}
