package modelo;
public class Cliente {

    private Integer codigo;
    private Integer documento;
    private String nombre;
    private String apellido;

    public Cliente() {
    }

    public Cliente(Integer codigo, Integer documento, String nombre, String apellido) {
        this.codigo = codigo;
        this.documento = documento;
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public Integer getDocumento() {
        return documento;
    }

    public void setDocumento(Integer documento) {
        this.documento = documento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    @Override
    public String toString() {
        return "Codigo = " + codigo + "\t Documento = " + documento + "\t Nombre = " + nombre + "\t Apellido = " + apellido;
    }
    
}
