package br.com.delos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.delos.model.Item;
import br.com.delos.model.Section;
import br.com.delos.repository.ItemRepository;

@Service
public class ItemService {

	@Autowired
	private ItemRepository itemRepository;
	
	public List<Item> list() {
		return itemRepository.findAll();
	}

	public List<Item> list(Section section) {
		return itemRepository.listBySection(section);
	}

}
