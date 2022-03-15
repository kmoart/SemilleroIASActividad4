package co.com.ias.training.ServicioMantenimiento.infrastructure.services;

import co.com.ias.training.ServicioMantenimiento.core.domain.Servicio;
import co.com.ias.training.ServicioMantenimiento.core.domain.ServicioDescription;
import co.com.ias.training.ServicioMantenimiento.core.domain.ServicioId;
import co.com.ias.training.ServicioMantenimiento.core.domain.ServicioName;
import co.com.ias.training.ServicioMantenimiento.core.gateways.ServiceMaintenanceRepository;
import co.com.ias.training.ServicioMantenimiento.infrastructure.controller.models.ServicioDTO;
import co.com.ias.training.ServicioMantenimiento.infrastructure.controller.models.ServicioInput;
import co.com.ias.training.ServicioMantenimiento.shared.domain.Limit;
import co.com.ias.training.ServicioMantenimiento.shared.domain.PageQuery;
import co.com.ias.training.ServicioMantenimiento.shared.domain.Skip;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ServicesMaintenanceServices {

    public ServicesMaintenanceServices(ServiceMaintenanceRepository serviceMaintenanceRepository) {
        this.serviceMaintenanceRepository = serviceMaintenanceRepository;
    }

    private final ServiceMaintenanceRepository serviceMaintenanceRepository;

    public ServicioDTO createServiceMaintenance(ServicioInput servicioInput){
        String value = UUID.randomUUID().toString();

        try{
            Servicio servicio = new Servicio(
                    new ServicioId(UUID.randomUUID().toString()),
                    new ServicioName(servicioInput.getName()),
                    new ServicioDescription(servicioInput.getDescription())
            );

            serviceMaintenanceRepository.store(servicio);
            return ServicioDTO.fromDomain(servicio);
        }catch(NullPointerException | IllegalArgumentException e){
            //Error de validación de datos
            throw new IllegalArgumentException("Error validating product", e);
        }
    }

    public List<ServicioDTO> queryServiceMaintenance(Integer skip,Integer limit){
        PageQuery pageQuery = new PageQuery(
                new Skip(skip),
                new Limit(limit)
        );

        List<Servicio> servicios = serviceMaintenanceRepository.query(pageQuery);
        List<ServicioDTO> result = new ArrayList<>();

        for (Servicio servicio : servicios) {
            result.add(ServicioDTO.fromDomain(servicio));
        }
        return result;
    }

    public Optional<ServicioDTO> getServicesMaintenance(String servicioId) {
        return serviceMaintenanceRepository.get(new ServicioId(servicioId))
                //Se crea una función que recibe un servicio de mantenimiento que llega desde serviceMaintenanceRepository.get y retorna un servicioDTO
                .map((servicio)->{
                        return ServicioDTO.fromDomain(servicio);
                });
    }
}
