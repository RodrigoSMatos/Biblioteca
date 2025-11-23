package com.biblioteca.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "livros")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 150)
    @Column(nullable = false, length = 150)
    private String titulo;

    @NotBlank
    @Size(max = 120)
    @Column(nullable = false, length = 120)
    private String autor;

    @NotBlank
    @Size(max = 20)
    @Column(nullable = false, unique = true, length = 20)
    private String isbn;

    @NotNull
    @Min(1500)
    @Max(2100)
    @Column(nullable = false)
    private Integer anoPublicacao;

    @NotNull
    @Min(0)
    @Column(nullable = false)
    private Integer quantidadeExemplares;
}
