package com.algaworks.algafood.infrastructure.service.report;

import java.util.HashMap;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood.domain.service.VendaQueryService;
import com.algaworks.algafood.domain.service.VendaReportService;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class PdfVendaReportService implements VendaReportService {

	@Autowired
	private VendaQueryService vendaQueryService;
	
	@Override// Imputstream é o fluxo de dados do relatorio
	public byte[] emitirVendasDiarias(VendaDiariaFilter filtro, String timeOffset) {
		try {
			//retorna um fluxo de dados
			var inputStream = this.getClass().getResourceAsStream(
					"/relatorios/vendas-diarias.jasper");
			
			// Aqui é para aparecer em real os valores
			var parametros = new HashMap<String, Object>();
			parametros.put("REPORT_LOCALE", new Locale("pt", "BR"));
			
			var vendasDiarias = vendaQueryService.consultarVendasDiarias(filtro, timeOffset);
			var dataSource = new JRBeanCollectionDataSource(vendasDiarias);
			
			// jaspersprint é um objeto que representa um relatorio preenchido	
			var jasperPrint = JasperFillManager.fillReport(inputStream, parametros, dataSource);
		
			return JasperExportManager.exportReportToPdf(jasperPrint);// retorna um array de bytes
		} catch (Exception e) {
			throw new ReportException("Não foi possível emitir relatório de vendas diárias", e);
		}
	}

}