package springboot.microservicio.taller.empresa.api.models;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/** Clase Departamento */
@Data
@Entity
@Table(name = "departamento", schema = "desarrollo")
public class Departamento implements Serializable {

    private static final long serialVersionUID = 1285454306356845809L;

    /** iD Departamento */
    @GeneratedValue(generator = "secDepartamento", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "secDepartamento", allocationSize = 1, initialValue = 1, sequenceName = "sec_departamento")
    @Id
    @Column(name = "id")
    private Long idDepartamento;

    /** Nombre departamento */
    @Column(name = "nombre", nullable = false, length = 200)
    private String nombre;

    /** Estado */
    @Column(name = "estado")
    private String estado;

    /** Fecha registro */
    @Column(name = "fecha_registro")
    private Date fechaRegistro;
}
