package co.com.ias.training.ServicioMantenimiento.infrastructure.controller;

import co.com.ias.training.ServicioMantenimiento.core.gateways.ServiceMaintenanceRepository;
import co.com.ias.training.ServicioMantenimiento.infrastructure.controller.models.ServicioDTO;
import co.com.ias.training.ServicioMantenimiento.infrastructure.controller.models.ServicioInput;
import co.com.ias.training.ServicioMantenimiento.core.domain.Servicio;
import co.com.ias.training.ServicioMantenimiento.core.domain.ServicioDescription;
import co.com.ias.training.ServicioMantenimiento.core.domain.ServicioId;
import co.com.ias.training.ServicioMantenimiento.core.domain.ServicioName;
import co.com.ias.training.ServicioMantenimiento.infrastructure.services.ServicesMaintenanceServices;
import co.com.ias.training.ServicioMantenimiento.shared.domain.Limit;
import co.com.ias.training.ServicioMantenimiento.shared.domain.PageQuery;
import co.com.ias.training.ServicioMantenimiento.shared.domain.Skip;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class ServicioController {



    private final ServicesMaintenanceServices servicesMaintenanceServices;

    public ServicioController(ServicesMaintenanceServices servicesMaintenanceServices) {
        this.servicesMaintenanceServices = servicesMaintenanceServices;
    }

    @RequestMapping(value = "/services", method = RequestMethod.POST)
    public ServicioDTO createService(
            //@RequestHeader("Authorization") String authorizationToken,
            @RequestBody ServicioInput servicioInput) {

        return servicesMaintenanceServices.createServiceMaintenance(servicioInput);
    }

    @RequestMapping(value = "/services/{id}", method = RequestMethod.GET)
    public Optional<ServicioDTO> getServicio(
            @PathVariable("id") String servicioId
    ) {
        // Representa una fecha local, representa fecha y hora
        //java.time.LocalDateTime
        return servicesMaintenanceServices.getServicesMaintenance(servicioId) ;
    }

    //Metodo para listar los servicios de mantenimiento de manera paginada
    @RequestMapping(value = "/services", method = RequestMethod.GET)
    public List<ServicioDTO> listServices(
            @RequestParam(name = "skip", defaultValue = "0") Integer skip,
            @RequestParam(name = "limit", defaultValue = "50") Integer limit
    ) {

            return servicesMaintenanceServices.queryServiceMaintenance(skip,limit);
    }
}
