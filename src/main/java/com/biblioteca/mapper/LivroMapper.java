package com.biblioteca.mapper; // ajuste

import com.biblioteca.dto.*;
import com.biblioteca.model.Livro;
import com.biblioteca.model.Categoria;

public class LivroMapper {

    public static LivroDTO toDTO(Livro livro) {
        if (livro == null) return null;

        return LivroDTO.builder()
                .id(livro.getId())
                .titulo(livro.getTitulo())
                .autor(livro.getAutor())
                .isbn(livro.getIsbn())
                .anoPublicacao(livro.getAnoPublicacao())
                .quantidadeExemplares(livro.getQuantidadeExemplares())
                .categoria(
                        CategoriaDTO.builder()
                                .id(livro.getCategoria().getId())
                                .nome(livro.getCategoria().getNome())
                                .build()
                )
                .build();
    }

    public static Livro fromCreateDTO(LivroCreateUpdateDTO dto, Categoria categoria) {
        return Livro.builder()
                .titulo(dto.getTitulo())
                .autor(dto.getAutor())
                .isbn(dto.getIsbn())
                .anoPublicacao(dto.getAnoPublicacao())
                .quantidadeExemplares(dto.getQuantidadeExemplares())
                .categoria(categoria)
                .build();
    }

    public static void updateEntity(Livro livro, LivroCreateUpdateDTO dto, Categoria categoria) {
        livro.setTitulo(dto.getTitulo());
        livro.setAutor(dto.getAutor());
        livro.setIsbn(dto.getIsbn());
        livro.setAnoPublicacao(dto.getAnoPublicacao());
        livro.setQuantidadeExemplares(dto.getQuantidadeExemplares());
        livro.setCategoria(categoria);
    }
}
