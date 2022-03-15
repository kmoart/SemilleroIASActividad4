package co.com.ias.training.ServicioMantenimiento.core.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class ServicioIdTest {

    //Se valida o prueba un dato nulo
    @Test
    @DisplayName("Null productId should throw an error")
    void null_servicio_id_tests(){

        //Arrange o preparación del escenario
        String unsafeId = null;

        //Assert o verficación
        // Assertthrows recibe la clase con la excepción que estamos esperando para este caso NullPointerException.class
        // Tambien debemos enviarle el cuerpo de ejecución o la actuación anterior
        assertThrows(NullPointerException.class, () ->{
            //Actuación o Act
            new ServicioId(unsafeId);
        });

    }

    //Se valida por ejemplo una cadena de texto vacía
    @Test
    @DisplayName("Invalid productId should throw an error")
    void invalid_product_id_tests(){

        //Arrange o preparación del escenario
        String unsafeId = "";

        //Assert o verficación
        // Assertthrows recibe la clase con la excepción que estamos esperando para este caso NullPointerException.class
        // Tambien debemos enviarle el cuerpo de ejecución o la actuación anterior
        assertThrows(IllegalArgumentException.class, () ->{
            //Actuación o Act
            new ServicioId(unsafeId);
        });

    }

    @Test
    @DisplayName("A valid Id should not throw an error")
    void valid_id_test(){
        String validId = UUID.randomUUID().toString() ;
        //Verificamos que no lanza ninguna excepción
        assertDoesNotThrow(() ->{
            new ServicioId(validId);
        });
    }
}
