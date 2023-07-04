package com.apirest.controller;

import com.apirest.config.HateoasConfig;
import com.apirest.entity.Flight;
import com.apirest.service.FlightService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/flights")
public class FlightController {

    private final FlightService flightService;
    private final HateoasConfig hateoasConfig;

    public FlightController(FlightService flightService, HateoasConfig hateoasConfig) {
        this.flightService = flightService;
        this.hateoasConfig = hateoasConfig;
    }


    @GetMapping(value = "/listar", produces = MediaType.APPLICATION_JSON_VALUE)
    public CollectionModel<EntityModel<Flight>> getAll() {
        List<EntityModel<Flight>> vuelos = this.flightService.listFlights().stream()
                .map(hateoasConfig::toModel).collect(Collectors.toList());
        return CollectionModel.of(vuelos,
                linkTo(methodOn(FlightController.class).getAll()).withRel("vuelos")
        );
    }

    @GetMapping(value="/get-id/{id}", produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<EntityModel<Flight>> getOne(@PathVariable("id") Long id) {
        Flight flight = this.flightService.getFlightById(id);
        return new ResponseEntity<>(hateoasConfig.toModel(flight), HttpStatus.OK);
    }

    @PostMapping(value = "/crear", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntityModel<Flight>> add(@RequestBody Flight flight) {
        Flight savedflight = this.flightService.createFlight(flight);
        EntityModel<Flight> model = hateoasConfig.toModel(savedflight);
        return ResponseEntity.created(linkTo(methodOn(FlightController.class).getOne(savedflight.getId())).toUri()).body(model);
    }

    @PutMapping(value="/actualizar/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntityModel<Flight>> updateFlight(@PathVariable Long id, @RequestBody Flight flight) {
        Flight updateFlight = this.flightService.updateFlight(id, flight);
        return new ResponseEntity<>(hateoasConfig.toModel(updateFlight), HttpStatus.OK);
    }


    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> deleteFlight(@PathVariable("id") Long id) {
        this.flightService.deleteFlight(id);
        return new ResponseEntity<>("Vuelo Eliminado !! ", HttpStatus.OK);
    }



}
