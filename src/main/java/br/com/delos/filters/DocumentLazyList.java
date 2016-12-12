package br.com.delos.filters;

import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import br.com.delos.model.Document;
import br.com.delos.service.DocumentService;

public class DocumentLazyList extends LazyDataModel<Document> {

	private static final long serialVersionUID = -4091852855590985867L;

	private Long idTipoDocumento;
	private DocumentService documentoServico;

	public DocumentLazyList(Long idTipoDocumento,
			DocumentService documentoServico) {
		this.idTipoDocumento = idTipoDocumento;
		this.documentoServico = documentoServico;
	}

	@Override
	public List<Document> load(int first, int pageSize, String sortField,
			SortOrder sortOrder, Map<String, Object> filters) {
		Sort sort;
		if (sortField == null) {
			sort = new Sort(new Sort.Order(Sort.Direction.ASC, "id"));
		} else {
			if (SortOrder.ASCENDING.equals(sortOrder)) {
				sort = new Sort(new Sort.Order(Sort.Direction.ASC, sortField));
			} else {
				sort = new Sort(new Sort.Order(Sort.Direction.DESC, sortField));
			}
		}
		Pageable pagina = new PageRequest(first/pageSize, pageSize, sort);
		
		Long dataSize = this.documentoServico.countByTipoDocumento(idTipoDocumento);
        this.setRowCount(dataSize.intValue());

		return this.documentoServico.listByTipoDocumento(idTipoDocumento, pagina);
	}

	@Override
	public Object getRowKey(Document documento) {
		return documento.getId();
	}

	@Override
	public Document getRowData(String idDocumento) {
		return this.documentoServico.findOn(Long.valueOf(idDocumento));
	}
	
	@Override
	public void setRowIndex(final int rowIndex) {
		if (rowIndex == -1 || getPageSize() == 0) {
			super.setRowIndex(-1);
		} else {
			super.setRowIndex(rowIndex % getPageSize());
		}
	}
}
