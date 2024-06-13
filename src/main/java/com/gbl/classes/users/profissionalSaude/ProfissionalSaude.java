// UNISINOS
// Curso: Ciência da Computação - Híbrido
// Atividade Acadêmica - Estágio
// Módulo X
// Tarefa: SysImg
// Data: 25/05/2024
// Professor:
// Aluno: Paulo Griebler Júnior
// Registro Acadêmico: 1930669

package com.gbl.classes.users.profissionalSaude;
import com.gbl.Crud;
import com.gbl.classes.endereco.estadoBrasil.EstadoBrasil;
import com.gbl.classes.ExcecaoValorInvalido;

public class ProfissionalSaude extends Crud {
    private int numeroRegistro;
    private EstadoBrasil estadoConselho;

    public ProfissionalSaude() {
        super(0);
        this.numeroRegistro = 0; // Continua a execução com valores seguros
        this.estadoConselho = new EstadoBrasil(); // Construtor vazio com valores seguros


        /*this.numeroRegistro = 0;
        try {
            EstadoBrasil eb = new EstadoBrasil("IN"); // {"IN", "INDEFINIDO"}, // 0
            this.estadoConselho = eb;
        }
        catch (ExcecaoValorInvalido e) {
            log.erroN(e.toString());
            log.erroN("Não foi possível inicializar a classe EstadoBrasil com o valor 'IN' (estado indefinido).");
            // Aqui deve parar o programa pois é um erro muito grave !
            log.erroN("PARADA DO PROGRAMA DEVIDO AOS ERROS ACIMA !!!")
            System.exit(1);
        }*/ // DELETAR -- acho que não preciso de tudo isso -- só usar construtores vazios
    }

    public ProfissionalSaude(int id, int numeroRegistro, EstadoBrasil estadoConselho) {
        super(id);
        this.numeroRegistro = numeroRegistro;
        this.estadoConselho = estadoConselho;
    }

    public ProfissionalSaude(int id, int numeroRegistro, String estadoConselho) {
        super(id);
        this.numeroRegistro = numeroRegistro;
        try {
            EstadoBrasil eb = new EstadoBrasil(estadoConselho);
            this.estadoConselho = eb;
        }
        catch (ExcecaoValorInvalido e) {
            log.erroN(e.toString());
            this.numeroRegistro = 0; // Continua a execução com valores seguros
            this.estadoConselho = new EstadoBrasil(); // Construtor vazio com valores seguros

        }
    }

    public void setNumeroRegistro(int numeroRegistro) {
        if ((numeroRegistro < 0) || (numeroRegistro > 1000000)) {
        // Fora da faixa --> throw
            throw new ExcecaoValorInvalido("O inteiro informado para indicar um número de registro de um profissional de saúde não está dentro da faixa de valores válidos que vai de 0 à 1000000 (1 milhão).\nint informado ="+numeroRegistro+".\n");
        }
        else {
            this.numeroRegistro = numeroRegistro;
        }
    }

    public int getNumeroRegistro() {
        return this.numeroRegistro;
    }

    public void setEstadoConselho(EstadoBrasil estadoConselho){
        this.estadoConselho = estadoConselho;
    }

    // Aceita sigla ou nome por extenso do estado
    public void setEstadoConselhoStr(String estadoConselho) {
        try {
            EstadoBrasil eb = new EstadoBrasil(estadoConselho);
            this.estadoConselho = eb;
        }
        catch (ExcecaoValorInvalido e) {
            log.erroN(e.toString());
            this.estadoConselho = new EstadoBrasil(); // Construtor vazio com valores seguros
        }
    }

    public void setEstadoConselhoInt(int estadoConselhoInt) {
        try {
            EstadoBrasil eb = new EstadoBrasil();
            //public void setCodigoEstado(int codigoEstado) throws ExcecaoValorInvalido {
            eb.setCodigoEstado(estadoConselhoInt);
            this.estadoConselho = eb;
        }
        catch (ExcecaoValorInvalido e) {
            log.erroN(e.toString());
            this.estadoConselho = new EstadoBrasil(); // Construtor vazio com valores seguros
        }
    }

    public EstadoBrasil getEstadoConselho() {
        return estadoConselho;
    }

    public String getEstadoConselhoSigla() {
        return estadoConselho.getCodigoEstadoSigla();
    }

    public int getEstadoConselhoInt() {
        return estadoConselho.getCodigoEstado();
    }

    @Override
    public String toString() {
        return "ProfissionalSaude [numeroRegistro: "+this.numeroRegistro+", estadoConselho: "+this.estadoConselho.getCodigoEstadoSigla()+"]";
    }

}

