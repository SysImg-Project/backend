// UNISINOS
// Curso: Ciência da Computação - Híbrido
// Atividade Acadêmica - Estágio
// Módulo X
// Tarefa: SysImg
// Data: 12/05/2024
// Professor:
// Aluno: Paulo Griebler Júnior
// Registro Acadêmico: 1930669

package com.gbl.classes;
import com.gbl.Crud;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Pessoa extends Crud { // APESAR DESTA CLASSE EXTENDER A CLASSE CRUD, COMO ELA É UMA CLASSE BASE PARA USUARIO NAO É NECESSARIO QUE ELA ARMAZENA SEUS DADOS NA DB. OS DADOS DE PESSOA E USUARIO SERAO ARMAZENADOS NA DB NA TABELA USUARIO QUE É CONTROLADA NA CLASSE USUARIO QUE HERDA PESSOA

    private String nome;
    private String sobreNome;
    //private LocalDate datanas;
    private String datanasIso;
    private Sexo sexo;
    private Tipo tipo;

    public Pessoa() {
        super(0);
        log.logN("Entrou em: public Pessoa()");
    }

    public Pessoa(int _id, String _nome, String _sobreNome, String _datanasIso, Sexo _sexo, Tipo _tipo) {
        super(_id);
        log.logN("Entrou em: public Pessoa(int _id, String _nome, String _sobreNome, String _datanasIso, Sexo _sexo, Tipo _tipo");
        nome = _nome;
        sobreNome = _sobreNome;
        datanasIso = _datanasIso;
        sexo = _sexo;
        tipo = _tipo;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public String getNome() {
        return this.nome;
    }

    public void setSobreNome(String sobreNome){
        this.sobreNome = sobreNome;
    }

    public String getSobreNome() {
        return this.sobreNome;
    }

    /*public void setDatanas(LocalDate datanas){
        this.datanas = datanas;
    }

    public LocalDate getDatanas() {
        return this.datanas;
    }*/

    public void setDatanasIso(String datanasIso){
        //public static LocalDate parse(CharSequence text)
        //Obtains an instance of LocalDate from a text string such as 2007-12-03.
        //The string must represent a valid date and is parsed using DateTimeFormatter.ISO_LOCAL_DATE.
        //CharSequence 	subSequence(int beginIndex, int endIndex) --> método de String
        try{
            //this.datanas = LocalDate.parse(_datanas);
            LocalDate.parse(datanasIso); // Faz um teste se a data está na forma correta
            this.datanasIso = datanasIso; // Mas armazena em formato ISO que é mais acessível
        }
        catch(DateTimeParseException e) {
            log.erroN("###Erro ao gerar 'datanas' da classe Pessoa em formato ISO. Motivo: "+e.toString()+" -- LOCAL DO ERRO:  public void setDatanasIso(String "+datanasIso+")");
        }
    }

    public String getDatanasIso() {
        return this.datanasIso;
    }

    public void setSexo(Sexo sexo){
        this.sexo = sexo;
    }

    public Sexo getSexo() {
        return this.sexo;
    }

    public void setSexoInt(int sexoInt){ // Para uso no intercâmbio com a DB
        switch(sexoInt) {
            case 1: this.sexo = Sexo.MASCULINO; break;
            case 2: this.sexo = Sexo.FEMININO; break;
            case 3: this.sexo = Sexo.OUTRO; break;
            default:
            case 0: this.sexo = Sexo.INDEFINIDO; break;
        }
    }

    public int getSexoInt() { // Para uso no intercâmbio com a DB
        // public final int ordinal() --> https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/Enum.html
        //return this.sexo.ordinal();
        //Para evitar: java.lang.NullPointerException: Cannot invoke "com.gbl.classes.Sexo.getValor()" because "this.sexo" is null
        // Fica definido que se o sexo é 'null' ele estará apontando para Sexo.INDEFINIDO
        if (this.sexo == null) {
            this.sexo = Sexo.INDEFINIDO;
        }
        return this.sexo.getValor(); // Mais correto, pois a enum pode não seguir uma ordem...
    }

    public void setTipo(Tipo tipo){
        this.tipo = tipo;
    }

    public Tipo getTipo() {
        return this.tipo;
    }

    public void setTipoInt(int tipoInt){ // Para uso no intercâmbio com a DB
        switch(tipoInt) {
            case 1: this.tipo = Tipo.RECEPCIONISTA; break;
            case 2: this.tipo = Tipo.TECNICO_DE_ENFERMAGEM; break;
            case 3: this.tipo = Tipo.TECNICO_DE_RADIOLOGIA; break;
            case 4: this.tipo = Tipo.ENFERMEIRO; break;
            case 5: this.tipo = Tipo.MEDICO; break;
            case 6: this.tipo = Tipo.PACIENTE; break;

            case 99: this.tipo = Tipo.DESENVOLVEDOR; break;

            default:
            case 0: this.tipo = Tipo.INDEFINIDO; break;
        }
    }

    public int getTipoInt() { // Para uso no intercâmbio com a DB
        // public final int ordinal() --> https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/Enum.html
        //return this.Tipo.ordinal();
        //Para evitar: java.lang.NullPointerException: Cannot invoke "com.gbl.classes.Tipo.getValor()" because "this.tipo" is null
        // Fica definido que se o tipo é 'null' ele estará apontando para Tipo.INDEFINIDO
        if (this.tipo == null) {
            this.tipo = Tipo.INDEFINIDO;
        }
        return this.tipo.getValor(); // Mais correto, pois a enum pode não seguir uma ordem...
    }

    @Override
    public String toString() {
        //return "Pessoa: [id: "+this.id+", nome: "++", sobreNome: "++" datanas: "+this.datanas.super.toString()+", sexo: "+this.sexo.super.toString()+"]";
        //ISO_LOCAL_DATE
        //return "Pessoa: [id: "+this.id+", nome: "+this.nome+", sobreNome: "+this.sobreNome+" datanas: "+this.datanas.format(ISO_LOCAL_DATE())+", sexo: "+this.sexo.super.toString()+"]";
        return "Pessoa: [Crud: "+super.toString()+", nome: "+this.nome+", sobreNome: "+this.sobreNome+" datanas: ???, sexo: "+this.sexo.toString()+"]";

    }

}
