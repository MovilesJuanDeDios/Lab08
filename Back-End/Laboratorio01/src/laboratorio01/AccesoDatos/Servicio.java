package laboratorio01.AccesoDatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import oracle.jdbc.pool.OracleDataSource;

/**
 *
 * @author casca
 */
public class Servicio {

    public Servicio() {
    }

    public Connection connect() throws NoDataException, GlobalException {
        try {
            ds = new OracleDataSource();
            ds.setURL(jdbcUrl);
            cn = ds.getConnection(user, password);
            return cn;
        } catch (SQLException ex) {
            throw new NoDataException("La base de datos no se encuentra disponible");
        }
    }

    public Boolean disconnect() {
        try {
            cn.close();
            return true;
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            return false;
        }
    }

    protected Connection cn;
    private final String user = "servidor";
    private final String password = "servidor";
    private final String jdbcUrl = "jdbc:oracle:thin:@localhost:1521:XE";
    private OracleDataSource ds;
}

/*
public class Servicio {

    protected Connection conexion= null;
    
    public Servicio() {
        
    }
    
    protected void conectar() throws SQLException,ClassNotFoundException 
    {
            Class.forName("oracle.jdbc.driver.OracleDriver");
       // try {
            conexion = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","system","manager");
            //conexion = getJdbcMydbsource();
//        } catch (NamingException ex) {
//            ex.printStackTrace();
//        }
        
    }
    
    protected void desconectar() throws SQLException{
        if(!conexion.isClosed())
        {
            conexion.close();
        }
    }

    private Connection getJdbcMydbsource() throws NamingException {
        Context c = new InitialContext();
        try {
            return ((DataSource) c.lookup("jdbc/Mydbsource")).getConnection();
        } catch (NamingException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
*/

/* 
// OTRA FORMA DE CONECTARSE

import java.sql.Connection;
import java.sql.SQLException;
import oracle.jdbc.pool.OracleDataSource;

public class Servicio {

    public Servicio() {
    }

    public Connection connect() {
        try {
            ds = new OracleDataSource();
            ds.setURL(jdbcUrl);
            cn = ds.getConnection(user, password);
            return cn;
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            //return null;
        }
        return null;
    }

    public Boolean disconnect() {
        try {
            cn.close();
            return true;
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            return false;
        }
    }
    
    private Connection cn;
    private final String user = "servidor";
    private final String password = "servidor";
    private final String jdbcUrl = "jdbc:oracle:thin:@localhost:1521:ORCL";
    private OracleDataSource ds;
}



*/