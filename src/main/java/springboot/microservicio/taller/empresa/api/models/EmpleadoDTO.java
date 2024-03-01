package springboot.microservicio.taller.empresa.api.models;

import lombok.Data;
import java.io.Serializable;

/** Clase EmpleadoDTO */
@Data
public class EmpleadoDTO implements Serializable {
    private static final long serialVersionUID = 1285454306356845809L;

    /** Id Empleado */
    private Long idEmpleado;

    /** Identificacion */
    private String  identificacion;

    /** Nombre */
    private String nombre;

    /** Apellido */
    private String apellido;

    /** Correo */
    private String  correo;

    /** Id Departamento  */
    private Long idDepartamento;
}