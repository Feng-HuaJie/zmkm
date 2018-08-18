package com.sjq.zmkm.service;

import java.util.ArrayList;

import com.sjq.zmkm.controller.resDto.AwardResultDto;
import com.sjq.zmkm.exception.BusinessException;

public interface VisitorsService {
	
	ArrayList<AwardResultDto> awarded(String phone,int cardId, String[] phoneStr) throws BusinessException;
}
