/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;

import Datos.VHabitacion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Tester
 */
public class FHabitacion {
    private Conexion mysql = new Conexion();
    private Connection cn = mysql.conectar();
    private String sSQL = "";
    public int totalregistros;
    
    public DefaultTableModel mostrar(String buscar) {
        DefaultTableModel modelo;
        String [] titulos = { "ID", "Numero", "piso", "Descripcion", "Caracteristicas", "Precio", "Estado", "Tipo Habitación" };
        String [] registro = new String[8];
        totalregistros = 0;
        modelo = new DefaultTableModel(null, titulos);
        sSQL = "SELECT * FROM habitacion WHERE piso like '%" + buscar + "%' ORDER BY idhabitacion";
        try {
            Statement st = cn.createStatement();
            ResultSet rs =  st.executeQuery(sSQL);
            
            while(rs.next()) {
                registro[0] = rs.getString("idhabitacion");
                registro[1] = rs.getString("numero");
                registro[2] = rs.getString("piso");
                registro[3] = rs.getString("descripcion");
                registro[4] = rs.getString("caracteristicas");
                registro[5] = rs.getString("precio_diario");
                registro[6] = rs.getString("estado");
                registro[7] = rs.getString("tipo_habitacion");
                totalregistros++;
                modelo.addRow(registro);
            }
            return modelo;
        } catch (Exception e) {
            JOptionPane.showConfirmDialog(null, e);
            return null;
        }     
    }
    
    public boolean insertar(VHabitacion dts) {
        sSQL = "INSERT INTO habitacion(numero, piso, descripcion, caracteristicas, precio_diario, estado, tipo_habitacion) VALUES(?,?,?,?,?,?,?)";
        try {
            PreparedStatement pst = cn.prepareStatement(sSQL);
            pst.setString(1, dts.getNumero());
            System.out.println(dts.getPiso());
            pst.setString(2, dts.getPiso());
            pst.setString(3, dts.getDescripcion());
            pst.setString(4, dts.getCaracteristicas());
            pst.setDouble(5, dts.getPrecio_diario());
            pst.setString(6, dts.getEstado());
            pst.setString(7, dts.getTipo_habitacion());
            
            int n = pst.executeUpdate();
            if(n != 0) {
                return true;
            }
            else {
                return false;
            }
        } catch (Exception e) {
            JOptionPane.showConfirmDialog(null, e);
            return false;
        }
    }
    
    public boolean editar(VHabitacion dts) {
        sSQL = "UPDATE habitacion set numero = ? , piso = ?, descripcion = ?, caracteristicas = ?, precio_diario = ?, estado = ?, tipo_habitacion = ? WHERE idhabitacion = ?";      
        try {
            PreparedStatement pst = cn.prepareStatement(sSQL);
            pst.setString(1, dts.getNumero());
            pst.setString(2, dts.getPiso());
            pst.setString(3, dts.getDescripcion());
            pst.setString(4, dts.getCaracteristicas());
            pst.setDouble(5, dts.getPrecio_diario());
            pst.setString(6, dts.getEstado());
            pst.setString(7, dts.getTipo_habitacion());
            pst.setInt(8, dts.getIdhabitacion());
            
            int n = pst.executeUpdate();
            if(n != 0) {
                return true;
            }
            else {
                return false;
            }
        } catch (Exception e) {
            JOptionPane.showConfirmDialog(null, e);
            return false;
        }
    }
    
    public boolean eliminar(VHabitacion dts) {
        sSQL = "DELETE FROM habitacion WHERE idhabitacion = ?";
        
        try {
            PreparedStatement pst = cn.prepareStatement(sSQL);
            pst.setInt(1, dts.getIdhabitacion());
            
            int n = pst.executeUpdate();
            if(n != 0) {
                return true;
            }
            else {
                return false;
            }
        } catch (Exception e) {
            JOptionPane.showConfirmDialog(null, e);
            return false;
        }
        
    }
}
