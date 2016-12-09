package br.com.delos.utils;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.MultiPartEmail;
import org.apache.commons.mail.SimpleEmail;

import br.com.delos.model.Reclamation;

public class EmailUtil {
	
	private String smtp;
	private Integer portaSmtp;
	private String usuario;
	private String senha;
	private String mensagem;
	private String assunto;
	private String emailRemetente;
	private String nomeRemetente;
	private String emailDestinatario;
	private String nomeDestinatario;
	private String nomePropriedade =  "/application.properties";

	public EmailUtil(String mensagem, String assunto,
			String emailDestinatario, String nomeDestinatario) {
		
		PropertiesLoader prop = new PropertiesLoader(nomePropriedade);
		this.smtp = prop.getValor(Constantes.EMAIL_SMTP);
		this.portaSmtp = 587;
		this.usuario = prop.getValor(Constantes.EMAIL_USUARIO);
		this.senha = prop.getValor(Constantes.EMAIL_SENHA);
		this.mensagem = mensagem;
		this.assunto = assunto;
		this.emailRemetente = prop.getValor(Constantes.EMAIL_REMETENTE);
		this.nomeRemetente = prop.getValor(Constantes.EMAIL_REMETENTE_NOME);
		this.emailDestinatario = emailDestinatario;
		this.nomeDestinatario = nomeDestinatario;
		
	}

	public EmailUtil() throws EmailException, MalformedURLException {
		enviaEmailSimples();
		enviaEmailComAnexo();
//		enviaEmailFormatoHtml();
	}
	
	/**
	 * envia email simples(somente texto)
	 * @throws EmailException
	 */
	public void enviaEmailSimples() throws EmailException {
		SimpleEmail email = new SimpleEmail();
		email.setHostName(this.smtp); // o servidor SMTP para envio do e-mail
		email.setAuthentication(this.usuario, this.senha);
		email.addTo(this.emailDestinatario, this.nomeDestinatario); //destinatário
		email.setFrom(this.emailRemetente, this.nomeRemetente); // remetente
		email.setSubject(this.assunto); // assunto do e-mail
		email.setMsg(this.mensagem); //conteudo do e-mail
		email.setSmtpPort(this.portaSmtp);
		email.setSSLOnConnect(true);
		email.setStartTLSEnabled(true);
		email.send();	
	}
	
	/**
	 * envia email com arquivo anexo
	 * @throws EmailException
	 */
	public void enviaEmailComAnexo() throws EmailException{
		// cria o anexo 1.
		EmailAttachment anexo1 = new EmailAttachment();
		anexo1.setPath("teste/teste.txt"); //caminho do arquivo (RAIZ_PROJETO/teste/teste.txt)
		anexo1.setDisposition(EmailAttachment.ATTACHMENT);
		anexo1.setDescription("Exemplo de arquivo anexo");
		anexo1.setName("teste.txt");		
		// cria o anexo 2.
//		EmailAttachment anexo2 = new EmailAttachment();
//		anexo2.setPath("teste/teste2.jsp"); //caminho do arquivo (RAIZ_PROJETO/teste/teste2.jsp)
//		anexo2.setDisposition(EmailAttachment.ATTACHMENT);
//		anexo2.setDescription("Exemplo de arquivo anexo");
//		anexo2.setName("teste2.jsp");		
		// configura o email
		MultiPartEmail email = new MultiPartEmail();
		email.setHostName("smtp.gmail.com"); // o servidor SMTP para envio do e-mail
		email.addTo("andersonmarques.ci@gmail.com", "Guilherme"); //destinatário
		email.setFrom("andersonmarques.ci@gmail.com", "Eu"); // remetente
		email.setSubject("Teste -&gt; Email com anexos"); // assunto do e-mail
		email.setMsg("Teste de Email utilizando commons-email"); //conteudo do e-mail
		email.setAuthentication("andersonmarques.ci", "awexqyswjlgumyuy");
		email.setSmtpPort(this.portaSmtp);
		email.setSSLOnConnect(true);
		email.setStartTLSEnabled(true);
		// adiciona arquivo(s) anexo(s)
		email.attach(anexo1);
//		email.attach(anexo2);
		// envia o email
		email.send();
	}
	/**
	 * Envia email no formato HTML
	 * @throws EmailException 
	 * @throws MalformedURLException 
	 */
	public void enviaEmailFormatoHtml(Reclamation reclamation) throws EmailException, MalformedURLException {
		HtmlEmail email = new HtmlEmail();
		// adiciona uma imagem ao corpo da mensagem e retorna seu id
		URL url = new URL("http://www.botecodigital.info/wp-content/themes/boteco/img/logo.png");
		
//		String logoEmpresa = email.embed(new File(Constantes.CAMINHO_LOGO_EMPRESA));
		
		String cid = email.embed(url, "Logo Empresa");	
		// configura a mensagem para o formato HTML
		Object[] params = new Object[5];
		params[0] = cid;
		params[1] = DateUtil.getDataFormatada(reclamation.getDateInclusion());
		params[2] = reclamation.getCustomer().getName();
		params[3] = reclamation.getNumber();
		params[4] = reclamation.getDescription();
		StringBuilder builder = new StringBuilder();
		builder.append(FacesUtil.obterTexto(MsgConstantes.EMAIL_RECLAMACAO_PRIMEIRA_ETAPA, params));
	    email.setHtmlMsg(builder.toString());
		// configure uma mensagem alternativa caso o servidor não suporte HTML
		email.setTextMsg("Seu servidor de e-mail não suporta mensagem HTML");
		email.setHostName(this.smtp); // o servidor SMTP para envio do e-mail
		email.addTo(this.emailDestinatario, this.nomeDestinatario); //destinatário
		email.setFrom(this.emailRemetente, this.nomeRemetente); // remetente
		email.setSubject(this.assunto); // assunto do e-mail
		email.setAuthentication(this.usuario, this.senha);
		email.setSmtpPort(this.portaSmtp);
		email.setSSLOnConnect(false);
		email.setStartTLSEnabled(true);
		// envia email
		email.send();
	}
}
