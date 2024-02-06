package kea.dpang.eventserver.service;

import kea.dpang.eventserver.dto.SellerEventDto;
import kea.dpang.eventserver.dto.EventDto;
import kea.dpang.eventserver.dto.request.RequestItemEventDto;
import kea.dpang.eventserver.dto.request.RequestSellerEventDto;
import kea.dpang.eventserver.dto.response.ResponseItemEventDto;
import kea.dpang.eventserver.dto.response.ResponseItemEventListDto;
import kea.dpang.eventserver.dto.response.ResponseSellerEventDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EventService {

    Page<ResponseItemEventListDto> getItemEventList(Pageable pageable);
    //상품 이벤트 목록 조회(사용자)
    //List<ItemEventDto> getItemEvents();

    Page<SellerEventDto> getSellerEventList(Pageable pageable);
    //판매처 이벤트 목록 조회(사용자)
    //List<SellerEventDto> getSellerEvents();

    //이벤트 목록 조회(관리자)
    Page<EventDto> getEventList(Pageable pageable);

    //상품 이벤트 상세 조회(관리자)
    ResponseItemEventDto getItemEvent(Long id);

    //판매처 이벤트 상세 조회(관리자)
    ResponseSellerEventDto getSellerEvent(Long id);

    List<Long> getSellerEventsBySeller(List<Long> sellerIds);

    //상품 이벤트 등록(관리자)
    void createItemEvent(RequestItemEventDto itemEvent);

    //판매처 이벤트 등록(관리자)
    void createSellerEvent(RequestSellerEventDto sellerEvent);

    //상품 이벤트 수정(관리자)
    void updateItemEvent(Long id, RequestItemEventDto itemEvent);

    //판매처 이벤트 수정(관리자)
    void updateSellerEvent(Long id, SellerEventDto sellerEvent);

    //이벤트 삭제(관리자)
    void deleteEvent(List<Long> ids);
}
