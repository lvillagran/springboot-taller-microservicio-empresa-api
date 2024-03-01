package springboot.microservicio.taller.empresa.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springboot.microservicio.taller.empresa.api.models.Empleado;
import springboot.microservicio.taller.empresa.api.repository.IEmpleadoRepository;
import java.util.Optional;
import java.util.List;
import java.util.NoSuchElementException;



/** Clase EmpleadoService */
@Service
public class EmpleadoService {
    @Autowired
    private final IEmpleadoRepository lEmpleadoRepositorio;
    public EmpleadoService(IEmpleadoRepository lEmpleadoRepositorio) {
        this.lEmpleadoRepositorio = lEmpleadoRepositorio;
    }

    /** Registra empleado */
    public Empleado createEmpleado(Empleado empleado) {
        return lEmpleadoRepositorio.save(empleado);
    }

    /** Consulta empleado por Id */
    public Optional<Empleado> buscaEmpleadoPorId(Long empleado) {
        return lEmpleadoRepositorio.findById(empleado);
    }

    public List<Empleado> getAllEmpleadosService() {
        return lEmpleadoRepositorio.findAll();
    }


    public Empleado consultaIdEmpleado(Long empleado) {
        return lEmpleadoRepositorio.findById(empleado).orElseThrow(() -> new NoSuchElementException("Empleado no encontrado con el ID: " + empleado));
    }


    public Empleado updateEmpleado(Empleado pelicula) {
        return lEmpleadoRepositorio.save(pelicula);
    }

    public void deleteEmpleado(Long idEmpleado) {
        lEmpleadoRepositorio.deleteById(idEmpleado);
    }

    /*NOTA: Consulta con Query Nativo, usando vaias tablas*/
    public List<Object[]> buscaEmpleadoPorIdUsandoDosTablas(Long idEmpleado) {
        return  lEmpleadoRepositorio.buscaEmpleadoPorId(idEmpleado);
    }
}
