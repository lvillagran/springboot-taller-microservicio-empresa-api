package springboot.microservicio.taller.empresa.api.models;

import java.io.Serializable;
import java.util.Date;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.*;

/** Clase empleado */
@Data
@Entity
@Table(name = "empleado", schema = "desarrollo")
public class Empleado implements Serializable  {
    private static final long serialVersionUID = 1285454306356845809L;

    /** Id Empleado */
    @GeneratedValue(generator = "secEmpleado", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "secEmpleado", allocationSize = 1, initialValue = 1, sequenceName = "sec_empleado")
    @Id
    @Column(name = "id")
    private Long idEmpleado;

    /** Identificacion empleado */
    @Column(name = "identificacion", nullable = false, length = 10)
    private String identificacion;

    /** Nombre empleado */
    @Column(name = "nombre" )
    private String nombre;

    /** Apellido empleado */
    @Column(name = "apellido" )
    private String apellido;

    /** Correo empleado */
    @Column(name = "correo" )
    private String correo;

    /** Id Departamento */
    @ManyToOne
    @JoinColumn(name = "id_departamento")
    private Departamento idDepartamento;

    /** Fecha registro empleado */
    @Column(name = "fecha_registro" )
    private Date fechaRegistro;

    /** Estado empleado */
    @Column(name = "estado" )
    private String estado;
}
