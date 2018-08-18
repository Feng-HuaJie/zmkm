package com.sjq.zmkm.service;

import java.util.ArrayList;
import com.sjq.zmkm.dao.entity.Card;
import com.sjq.zmkm.pojo.Page;

public interface CardService {

	ArrayList<Card> listAll() ;
	
	void addCard(Card b);

	void updateCard(Card b);
	
	void disable(int cardId);

	void enable(int cardId);

	Page page(int pageNum, int pageSize);
}
