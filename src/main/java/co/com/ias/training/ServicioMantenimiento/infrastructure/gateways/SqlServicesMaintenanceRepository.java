package co.com.ias.training.ServicioMantenimiento.infrastructure.gateways;

import co.com.ias.training.ServicioMantenimiento.core.domain.Servicio;
import co.com.ias.training.ServicioMantenimiento.core.domain.ServicioId;
import co.com.ias.training.ServicioMantenimiento.core.gateways.ServiceMaintenanceRepository;
import co.com.ias.training.ServicioMantenimiento.infrastructure.gateways.models.ServicioDBO;
import co.com.ias.training.ServicioMantenimiento.shared.domain.PageQuery;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SqlServicesMaintenanceRepository implements ServiceMaintenanceRepository {

    private final DataSource dataSource;

    public SqlServicesMaintenanceRepository(DataSource dataSource) {

        this.dataSource = dataSource;
    }

    @Override
    public List<Servicio> query(PageQuery pageQuery) {

    String sql  ="SELECT * FROM servicesMaintenance LIMIT ? OFFSET ?";

        try(Connection connection  = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement( sql )){

            preparedStatement.setInt(1, pageQuery.getLimit().getValue());
            preparedStatement.setInt(2, pageQuery.getSkip().getValue());

            //El famoso cursor Resultset que recorre cada uno de los registros de la bd
            ResultSet resultSet= preparedStatement.executeQuery();
            List<Servicio> result = new ArrayList<>();
            while(resultSet.next()){
                //Creamos uno nuevo en cada iteración de resultset
                ServicioDBO dbo = new ServicioDBO();
                dbo.setId(resultSet.getString("servcio_id"));
                dbo.setName(resultSet.getString("servicio_name"));
                dbo.setDescription(resultSet.getString("servicio_description"));
                result.add(dbo.toDomain());
            }

            resultSet.close();

            return result;
        }catch(SQLException exception){
            throw new RuntimeException("Error querying database", exception);
        }
    }

    @Override
    public Optional<Servicio> get(ServicioId servicioId) {

        String sql="SELECT * from servicesMaintenance WHERE servicioId= ?";
        try(Connection connection  = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement( sql )){

            preparedStatement.setString(1, servicioId.toString());

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.first()){
                ServicioDBO productDBO = ServicioDBO.fromResultSet(resultSet);
                Servicio domainServicio = productDBO.toDomain();
                return Optional.of(domainServicio);
            }else{
                return Optional.empty();
            }
        }catch (SQLException exception){
            throw new RuntimeException("Error querying database", exception);
        }
    }

    @Override
    public void store(Servicio servicio) {

        String sql="INSERT INTO serviciosMaintenance (product_id, product_name, product_description) VALUES (?, ?, ?)";
        try(Connection connection  = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement( sql )){

            preparedStatement.setString(1, servicio.getId().toString());
            preparedStatement.setString(2, servicio.getName().toString());
            preparedStatement.setString(3, servicio.getDescription().toString());

            preparedStatement.executeUpdate();

        }catch (SQLException exception){
            throw new RuntimeException("Error querying database", exception);
        }

    }
}
