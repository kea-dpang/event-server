package kea.dpang.eventserver.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import kea.dpang.eventserver.base.BaseResponse;
import kea.dpang.eventserver.base.SuccessResponse;
import kea.dpang.eventserver.client.ItemServiceClient;
import kea.dpang.eventserver.client.SellerSerivceClient;
import kea.dpang.eventserver.dto.ItemEventDto;
import kea.dpang.eventserver.dto.SellerEventDto;
import kea.dpang.eventserver.dto.EventDto;
import kea.dpang.eventserver.dto.request.RequestItemEventDto;
import kea.dpang.eventserver.dto.request.RequestSellerEventDto;
import kea.dpang.eventserver.service.EventServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Event", description = "Event 서비스 API")
@RequestMapping("/api/events")
@RequiredArgsConstructor
@RestController
public class EventControllerImpl implements EventController{

    private final EventServiceImpl eventService;

    private final ItemServiceClient itemServiceClient;
    private final SellerSerivceClient sellerSerivceClient;

    @Override
    @GetMapping("/item")
    @Operation(summary = "사용자 기능 : 상품 이벤트 목록 조회", description = "사용자가 자사몰의 예정/진행중/종료 상품 이벤트를 모두 조회할 수 있습니다.")
    public ResponseEntity<SuccessResponse<Page<ItemEventDto>>> getItemEventList(
            Pageable pageable
    ){
        Page<ItemEventDto> itemEventDtos = eventService.getItemEventList(pageable);
        return ResponseEntity.ok(new SuccessResponse<>(200,"상품 이벤트 목록 조회가 완료되었습니다.",itemEventDtos));
    }

    @Override
    @GetMapping("/seller")
    @Operation(summary = "사용자 기능 : 판매처 이벤트 목록 조회", description = "사용자가 자사몰의 예정/진행중/종료 판매처 이벤트를 모두 조회할 수 있습니다.")
    public ResponseEntity<SuccessResponse<Page<SellerEventDto>>> getSellerEventList(
            Pageable pageable
    ){
        Page<SellerEventDto> sellerEventDtos = eventService.getSellerEventList(pageable);
        return ResponseEntity.ok(new SuccessResponse<>(200,"판매처 이벤트 목록 조회가 완료되었습니다.",sellerEventDtos));
    }

    @Override
    @GetMapping("/admin")
    @Operation(summary = "관리자 기능 : 이벤트 목록 조회", description = "관리자가 자사몰의 예정/진행중/종료 이벤트를 모두 조회할 수 있습니다.")
    public ResponseEntity<SuccessResponse<Page<EventDto>>> getAllEventList(
            Pageable pageable
    ){
        Page<EventDto> eventDtos = eventService.getEventList(pageable);
        return ResponseEntity.ok(new SuccessResponse<>(200,"모든 이벤트 목록 조회가 완료되었습니다.", eventDtos));
    }

    @Override
    @GetMapping("/item/{id}")
    @Operation(summary = "관리자 기능 : 상품 이벤트 상세 조회")
    public ResponseEntity<SuccessResponse<ItemEventDto>> getItemEvent(
            @PathVariable @Parameter(description = "상품 이벤트 ID") Long id
    ) {
        ItemEventDto itemEvent = eventService.getItemEvent(id);
        return ResponseEntity.ok(new SuccessResponse<>(200, "특정 상품 이벤트 조회가 완료되었습니다.", itemEvent));
    }

    @Override
    @GetMapping("/seller/{id}")
    @Operation(summary = "관리자 기능 : 판매처 이벤트 상세 조회")
    public ResponseEntity<SuccessResponse<SellerEventDto>> getSellerEvent(
            @PathVariable @Parameter(description = "판매처 이벤트 ID") Long id
    ) {
        SellerEventDto sellerEvent = eventService.getSellerEvent(id);
        return ResponseEntity.ok(new SuccessResponse<>(200, "특정 판매처 이벤트 조회가 완료되었습니다.", sellerEvent));
    }

    @Override
    @PostMapping("/item")
    @Operation(summary = "관리자 기능 : 상품 이벤트 등록", description = "관리자가 자사몰에 상품 이벤트를 등록할 수 있습니다.")
    public ResponseEntity<BaseResponse> createItemEvent(
            @RequestBody @Parameter(description = "상품 이벤트 생성 정보") RequestItemEventDto itemEventDto
    ){
        eventService.createItemEvent(itemEventDto);
        return ResponseEntity.ok(new BaseResponse(201,"상품 이벤트 생성이 완료되었습니다."));
    }

    @Override
    @PostMapping("/seller")
    @Operation(summary = "관리자 기능 : 판매처 이벤트 등록", description = "관리자가 자사몰에 판매처 이벤트를 등록할 수 있습니다.")
    public ResponseEntity<BaseResponse> postSellerEvent(
            @RequestBody @Parameter(description = "판매처 이벤트 생성 정보") RequestSellerEventDto sellerEventDto
    ){
        eventService.createSellerEvent(sellerEventDto);
        return ResponseEntity.ok(new BaseResponse(201, "판매처 이벤트 생성이 완료되었습니다."));
    }

    @Override
    @PutMapping("/item/{id}")
    @Operation(summary = "관리자 기능 : 상품 이벤트 수정", description = "관리자가 자사몰에 등록된 상품 이벤트를 수정할 수 있습니다.")
    public ResponseEntity<BaseResponse> updateItemEvent(
            @PathVariable @Parameter(description = "상품 이벤트 ID") Long id,
            @RequestBody @Parameter(description = "상품 이벤트 수정 정보") RequestItemEventDto itemEventDto
    ){
        eventService.updateItemEvent(id,itemEventDto);
        return ResponseEntity.ok(new BaseResponse(200, "상품 이벤트 수정이 완료되었습니다."));
    }

    @Override
    @PutMapping("/seller/{id}")
    @Operation(summary = "관리자 기능 : 판매처 이벤트 수정", description = "관리자가 자사몰에 등록된 관리자 이벤트를 수정할 수 있습니다.")
    public ResponseEntity<BaseResponse> putSellerEvent(
            @PathVariable @Parameter(description = "판매처 이벤트 ID") Long id,
            @RequestBody @Parameter(description = "판매처 이벤트 수정 정보") SellerEventDto request
    ){
        eventService.updateSellerEvent(id,request);
        return ResponseEntity.ok(new BaseResponse(200, "판매처 이벤트 수정이 완료되었습니다."));
    }

    @Override
    @DeleteMapping
    @Operation(summary = "관리자 기능 : 이벤트 삭제", description = "관리자가 자사몰에 등록된 모든 종류의 이벤트를 하나 혹은 다수로 삭제할 수 있습니다.")
    public ResponseEntity<BaseResponse> deleteEvents(
            @RequestBody @Parameter(description = "삭제할 이벤트 id 리스트") List<Long> ids
    ){
        eventService.deleteEvent(ids);
        return ResponseEntity.ok(new BaseResponse(200, "이벤트 삭제가 완료되었씁니다."));
    }

    @Override
    @GetMapping("/toItme/{itemId}")
    @Operation(summary = "상품 ID로 상품 이름을 검색", description = "상품 이벤트를 등록하거나 수정할 때 이벤트 대상 상품을 검색하는 기능을 수행합니다.")
    public ResponseEntity<SuccessResponse<String>> getItemName(
            @PathVariable @Parameter(description = "상품 ID") Long itemId
    ){
        return itemServiceClient.getItemName(itemId);
    }

    @Override
    @GetMapping("/toSeller")
    @Operation(summary = "판매처 ID로 이름을 검색", description = "판매처 이벤트를 등록하거나 수정할 때 이벤트 대상 판매처를 검색하는 기능을 수행합니다.")
    public ResponseEntity<SuccessResponse<String>> getSellerName(
            @RequestParam @Parameter(description = "판매처 ID") Long id
    ) {
        return sellerSerivceClient.getSellerName(id);
    }

}
