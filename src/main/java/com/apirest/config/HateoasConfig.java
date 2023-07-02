package com.apirest.config;

import com.apirest.controller.FlightController;
import com.apirest.entity.Flight;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class HateoasConfig implements RepresentationModelAssembler<Flight, EntityModel<Flight>> {


    @Override
    public EntityModel<Flight> toModel(Flight entity) {
        EntityModel<Flight> flightModel = EntityModel.of(entity);
        flightModel.add(linkTo(methodOn(FlightController.class).getOne(entity.getId())).withRel("get-Id"));
        flightModel.add(linkTo(methodOn(FlightController.class).getAll()).withRel(IanaLinkRelations.COLLECTION));
        flightModel.add(linkTo(methodOn(FlightController.class).add(entity)).withRel("create-Flight"));
        flightModel.add(linkTo(methodOn(FlightController.class).updateFlight(entity.getId(), entity)).withRel("update-Flight")); // Corrección aquí

        return flightModel;
    }
}
