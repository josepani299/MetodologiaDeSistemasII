package MetodologiaDeSistema.Proyecto.feature.Cliente.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import MetodologiaDeSistema.Proyecto.feature.Cliente.models.Cliente;

@Repository
public interface  ClienteRepository extends JpaRepository<Cliente, Long> {
    
     boolean existsByEmail(String email); // verifica si es que existe el mail en la bd
}
