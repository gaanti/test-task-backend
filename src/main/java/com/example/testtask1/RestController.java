package com.example.testtask1;

import com.example.testtask1.domain.Comment;
import com.example.testtask1.domain.Item;
import com.example.testtask1.domain.Size;
import com.example.testtask1.repositories.CommentRepository;
import com.example.testtask1.repositories.ItemRepository;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;

@org.springframework.web.bind.annotation.RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/items")
public class RestController {
	@Autowired
	private ItemRepository itemRepository;
	@Autowired
	private CommentRepository commentRepository;


	@GetMapping("/all")
	private Page<Item> getItems() {
		int currentPage = 0;
		Pageable pageable1 = PageRequest.of(currentPage, 8, Sort.by(Sort.Direction.DESC, "name"));

		return itemRepository.findAll(pageable1);
	}
	@PostMapping("/create")
	private @NotNull Item addItem(@RequestBody Object items) {
		Item item1 = new Item();
		item1.setName((String) ((LinkedHashMap) items).get("name"));
		item1.setImageUrl((String) ((LinkedHashMap) items).get("imageUrl"));
		item1.setCount((Integer) ((LinkedHashMap) items).get("count"));
		Size size = new Size();
		size.setWidth((Integer) ((LinkedHashMap<String, LinkedHashMap>) items).get("size").get("width"));
		size.setHeight((Integer) ((LinkedHashMap<String, LinkedHashMap>) items).get("size").get("height"));
		item1.setSize(size);

		return itemRepository.save(item1);
	}
	@PutMapping("/edit/{id}")
	private Item editItem(@RequestBody Item item, @PathVariable("id") Long id) {
		Item updateItem =  itemRepository.findById(id).orElseThrow();
			updateItem.setCount(item.getCount());
			updateItem.setImageUrl(item.getImageUrl());
			updateItem.setName(item.getName());
			updateItem.setSize(item.getSize());
			return itemRepository.save(updateItem);
	}
	@DeleteMapping("/delete/{id}")
	private ResponseEntity<HttpStatus> deleteItem(@PathVariable("id") Long id){
		itemRepository.deleteById(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@PostMapping("/add-comment/{itemId}")
	private ResponseEntity<Comment> addComment(@PathVariable(value = "itemId")Long itemId, @RequestBody Comment commentRequest) throws Exception {
		Comment comment = itemRepository.findById(itemId).map(tutorial -> {
			tutorial.getComments().add(commentRequest);
			return commentRepository.save(commentRequest);
		}).orElseThrow(() -> new Exception("Not found Tutorial with id = " + itemId));
		return new ResponseEntity<>(comment, HttpStatus.CREATED);
	}
	@DeleteMapping("/delete-comment/{id}")
	private Long deleteComment(@PathVariable("id") Long id){
		commentRepository.deleteById(id);
		return id;
	}
}
