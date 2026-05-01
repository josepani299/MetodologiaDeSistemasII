package MetodologiaDeSistema.Proyecto.feature.Cliente.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Direccion {
    
    private String pais;
    private String provincia;
    private String localidad;
    private String calle;
    private int numeroCalle;
    private String depto;


}
