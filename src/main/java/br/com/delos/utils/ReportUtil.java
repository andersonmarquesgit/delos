package br.com.delos.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import br.com.delos.config.RepositoryConfig;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;

public abstract class ReportUtil {
	
	public static StreamedContent gerarStreamedRelatorio(HashMap parametrosRelatorio, String diretorioRelatorio)
			throws Exception {

		StreamedContent arquivoRetorno = null;

		try {
			Connection conexao = getConexao();
			InputStream reportStream = ReportUtil.class.getResourceAsStream(diretorioRelatorio);
			JasperDesign jd = JRXmlLoader.load(reportStream);
			JasperReport jr = JasperCompileManager.compileReport(jd);
			JasperPrint jp = JasperFillManager.fillReport(jr,
					parametrosRelatorio, conexao);

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			JRPdfExporter exporter = new JRPdfExporter();
			exporter.setExporterInput(new SimpleExporterInput(jp));
			exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(baos));
			exporter.exportReport();
			
			ByteArrayInputStream bais = new ByteArrayInputStream(
					baos.toByteArray());

			arquivoRetorno = new DefaultStreamedContent(bais, "pdf", "users.pdf");

		} catch (JRException e) {
			e.printStackTrace();
			throw new Exception("Não foi possível gerar o relatório.", e);
		} catch (FileNotFoundException e) {
			throw new Exception("Arquivo do relatório não encontrado.", e);
		}
		return arquivoRetorno;
	}

	public static byte[] gerarBytesRelatorio(HashMap parametros, String diretorioRelatorio) throws Exception {
		try {
			Connection conexao = getConexao();
			InputStream reportStream = ReportUtil.class.getResourceAsStream(diretorioRelatorio);
			JasperDesign jd = JRXmlLoader.load(reportStream);
			JasperReport jr = JasperCompileManager.compileReport(jd);
			JasperPrint jp = JasperFillManager.fillReport(jr,
					parametros, conexao);
			return JasperExportManager.exportReportToPdf(jp); 
		} catch (JRException e) {
			throw new Exception("Não foi possível gerar o relatório.", e);
		} catch (FileNotFoundException e) {
			throw new Exception("Arquivo do relatório não encontrado.", e);
		}
	}
	
	private static Connection getConexao() throws Exception {
		java.sql.Connection conexao = null;
		try {
			Context initContext = new InitialContext();
			Context envContext = (Context) initContext
					.lookup("java:/comp/env/");
			
			ApplicationContext context = new AnnotationConfigApplicationContext(RepositoryConfig.class);
			javax.sql.DataSource ds = (javax.sql.DataSource) context.getBean("dataSource");
			conexao = (java.sql.Connection) ds.getConnection();
		} catch (NamingException e) {
			throw new Exception("Não foi possível encontrar o nome da conexão do banco.", e);
		} catch (SQLException e) {
			throw new Exception("Ocorreu um erro de SQL.", e);
		}
		return conexao;
	}
}
