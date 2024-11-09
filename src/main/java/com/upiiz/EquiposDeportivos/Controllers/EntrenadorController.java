package com.upiiz.EquiposDeportivos.Controllers;

import com.upiiz.EquiposDeportivos.Entities.CustomResponse;
import com.upiiz.EquiposDeportivos.Entities.Entrenador;
import com.upiiz.EquiposDeportivos.Services.EntrenadorService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/entrenadores")
@Tag(name = "Entrenadores", description = "API para administrar los entrenadores de los equipos")
public class EntrenadorController {
    @Autowired
    private EntrenadorService entrenadorService;

    // Implementar los métodos de la API REST
    //POST
    @PostMapping
    public ResponseEntity<CustomResponse<Entrenador>> saveEntrenador(@RequestBody Entrenador entrenador){
        Link allEntrenadoresLinks = linkTo(EntrenadorController.class).withSelfRel();
        List<Link> links = List.of(allEntrenadoresLinks);

        try {
            Entrenador nuevoEntrenador = entrenadorService.save(entrenador);

            if(nuevoEntrenador == null){
                CustomResponse<Entrenador> response = new CustomResponse<>(0, "Error al guardar el entrenador", null, links);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }

            CustomResponse<Entrenador> response = new CustomResponse<>(1, "Entrenador guardado con éxito", nuevoEntrenador, links);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }
        catch (Exception e){
            CustomResponse<Entrenador> response = new CustomResponse<>(0, "Error al guardar el entrenador", null, links);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    //GET
    @GetMapping
    public ResponseEntity<CustomResponse<List<Entrenador>>> findAllEntrenadores(){
        Link allEntrenadoresLinks = linkTo(EntrenadorController.class).withSelfRel();
        List<Link> links = List.of(allEntrenadoresLinks);

        try{
            List<Entrenador> entrenadores = entrenadorService.findAll();

            if(entrenadores.isEmpty()){
                CustomResponse<List<Entrenador>> response = new CustomResponse<>(0, "No hay entrenadores registrados", null, links);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            CustomResponse<List<Entrenador>> response = new CustomResponse<>(1, "Entrenadores encontrados", entrenadores, links);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        catch (Exception e){
            CustomResponse<List<Entrenador>> response = new CustomResponse<>(0, "Error al buscar los entrenadores", null, links);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    //GET por Id
    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse<Entrenador>> findEntrenadorById(@PathVariable("id") Long id){
        Link allEntrenadoresLinks = linkTo(EntrenadorController.class).withSelfRel();
        List<Link> links = List.of(allEntrenadoresLinks);

        try{
            Entrenador entrenador = entrenadorService.findById(id);

            if(entrenador == null){
                CustomResponse<Entrenador> response = new CustomResponse<>(0, "Entrenador no encontrado", null, links);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            CustomResponse<Entrenador> response = new CustomResponse<>(1, "Entrenador encontrado", entrenador, links);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        catch (Exception e){
            CustomResponse<Entrenador> response = new CustomResponse<>(0, "Error al buscar el entrenador", null, links);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    //PUT
    @PutMapping("/{id}")
    public ResponseEntity<CustomResponse<Entrenador>> updateEntrenador(@PathVariable("id") Long id, @RequestBody Entrenador entrenador){
        Link allEntrenadoresLinks = linkTo(EntrenadorController.class).withSelfRel();
        List<Link> links = List.of(allEntrenadoresLinks);

        try{
            entrenador.setId(id);
            Entrenador entrenadorActualizado = entrenadorService.update(entrenador);

            if(entrenadorActualizado == null){
                CustomResponse<Entrenador> response = new CustomResponse<>(0, "Entrenador no encontrado", null, links);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            CustomResponse<Entrenador> response = new CustomResponse<>(1, "Entrenador actualizado con éxito", entrenadorActualizado, links);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        catch (Exception e){
            CustomResponse<Entrenador> response = new CustomResponse<>(0, "Error al actualizar el entrenador", null, links);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    //Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse<Entrenador>> deleteEntrenadorById(@PathVariable("id") Long id){
        Link allEntrenadoresLinks = linkTo(EntrenadorController.class).withSelfRel();
        List<Link> links = List.of(allEntrenadoresLinks);

        try{
            boolean isDeleted = entrenadorService.deleteById(id);

            if(!isDeleted){
                CustomResponse<Entrenador> response = new CustomResponse<>(0, "Entrenador no encontrado", null, links);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            CustomResponse<Entrenador> response = new CustomResponse<>(1, "Entrenador eliminado con éxito", null, links);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        catch (Exception e){
            CustomResponse<Entrenador> response = new CustomResponse<>(0, "Error al eliminar el entrenador", null, links);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
