package co.com.ias.training.ServicioMantenimiento.infrastructure.gateways.models;

import co.com.ias.training.ServicioMantenimiento.core.domain.Servicio;
import co.com.ias.training.ServicioMantenimiento.core.domain.ServicioDescription;
import co.com.ias.training.ServicioMantenimiento.core.domain.ServicioId;
import co.com.ias.training.ServicioMantenimiento.core.domain.ServicioName;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ServicioDBO {

    private String id;
    private String name;
    private String description;

    public ServicioDBO(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public ServicioDBO() {
    }

    public Servicio toDomain(){
        return new Servicio(
                new ServicioId(id),
                new ServicioName(name),
                new ServicioDescription(description)
        );
    }

    //Método desde el dominio
    public static ServicioDBO fromDomain(Servicio servicio){
        return new ServicioDBO(
                servicio.getId().toString(),
                servicio.getName().toString(),
                servicio.getDescription().toString()
        );
    }

    public static ServicioDBO fromResultSet(ResultSet resultSet) throws SQLException {
        ServicioDBO dbo = new ServicioDBO();
        dbo.setId(resultSet.getString("product_id"));
        dbo.setName(resultSet.getString("product_name"));
        dbo.setDescription(resultSet.getString("product_description"));
        return dbo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
