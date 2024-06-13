// UNISINOS
// Curso: Ciência da Computação - Híbrido
// Atividade Acadêmica - Estágio
// Módulo X
// Tarefa: SysImg
// Data: 25/05/2024
// Professor:
// Aluno: Paulo Griebler Júnior
// Registro Acadêmico: 1930669


/*package com.gbl.classes;

import java.util.Map; // Isso é abstrato !
import java.util.HashMap; // Aqui é concreto !
//import com.gbl.ICrud;
import com.gbl.Log;

public class Prototipo {
    private Log log; // Instância do objeto Log -- para logs

    private int id;



    public Prototipo(int _id) {
        this.log = Log.getInstance(); // Obtêm uma instância do objeto Log -- para logs
        id = _id;
    }

    @Override
    public String obterNomeTabela() { // Mostra o nome da tabela que está representando este modelo na DB

        return "";
    }
    @Override
    public Map<String, String> obterCampos() { // Mostra em um mapa o nome dos campos e suas características

        //return new Map<String, String>(); //Map is abstract; cannot be instantiated
        Map<String, String> map = new HashMap<String, String>(); // Tem que escolher um tipo de Map --> java.util.HashMap, java.util.SortedMap, java.util.TreeMap...
        return map;
    }


/*    public void set#(String #){
        this.# = #;
    }

    public String get#() {
        return this.#;
    }

    public void set#(String #){
        this.# = #;
    }

    public String get#() {
        return this.#;
    }
*/

    /*@Override
    public String toString() {
        return "X: [id: "+this.id+"]";
    }
}*/
