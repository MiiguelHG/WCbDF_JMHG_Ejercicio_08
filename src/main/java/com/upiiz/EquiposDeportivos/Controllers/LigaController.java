package com.upiiz.EquiposDeportivos.Controllers;

import com.upiiz.EquiposDeportivos.Entities.CustomResponse;
import com.upiiz.EquiposDeportivos.Entities.Liga;
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
@RequestMapping("api/v1/ligas")
@Tag(name = "Ligas", description = "API para administrar las ligas de los equipos")
public class LigaController {
    @Autowired
    private LigaService ligaService;

    // Implementar los métodos de la API REST
    // POST
    @PostMapping
    public ResponseEntity<CustomResponse<Liga>> saveLiga(@RequestBody Liga liga){
        Link allEntrenadoresLinks = linkTo(EntrenadorController.class).withSelfRel();
        List<Link> links = List.of(allEntrenadoresLinks);

        try {
            Liga nuevaLiga = ligaService.save(liga);

            if(nuevaLiga == null){
                CustomResponse<Liga> response = new CustomResponse<>(0, "Error al guardar la liga", null, links);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }

            CustomResponse<Liga> response = new CustomResponse<>(1, "Liga guardada con éxito", nuevaLiga, links);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }
        catch (Exception e) {
            CustomResponse<Liga> response = new CustomResponse<>(0, "Error al guardar la liga", null, links);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // GET
    @GetMapping
    public ResponseEntity<CustomResponse<List<Liga>>> findAllLigas(){
        Link allEntrenadoresLinks = linkTo(EntrenadorController.class).withSelfRel();
        List<Link> links = List.of(allEntrenadoresLinks);

        try{
            List<Liga> ligas = ligaService.findAll();

            if(ligas.isEmpty()){
                CustomResponse<List<Liga>> response = new CustomResponse<>(0, "No hay ligas registradas", null, links);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            CustomResponse<List<Liga>> response = new CustomResponse<>(1, "Ligas encontradas", ligas, links);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        catch (Exception e){
            CustomResponse<List<Liga>> response = new CustomResponse<>(0, "Error al buscar las ligas", null, links);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // GET by Id
    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse<Liga>> findLigaById(@PathVariable Long id){
        Link allEntrenadoresLinks = linkTo(EntrenadorController.class).withSelfRel();
        List<Link> links = List.of(allEntrenadoresLinks);

        try{
            Liga liga = ligaService.findById(id);

            if(liga == null){
                CustomResponse<Liga> response = new CustomResponse<>(0, "Liga no encontrada", null, links);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            CustomResponse<Liga> response = new CustomResponse<>(1, "Liga encontrada", liga, links);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        catch (Exception e){
            CustomResponse<Liga> response = new CustomResponse<>(0, "Error al buscar la liga", null, links);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // PUT
    @PutMapping("/{id}")
    public ResponseEntity<CustomResponse<Liga>> updateLiga(@PathVariable Long id, @RequestBody Liga liga){
        Link allEntrenadoresLinks = linkTo(EntrenadorController.class).withSelfRel();
        List<Link> links = List.of(allEntrenadoresLinks);

        try{
            liga.setId(id);
            Liga ligaActualizada = ligaService.update(liga);

            if(ligaActualizada == null){
                CustomResponse<Liga> response = new CustomResponse<>(0, "Error al actualizar la liga", null, links);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }

            CustomResponse<Liga> response = new CustomResponse<>(1, "Liga actualizada con éxito", ligaActualizada, links);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        catch (Exception e){
            CustomResponse<Liga> response = new CustomResponse<>(0, "Error al actualizar la liga", null, links);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse<Liga>> deleteLigaById(@PathVariable Long id){
        Link allEntrenadoresLinks = linkTo(EntrenadorController.class).withSelfRel();
        List<Link> links = List.of(allEntrenadoresLinks);

        try{
            boolean eliminado = ligaService.deleteById(id);

            if(!eliminado){
                CustomResponse<Liga> response = new CustomResponse<>(0, "Liga no encontrada", null, links);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }

            CustomResponse<Liga> response = new CustomResponse<>(1, "Liga eliminada con éxito", null, links);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        catch (Exception e){
            CustomResponse<Liga> response = new CustomResponse<>(0, "Error al eliminar la liga", null, links);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
