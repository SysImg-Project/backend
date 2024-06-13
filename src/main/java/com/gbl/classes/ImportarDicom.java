// UNISINOS
// Curso: Ciência da Computação - Híbrido
// Atividade Acadêmica - Estágio
// Módulo X
// Tarefa: SysImg
// Data: 02/06/2024
// Professor:
// Aluno: Rodrigo Pires

package com.gbl.classes;

import org.dcm4che3.io.DicomInputStream;
import org.dcm4che3.data.Attributes;
import java.io.File;
import java.io.IOException;


public class ImportarDicom {

    public Attributes importarArquivoDicom(String arquivo) {
        File dicomFile = new File(arquivo);
        Attributes dicomAttributes = null;

        try(DicomInputStream din = new DicomInputStream(dicomFile)){
            dicomAttributes = din.readDataset(-1, -1);

        }catch(IOException e){
            System.err.println("Erro ao abrir arquivo DICOM: " + e.getMessage());
        }
        return dicomAttributes;
    }


}
