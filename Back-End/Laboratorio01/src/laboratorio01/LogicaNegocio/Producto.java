package laboratorio01.LogicaNegocio;

/**
 *
 * @author casca
 */
public class Producto {

    public Producto(String codigo, String nombreProducto, double precio, int importado, String tipo) {
        this.codigo = codigo;
        this.nombreProducto = nombreProducto;
        this.precio = precio;
        this.importado = importado;
        this.tipo = tipo;
    }
	
    public Producto() {
        this.codigo = "";
        this.nombreProducto = "";
        this.precio = 0.0;
        this.importado = 0;
        this.tipo = "";
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public double getPrecio() {
        return precio;
    }

    public int getImportado() {
        return importado;
    }

    public String getTipo() {
        return tipo;
    }
	
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public void setImportado(int importado) {
        this.importado = importado;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    public static String[] nombreCampos(){
        return NOMBRE_CAMPOS;
    }
    
    public Object[] toArray(){
        Object[] r = new Object[4];
        r[0] = getNombreProducto();
        r[1] = getImportado();
        r[2] = getPrecio();
        r[3] = getTipo();
        return r;
    }

    @Override
    public String toString() {
        return "Producto{" + "codigo = " + codigo + " , nombreProducto = " + nombreProducto + " , precio = " + precio + " , importado = " + importado + " , tipo = " + tipo + '}';
    }

    private String codigo;
    private String nombreProducto;
    private double precio;
    private int importado;
    private String tipo;
    private static final String[] NOMBRE_CAMPOS = {"Nombre", "Importado", "Precio", "Tipo", "Porcentaje", "Impuesto", "Precio Final"};
}
