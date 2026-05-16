/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MetodologiaDeSistema.Proyecto.feature.Producto.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import MetodologiaDeSistema.Proyecto.feature.Producto.Models.Producto;

/**
 *
 * @author EFRAIN
 */
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    List<Producto> findByNombreContainingIgnoreCaseAndActivoTrue(String nombre);
    List<Producto> findByActivoTrue();
    default boolean existeEnKitActivo(Long productoId) {
    return false;
}
}