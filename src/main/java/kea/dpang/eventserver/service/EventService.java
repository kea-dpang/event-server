package kea.dpang.eventserver.service;

import kea.dpang.eventserver.dto.request.ItemEventCreateDto;
import kea.dpang.eventserver.dto.request.SellerEventCreateDto;
import kea.dpang.eventserver.dto.response.AllEventGetDto;
import kea.dpang.eventserver.dto.response.ItemEventGetDto;
import kea.dpang.eventserver.dto.response.SellerEventGetDto;

import java.util.List;

public interface EventService {
    //상품 이벤트 목록 조회(사용자)
    List<ItemEventGetDto> getItemEvents();
    //판매처 이벤트 목록 조회(사용자)
    List<SellerEventGetDto> getSellerEvents();
    //이벤트 목록 조회(관리자)
    List<AllEventGetDto> getAllEvent();
    //상품 이벤트 상세 조회(관리자)
    ItemEventCreateDto getItemEventDetail(Long id);
    //판매처 이벤트 상세 조회(관리자)
    SellerEventCreateDto getSellerEventDetail(Long id);
    //상품 이벤트 등록(관리자)
    void createItemEvent(ItemEventCreateDto itemEvent);
    //판매처 이벤트 등록(관리자)
    void createSellerEvent(SellerEventCreateDto sellerEvent);
    //상품 이벤트 수정(관리자)
    void updateItemEvent(Long id, ItemEventCreateDto itemEvent);
    //판매처 이벤트 수정(관리자)
    void updateSellerEvent(Long id, SellerEventCreateDto sellerEvent);
    //이벤트 삭제(관리자)
    void deleteEvent(List<Long> ids);
}
