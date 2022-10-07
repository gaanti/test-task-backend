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
import java.util.Optional;
import java.util.Set;

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
	private @NotNull Item addItem(@RequestBody Object response) {
		Item item1 = new Item();
		LinkedHashMap items = (LinkedHashMap) ((LinkedHashMap) response).get("item");
		item1.setName((String) (items).get("name"));
		item1.setImageUrl((String) (items).get("imageUrl"));
		item1.setWeightInGrams(Integer.parseInt(String.valueOf((items).get("weightInGrams"))));
		item1.setCount(Integer.parseInt(String.valueOf((items).get("count"))));
		Size size = new Size();
		size.setWidth(Integer.parseInt(String.valueOf(((LinkedHashMap<String, LinkedHashMap>) items).get("size").get("width"))));
		size.setHeight(Integer.parseInt(String.valueOf(((LinkedHashMap<String, LinkedHashMap>) items).get("size").get("height"))));
		item1.setSize(size);

		return itemRepository.save(item1);

	}
	@PutMapping("/edit/{id}")
	private Item editItem(@RequestBody Object item, @PathVariable("id") Long id) {
		Optional<Item> updateItem1 =  itemRepository.findById(id);
		if (updateItem1.isPresent()) {
			Item updateItem = updateItem1.get();
			LinkedHashMap items = (LinkedHashMap) ((LinkedHashMap) item).get("item");
			updateItem.setName((String) (items).get("name"));
			updateItem.setImageUrl((String) (items).get("imageUrl"));
			updateItem.setWeightInGrams(Integer.parseInt(String.valueOf((items).get("weightInGrams"))));
			updateItem.setCount(Integer.parseInt(String.valueOf((items).get("count"))));
			Size size = new Size();
			size.setWidth(Integer.parseInt(String.valueOf(((LinkedHashMap<String, LinkedHashMap>) items).get("size").get("width"))));
			size.setHeight(Integer.parseInt(String.valueOf(((LinkedHashMap<String, LinkedHashMap>) items).get("size").get("height"))));
			updateItem.setSize(size);

			return itemRepository.save(updateItem);
		}
	}
	@DeleteMapping("/delete/{id}")
	private boolean deleteItem(@PathVariable("id") Long id){
		itemRepository.deleteById(id);
		return true;
	}

	@PostMapping("/comment/add/{itemId}")
	private ResponseEntity<Comment> addComment(@PathVariable(value = "itemId")Long itemId, @RequestBody Object commentRequest) throws Exception {
		String description = (String) ((LinkedHashMap<String, LinkedHashMap>) commentRequest).get("comment").get("description");
		Comment inGoingComment = new Comment();
		 inGoingComment.setDescription(description);
		Comment comment = itemRepository.findById(itemId).map(tutorial -> {
			Set<Comment> commentSet = tutorial.getComments();
			commentSet.add(inGoingComment);
			tutorial.getComments().add(inGoingComment);
			return commentRepository.save(inGoingComment);
		}).orElseThrow(() -> new Exception("Not found Tutorial with id = " + itemId));
		return new ResponseEntity<>(comment, HttpStatus.CREATED);
	}
	@DeleteMapping("/comment/delete/{id}")
	private boolean deleteComment(@PathVariable("id") Long id){
		commentRepository.deleteById(id);
		return true;
	}
}
