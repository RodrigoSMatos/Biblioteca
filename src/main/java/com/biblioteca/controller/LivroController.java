package com.biblioteca.controller;

import com.biblioteca.model.Livro;
import com.biblioteca.service.LivroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/livros")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class LivroController {

    private final LivroService livroService;

    // 1) Criar livro
    @PostMapping
    public ResponseEntity<Livro> criar(@Valid @RequestBody Livro livro) {
        Livro criado = livroService.criar(livro);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    // 2) Listar todos
    @GetMapping
    public ResponseEntity<List<Livro>> listarTodos() {
        return ResponseEntity.ok(livroService.listarTodos());
    }

    // 3) Buscar por id
    @GetMapping("/{id}")
    public ResponseEntity<Livro> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(livroService.buscarPorId(id));
    }

    // 4) Atualizar
    @PutMapping("/{id}")
    public ResponseEntity<Livro> atualizar(@PathVariable Long id,
                                           @Valid @RequestBody Livro livro) {
        return ResponseEntity.ok(livroService.atualizar(id, livro));
    }

    // 5) Deletar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        livroService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    // 6) Buscar por autor (exemplo de rota com filtro)
    @GetMapping("/autor")
    public ResponseEntity<List<Livro>> buscarPorAutor(@RequestParam String nome) {
        return ResponseEntity.ok(livroService.buscarPorAutor(nome));
    }

    // 7) Buscar por título (rota extra opcional)
    @GetMapping("/titulo")
    public ResponseEntity<List<Livro>> buscarPorTitulo(@RequestParam String titulo) {
        return ResponseEntity.ok(livroService.buscarPorTitulo(titulo));
    }
}
