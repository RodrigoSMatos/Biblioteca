package com.biblioteca.service; // ajuste o pacote

import com.biblioteca.dto.LivroCreateUpdateDTO;
import com.biblioteca.dto.LivroDTO;
import com.biblioteca.exception.ResourceNotFoundException;
import com.biblioteca.mapper.LivroMapper;
import com.biblioteca.model.Categoria;
import com.biblioteca.model.Livro;
import com.biblioteca.repository.CategoriaRepository;
import com.biblioteca.repository.LivroRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LivroService {

    private final LivroRepository livroRepository;
    private final CategoriaRepository categoriaRepository;

    public LivroDTO criar(LivroCreateUpdateDTO dto) {
        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada: " + dto.getCategoriaId()));

        Livro livro = LivroMapper.fromCreateDTO(dto, categoria);

        Livro salvo = livroRepository.save(livro);
        return LivroMapper.toDTO(salvo);
    }

    public List<LivroDTO> listar() {
        return livroRepository.findAll()
                .stream()
                .map(LivroMapper::toDTO)
                .toList();
    }

    public LivroDTO buscarPorId(Long id) {
        Livro livro = livroRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Livro não encontrado: " + id));

        return LivroMapper.toDTO(livro);
    }

    public LivroDTO atualizar(Long id, LivroCreateUpdateDTO dto) {
        Livro livro = livroRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Livro não encontrado: " + id));

        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada: " + dto.getCategoriaId()));

        LivroMapper.updateEntity(livro, dto, categoria);

        Livro salvo = livroRepository.save(livro);
        return LivroMapper.toDTO(salvo);
    }

    public void deletar(Long id) {
        Livro livro = livroRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Livro não encontrado: " + id));

        livroRepository.delete(livro);
    }
}
