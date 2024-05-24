package com.gbl.app;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "TesteJDBCMySQL", urlPatterns = {"/testejdbcmysql"})

public class TesteJDBCMySQL extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setContentType("text/plain;charset=UTF-8");
        var out = response.getOutputStream();

        out.print("TesteJDBCMySQL\n");

        //try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/test?serverTimezone=UTC","myUsername", "myPassword")) {
        //CREATE USER 'spring'@'localhost' IDENTIFIED BY 'qhw29YWd';
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/sysimg_des?serverTimezone=UTC","sysimg_des", "D8yx3Rk2")) { // No fim a DB e o servidor vão estar localizados no Astrolabium o que torna tudo acesso local

        //CREATE USER 'sysimg_des'@'localhost' IDENTIFIED BY 'D8yx3Rk2';
        //try (Connection conn = DriverManager.getConnection("jdbc:mysql://192.168.1.20:3306/sysimg_des?serverTimezone=UTC","sysimg_des", "D8yx3Rk2")) { // Acesso pelo MySQL do astrolabium -- pela intranet de casa

            String comando = "SELECT * FROM usuarios;";
            PreparedStatement selectStatement = conn.prepareStatement(comando);
            ResultSet rsSelect = selectStatement.executeQuery();
            out.print(comando+"\n");


            //List<Usuarios> usuarios = new ArrayList<>();

            while (rsSelect.next()) { // will traverse through all rows
                Integer id = rsSelect.getInt("id");
                String nome = rsSelect.getString("nome");
                String sobrenome = rsSelect.getString("sobrenome");

                //User user = new User(id, firstName, lastName);
                //users.add(user);

                out.print("rsSelect.getInt(\"id\")"+id+"\n");
                out.print("rsSelect.getString(\"nome\")"+nome+"\n");
                out.print("rsSelect.getString(\"sobrenome\")"+sobrenome+"\n");

            }

        }

        catch(SQLException e) {
            out.print(e.toString());
        }
    }
}
