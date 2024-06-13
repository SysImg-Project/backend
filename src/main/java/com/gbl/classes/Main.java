// UNISINOS
// Curso: Ciência da Computação - Híbrido
// Atividade Acadêmica - Estágio
// Módulo X
// Tarefa: SysImg
// Data: 02/06/2024
// Professor:
// Aluno: Rodrigo Pires

package com.gbl.classes;

import org.dcm4che3.data.Attributes;

public class Main {
    public static void main(String[] args) {

        ImportarDicom importer = new ImportarDicom();
        String filePath = "caminho/arquivo.dcm"; //Caminho para o arquivo DICOM
        Attributes attributes = importer.importarArquivoDicom(filePath);

        if (attributes != null) {
            System.out.println("Arquivo DICOM importado com sucesso!");
            System.out.println(attributes.toString());
        }
        else System.out.println("Falha ao importar arquivo DICOM!");
    }
}
