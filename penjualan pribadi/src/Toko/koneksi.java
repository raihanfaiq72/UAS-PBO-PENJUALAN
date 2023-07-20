/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Toko;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author PY7
 */
public class koneksi {
    
        private static Connection koneksi;

    public static Connection getKoneksi() {
        if (koneksi == null) {
            try {
                String url = "jdbc:mysql://localhost/penjualan";
                String user = "root";
                String password = "";
                Class.forName("com.mysql.cj.jdbc.Driver"); // Register the driver
                koneksi = DriverManager.getConnection(url, user, password);
                System.out.println("Berhasil");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return koneksi;
    }
    public static void main(String args[]){
        getKoneksi();
    }  
}
