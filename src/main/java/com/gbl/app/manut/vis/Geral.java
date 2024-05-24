// UNISINOS
// Curso: Ciência da Computação - Híbrido
// Atividade Acadêmica - Estágio
// Módulo X
// Tarefa: SysImg
// Data: 28/04/2024
// Professor:
// Aluno: Paulo Griebler Júnior
// Registro Acadêmico: 1930669

package com.gbl.app.manut.vis;

public class Geral {
    private String contextPath; // Seria o 'document Root do Apache'

    public Geral(String _contextPath) {
        this.contextPath = _contextPath;
    }

    public String mostrarBotoesDeRetornoParaPublico() {
        String ret = "<a href="+this.contextPath+"/publico/inicio>Área Pública</a>";
        ret+= "";
        return ret;
    }

    @Override
    public String toString() {
        return "Geral: [contextPath: "+this.contextPath+"]";
    }
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
