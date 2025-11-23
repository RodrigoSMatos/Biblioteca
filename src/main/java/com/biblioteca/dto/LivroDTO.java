package com.biblioteca.dto; // ajuste

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LivroDTO {
    private Long id;
    private String titulo;
    private String autor;
    private String isbn;
    private Integer anoPublicacao;
    private Integer quantidadeExemplares;

    // Categoria como DTO simples
    private CategoriaDTO categoria;
}
