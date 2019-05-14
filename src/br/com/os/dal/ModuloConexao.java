/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.os.dal;

import java.sql.*;

/**
 *
 * @author bruno.alves
 */
public class ModuloConexao {

    //metodo responsavel por estabelecer a conexão com o banco de dados
    public static Connection conector() {
        java.sql.Connection conexao = null;
        // linha a baixo chama o drive
        String driver = "com.mysql.jdbc.Driver";
        //armazenando informações referente ao banco 
        String url = "jdbc:mysql://localhost:3306/db_os";
        String user = "root";
        String password = "";

        // estabelecendo a conexao 
        try {
            Class.forName(driver);
            conexao = DriverManager.getConnection(url, user, password);
            return conexao;
        } catch (Exception e) {
            return null;
        }
    }

}
