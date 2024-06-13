// UNISINOS
// Curso: Ciência da Computação - Híbrido
// Atividade Acadêmica - Estágio
// Módulo X
// Tarefa: SysImg
// Data: 04/06/2024
// Professor:
// Aluno: Rodrigo Pires


package com.gbl.classes;

import org.dcm4che3.data.Attributes;
import org.dcm4che3.imageio.plugins.dcm.DicomImageReadParam;
import org.dcm4che3.io.DicomInputStream;
import org.dcm4che3.util.StreamUtils;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

public class ConverterDicomParaJpeg {

    public void converterDicomParaJpeg(String dicomFilePath, String jpegFilePath) {
        File dicomFile = new File(dicomFilePath);
        File jpegFile = new File(jpegFilePath);

        try (DicomInputStream dicomInputStream = new DicomInputStream(dicomFile);
            ImageInputStream iis = ImageIO.createImageInputStream(dicomInputStream)){
            Iterator<ImageReader> readers = ImageIO.getImageReadersByFormatName("DICOM");
            ImageReader reader = readers.next();

            DicomImageReadParam param = (DicomImageReadParam) reader.getDefaultReadParam();
            reader.setInput(iis, true);
            BufferedImage image = reader.read(0, param);


            ImageIO.write(image, "jpeg", jpegFile);
            System.out.println("Imagem salva em: " +jpegFilePath);

        } catch (IOException e){
            e.printStackTrace();
            System.out.println("Erro ao converter imagem no formato Dicom para Jpeg: " + e.getMessage());
        }

    }

}
