package com.apirest.service;

import com.apirest.entity.Flight;
import com.apirest.repository.FlightRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FlightService {

    private final FlightRepository flightRepository;

    public FlightService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    public List<Flight> listFlights() {
        return this.flightRepository.findAll();
    }

    public Flight createFlight(Flight flight) {
        return this.flightRepository.save(flight);
    }

    public Flight getFlightById(Long id) {
        Optional<Flight> optionalFlight = this.flightRepository.findById(id);
        return optionalFlight.orElse(null);
    }

    public Flight updateFlight(Long id, Flight updatedFlight) {
        Optional<Flight> optionalFlight = this.flightRepository.findById(id);
        if (optionalFlight.isPresent()) {
            Flight flight = optionalFlight.get();
            // Actualizar los campos necesarios del vuelo
            flight.setNumeroVuelo(updatedFlight.getNumeroVuelo());
            flight.setAerolinea(updatedFlight.getAerolinea());
            flight.setAeropuertoSalida(updatedFlight.getAeropuertoSalida());
            flight.setPrecio(updatedFlight.getPrecio());
            flight.setAeropuertoLlegada(updatedFlight.getAeropuertoLlegada());
            return flightRepository.save(flight);
        }
        return null;
    }

    public void deleteFlight(Long id) {
        this.flightRepository.deleteById(id);
    }

}
