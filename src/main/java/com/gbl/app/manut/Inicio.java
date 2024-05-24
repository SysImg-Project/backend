package com.gbl.app.manut; // Isso funciona como um 'namespace'

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


@WebServlet(name = "Inicio", urlPatterns = {"/manut/inicio"})

public class Inicio extends HttpServlet {
    private Log log; // Instância do objeto Log -- para logs

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
         this.log = Log.getInstance(); // Obtêm uma instância do objeto Log -- para logs

        response.setContentType("text/plain;charset=UTF-8");
        var out = response.getOutputStream();

        String html = "<a href=\"criartabelausuarios\">CriarTabelaUsuarios</a>";
        html += "<a href=\"../publico/inicio\">Público</a>";

        out.print(html);

    }
}
