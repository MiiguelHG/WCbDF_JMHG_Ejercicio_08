package com.upiiz.EquiposDeportivos.Controllers;

import com.upiiz.EquiposDeportivos.Entities.*;
import com.upiiz.EquiposDeportivos.Services.CompetenciaService;
import com.upiiz.EquiposDeportivos.Services.EntrenadorService;
import com.upiiz.EquiposDeportivos.Services.EquipoService;
import com.upiiz.EquiposDeportivos.Services.LigaService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping("api/v1/equipos")
@Tag(name = "Equipos", description = "API para administrar los equipos y sus competencias")
public class EquipoController {
    @Autowired
    private EquipoService equipoService;

    @Autowired
    private EntrenadorService entrenadorService;

    @Autowired
    private LigaService ligaService;

    @Autowired
    private CompetenciaService competenciaService;

    // Implementar los métodos de la API REST
    // POST
    @PostMapping("/ligas/{liga_id}/entrenadores/{entrenador_id}")
    public ResponseEntity<CustomResponse<Equipo>> saveEquipo(@RequestBody Equipo equipo, @PathVariable Long liga_id, @PathVariable Long entrenador_id){
        Link allEntrenadoresLinks = linkTo(EntrenadorController.class).withSelfRel();
        List<Link> links = List.of(allEntrenadoresLinks);

        try {
            //Buscar el entrenador
            Entrenador entrenador = entrenadorService.findById(entrenador_id);
            if(entrenador == null){
                CustomResponse<Equipo> response = new CustomResponse<>(0, "Entrenador no encontrado", null, links);
                return ResponseEntity.status(404).body(response);
            }

            // Buscar la liga
            Liga liga = ligaService.findById(liga_id);
            if (liga == null){
                CustomResponse<Equipo> response = new CustomResponse<>(0, "Liga no encontrada", null, links);
                return ResponseEntity.status(404).body(response);
            }

            // Agrergar el entrenador y la liga al equipo
            equipo.setEntrenador(entrenador);
            equipo.setLiga(liga);

            // Guardar el equipo
            Equipo nuevoEquipo = equipoService.save(equipo);
            if (nuevoEquipo == null){
                CustomResponse<Equipo> response = new CustomResponse<>(0, "Error al guardar el equipo", null, links);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }

            CustomResponse<Equipo> response = new CustomResponse<>(1, "Equipo guardado con éxito", nuevoEquipo, links);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }
        catch (Exception e) {
            CustomResponse<Equipo> response = new CustomResponse<>(0, "Error al guardar el equipo", null, links);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // GET
    @GetMapping
    public ResponseEntity<CustomResponse<List<Equipo>>> findAllEquipos(){
        Link allEntrenadoresLinks = linkTo(EntrenadorController.class).withSelfRel();
        List<Link> links = List.of(allEntrenadoresLinks);

        try{
            List<Equipo> equipos = equipoService.findAll();

            if(equipos.isEmpty()){
                CustomResponse<List<Equipo>> response = new CustomResponse<>(0, "No hay equipos registrados", null, links);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            CustomResponse<List<Equipo>> response = new CustomResponse<>(1, "Equipos encontrados", equipos, links);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        catch (Exception e){
            CustomResponse<List<Equipo>> response = new CustomResponse<>(0, "Error al buscar los equipos", null, links);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // GET by Id
    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse<Equipo>> findEquipoById(@PathVariable Long id){
        Link allEntrenadoresLinks = linkTo(EntrenadorController.class).withSelfRel();
        List<Link> links = List.of(allEntrenadoresLinks);

        try{
            Equipo equipo = equipoService.findById(id);

            if(equipo == null){
                CustomResponse<Equipo> response = new CustomResponse<>(0, "Equipo no encontrado", null, links);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            CustomResponse<Equipo> response = new CustomResponse<>(1, "Equipo encontrado", equipo, links);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        catch (Exception e){
            CustomResponse<Equipo> response = new CustomResponse<>(0, "Error al buscar el equipo", null, links);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // PUT
    @PutMapping("/{id}/ligas/{liga_id}/entrenadores/{entrenador_id}")
    public ResponseEntity<CustomResponse<Equipo>> updateEquipo(@RequestBody Equipo equipo, @PathVariable Long id, @PathVariable Long liga_id, @PathVariable Long entrenador_id){
        Link allEntrenadoresLinks = linkTo(EntrenadorController.class).withSelfRel();
        List<Link> links = List.of(allEntrenadoresLinks);

        try {
            //Buscar el entrenador
            Entrenador entrenador = entrenadorService.findById(entrenador_id);
            if(entrenador == null){
                CustomResponse<Equipo> response = new CustomResponse<>(0, "Entrenador no encontrado", null, links);
                return ResponseEntity.status(404).body(response);
            }

            // Buscar la liga
            Liga liga = ligaService.findById(liga_id);
            if (liga == null){
                CustomResponse<Equipo> response = new CustomResponse<>(0, "Liga no encontrada", null, links);
                return ResponseEntity.status(404).body(response);
            }

            // Buscar el equipo
            Equipo equipoActual = equipoService.findById(id);
            if (equipoActual == null){
                CustomResponse<Equipo> response = new CustomResponse<>(0, "Equipo no encontrado", null, links);
                return ResponseEntity.status(404).body(response);
            }

            // Actualizar el equipo
            equipo.setId(id);
            equipo.setEntrenador(entrenador);
            equipo.setLiga(liga);
            Equipo equipoActualizado = equipoService.update(equipo);
            if (equipoActualizado == null){
                CustomResponse<Equipo> response = new CustomResponse<>(0, "Error al actualizar el equipo", null, links);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }

            CustomResponse<Equipo> response = new CustomResponse<>(1, "Equipo actualizado con éxito", equipoActualizado, links);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        catch (Exception e) {
            CustomResponse<Equipo> response = new CustomResponse<>(0, "Error al actualizar el equipo", null, links);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse<Equipo>> deleteEquipoById(@PathVariable Long id){
        Link allEntrenadoresLinks = linkTo(EntrenadorController.class).withSelfRel();
        List<Link> links = List.of(allEntrenadoresLinks);

        try{
            boolean deleted = equipoService.deleteById(id);

            if(!deleted){
                CustomResponse<Equipo> response = new CustomResponse<>(0, "Equipo no encontrado", null, links);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            CustomResponse<Equipo> response = new CustomResponse<>(1, "Equipo eliminado", null, links);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        catch (Exception e){
            CustomResponse<Equipo> response = new CustomResponse<>(0, "Error al eliminar el equipo", null, links);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // src/main/java/com/upiiz/EquiposDeportivos/Controllers/EquipoController.java
    @PutMapping("/{equipo_id}/competencias/{competencia_id}")
    public ResponseEntity<CustomResponse<Equipo>> addCompetenciaToEquipo(@PathVariable Long equipo_id, @PathVariable Long competencia_id) {
        Link allEntrenadoresLinks = linkTo(EntrenadorController.class).withSelfRel();
        List<Link> links = List.of(allEntrenadoresLinks);

        try {
            // Buscar el equipo
            Equipo equipo = equipoService.findById(equipo_id);
            if (equipo == null) {
                CustomResponse<Equipo> response = new CustomResponse<>(0, "Equipo no encontrado", null, links);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            // Buscar la competencia
            Competencia competencia = competenciaService.findById(competencia_id);
            if (competencia == null) {
                CustomResponse<Equipo> response = new CustomResponse<>(0, "Competencia no encontrada", null, links);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            // Agregar la competencia al equipo
            equipo.getCompetencias().add(competencia);
            Equipo equipoActualizado = equipoService.update(equipo);

            CustomResponse<Equipo> response = new CustomResponse<>(1, "Competencia agregada al equipo con éxito", equipoActualizado, links);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            CustomResponse<Equipo> response = new CustomResponse<>(0, "Error al agregar la competencia al equipo", null, links);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // src/main/java/com/upiiz/EquiposDeportivos/Controllers/EquipoController.java
    @DeleteMapping("/{equipo_id}/competencias/{competencia_id}")
    public ResponseEntity<CustomResponse<Equipo>> removeCompetenciaFromEquipo(@PathVariable Long equipo_id, @PathVariable Long competencia_id) {
        Link allEntrenadoresLinks = linkTo(EntrenadorController.class).withSelfRel();
        List<Link> links = List.of(allEntrenadoresLinks);

        try {
            // Buscar el equipo
            Equipo equipo = equipoService.findById(equipo_id);
            if (equipo == null) {
                CustomResponse<Equipo> response = new CustomResponse<>(0, "Equipo no encontrado", null, links);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            // Buscar la competencia
            Competencia competencia = competenciaService.findById(competencia_id);
            if (competencia == null) {
                CustomResponse<Equipo> response = new CustomResponse<>(0, "Competencia no encontrada", null, links);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            // Eliminar la competencia del equipo
            equipo.getCompetencias().remove(competencia);
            Equipo equipoActualizado = equipoService.update(equipo);

            CustomResponse<Equipo> response = new CustomResponse<>(1, "Competencia eliminada del equipo con éxito", equipoActualizado, links);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            CustomResponse<Equipo> response = new CustomResponse<>(0, "Error al eliminar la competencia del equipo", null, links);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
