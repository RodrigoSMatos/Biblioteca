package com.biblioteca.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ModelTest {

    @Test
    void deveCriarCategoria() {
        Categoria c = new Categoria();
        c.setId(1L);
        c.setNome("Fantasia");

        assertEquals(1L, c.getId());
        assertEquals("Fantasia", c.getNome());
    }

    @Test
    void deveCriarLivro() {
        Categoria cat = new Categoria();
        cat.setId(1L);

        Livro l = new Livro();
        l.setId(10L);
        l.setTitulo("Teste");
        l.setCategoria(cat);

        assertEquals(10L, l.getId());
        assertEquals("Teste", l.getTitulo());
        assertEquals(1L, l.getCategoria().getId());
    }
}
