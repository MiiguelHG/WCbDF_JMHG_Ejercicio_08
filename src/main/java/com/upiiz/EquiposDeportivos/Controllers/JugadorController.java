package com.upiiz.EquiposDeportivos.Controllers;

import com.upiiz.EquiposDeportivos.Entities.CustomResponse;
import com.upiiz.EquiposDeportivos.Entities.Equipo;
import com.upiiz.EquiposDeportivos.Entities.Jugador;
import com.upiiz.EquiposDeportivos.Services.EquipoService;
import com.upiiz.EquiposDeportivos.Services.JugadorService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping("api/v1/jugadores")
@Tag(name = "Jugadores", description = "API para administrar los jugadores de los equipos")
public class JugadorController {
    @Autowired
    private JugadorService jugadorService;

    @Autowired
    private EquipoService equipoService;

    private static final Logger logger = Logger.getLogger(JugadorController.class.getName());

    // Implementar los métodos de la API REST
    // POST
    @PostMapping("/equipos/{equipo_id}")
    public ResponseEntity<CustomResponse<Jugador>> saveJugador(@RequestBody Jugador jugador, @PathVariable Long equipo_id){
        Link allEntrenadoresLinks = linkTo(EntrenadorController.class).withSelfRel();
        List<Link> links = List.of(allEntrenadoresLinks);

        try {
            //Buscar el equipo
            Equipo equipo = equipoService.findById(equipo_id);
            if(equipo == null){
                CustomResponse<Jugador> response = new CustomResponse<>(0, "Equipo no encontrado", null, links);
                return ResponseEntity.status(404).body(response);
            }

            // Agregar el equipo al jugador
            jugador.setEquipo(equipo);

            // Guardar el jugador
            Jugador nuevoJugador = jugadorService.save(jugador);
            if (nuevoJugador == null){
                CustomResponse<Jugador> response = new CustomResponse<>(0, "Error al guardar el jugador", null, links);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }

            CustomResponse<Jugador> response = new CustomResponse<>(1, "Jugador guardado con éxito", nuevoJugador, links);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }
        catch (Exception e) {
            CustomResponse<Jugador> response = new CustomResponse<>(0, "Error al guardar el jugador", null, links);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // GET
    @GetMapping
    public ResponseEntity<CustomResponse<List<Jugador>>> findAllJugadores(){
        Link allEntrenadoresLinks = linkTo(EntrenadorController.class).withSelfRel();
        List<Link> links = List.of(allEntrenadoresLinks);

        try{
            List<Jugador> jugadores = jugadorService.findAll();

            if(jugadores.isEmpty()){
                CustomResponse<List<Jugador>> response = new CustomResponse<>(0, "No hay jugadores registrados", null, links);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            CustomResponse<List<Jugador>> response = new CustomResponse<>(1, "Jugadores encontrados", jugadores, links);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        catch (Exception e) {
            CustomResponse<List<Jugador>> response = new CustomResponse<>(0, "Error al buscar los jugadores", null, null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // GET by Id
    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse<Jugador>> findJugadorById(@PathVariable Long id){
        Link allEntrenadoresLinks = linkTo(EntrenadorController.class).withSelfRel();
        List<Link> links = List.of(allEntrenadoresLinks);

        try{
            Jugador jugador = jugadorService.findById(id);

            if(jugador == null){
                CustomResponse<Jugador> response = new CustomResponse<>(0, "Jugador no encontrado", null, links);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            CustomResponse<Jugador> response = new CustomResponse<>(1, "Jugador encontrado", jugador, links);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        catch (Exception e) {
            CustomResponse<Jugador> response = new CustomResponse<>(0, "Error al buscar el jugador", null, null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // PUT
    @PutMapping("/{id}/equipos/{equipo_id}")
    public ResponseEntity<CustomResponse<Jugador>> updateJugador(@RequestBody Jugador jugador, @PathVariable Long id, @PathVariable Long equipo_id){
        Link allEntrenadoresLinks = linkTo(EntrenadorController.class).withSelfRel();
        List<Link> links = List.of(allEntrenadoresLinks);

        try {
            //Buscar el equipo
            Equipo equipo = equipoService.findById(equipo_id);
            if(equipo == null){
                CustomResponse<Jugador> response = new CustomResponse<>(0, "Equipo no encontrado", null, links);
                return ResponseEntity.status(404).body(response);
            }

            //Buscar el jugador
            Jugador jugadorActual = jugadorService.findById(id);
            if(jugadorActual == null){
                CustomResponse<Jugador> response = new CustomResponse<>(0, "Jugador no encontrado", null, links);
                return ResponseEntity.status(404).body(response);
            }

            //Establecer el id del jugador
            jugador.setId(id);
            //Actualizar el jugador
            jugador.setEquipo(equipo);

            Jugador jugadorActualizado = jugadorService.update(jugador);
            if (jugadorActualizado == null){
                CustomResponse<Jugador> response = new CustomResponse<>(0, "Error Jugador No actualizado", null, links);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }

            CustomResponse<Jugador> response = new CustomResponse<>(1, "Jugador actualizado con éxito", jugadorActualizado, links);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        catch (Exception e) {
            logger.log(Level.SEVERE, "Error al actualizar el jugador: ", e);
            CustomResponse<Jugador> response = new CustomResponse<>(0, "Error al actualizar el jugador", null, links);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse<Jugador>> deleteJugadorById(@PathVariable Long id){
        Link allEntrenadoresLinks = linkTo(EntrenadorController.class).withSelfRel();
        List<Link> links = List.of(allEntrenadoresLinks);

        try{
            boolean eliminado = jugadorService.deleteById(id);

            if(!eliminado){
                CustomResponse<Jugador> response = new CustomResponse<>(0, "Jugador no encontrado", null, links);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }

            CustomResponse<Jugador> response = new CustomResponse<>(1, "Jugador eliminado con éxito", null, links);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        catch (Exception e) {
            CustomResponse<Jugador> response = new CustomResponse<>(0, "Error al eliminar el jugador", null, links);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
