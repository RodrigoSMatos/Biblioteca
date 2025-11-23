package com.biblioteca.service;

import com.biblioteca.exception.ResourceNotFoundException;
import com.biblioteca.model.Livro;
import com.biblioteca.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LivroService {

    private final LivroRepository livroRepository;

    public Livro criar(Livro livro) {
        if (livroRepository.existsByIsbn(livro.getIsbn())) {
            throw new IllegalArgumentException("Já existe um livro com esse ISBN.");
        }
        return livroRepository.save(livro);
    }

    public List<Livro> listarTodos() {
        return livroRepository.findAll();
    }

    public Livro buscarPorId(Long id) {
        return livroRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Livro com id " + id + " não encontrado."));
    }

    public Livro atualizar(Long id, Livro dadosAtualizados) {
        Livro existente = buscarPorId(id);

        existente.setTitulo(dadosAtualizados.getTitulo());
        existente.setAutor(dadosAtualizados.getAutor());
        existente.setIsbn(dadosAtualizados.getIsbn());
        existente.setAnoPublicacao(dadosAtualizados.getAnoPublicacao());
        existente.setQuantidadeExemplares(dadosAtualizados.getQuantidadeExemplares());

        return livroRepository.save(existente);
    }

    public void deletar(Long id) {
        Livro existente = buscarPorId(id);
        livroRepository.delete(existente);
    }

    public List<Livro> buscarPorAutor(String autor) {
        return livroRepository.findByAutorContainingIgnoreCase(autor);
    }

    public List<Livro> buscarPorTitulo(String titulo) {
        return livroRepository.findByTituloContainingIgnoreCase(titulo);
    }
}
