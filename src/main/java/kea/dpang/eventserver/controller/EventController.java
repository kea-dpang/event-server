package kea.dpang.eventserver.controller;

import io.swagger.v3.oas.annotations.Parameter;
import kea.dpang.eventserver.base.BaseResponse;
import kea.dpang.eventserver.base.SuccessResponse;
import kea.dpang.eventserver.dto.EventDto;
import kea.dpang.eventserver.dto.ItemEventDto;
import kea.dpang.eventserver.dto.SellerEventDto;
import kea.dpang.eventserver.dto.request.RequestItemEventDto;
import kea.dpang.eventserver.dto.request.RequestSellerEventDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * 이벤트 서비스 컨트롤러 인터페이스
 * @author Tomas
 */
public interface EventController {

    /**
     * API
     * GET : 사용자 - 상품 이벤트 목록 조회
     * @param pageable 페이지네이션을 위한 객체
     * @return 응답 코드(200), 상품 이벤트 목록
     */
    ResponseEntity<SuccessResponse<Page<ItemEventDto>>> getItemEventList(Pageable pageable);

    /**
     * API
     * GET : 사용자 - 판매처 이벤트 목록 조회
     * @param pageable 페이지네이션을 위한 객체
     * @return 응답 코드(200), 판매처 이벤트 목록
     */
    ResponseEntity<SuccessResponse<Page<SellerEventDto>>> getSellerEventList(Pageable pageable);

    /**
     * API
     * GET : 관리자 - 전체 이벤트 목록 조회
     * @param pageable 페이지네이션을 위한 객체
     * @return 응답 코드(200), 상품 이벤트 목록
     */
    ResponseEntity<SuccessResponse<Page<EventDto>>> getAllEventList(Pageable pageable);

    /**
     * API
     * POST : 관리자 - 상품 이벤트 등록
     * @param itemEventDto 등록할 상품 이벤트에 대한 정보
     * @return 응답 코드(201)
     */
    ResponseEntity<BaseResponse> createItemEvent(RequestItemEventDto itemEventDto);

    /**
     * API
     * POST : 관리자 - 판매처 이벤트 등록
     * @param sellerEventDto 등록할 판매처 이벤트에 대한 정보
     * @return 응답 코드(201)
     */
    ResponseEntity<BaseResponse> postSellerEvent(RequestSellerEventDto sellerEventDto);

    /**
     * API
     * PUT : 관리자 - 상품 이벤트 수정
     * @param id 수정할 상품 이벤트의 ID
     * @param itemEventDto 상품 이벤트의 수정 내용
     * @return 응답 코드(200)
     */
    ResponseEntity<BaseResponse> updateItemEvent(Long id, ItemEventDto itemEventDto);

    /**
     * API
     * PUT : 관리자 - 판매처 이벤트 수정
     * @param id 수정할 판매처 이벤트의 ID
     * @param request 판매처 이벤트의 수정 내용
     * @return 응답 코드(200)
     */
    ResponseEntity<BaseResponse> putSellerEvent(Long id, SellerEventDto request);

    /**
     * API
     * DELETE : 관리자 - 이벤트 삭제
     * @param ids 삭제할 이벤트의 ID 목록
     * @return 응답 코드(200)
     */
    ResponseEntity<BaseResponse> deleteEvents(List<Long> ids);

    /**
     * API
     * GET : 상품 서비스 로부터 상품 ID에 해당하는 상품 이름을 반환받는다.
     * @param itemId 이름을 반환받을 상품의 ID
     * @return 응답 코드(200), 상품의 이름
     */
    ResponseEntity<SuccessResponse<String>> getItemName(Long itemId);

    /**
     * API
     * GET : 판매처 서비스 로부터 판매처 ID에 해당하는 판매처 이름을 반환받는다.
     * @param sellerId 이름을 반환받을 판매처의 ID
     * @return 응답 코드(200), 판매처의 이름
     */
    ResponseEntity<SuccessResponse<String>> getSellerName(Long sellerId);
}
