package com.sjq.zmkm.service;

import java.util.ArrayList;

import com.sjq.zmkm.controller.resDto.AwardResultDto;
import com.sjq.zmkm.exception.BusinessException;

public interface UserCardService {

	//void applyCard(UserCardMapping b) throws SjqException;

	//批量授予钥匙
	ArrayList<AwardResultDto> awarded(int cardId, String[] phoneStr) throws BusinessException;

}
