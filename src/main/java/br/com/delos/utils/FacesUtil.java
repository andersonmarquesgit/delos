package br.com.delos.utils;

import java.io.File;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.ResourceBundle;

import javax.faces.FactoryFinder;
import javax.faces.application.Application;
import javax.faces.application.ApplicationFactory;
import javax.faces.application.FacesMessage;
import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.el.ValueBinding;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.delos.utils.core.BeanSuportFactory;

public final class FacesUtil {
	
	private static String nomeResourceBundle = BeanSuportFactory
			.obterProperty("resources.mensagens");
	
//	public static void sendRedirect(String url) {
//		ExternalContext ec = obterContexto().getExternalContext();
//		try {
//			ec.redirect(url + ".jsf?faces-redirect=true");
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
	
	public static String sendRedirect(String url) {
		return url + ".jsf?faces-redirect=true";
	}
	
	public static ServletContext getServletContext() {
		return ((ServletContext) FacesContext.getCurrentInstance()
				.getExternalContext().getContext());
	}

	public static void setManagedBeanInSession(String beanName,
			Object managedBean) {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.put(beanName, managedBean);
	}

	public static Object getRequestParameter(Object name) {
		return FacesContext.getCurrentInstance().getExternalContext()
				.getRequestParameterMap().get(name);
	}

	public static void setRequestParameter(String key, Object value) {
		HttpServletRequest req = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();
		req.setAttribute(key, value);
	}

	public static Object setSessionAttribute(String key, Object value) {
		return FacesContext.getCurrentInstance().getExternalContext()
				.getSessionMap().put(key, value);
	}

	public static Object getSessionAttribute(String key) {
		return FacesContext.getCurrentInstance().getExternalContext()
				.getSessionMap().get(key);
	}

	private static Application getApplication() {
		ApplicationFactory appFactory = (ApplicationFactory) FactoryFinder
				.getFactory("javax.faces.application.ApplicationFactory");
		return appFactory.getApplication();
	}

	private static ValueBinding getValueBinding(String el) {
		return getApplication().createValueBinding(el);
	}

	public static HttpServletRequest getServletRequest() {
		return ((HttpServletRequest) FacesContext.getCurrentInstance()
				.getExternalContext().getRequest());
	}

	public static HttpServletResponse getServletResponse() {
		return ((HttpServletResponse) FacesContext.getCurrentInstance()
				.getExternalContext().getResponse());
	}

	private static Object getElValue(String el) {
		return getValueBinding(el).getValue(FacesContext.getCurrentInstance());
	}

	private static String getJsfEl(String value) {
		return "#{" + value + "}";
	}

	public static String getRealPath(String pasta) {
		return getServletContext().getRealPath(File.separator + pasta);
	}
	
	public static void adicionarAtributoFlash(String nome, Object objeto) {
		obterContexto().getExternalContext().getFlash().put(nome, objeto);
	}

	public String getContextPath() {
		return getServletContext().getContextPath();
	}

	public static Object obterAtributoFlash(String nome) {
		return obterContexto().getExternalContext().getFlash().get(nome);
	}
	
	public static void adicionarMensagem(String texto) {
		registrarFacesMessage(obterTexto(texto), FacesMessage.SEVERITY_INFO);
	}

	public static void adicionarMensagem(String texto, Object[] params) {
		registrarFacesMessage(obterTexto(texto, params),
				FacesMessage.SEVERITY_INFO);
	}

	public static void adicionarAviso(String texto) {
		registrarFacesMessage(obterTexto(texto), FacesMessage.SEVERITY_WARN);
	}

	public static void adicionarAviso(String texto, Object[] params) {
		registrarFacesMessage(obterTexto(texto, params),
				FacesMessage.SEVERITY_WARN);
	}

	public static void adicionarErro(String texto) {
		registrarFacesMessage(obterTexto(texto), FacesMessage.SEVERITY_ERROR);
	}

	public static void adicionarErro(String texto, Object[] params) {
		registrarFacesMessage(obterTexto(texto, params),
				FacesMessage.SEVERITY_ERROR);
	}

	public static void manterMensagensNoFlash() {
		obterContexto().getExternalContext().getFlash().setKeepMessages(true);
	}

	public static String obterTexto(String chave) {
		ResourceBundle bundle = obterResourceBundle();
		try {
			return bundle.getString(chave);
		} catch (Exception e) {
		}
		return chave;
	}

	public static String obterTexto(String chave, Object[] params) {
		String texto = obterTexto(chave);
		return MessageFormat.format(texto, params);
	}

	public static NavigationHandler obterNavigationHandler() {
		return obterContexto().getApplication().getNavigationHandler();
	}

	private static ResourceBundle obterResourceBundle() {
		return ResourceBundle.getBundle(nomeResourceBundle);
	}

	private static void registrarFacesMessage(String texto,
			FacesMessage.Severity severidade) {
		FacesMessage mensagem = new FacesMessage();

		mensagem.setSummary(texto);
		mensagem.setSeverity(severidade);

		obterContexto().addMessage(null, mensagem);
	}
	
	public static InputStream obterInputStreamDeRecurso(String resourcePath) {
		return obterContexto().getExternalContext().getResourceAsStream(resourcePath);
	}

	public static Flash obterFlashScope() {
		return FacesContext.getCurrentInstance().getExternalContext().getFlash();
	   }
	
	public static FacesContext obterContexto() {
		return FacesContext.getCurrentInstance();
	}
	
}
