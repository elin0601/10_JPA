package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public void saveItem(Item item) {
        itemRepository.save(item);
    }
    
    @Transactional
    public void updateItem(Long itemId, String name, int price, int stockQuantity) { // param : 파라미터로 넘어온 준영속 상태의 엔티티
    	Item findItem = itemRepository.findOne(itemId); // 같은 엔티티를 조회
    	
    	findItem.setName(name);
    	findItem.setPrice(price);
    	findItem.setStockQuantity(stockQuantity); // 데이터 수정
    	
    }

//    @Transactional
//    public void updateItem(Long itemId, String name, int price, int stockQuantity) {
//        Item item = itemRepository.findOne(itemId);
//        item.setName(name);
//        item.setPrice(price);
//        item.setStockQuantity(stockQuantity);
//    }

    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }

}
