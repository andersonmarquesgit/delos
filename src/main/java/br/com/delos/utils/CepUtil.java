package br.com.delos.utils;

import br.com.delos.model.Address;
import br.com.delos.utils.webservices.WebServiceCep;

public class CepUtil {
	
    public static Address buscarEndereco(String cep) {
        WebServiceCep webServiceCep = WebServiceCep.searchCep(cep);
        Address endereco = new Address();
        
        if(webServiceCep.wasSuccessful()) {
        	endereco.setAddress(webServiceCep.getLogradouroFull());
        	endereco.setBairro(webServiceCep.getBairro());
//        	endereco.setCidade(webServiceCep.getCidade());
//        	endereco.setUf(webServiceCep.getUf());
        	endereco.setCep(webServiceCep.getCep());
        }
        return endereco;
    }
}
