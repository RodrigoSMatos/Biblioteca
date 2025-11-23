package com.biblioteca.dto; // ajuste

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LivroCreateUpdateDTO {

    @NotBlank(message = "O título é obrigatório")
    @Size(max = 255)
    private String titulo;

    @NotBlank(message = "O autor é obrigatório")
    @Size(max = 255)
    private String autor;

    @NotBlank(message = "O ISBN é obrigatório")
    @Size(max = 20)
    private String isbn;

    @NotNull(message = "O ano de publicação é obrigatório")
    @Min(1500)
    @Max(2100)
    private Integer anoPublicacao;

    @NotNull(message = "A quantidade de exemplares é obrigatória")
    @Min(0)
    private Integer quantidadeExemplares;

    @NotNull(message = "A categoria é obrigatória")
    private Long categoriaId;
}
