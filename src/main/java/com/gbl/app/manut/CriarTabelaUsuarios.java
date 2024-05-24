package com.gbl.app.manut;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.gbl.Log; // Para obter 'log'
import com.gbl.DB; // Para obter 'conn'

@WebServlet(name = "CriarTabelaUsuarios", urlPatterns = {"/manut/criartabelausuarios"})

public class CriarTabelaUsuarios extends HttpServlet {
    private Log log; // Instância do objeto Log -- para logs
    private DB db;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        this.log = Log.getInstance(); // Obtêm uma instância do objeto Log -- para logs

        response.setContentType("text/plain;charset=UTF-8");
        var out = response.getOutputStream();

        out.print("CriarTabelaUsuarios\n"); // Converte o '\n' para '<br>' do HTML automaticamente !

        this.db = DB.getInstance();

        String comando = "";

        try(Connection conn = DriverManager.getConnection(this.db.conexaoStr, this.db.conexaoLogin, this.db.conexaoSenha)) {
            comando = "DROP database sysimg_des;"; // comando DDL -- retorna um int
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

        catch (SQLException e) {
            log.erroN(e.toString()); // log normal (console e/ou arquivo)
            out.print(e.toString()); // Imprime no browser ?
        }

    }
}

// OK !!! EM 27/04/2024 20h54 isso funcionou !
