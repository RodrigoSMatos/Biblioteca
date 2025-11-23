package com.biblioteca.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import lombok.*;

@Entity
@Table(name = "livros")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @NotBlank(message = "O título é obrigatório")
    @Size(max = 255, message = "O título deve ter no máximo 255 caracteres")
    @Column(nullable = false, length = 255)
    private String titulo;

    @NotBlank(message = "O autor é obrigatório")
    @Size(max = 255, message = "O autor deve ter no máximo 255 caracteres")
    @Column(nullable = false, length = 255)
    private String autor;

    @NotBlank(message = "O ISBN é obrigatório")
    @Size(max = 20, message = "O ISBN deve ter no máximo 20 caracteres")
    @Column(nullable = false, length = 20, unique = true)
    private String isbn;

    @NotNull(message = "O ano de publicação é obrigatório")
    @Min(value = 1500, message = "Ano de publicação inválido")
    @Max(value = 2100, message = "Ano de publicação inválido")
    @Column(nullable = false)
    private Integer anoPublicacao;

    @NotNull(message = "A quantidade de exemplares é obrigatória")
    @Min(value = 0, message = "A quantidade de exemplares não pode ser negativa")
    @Column(nullable = false)
    private Integer quantidadeExemplares;

    // 🔥 NOVO: relacionamento ManyToOne com Categoria
    @NotNull(message = "A categoria é obrigatória")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;
}