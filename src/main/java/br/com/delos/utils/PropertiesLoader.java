package br.com.delos.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesLoader {
	private Properties props;
    
    public PropertiesLoader(String nomeDoProperties) {
		props = new Properties();
		InputStream in = this.getClass().getResourceAsStream(nomeDoProperties);
		try{
			props.load(in);
			in.close();
		}
		catch(IOException e){e.printStackTrace();}
	}
    
	public PropertiesLoader(){
    }
    public String getValor(String chave){
            return (String)props.getProperty(chave);
    }
}
