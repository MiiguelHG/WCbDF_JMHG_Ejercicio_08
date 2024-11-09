package com.upiiz.EquiposDeportivos.Controllers;

import com.upiiz.EquiposDeportivos.Entities.Competencia;
import com.upiiz.EquiposDeportivos.Entities.CustomResponse;
import com.upiiz.EquiposDeportivos.Services.CompetenciaService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping("api/v1/competencias")
@Tag(name = "Competencias", description = "API para administrar las competencias de los equipos")
public class CompetenciaController {
    @Autowired
    private CompetenciaService competenciaService;

    // Implementar los métodos de la API REST
    // POST
    @PostMapping
    public ResponseEntity<CustomResponse<Competencia>> saveCompetencia(@RequestBody Competencia competencia){
        Link allEntrenadoresLinks = linkTo(EntrenadorController.class).withSelfRel();
        List<Link> links = List.of(allEntrenadoresLinks);

        try {
            Competencia nuevaCompetencia = competenciaService.save(competencia);

            if(nuevaCompetencia == null){
                CustomResponse<Competencia> response = new CustomResponse<>(0, "Error al guardar la competencia", null, links);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }

            CustomResponse<Competencia> response = new CustomResponse<>(1, "Competencia guardada con éxito", nuevaCompetencia, links);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }
        catch (Exception e) {
            CustomResponse<Competencia> response = new CustomResponse<>(0, "Error al guardar la competencia", null, links);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // GET
    @GetMapping
    public ResponseEntity<CustomResponse<List<Competencia>>> findAllCompetencias(){
        Link allEntrenadoresLinks = linkTo(EntrenadorController.class).withSelfRel();
        List<Link> links = List.of(allEntrenadoresLinks);

        try{
            List<Competencia> competencias = competenciaService.findAll();

            if(competencias.isEmpty()){
                CustomResponse<List<Competencia>> response = new CustomResponse<>(0, "No hay competencias registradas", null, links);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            CustomResponse<List<Competencia>> response = new CustomResponse<>(1, "Competencias encontradas", competencias, links);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        catch (Exception e) {
            CustomResponse<List<Competencia>> response = new CustomResponse<>(0, "Error al buscar las competencias", null, links);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // GET by id
    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse<Competencia>> findCompetenciaById(@PathVariable Long id){
        Link allEntrenadoresLinks = linkTo(EntrenadorController.class).withSelfRel();
        List<Link> links = List.of(allEntrenadoresLinks);

        try {
            Competencia competencia = competenciaService.findById(id);

            if(competencia == null){
                CustomResponse<Competencia> response = new CustomResponse<>(0, "Competencia no encontrada", null, links);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            CustomResponse<Competencia> response = new CustomResponse<>(1, "Competencia encontrada", competencia, links);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        catch (Exception e) {
            CustomResponse<Competencia> response = new CustomResponse<>(0, "Error al buscar la competencia", null, links);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // PUT
    @PutMapping("/{id}")
    public ResponseEntity<CustomResponse<Competencia>> updateCompetencia(@PathVariable Long id, @RequestBody Competencia competencia){
        Link allEntrenadoresLinks = linkTo(EntrenadorController.class).withSelfRel();
        List<Link> links = List.of(allEntrenadoresLinks);

        try {
            competencia.setId(id);
            Competencia competenciaActualizada = competenciaService.update(competencia);

            if(competenciaActualizada == null){
                CustomResponse<Competencia> response = new CustomResponse<>(0, "Error al actualizar la competencia", null, links);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }

            CustomResponse<Competencia> response = new CustomResponse<>(1, "Competencia actualizada con éxito", competenciaActualizada, links);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        catch (Exception e) {
            CustomResponse<Competencia> response = new CustomResponse<>(0, "Error al actualizar la competencia", null, links);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse<Competencia>> deleteCompetencia(@PathVariable Long id){
        Link allEntrenadoresLinks = linkTo(EntrenadorController.class).withSelfRel();
        List<Link> links = List.of(allEntrenadoresLinks);

        try {
            boolean eliminado = competenciaService.deleteById(id);

            if(!eliminado){
                CustomResponse<Competencia> response = new CustomResponse<>(0, "Error al eliminar la competencia", null, links);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }

            CustomResponse<Competencia> response = new CustomResponse<>(1, "Competencia eliminada con éxito", null, links);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        catch (Exception e) {
            CustomResponse<Competencia> response = new CustomResponse<>(0, "Error al eliminar la competencia", null, links);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
