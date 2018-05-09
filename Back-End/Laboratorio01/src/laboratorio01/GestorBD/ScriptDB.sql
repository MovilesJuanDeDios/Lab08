/**
 *
 * @author casca
 */
connect system/manager as sysdba;
 
-- ----------------- USUARIO ----------------- 
create user servidor identified by servidor;

grant all privileges to servidor identified by servidor;

connect servidor/servidor;

-- ----------------- CREACION DE UN CURSOR -----------------
CREATE OR REPLACE PACKAGE types
AS
     TYPE ref_cursor IS REF CURSOR;
END;
/

-- ################################### PRODUCTOS ###################################
-- ----------------- TABLA DE PRODUCTO -----------------
CREATE TABLE Producto(
        codigo VARCHAR(9),
	nombreProducto VARCHAR(50),
	precio NUMBER,
	importado INT,
	nombreTipo VARCHAR(50),
	CONSTRAINTS pkProducto PRIMARY KEY (codigo)
);

-- ----------------- INSERTAR PRODUCTO ----------------- 
CREATE OR REPLACE PROCEDURE insertarProducto(codigo IN Producto.codigo%TYPE, nombreProducto IN Producto.nombreProducto%TYPE, precio IN Producto.precio%TYPE, importado IN Producto.importado%TYPE, nombreTipo IN Producto.nombreTipo%TYPE)
AS
BEGIN
	INSERT INTO Producto VALUES(codigo,nombreProducto,precio,importado,nombreTipo);
END;
/

-- ----------------- ACTUALIZAR PRODUCTO ----------------- 
CREATE OR REPLACE PROCEDURE actualizarProducto(nombreProductoin IN Producto.nombreProducto%TYPE, precioin IN Producto.precio%TYPE,importadoin IN Producto.importado%TYPE)
AS
BEGIN
	UPDATE Producto SET precio=precioin,importado=importadoin WHERE nombreProducto=nombreProductoin;
END;
/

-- ----------------- BUSCAR PRODUCTO ----------------- 
CREATE OR REPLACE FUNCTION buscarProducto(nombreProductoin IN Producto.nombreProducto%TYPE)
RETURN Types.ref_cursor 
AS 
        producto_cursor types.ref_cursor; 
BEGIN 
  OPEN producto_cursor FOR 
       SELECT * FROM Producto WHERE nombreProducto=nombreProductoin; 
RETURN producto_cursor; 
END;
/

-- ----------------- LISTAR PRODUCTO ----------------- 
CREATE OR REPLACE FUNCTION listarProducto
RETURN Types.ref_cursor 
AS 
        producto_cursor types.ref_cursor; 
BEGIN 
  OPEN producto_cursor FOR 
       SELECT * FROM Producto ; 
RETURN producto_cursor; 
END;
/

-- ----------------- ELIMINAR PRODUCTO ----------------- 
create or replace procedure eliminarProducto(nombreProductoin IN Producto.nombreProducto%TYPE)
as
begin
    delete from Producto where nombreProducto=nombreProductoin;
end;
/

-- ----------------- IMPUESTO ----------------- 
CREATE OR REPLACE FUNCTION impuesto(precio IN NUMBER, importado IN INT, nombreTipo IN VARCHAR)
RETURN NUMBER
AS          
BEGIN 
    IF importado = 0 THEN
        CASE nombreTipo 
            WHEN 'Canasta' THEN RETURN precio*0.05;
            WHEN 'Popular' THEN RETURN precio*0.13;
            WHEN 'Suntuario' THEN RETURN precio*0.15;
            ELSE RETURN 0.0;
        END CASE;
    ELSE 
        CASE nombreTipo 
            WHEN 'Canasta' THEN RETURN precio*0.05+precio*0.5;
            WHEN 'Popular' THEN RETURN precio*0.13+precio*0.5;
            WHEN 'Suntuario' THEN RETURN precio*0.15+precio*0.5;
            ELSE RETURN 0.0;
        END CASE;
     END IF;
END;
/

-- ----------------- TOTAL A PAGAR ----------------- 
CREATE OR REPLACE FUNCTION totalPagar(precio IN NUMBER, impuesto IN INT)
RETURN NUMBER
AS          
BEGIN 
    RETURN precio+impuesto;
END;
/


-- #############################################################################

-- PRUEBAS NO EJECUTAR

-- -----------------  DROPS -----------------
DROP PROCEDURE insertarProducto;
DROP PROCEDURE actualizarProducto;
DROP FUNCTION buscarProducto;
DROP FUNCTION listarProducto;
DROP PROCEDURE eliminarProducto;
DROP TABLE Producto;
DROP PACKAGE types;
DROP USER servidor CASCADE; 

DECLARE
BEGIN
    insertarProducto('01','Frijoles',5000,1,'Canasta');
END;
/

SELECT impuesto(5000,1,'Canasta') FROM DUAL;

-- #############################################################################