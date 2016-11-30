package br.com.delos.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.delos.model.Item;
import br.com.delos.model.ItemCriticalAnalisys;
import br.com.delos.model.Result;
import br.com.delos.model.Section;
import br.com.delos.model.SectionCriticalAnalisys;

@Service
public class ItemCriticalAnalisysService {

	@Autowired
	private ItemService itemService;
	
	public List<ItemCriticalAnalisys> constructItemsCriticalAnalisys(
			SectionCriticalAnalisys sectionCriticalAnalisys, Section section) {
		List<ItemCriticalAnalisys> items = new ArrayList<ItemCriticalAnalisys>();
		for (Item item : itemService.list(section)) {
			ItemCriticalAnalisys itemCriticalAnalisys = new ItemCriticalAnalisys();
			itemCriticalAnalisys.setSectionCriticalAnalisys(sectionCriticalAnalisys);
			itemCriticalAnalisys.setItem(item);
			itemCriticalAnalisys.setResult(new Result());
			items.add(itemCriticalAnalisys);
		}
		return items;
	}

}
