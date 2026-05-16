package MetodologiaDeSistema.Proyecto.feature.Producto.Dtos.Request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoCreateDto {
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "La marca es obligatoria")
    private String marca;

    @NotNull
    @Positive(message = "El precio debe ser positivo")
    private Double precio;

    @NotNull
    @Positive(message = "El stock actual debe ser positivo")
    private Integer stockActual;

    @NotNull
    @Positive(message = "El stock minimo debe ser positivo")
    private Integer stockMinimo;
}
