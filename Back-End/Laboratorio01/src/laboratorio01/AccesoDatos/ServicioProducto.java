package laboratorio01.AccesoDatos;

import laboratorio01.LogicaNegocio.Producto;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import oracle.jdbc.internal.OracleTypes;

/**
 *
 * @author casca
 */
public class ServicioProducto extends Servicio {

    private static final String INSERTAPRODUCTO = "{call insertarProducto(?,?,?,?,?)}";
    private static final String LISTARPRODUCTO = "{?=call listarProducto()}";
    private static final String BUSCARPRODUCTO = "{?=call buscarProducto(?)}";
    private static final String ACTUALIZARPRODUCTO = "{call actualizarProducto(?,?,?)}";
    private static final String ELIMINARPRODUCTO = "{call eliminarProducto(?)}";
    private static final String IMPUESTO = "{?=call impuesto(?,?,?)}";
    private static final String TOTALPAGAR = "{?=call totalPagar(?,?)}";

    public ServicioProducto() {

    }

    public void insertarProducto(Producto producto) throws GlobalException, NoDataException {
        connect();
        CallableStatement pstmt = null;

        try {
            pstmt = cn.prepareCall(INSERTAPRODUCTO);
            pstmt.setString(1, producto.getCodigo());
            pstmt.setString(2, producto.getNombreProducto());
            pstmt.setDouble(3, producto.getPrecio());
            pstmt.setInt(4, producto.getImportado());
            pstmt.setString(5, producto.getTipo());

            boolean resultado = pstmt.execute();

            if (resultado == true) {
                throw new NoDataException("No se realizo la inserción");
            } else {
                System.out.println("\nInserción Satisfactoria!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new GlobalException("Llave duplicada");
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                disconnect();
            } catch (SQLException e) {
                throw new GlobalException("Estatutos invalidos o nulos");
            }
        }
    }

    public Collection listarProducto() throws GlobalException, NoDataException {
        connect();

        ResultSet rs = null;
        ArrayList coleccion = new ArrayList();
        Producto producto = null;
        CallableStatement pstmt = null;
        try {
            pstmt = cn.prepareCall(LISTARPRODUCTO);
            pstmt.registerOutParameter(1, OracleTypes.CURSOR);
            pstmt.execute();
            rs = (ResultSet) pstmt.getObject(1);
            while (rs.next()) {
                producto = new Producto(rs.getString("codigo"),
                        rs.getString("nombreProducto"),
                        rs.getDouble("precio"),
                        rs.getInt("importado"),
                        rs.getString("nombreTipo"));
                coleccion.add(producto);
            }
        } catch (SQLException e) {
            e.printStackTrace();

            throw new GlobalException("Sentencia no valida");
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
                disconnect();
            } catch (SQLException e) {
                throw new GlobalException("Estatutos invalidos o nulos");
            }
        }
        if (coleccion == null || coleccion.size() == 0) {
            throw new NoDataException("No hay datos");
        }
       
        return coleccion;
    }

    public Producto buscarProducto(String nombre) throws GlobalException, NoDataException {
        connect();

        ResultSet rs = null;
        Producto producto = null;
        CallableStatement pstmt = null;
        try {
            pstmt = cn.prepareCall(BUSCARPRODUCTO);
            pstmt.registerOutParameter(1, OracleTypes.CURSOR);
            pstmt.setString(2, nombre);
            pstmt.execute();
            rs = (ResultSet) pstmt.getObject(1);
            while (rs.next()) {
                producto = new Producto(rs.getString("codigo"),
                        rs.getString("nombreProducto"),
                        rs.getDouble("precio"),
                        rs.getInt("importado"),
                        rs.getString("nombreTipo"));
            }
        } catch (SQLException e) {
            e.printStackTrace();

            throw new GlobalException("Sentencia no valida");
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
                disconnect();
            } catch (SQLException e) {
                throw new GlobalException("Estatutos invalidos o nulos");
            }
        }
        if (producto == null) {
            throw new NoDataException("No hay datos");
        }
        System.out.print(producto.toString());
        return producto;
    }

    public void actualizarProducto(Producto producto) throws GlobalException, NoDataException {
        connect();
        PreparedStatement pstmt = null;
        try {
            pstmt = cn.prepareStatement(ACTUALIZARPRODUCTO);
            pstmt.setString(1, producto.getNombreProducto());
            pstmt.setDouble(2, producto.getPrecio());
            pstmt.setInt(3, producto.getImportado());
            boolean resultado = pstmt.execute();

            if (resultado == true) {
                throw new NoDataException("No se realizo la actualización");
            } else {
                System.out.println("\nModificación Satisfactoria!");
            }
        } catch (SQLException e) {
            throw new GlobalException("Sentencia no valida");
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                disconnect();
            } catch (SQLException e) {
                throw new GlobalException("Estatutos invalidos o nulos");
            }
        }
    }

    public void eliminarProducto(String nombre) throws GlobalException, NoDataException {
        connect();
        PreparedStatement pstmt = null;
        try {
            pstmt = cn.prepareStatement(ELIMINARPRODUCTO);
            pstmt.setString(1, nombre);

            boolean resultado = pstmt.execute();

            if (resultado == true) {
                throw new NoDataException("No se realizo el borrado");
            } else {
                System.out.println("\nEliminación Satisfactoria!");
            }
        } catch (SQLException e) {
            throw new GlobalException("Sentencia no valida");
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                disconnect();
            } catch (SQLException e) {
                throw new GlobalException("Estatutos invalidos o nulos");
            }
        }
    }

    public double impuesto(Producto producto) throws GlobalException, NoDataException {
        connect();
        double imp;
        CallableStatement pstmt = null;
        try {
            pstmt = cn.prepareCall(IMPUESTO);
            pstmt.registerOutParameter(1, OracleTypes.DOUBLE);
            pstmt.setDouble(2, producto.getPrecio());
            pstmt.setInt(3, producto.getImportado());
            pstmt.setString(4, producto.getTipo());
            pstmt.execute();
            imp = pstmt.getDouble(1);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new GlobalException("Sentencia no valida");
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                disconnect();
            } catch (SQLException e) {
                throw new GlobalException("Estatutos invalidos o nulos");
            }
        }

        
        return imp;
    }

    public double totalPagar(Producto producto) throws GlobalException, NoDataException {
        connect();
        double total;
        CallableStatement pstmt = null;
        try {
            pstmt = cn.prepareCall(TOTALPAGAR);
            pstmt.registerOutParameter(1, OracleTypes.DOUBLE);
            pstmt.setDouble(2, producto.getPrecio());
            pstmt.setDouble(3, impuesto(producto));
            pstmt.execute();
            total = pstmt.getDouble(1);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new GlobalException("Sentencia no valida");
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                disconnect();
            } catch (SQLException e) {
                throw new GlobalException("Estatutos invalidos o nulos");
            }
        }

        
        return total;
    }
}
