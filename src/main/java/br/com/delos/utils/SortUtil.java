package br.com.delos.utils;

import java.text.Collator;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.comparators.ComparableComparator;
import org.apache.commons.collections.comparators.ComparatorChain;

public class SortUtil {
	private SortUtil() {
	}

	/**
	 * Ordena a lista de acordo com as propriedades informadas.
	 *
	 * @param lista Parâmetro a ser ordenado.
	 * @param desc Informar se crescente ou decrescente.
	 * @param properties Propriedades que serão ordenadas.
	 */
	@SuppressWarnings("unchecked")
	public static <T> void sortList(List<T> lista, boolean desc, String... properties) {
	        Comparator<T> comparator = new Comparator<T>() {
	                @Override
	                public int compare(T first, T second) {
	                        if (first != null && second != null) {
	                                if (first instanceof String && second instanceof String) {
	                                        return Collator.getInstance(new Locale("pt/BR")).compare((String)first, (String) second);
	                                }
	                                return ComparableComparator.getInstance().compare(first, second);
	                        }
	                        return -1;
	                }
	        };
	        ComparatorChain comparatorChain = new ComparatorChain();
	        for (String propertie : properties) {
	                comparatorChain.addComparator(new BeanComparator(propertie, comparator), desc);
	        }
	        Collections.sort(lista, comparatorChain);
	}
}
