package springboot.microservicio.taller.empresa.api.controllers;

import java.util.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springboot.microservicio.taller.empresa.api.models.Departamento;
import springboot.microservicio.taller.empresa.api.models.Empleado;
import springboot.microservicio.taller.empresa.api.models.EmpleadoDTO;
import springboot.microservicio.taller.empresa.api.service.EmpleadoService;
import springboot.microservicio.taller.empresa.api.utils.EstadosRegistros;


/** Clase EmpleadoService */
@RestController
public class Controllers {

    private final EmpleadoService lServicio;

    public Controllers(EmpleadoService lServicio) {
        this.lServicio = lServicio;
    }

    /** Registra empleado */
    /** URL: http://localhost:8081/crud-empleado/registra | Metodo: POST */
    /** JSON: {"identificacion":"0945821672","nombre":"RAMONCITO","apellido":"CALLE","correo":"ramoncito@gmail.com","idDepartamento":2} */
    @PostMapping("/registra")
    public ResponseEntity<String> EmpleadoSave(@RequestBody EmpleadoDTO empleadoDTO) {

        Departamento lDepartamento = new Departamento();
        lDepartamento.setIdDepartamento(empleadoDTO.getIdDepartamento());

        try {
            Empleado lEmpleado = new Empleado();
            lEmpleado.setIdentificacion(empleadoDTO.getIdentificacion().trim());
            lEmpleado.setNombre(empleadoDTO.getNombre().trim().toUpperCase());
            lEmpleado.setApellido(empleadoDTO.getApellido().toUpperCase());
            lEmpleado.setCorreo(empleadoDTO.getCorreo());
            lEmpleado.setIdDepartamento(lDepartamento);
            lEmpleado.setFechaRegistro(new Date());
            lEmpleado.setEstado(EstadosRegistros.ESTADO_ACTIVO);

            lServicio.createEmpleado(lEmpleado);

            return ResponseEntity.ok("Registro correctamente.");
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /** Consulta empleado por Id */
    /**
     * URL: http://localhost:8081/crud-empleado/empleado/40 | Metodo: GET
     */
    @GetMapping("/empleado/{id_empleado}")
    public ResponseEntity<Optional<Empleado>> consultarEmpleadoById(@PathVariable("id_empleado") Long id_empleado) {

        try {
            Optional<Empleado> listEmpleado = lServicio.buscaEmpleadoPorId(id_empleado);
            return new ResponseEntity<>(listEmpleado, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /** Consulta todos de todos los empleados */
    /**
     * URL: http://localhost:8081/crud-empleado/empleadosAll | Metodo: GET
     */
    @GetMapping("/empleadosAll")
    public ResponseEntity<List<Empleado>> getAllEmpleados() {

        try {
            List<Empleado> listEmpleado = new ArrayList<>();
            listEmpleado = lServicio.getAllEmpleadosService();
            return new ResponseEntity<>(listEmpleado, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /** Actualizar empleado por iD */
    /** http://localhost:8081/crud-empleado/updateEmpleado | Metodo: PUT */
    /**
     * JSON: {"idEmpleado":40,"nombre":" RAMONCITO","apellido":"PERES","correo":"abigail@gmail.com"}
     */
    @PutMapping("/updateEmpleado")
    public ResponseEntity<String> EmpleadoUpdate(@RequestBody EmpleadoDTO empleadoDTO) {

        Empleado empleado = lServicio.consultaIdEmpleado(empleadoDTO.getIdEmpleado());

        try {
            if (!empleado.toString().isEmpty()) {

                if (!empleadoDTO.getNombre().trim().isEmpty()) {
                    empleado.setNombre(empleadoDTO.getNombre().trim().toUpperCase());
                }

                if (!empleadoDTO.getApellido().trim().isEmpty()) {
                    empleado.setApellido(empleadoDTO.getApellido().trim().toUpperCase());
                }

                if (!empleadoDTO.getCorreo().trim().isEmpty()) {
                    empleado.setCorreo(empleadoDTO.getCorreo().trim());
                }

                Empleado updateEmpleado = lServicio.updateEmpleado(empleado);

                return ResponseEntity.ok("Registro actualizado correctamente.");
            } else {
                return ResponseEntity.ok("Empleado con ID: " + empleadoDTO + " ,no existe.");
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al consultar el empleado: " + e.getStackTrace());
        }
    }


    /** Eliminar empleado por Id */
    /**
     * http://localhost:8081/crud-empleado/deleteEmpleado/38 | Metodo: DELETE
     */
    @DeleteMapping("/deleteEmpleado/{id_empleado}")
    public ResponseEntity<String> eliminarEmpleado(@PathVariable("id_empleado") Long id_empleado) {

        try {
            lServicio.deleteEmpleado(id_empleado);
            return ResponseEntity.ok("Empleado con " + id_empleado + " eliminado correctamente.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar empleado: " + e.getStackTrace());
        }
    }


    /** Consulta empleado por Id, usamos Query Native */
    /** http://localhost:8081/crud-empleado/empleadoQueyNative/1 | Metodo: GET */
    @GetMapping("/empleadoQueyNative/{id_empleado}")
    public ResponseEntity<String> buscaEmpleadoPorIdUsandoDosTablas(@PathVariable("id_empleado") Long id_empleado) {
        try {
            List<Object[]> resultado = lServicio.buscaEmpleadoPorIdUsandoDosTablas(id_empleado);
            /* Retornamos la respuesta en formato JSON al navegador */
            return ResponseEntity.ok().body(armaObjetoJson(resultado));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al consultar empleado: " + e.getStackTrace());
        }
    }

    /** Metodo arma objeto JSON */
    public String armaObjetoJson( List<Object[]> objetoDatos ){
        /** Convertimos la lista de Object[] a una lista de HashMap */
        List<Map<String, Object>> listaMapaEmpleado = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        String json;

        for (Object[] datos : objetoDatos) {
            Map<String, Object> mapa = new HashMap<>();
            for (int i = 0; i < datos.length; i++) {
                mapa.put("valor" + i, datos[i]);
            }
            listaMapaEmpleado.add(mapa);
        }

        try {
            return   json = objectMapper.writeValueAsString(listaMapaEmpleado);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
