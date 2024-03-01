package springboot.microservicio.taller.empresa.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import springboot.microservicio.taller.empresa.api.models.Empleado;

import java.util.List;


/** Clase IEmpleadoRepository */
@Repository
public interface IEmpleadoRepository extends JpaRepository<Empleado, Long> {
    @Query(value = " select e.identificacion, e.nombre, e.apellido, d.nombre as nombreDepartamento "
            + " from desarrollo.empleado e, desarrollo.departamento d "
            + " where d.id = e.id_departamento "
            + " and e.id = ?1", nativeQuery = true)
    List<Object[]> buscaEmpleadoPorId(Long idEmpleado);
}