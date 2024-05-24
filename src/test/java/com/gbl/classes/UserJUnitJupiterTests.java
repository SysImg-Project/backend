// UNISINOS
// Curso: Ciência da Computação - Híbrido
// Atividade Acadêmica - Estágio
// Módulo X
// Tarefa: SysImg
// Data: 28/04/2024
// Professor:
// Aluno: Paulo Griebler Júnior
// Registro Acadêmico: 1930669


import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import com.gbl.classes.User; // Classe a ser testada -- tem que importar !
//import java.time.LocalDate; // Usado por User
import com.gbl.classes.Sexo; // Usado por User
import com.gbl.classes.Tipo; // Usado por User

import com.gbl.DB; // Acesso à DB

class UserJUnitJupiterTests {

    //public static LocalDate of(int year, int month, int dayOfMonth)
    //LocalDate datanas = LocalDate.of(1974, 03, 28); // Sem 'new' porque é 'static' -- Del

    //public User(int _id, String _nome, String _sobreNome, LocalDate _datanas, Sexo _sexo, Tipo _tipo, String _login, String _senha)
    private final User user = new User(1, "Paulo", "Griebler Júnior", "1974-03-28", Sexo.MASCULINO, Tipo.MEDICO, "griebler", "12345678");

    @Test
    void id() {
        assertEquals(1, user.getId());
    }

    /*@Test
    // Testa a criação de registro do tipo User na DB
    // Pré-condição:
    // - Deve existir a DB 'sysimg_des'
    // - Deve estar criada a tabela 'users' na DB
    // - Instancia um objeto User
    // - Obs.: Este teste não precisa do servidor Jetty

    // Teste:
    // Usando o método 'create()' o insere os dados do objeto na DB em um novo registro na tabela 'users'

    // Pós-condição:
    // - Novo registro inserido na tabela 'users' na DB com os campos de acordo com o que foi instanciado no objeto User criado na pré-condição

    // Data de criação: 16/05/2024
    // Data de atualização: 16/05/2024
    void create() {
        // Cria as pré-condicões:
        LocalDate datanas = LocalDate.of(1974, 03, 28);
        User user = new User(1, "Paulo", "Griebler Júnior", datanas, Sexo.MASCULINO, Tipo.MEDICO, "griebler", "12345678");

        // Faz o teste:
        user.create();

        // Avalia as pós-condições:
        try(Connection conn = DriverManager.getConnection(this.db.conexaoStr, this.db.conexaoLogin, this.db.conexaoSenha)) {
            //comando = "SELECT * FROM "+getTabelaNome()+" WHERE id="+this.getId()+";";
            comando = "SELECT * FROM users WHERE id=1;";

            PreparedStatement selectStatement = conn.prepareStatement(comando);
            ResultSet result = selectStatement.executeQuery();
            log.dbN("result ="+result); // Não sei se isso dá certo

            if (result.next()) { // Significa que tem resultados
                do { // Recebe as colunas que estão dentro desta linha
                    assertEquals(1, result.getInt("id"));
                    assertEquals("Paulo", result.getString("nome"));
                    assertEquals("Griebler Júnior", result.getString("sobrenome"));
                    assertEquals("1974-03-28", result.getDate("datanas").toString()); // Date getDate(String columnLabel)   throws SQLException
                    assertEquals(1, result.getInt("sexo")); // Não existe getTinyInt()
                    assertEquals(5, result.getInt("tipo")); // Não existe getTinyInt()
                    assertEquals("griebler", result.getString("login"));
                    assertEquals("12345678", result.getString("senha"));
                }
                while (result.next()); // Passa para a próxima linha (row)
            }
            else { // Significa que não teve resultados

            }
        }
        catch (SQLException e) {
            String msg = "LOCAL DO ERRO: UserJUnitJupiterTests.create()";
            msg += e.getSQLState(); // Código do Erro
            msg += e.toString(); // Info do Erro
            fail(msg);
        }

    }*/ // REVER !!!

}
