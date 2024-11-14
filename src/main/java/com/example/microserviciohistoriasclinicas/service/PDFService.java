package com.example.microserviciohistoriasclinicas.service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.microserviciohistoriasclinicas.model.NotaEvolucionEntity;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class PDFService {
    public byte[] generatePdfReportNotaEvolucion(NotaEvolucionEntity notaEvolucionEntity) throws JRException {
        InputStream jrxmlInputStream = getClass().getClassLoader().getResourceAsStream("reports/nota_evolucion.jrxml");

        if (jrxmlInputStream == null) {
            throw new JRException("No se pudo encontrar el archivo .jrxml en el classpath.");
        }
        JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlInputStream);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("cambiosPacienteResultadosTratamiento", notaEvolucionEntity.getCambiosPacienteResultadosTratamiento());
        JRBeanCollectionDataSource emptyDataSource = new JRBeanCollectionDataSource(java.util.Collections.emptyList());
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, emptyDataSource);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);

       return outputStream.toByteArray();
    }
}
