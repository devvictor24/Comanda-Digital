package com.ibeus.Comanda.Digital.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ibeus.Comanda.Digital.model.Dish;
import com.ibeus.Comanda.Digital.service.DishService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequestMapping("/api/dishes")
@CrossOrigin(origins = {
        "http://localhost:4200",
        "https://*.onrender.com",
        "https://*.vercel.app",
        "https://*.netlify.app"
})@Tag(name = "Dish Controller", description = "Gerencia os pratos disponíveis no cardápio")
public class DishController {

    @Autowired
    private DishService dishService;

    @Operation(summary = "Listar todos os pratos", description = "Retorna uma lista completa de pratos cadastrados no sistema.")
    @GetMapping
    public List<Dish> getAllDishes() {
        return dishService.findAll();
    }

    @Operation(summary = "Buscar prato por ID", description = "Retorna os detalhes de um prato específico usando seu ID.")
    @GetMapping("/{id}")
    public Dish getDishById(@PathVariable Long id) {
        return dishService.findById(id);
    }

    @Operation(summary = "Criar novo prato", description = "Adiciona um novo prato ao cardápio com as informações fornecidas.")
    @PostMapping
    public Dish createDish(@RequestBody Dish dish) {
        return dishService.create(dish);
    }

    @Operation(summary = "Atualizar prato existente", description = "Atualiza os dados de um prato já cadastrado pelo ID.")
    @PutMapping("/{id}")
    public Dish updateDish(@PathVariable Long id, @RequestBody Dish dish) {
        return dishService.update(id, dish);
    }

    @Operation(summary = "Excluir prato", description = "Remove permanentemente um prato do sistema pelo ID.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDish(@PathVariable Long id) {
        dishService.delete(id);
        return ResponseEntity.noContent().build();
    }
}