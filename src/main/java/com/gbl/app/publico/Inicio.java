package com.gbl.app.publico; // Isso funciona como um 'namespace'

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import java.sql.*;
//DEL import java.util.ArrayList;
//DEL import java.util.List;

import com.gbl.Config;

// Este deve ser o ponto de início do App:
// Aqui serão executadas as rotinas de configuração do sistema e inicialização da DB
// Este setor 'publico' não deve exigir acesso por login/senha

@WebServlet(name = "Inicio", urlPatterns = {"/publico/inicio"})
public class Inicio extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        // Chama o singleton Config (se já não foi inicializado)
        Config config = Config.getInstance();
        config.executarRotinasInicializacao(); // Aqui deve chamar o singleton DB

        response.setContentType("text/plain;charset=UTF-8");
        var out = response.getOutputStream();

        String html = "Bem vindo ao SysImg";
        // Botões:

        html += "<a href=\"/publico/inicio\">Público</a>";

        out.print(html);

    }
}
