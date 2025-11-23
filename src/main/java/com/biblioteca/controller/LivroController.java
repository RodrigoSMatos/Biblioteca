package com.biblioteca.controller; // ajuste o pacote

import com.biblioteca.dto.LivroCreateUpdateDTO;
import com.biblioteca.dto.LivroDTO;
import com.biblioteca.service.LivroService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/livros")
@RequiredArgsConstructor
public class LivroController {

    private final LivroService livroService;

    @PostMapping
    public ResponseEntity<LivroDTO> criar(@Valid @RequestBody LivroCreateUpdateDTO dto) {
        LivroDTO criado = livroService.criar(dto);
        return ResponseEntity.created(URI.create("/livros/" + criado.getId())).body(criado);
    }

    @GetMapping
    public ResponseEntity<List<LivroDTO>> listar() {
        return ResponseEntity.ok(livroService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LivroDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(livroService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LivroDTO> atualizar(@PathVariable Long id,
                                              @Valid @RequestBody LivroCreateUpdateDTO dto) {
        return ResponseEntity.ok(livroService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        livroService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
