package com.example.livraria.dto.Livro;

import com.example.livraria.model.Categorias;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public record LivroRequest(

         @NotNull(message = "O título é obrigatório")
         @Length(min = 3, max = 100, message = "O título deve ter entre 3 e 100 caracteres")
         String titulo,
         @NotNull(message = "A descrição é obrigatória")
         @Length(min = 10, max= 300 , message = "A descrição deve ter no entre 10 e 300 caracteres")
         String descricao,
         @NotNull(message = "A categorias é obrigatória")
         Categorias categorias,
         @Pattern(regexp = "^(97(8|9))?\\d{9}(\\d|X)$", message = "O ISBN deve ser um número válido de 10 ou 13 dígitos")
         String isbn,
         @NotNull(message = "A data de publicação é obrigatória")
         @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
         @PastOrPresent(message = "A data de publicação não pode ser futura")
         LocalDate dataPublicacao
){
}
