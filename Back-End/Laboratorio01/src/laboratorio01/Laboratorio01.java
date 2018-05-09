package laboratorio01;

import laboratorio01.AccesoDatos.GlobalException;
import laboratorio01.AccesoDatos.NoDataException;
import laboratorio01.AccesoDatos.Servicio;
import laboratorio01.AccesoDatos.ServicioProducto;
import laboratorio01.LogicaNegocio.Producto;

/**
 *
 * @author casca
 */
public class Laboratorio01 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws NoDataException, GlobalException {
        // TODO code application logic here
        ServicioProducto serv=new ServicioProducto();
        //serv.actualizarProducto(new Producto("03","Azucar",8000,2,"Canasta"));
        //serv.insertarProducto(new Producto("03","Arroz",6000,1,"Canasta"));
        serv.buscarProducto("Arroz");
        //serv.eliminarProducto("Azucar");
        //serv.listarProducto();
        //serv.totalPagar(new Producto("03","Azucar",5000,1,"Canasta"));
    }

}
