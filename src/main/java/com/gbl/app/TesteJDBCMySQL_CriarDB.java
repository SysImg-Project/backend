package com.gbl.app;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "TesteJDBCMySQL_CriarDB", urlPatterns = {"/testejdbcmysql_criardb"})

public class TesteJDBCMySQL_CriarDB extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setContentType("text/plain;charset=UTF-8");
        var out = response.getOutputStream();

        out.print("TesteJDBCMySQL_CriarDB\n"); // Converte o '\n' para '<br>' do HTML automaticamente !

        //try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/test?serverTimezone=UTC","myUsername", "myPassword")) {
        //CREATE USER 'spring'@'localhost' IDENTIFIED BY 'qhw29YWd';
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/sysimg_des?serverTimezone=UTC","sysimg_des", "D8yx3Rk2")) { // No fim a DB e o servidor vão estar localizados no Astrolabium o que torna tudo acesso local

        //CREATE USER 'sysimg_des'@'localhost' IDENTIFIED BY 'D8yx3Rk2';
        //try (Connection conn = DriverManager.getConnection("jdbc:mysql://192.168.1.20:3306/sysimg_des?serverTimezone=UTC","sysimg_des", "D8yx3Rk2")) { // Acesso pelo MySQL do astrolabium -- pela intranet de casa
        //try (Connection conn = DriverManager.getConnection("jdbc:mysql://168.0.126.147:????/spring?serverTimezone=UTC","spring", "qhw29YWd")) { // Acesso pelo MySQL do astrolabium -- pela internet de casa --> porta não definida no roteador e firewall sem regra para este acesso ainda !
        //https://docs.oracle.com/en/java/javase/17/docs/api/java.sql/java/sql/Statement.html
        //ResultSet executeQuery(String sql) Executes the given SQL statement, which returns a single ResultSet object.
        //int executeUpdate(String sql) Executes the given SQL statement, which may be an INSERT, UPDATE, or DELETE statement or an SQL statement that returns nothing, such as an SQL DDL statement.
        //Data Definition Language (DDL) statements allow you to remove, edit, and add database objects. Some of the most common DDL statements you'll execute include CREATE , DROP , COMMENT , ALTER , and more. DDL commands are typically executed in a SQL browser or stored procedure.

            String comando = "DROP database sysimg_des;"; // comando DDL -- retorna um int
            PreparedStatement updateStatement = conn.prepareStatement(comando);
            int rsDrop = updateStatement.executeUpdate();
            out.print(comando+"\n");
            out.print("rsDrop ="+rsDrop+"\n");

            comando = "CREATE database sysimg_des;"; // comando DDL -- retorna um int
            //PreparedStatement
            updateStatement = conn.prepareStatement(comando);
            int rsCreate = updateStatement.executeUpdate();
            out.print(comando+"\n");
            out.print("rsCreate ="+rsCreate+"\n");

            comando = "USE sysimg_des ;"; // comando DDL -- retorna um int
            //PreparedStatement
            updateStatement = conn.prepareStatement(comando);
            int rsUse = updateStatement.executeUpdate();
            out.print(comando+"\n");
            out.print("rsUse ="+rsDrop+"\n");


            /*CREATE TABLE MyGuests (
                id INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
                firstname VARCHAR(30) NOT NULL,
                lastname VARCHAR(30) NOT NULL,
                email VARCHAR(50),
                reg_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
            )*/

            //comando = "CREATE TABLE usuarios (id INT(6) UNSIGNED AUTO_INCREMENT PRIMARY_KEY, nome VARCHAR(100) NOT NULL, sobrenome VARCHAR(100) NOT NULL);"; // PRIMARY KEY - não pode ter o sublinhado entre as palavras (PRIMARY_KEY está errado para o MySQL !)

            comando = "CREATE TABLE usuarios (id INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY, nome VARCHAR(100) NOT NULL, sobrenome VARCHAR(100) NOT NULL);";// comando DDL -- retorna um int
            //PreparedStatement
            updateStatement = conn.prepareStatement(comando);
            int rsCreateTable = updateStatement.executeUpdate();
            out.print(comando+"\n");
            out.print("rsCreateTable ="+rsCreateTable+"\n");

            //"INSERT INTO MyGuests (firstname, lastname, email) VALUES ('John', 'Doe', 'john@example.com')";
            comando = "INSERT INTO usuarios (nome, sobrenome) VALUES ('Paulo', 'Griebler');"; // Não insere no campo 'id' pois ele é auto incrementeado ! // comando INSERT -- retorna um int
            //PreparedStatement
            updateStatement = conn.prepareStatement(comando);
            int rsInsert = updateStatement.executeUpdate();
            out.print(comando+"\n");
            out.print("rsInsert ="+rsInsert+"\n");

            comando = "SELECT * FROM usuarios;";
            PreparedStatement selectStatement = conn.prepareStatement(comando);
            ResultSet rsSelect = selectStatement.executeQuery();
            out.print(comando+"\n");


           // List<Usuarios> usuarios = new ArrayList<>();

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

// OK !!! EM 27/04/2024 20h54 isso funcionou !
