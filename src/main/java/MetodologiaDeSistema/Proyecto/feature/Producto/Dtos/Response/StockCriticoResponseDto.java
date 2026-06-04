package MetodologiaDeSistema.Proyecto.feature.Producto.Dtos.Response;

public class StockCriticoResponseDto {

    private Long id;
    private String nombre;
    private String marca;
    private Integer stockActual;
    private Integer stockMinimo;
    private Long vendidosUltimos3Meses;

    public StockCriticoResponseDto() {}

    public StockCriticoResponseDto(Long id, String nombre, String marca,
                                   Integer stockActual, Integer stockMinimo,
                                   Long vendidosUltimos3Meses) {
        this.id = id;
        this.nombre = nombre;
        this.marca = marca;
        this.stockActual = stockActual;
        this.stockMinimo = stockMinimo;
        this.vendidosUltimos3Meses = vendidosUltimos3Meses;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    public Integer getStockActual() { return stockActual; }
    public void setStockActual(Integer stockActual) { this.stockActual = stockActual; }

    public Integer getStockMinimo() { return stockMinimo; }
    public void setStockMinimo(Integer stockMinimo) { this.stockMinimo = stockMinimo; }

    public Long getVendidosUltimos3Meses() { return vendidosUltimos3Meses; }
    public void setVendidosUltimos3Meses(Long v) { this.vendidosUltimos3Meses = v; }
}