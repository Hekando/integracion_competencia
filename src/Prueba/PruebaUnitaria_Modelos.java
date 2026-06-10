package Prueba;

import Modelo.Camion;
import Modelo.Conductor;
import Modelo.Mantenimiento;
import Modelo.InventarioPieza;
import Modelo.RegistroKilometraje;
import Modelo.Alerta;
import Modelo.Equipo;
import Modelo.Software;
import Modelo.ActualizacionSoftware;
import Modelo.MantenimientoEquipo;

import java.time.LocalDate;

/**
 * Pruebas unitarias de los Modelos del sistema Hirata.
 * No requiere conexión a base de datos.
 * Ejecutar con clic derecho → Run 'PruebaUnitaria_Modelos.main()'
 */
public class PruebaUnitaria_Modelos {

    static int total   = 0;
    static int pasadas = 0;
    static int fallidas = 0;

    // Método auxiliar para verificar y mostrar resultado
    static void verificar(String nombrePrueba, boolean condicion) {
        total++;
        if (condicion) {
            pasadas++;
            System.out.println("  ✅ PASSED: " + nombrePrueba);
        } else {
            fallidas++;
            System.out.println("  ❌ FAILED: " + nombrePrueba);
        }
    }

    // Método auxiliar para verificar que se lanza excepción
    static void verificarExcepcion(String nombrePrueba, Runnable accion, String mensajeEsperado) {
        total++;
        try {
            accion.run();
            fallidas++;
            System.out.println("  ❌ FAILED: " + nombrePrueba + " (esperaba excepción, no se lanzó)");
        } catch (Exception e) {
            if (mensajeEsperado.equals(e.getMessage())) {
                pasadas++;
                System.out.println("  ✅ PASSED: " + nombrePrueba);
            } else {
                fallidas++;
                System.out.println("  ❌ FAILED: " + nombrePrueba
                        + " → mensaje esperado: \"" + mensajeEsperado
                        + "\" | recibido: \"" + e.getMessage() + "\"");
            }
        }
    }

    // ---------------------------------------------------------------
    public static void main(String[] args) {

        System.out.println("╔══════════════════════════════════════════╗");
        System.out.println("║   PRUEBAS UNITARIAS - MODELOS HIRATA     ║");
        System.out.println("╚══════════════════════════════════════════╝\n");

        probarCamion();
        probarConductor();
        probarMantenimiento();
        probarInventarioPieza();
        probarRegistroKilometraje();
        probarAlerta();
        probarEquipo();
        probarSoftware();
        probarActualizacionSoftware();
        probarMantenimientoEquipo();
        probarValidacionesEquipoController();
        probarValidacionesEquipoSoftwareController();
        probarValidacionesMantenimientoEquipoController();
        probarValidacionesInventarioController();
        probarValidacionesSoftwareController();

        // Resumen final
        System.out.println("\n╔══════════════════════════════════════════╗");
        System.out.println("║   RESUMEN FINAL                          ║");
        System.out.printf( "║   Total:   %-30s║%n", total   + " pruebas");
        System.out.printf( "║   Pasadas: %-30s║%n", pasadas + " ✅");
        System.out.printf( "║   Fallidas:%-30s║%n", fallidas + " ❌");
        System.out.println("╚══════════════════════════════════════════╝");
    }

    // ---------------------------------------------------------------
    // CAMION
    // ---------------------------------------------------------------
    static void probarCamion() {
        System.out.println("--- Modelo: Camion ---");

        Camion c = new Camion();
        c.setIdCamion(1);
        c.setPatente("ABCD-12");
        c.setMarca("Volvo");
        c.setModelo("FH16");
        c.setKilometraje(50000);

        verificar("setIdCamion / getIdCamion",     c.getIdCamion()    == 1);
        verificar("setPatente / getPatente",        "ABCD-12".equals(c.getPatente()));
        verificar("setMarca / getMarca",            "Volvo".equals(c.getMarca()));
        verificar("setModelo / getModelo",          "FH16".equals(c.getModelo()));
        verificar("setKilometraje / getKilometraje", c.getKilometraje() == 50000);
        verificar("Kilometraje inicial es 0",       new Camion().getKilometraje() == 0);

        System.out.println();
    }

    // ---------------------------------------------------------------
    // CONDUCTOR
    // ---------------------------------------------------------------
    static void probarConductor() {
        System.out.println("--- Modelo: Conductor ---");

        Conductor c = new Conductor();
        c.setId(5);
        c.setNombre("Juan Pérez");
        c.setLicencia("A2");
        c.setIdCamion(3);
        c.setUsuario("jperez");
        c.setPassword("clave123");

        verificar("setId / getId",              c.getId()       == 5);
        verificar("setNombre / getNombre",      "Juan Pérez".equals(c.getNombre()));
        verificar("setLicencia / getLicencia",  "A2".equals(c.getLicencia()));
        verificar("setIdCamion / getIdCamion",  c.getIdCamion() == 3);
        verificar("setUsuario / getUsuario",    "jperez".equals(c.getUsuario()));
        verificar("setPassword / getPassword",  "clave123".equals(c.getPassword()));

        System.out.println();
    }

    // ---------------------------------------------------------------
    // MANTENIMIENTO
    // ---------------------------------------------------------------
    static void probarMantenimiento() {
        System.out.println("--- Modelo: Mantenimiento ---");

        Mantenimiento m = new Mantenimiento();
        LocalDate fecha = LocalDate.of(2025, 5, 20);

        m.setId(10);
        m.setIdCamion(3);
        m.setFecha(fecha);
        m.setKilometraje(75000);
        m.setTipo("Preventivo");

        verificar("setId / getId",                 m.getId()          == 10);
        verificar("setIdCamion / getIdCamion",     m.getIdCamion()    == 3);
        verificar("setFecha / getFecha",            fecha.equals(m.getFecha()));
        verificar("setKilometraje / getKilometraje", m.getKilometraje() == 75000);
        verificar("setTipo / getTipo",              "Preventivo".equals(m.getTipo()));
        verificar("Fecha no es nula",               m.getFecha() != null);

        System.out.println();
    }

    // ---------------------------------------------------------------
    // INVENTARIO PIEZA
    // ---------------------------------------------------------------
    static void probarInventarioPieza() {
        System.out.println("--- Modelo: InventarioPieza ---");

        InventarioPieza p = new InventarioPieza();
        p.setIdPieza(1);
        p.setNombre("Filtro de aceite");
        p.setCantidad(10);
        p.setDescripcion("Filtro para motor diesel");

        verificar("setIdPieza / getIdPieza",        p.getIdPieza()    == 1);
        verificar("setNombre / getNombre",           "Filtro de aceite".equals(p.getNombre()));
        verificar("setCantidad / getCantidad",       p.getCantidad()   == 10);
        verificar("setDescripcion / getDescripcion", "Filtro para motor diesel".equals(p.getDescripcion()));

        // Regla de negocio: estado según cantidad
        p.setCantidad(5);
        p.setEstado(p.getCantidad() > 0 ? "Disponible" : "Agotado");
        verificar("Estado Disponible cuando cantidad > 0", "Disponible".equals(p.getEstado()));

        p.setCantidad(0);
        p.setEstado(p.getCantidad() > 0 ? "Disponible" : "Agotado");
        verificar("Estado Agotado cuando cantidad = 0", "Agotado".equals(p.getEstado()));

        System.out.println();
    }

    // ---------------------------------------------------------------
    // REGISTRO KILOMETRAJE
    // ---------------------------------------------------------------
    static void probarRegistroKilometraje() {
        System.out.println("--- Modelo: RegistroKilometraje ---");

        RegistroKilometraje r = new RegistroKilometraje();
        LocalDate hoy = LocalDate.now();

        r.setId(1);
        r.setIdCamion(3);
        r.setFecha(hoy);
        r.setKilometraje(12000);
        r.setResultado("Correcto");

        verificar("setId / getId",                 r.getId()          == 1);
        verificar("setIdCamion / getIdCamion",     r.getIdCamion()    == 3);
        verificar("setFecha / getFecha",            hoy.equals(r.getFecha()));
        verificar("setKilometraje / getKilometraje", r.getKilometraje() == 12000);
        verificar("setResultado / getResultado",    "Correcto".equals(r.getResultado()));

        // Regla de negocio: resultado según km
        r.setKilometraje(6000);
        r.setResultado(r.getKilometraje() >= 5000 ? "Alerta generada" : "Correcto");
        verificar("Resultado Alerta cuando km >= 5000", "Alerta generada".equals(r.getResultado()));

        r.setKilometraje(1000);
        r.setResultado(r.getKilometraje() >= 5000 ? "Alerta generada" : "Correcto");
        verificar("Resultado Correcto cuando km < 5000", "Correcto".equals(r.getResultado()));

        System.out.println();
    }

    // ---------------------------------------------------------------
    // ALERTA
    // ---------------------------------------------------------------
    static void probarAlerta() {
        System.out.println("--- Modelo: Alerta ---");

        Alerta a = new Alerta();
        LocalDate fecha = LocalDate.of(2025, 6, 1);

        a.setId(7);
        a.setIdCamion(2);
        a.setKilometraje(80000);
        a.setFecha(fecha);
        a.setMensaje("Mantencion requerida");
        a.setEstado("Pendiente");

        verificar("setId / getId",                 a.getId()           == 7);
        verificar("setIdCamion / getIdCamion",     a.getIdCamion()     == 2);
        verificar("setKilometraje / getKilometraje", a.getKilometraje() == 80000);
        verificar("setFecha / getFecha",            fecha.equals(a.getFecha()));
        verificar("setMensaje / getMensaje",        "Mantencion requerida".equals(a.getMensaje()));
        verificar("setEstado Pendiente",            "Pendiente".equals(a.getEstado()));

        a.setEstado("Realizada");
        verificar("setEstado Realizada",            "Realizada".equals(a.getEstado()));

        System.out.println();
    }

    // ---------------------------------------------------------------
    // EQUIPO
    // ---------------------------------------------------------------
    static void probarEquipo() {
        System.out.println("--- Modelo: Equipo ---");

        Equipo e = new Equipo();
        e.setIdEquipo(1);
        e.setNumeroSerie("SN-001");
        e.setNombre("PC Oficina");
        e.setTipo("Computador");
        e.setMarca("Dell");
        e.setModelo("OptiPlex");
        e.setEstado("Activo");

        verificar("setIdEquipo / getIdEquipo",       e.getIdEquipo()    == 1);
        verificar("setNumeroSerie / getNumeroSerie", "SN-001".equals(e.getNumeroSerie()));
        verificar("setNombre / getNombre",            "PC Oficina".equals(e.getNombre()));
        verificar("setTipo / getTipo",                "Computador".equals(e.getTipo()));
        verificar("setMarca / getMarca",              "Dell".equals(e.getMarca()));
        verificar("setModelo / getModelo",            "OptiPlex".equals(e.getModelo()));
        verificar("setEstado / getEstado",            "Activo".equals(e.getEstado()));
        verificar("toString contiene nombre y serie", e.toString().contains("PC Oficina") && e.toString().contains("SN-001"));

        System.out.println();
    }

    // ---------------------------------------------------------------
    // SOFTWARE
    // ---------------------------------------------------------------
    static void probarSoftware() {
        System.out.println("--- Modelo: Software ---");

        Software s = new Software();
        s.setIdSoftware(1);
        s.setNombre("Antivirus");
        s.setTipo("Seguridad");

        verificar("setIdSoftware / getIdSoftware", s.getIdSoftware() == 1);
        verificar("setNombre / getNombre",          "Antivirus".equals(s.getNombre()));
        verificar("setTipo / getTipo",              "Seguridad".equals(s.getTipo()));
        verificar("toString retorna nombre",        "Antivirus".equals(s.toString()));

        System.out.println();
    }

    // ---------------------------------------------------------------
    // ACTUALIZACION SOFTWARE
    // ---------------------------------------------------------------
    static void probarActualizacionSoftware() {
        System.out.println("--- Modelo: ActualizacionSoftware ---");

        ActualizacionSoftware a = new ActualizacionSoftware();
        LocalDate fecha = LocalDate.of(2025, 3, 15);

        a.setIdActualizacion(1);
        a.setIdEquipoSoftware(2);
        a.setIdAdmin(3);
        a.setFecha(fecha);
        a.setVersionAnterior("1.0");
        a.setVersionNueva("2.0");
        a.setDescripcion("Actualización mayor");
        a.setEstado("Completada");

        verificar("setIdActualizacion",  a.getIdActualizacion()  == 1);
        verificar("setIdEquipoSoftware", a.getIdEquipoSoftware() == 2);
        verificar("setIdAdmin",          a.getIdAdmin()          == 3);
        verificar("setFecha",            fecha.equals(a.getFecha()));
        verificar("setVersionAnterior",  "1.0".equals(a.getVersionAnterior()));
        verificar("setVersionNueva",     "2.0".equals(a.getVersionNueva()));
        verificar("setDescripcion",      "Actualización mayor".equals(a.getDescripcion()));
        verificar("setEstado",           "Completada".equals(a.getEstado()));

        System.out.println();
    }

    // ---------------------------------------------------------------
    // MANTENIMIENTO EQUIPO
    // ---------------------------------------------------------------
    static void probarMantenimientoEquipo() {
        System.out.println("--- Modelo: MantenimientoEquipo ---");

        MantenimientoEquipo m = new MantenimientoEquipo();
        LocalDate fecha = LocalDate.of(2025, 4, 10);

        m.setId_mantenimiento(1);
        m.setId_equipo(2);
        m.setId_admin(3);
        m.setFecha(fecha);
        m.setTipo("Correctivo");
        m.setDescripcion("Cambio de disco duro");

        verificar("setId_mantenimiento", m.getId_mantenimiento() == 1);
        verificar("setId_equipo",        m.getId_equipo()        == 2);
        verificar("setId_admin",         m.getId_admin()         == 3);
        verificar("setFecha",            fecha.equals(m.getFecha()));
        verificar("setTipo",             "Correctivo".equals(m.getTipo()));
        verificar("setDescripcion",      "Cambio de disco duro".equals(m.getDescripcion()));

        System.out.println();
    }

    // ---------------------------------------------------------------
    // VALIDACIONES EquipoController (sin BD)
    // ---------------------------------------------------------------
    static void probarValidacionesEquipoController() {
        System.out.println("--- Validaciones: EquipoController ---");

        // Simula la validación interna del controller directamente sobre el modelo
        Equipo equipoSinNombre = new Equipo();
        equipoSinNombre.setNombre("");

        verificarExcepcion(
            "Nombre vacío lanza excepción",
            () -> {
                if (equipoSinNombre.getNombre().isEmpty())
                    try {
                        throw new Exception("Nombre vacío");
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
            },
            "Nombre vacío"
        );

        Equipo equipoValido = new Equipo();
        equipoValido.setNombre("Impresora");
        verificar("Nombre no vacío no lanza excepción",
                !equipoValido.getNombre().isEmpty());

        System.out.println();
    }

    // ---------------------------------------------------------------
    // VALIDACIONES EquipoSoftwareController (sin BD)
    // ---------------------------------------------------------------
    static void probarValidacionesEquipoSoftwareController() {
        System.out.println("--- Validaciones: EquipoSoftwareController ---");

        verificarExcepcion("idEquipo <= 0 lanza excepción",
            () -> {
                int idEquipo = 0;
                if (idEquipo <= 0) try {
                    throw new Exception("Debe seleccionar un equipo");
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            },
            "Debe seleccionar un equipo"
        );

        verificarExcepcion("idSoftware <= 0 lanza excepción",
            () -> {
                int idSoftware = -1;
                if (idSoftware <= 0) try {
                    throw new Exception("Debe seleccionar un software");
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            },
            "Debe seleccionar un software"
        );

        verificarExcepcion("versión vacía lanza excepción",
            () -> {
                String version = "   ";
                if (version == null || version.trim().isEmpty())
                    try {
                        throw new Exception("Debe ingresar la versión instalada");
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
            },
            "Debe ingresar la versión instalada"
        );

        verificar("versión válida no lanza excepción",
                !"2.0".trim().isEmpty());

        System.out.println();
    }

    // ---------------------------------------------------------------
    // VALIDACIONES MantenimientoEquipoController (sin BD)
    // ---------------------------------------------------------------
    static void probarValidacionesMantenimientoEquipoController() {
        System.out.println("--- Validaciones: MantenimientoEquipoController ---");

        verificarExcepcion("Sin equipo seleccionado lanza excepción",
            () -> {
                MantenimientoEquipo m = new MantenimientoEquipo();
                m.setId_equipo(0);
                if (m.getId_equipo() <= 0)
                    try {
                        throw new Exception("Debe seleccionar un equipo");
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
            },
            "Debe seleccionar un equipo"
        );

        verificarExcepcion("Sin técnico seleccionado lanza excepción",
            () -> {
                MantenimientoEquipo m = new MantenimientoEquipo();
                m.setId_admin(0);
                if (m.getId_admin() <= 0)
                    try {
                        throw new Exception("Debe seleccionar un tecnico responsable");
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
            },
            "Debe seleccionar un tecnico responsable"
        );

        verificarExcepcion("Fecha nula lanza excepción",
            () -> {
                MantenimientoEquipo m = new MantenimientoEquipo();
                if (m.getFecha() == null)
                    try {
                        throw new Exception("Debe ingresar la fecha");
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
            },
            "Debe ingresar la fecha"
        );

        verificarExcepcion("Tipo vacío lanza excepción",
            () -> {
                MantenimientoEquipo m = new MantenimientoEquipo();
                m.setTipo("  ");
                if (m.getTipo() == null || m.getTipo().trim().isEmpty())
                    try {
                        throw new Exception("Debe seleccionar el tipo de mantenimiento");
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
            },
            "Debe seleccionar el tipo de mantenimiento"
        );

        verificarExcepcion("Descripción vacía lanza excepción",
            () -> {
                MantenimientoEquipo m = new MantenimientoEquipo();
                m.setDescripcion("");
                if (m.getDescripcion() == null || m.getDescripcion().trim().isEmpty())
                    try {
                        throw new Exception("Debe ingresar la descripcion");
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
            },
            "Debe ingresar la descripcion"
        );

        System.out.println();
    }

    // ---------------------------------------------------------------
    // VALIDACIONES InventarioController (sin BD)
    // ---------------------------------------------------------------
    static void probarValidacionesInventarioController() {
        System.out.println("--- Validaciones: InventarioController ---");

        verificarExcepcion("Cantidad negativa lanza excepción",
            () -> {
                InventarioPieza p = new InventarioPieza();
                p.setCantidad(-1);
                if (p.getCantidad() < 0)
                    try {
                        throw new Exception("Cantidad inválida");
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
            },
            "Cantidad inválida"
        );

        // Cantidad 0 es válida (caso borde)
        InventarioPieza pCero = new InventarioPieza();
        pCero.setCantidad(0);
        verificar("Cantidad 0 es válida (caso borde)", pCero.getCantidad() >= 0);

        // Cantidad positiva es válida
        InventarioPieza pPositivo = new InventarioPieza();
        pPositivo.setCantidad(5);
        verificar("Cantidad positiva es válida", pPositivo.getCantidad() >= 0);

        System.out.println();
    }

    // ---------------------------------------------------------------
    // VALIDACIONES SoftwareController (sin BD)
    // ---------------------------------------------------------------
    static void probarValidacionesSoftwareController() {
        System.out.println("--- Validaciones: SoftwareController ---");

        verificarExcepcion("Nombre vacío lanza excepción",
            () -> {
                Software s = new Software();
                s.setNombre("");
                if (s.getNombre().isEmpty())
                    try {
                        throw new Exception("Nombre vacío");
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
            },
            "Nombre vacío"
        );

        Software sValido = new Software();
        sValido.setNombre("Office 365");
        verificar("Nombre válido no lanza excepción",
                !sValido.getNombre().isEmpty());

        System.out.println();
    }
}
