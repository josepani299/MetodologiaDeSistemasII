package MetodologiaDeSistema.Proyecto.feature.kits.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import MetodologiaDeSistema.Proyecto.feature.Producto.Models.Producto;
import MetodologiaDeSistema.Proyecto.feature.Producto.Repository.ProductoRepository;
import MetodologiaDeSistema.Proyecto.feature.kits.models.Kit;
import MetodologiaDeSistema.Proyecto.feature.kits.models.KitProducto;
import MetodologiaDeSistema.Proyecto.feature.kits.repository.KitProductoRepository;
import MetodologiaDeSistema.Proyecto.feature.kits.repository.KitRepository;

@Service
public class KitService {

    @Autowired
    private KitRepository kitRepository;

    @Autowired
    private KitProductoRepository kitProductoRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Transactional
    public Kit crearKit(String nombre, String descripcion, Double precio) {
        Kit kit = new Kit();
        kit.setNombre(nombre);
        kit.setDescripcion(descripcion);
        kit.setPrecio(precio);
        kit.setEstado(true);
        return kitRepository.save(kit);
    }

    @Transactional
    public KitProducto agregarProductoAKit(Long kitId, Long productoId, Integer cantidad) {
        Kit kit = kitRepository.findById(kitId)
            .orElseThrow(() -> new RuntimeException("Kit no encontrado con ID: " + kitId));

        Producto producto = productoRepository.findById(productoId)
            .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + productoId));

        Optional<KitProducto> existente = kitProductoRepository.findByKitIdAndProductoId(kitId, productoId);
        if (existente.isPresent()) {
            throw new RuntimeException("El producto con ID " + productoId + " ya existe en este kit");
        }

        KitProducto kitProducto = new KitProducto();
        kitProducto.setKit(kit);
        kitProducto.setProductoId(productoId);
        kitProducto.setCantidad(cantidad);

        return kitProductoRepository.save(kitProducto);
    }

    public Kit obtenerKitPorId(Long id) {
        return kitRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Kit no encontrado con ID: " + id));
    }

    public List<Kit> obtenerKitsActivos() {
        return kitRepository.findByEstadoTrue();
    }

    public List<Kit> obtenerTodosLosKits() {
        return kitRepository.findAll();
    }

    public Integer obtenerStockDisponibleKit(Long kitId) {
        Kit kit = obtenerKitPorId(kitId);
        List<KitProducto> productos = kit.getProductos();

        if (productos.isEmpty()) {
            return 0;
        }

        Integer stockMinimo = Integer.MAX_VALUE;

        for (KitProducto kp : productos) {
            Producto producto = productoRepository.findById(kp.getProductoId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
            
            Integer stockDisponible = producto.getStockActual() / kp.getCantidad();
            
            if (stockDisponible < stockMinimo) {
                stockMinimo = stockDisponible;
            }
        }

        return stockMinimo == Integer.MAX_VALUE ? 0 : stockMinimo;
    }

    @Transactional
    public Kit actualizarKit(Long id, String nombre, String descripcion, Double precio) {
        Kit kit = obtenerKitPorId(id);
        
        if (nombre != null) kit.setNombre(nombre);
        if (descripcion != null) kit.setDescripcion(descripcion);
        if (precio != null) kit.setPrecio(precio);
        return kitRepository.save(kit);
    }

    @Transactional
    public Kit darDeBajaKit(Long id) {
        Kit kit = obtenerKitPorId(id);
        kit.setEstado(false);
        return kitRepository.save(kit);
    }

    @Transactional
    public void eliminarProductoDelKit(Long kitId, Long kitProductoId) {
        KitProducto kp = kitProductoRepository.findById(kitProductoId)
            .orElseThrow(() -> new RuntimeException("KitProducto no encontrado"));
        
        if (!kp.getKit().getId().equals(kitId)) {
            throw new RuntimeException("El producto no pertenece a este kit");
        }
        
        kitProductoRepository.deleteById(kitProductoId);
    }

    public List<KitProducto> obtenerProductosDelKit(Long kitId) {
        return kitProductoRepository.findByKitId(kitId);
    }
}