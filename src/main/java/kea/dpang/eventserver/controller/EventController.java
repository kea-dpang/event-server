package kea.dpang.eventserver.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.websocket.server.PathParam;
import kea.dpang.eventserver.dto.request.ItemEventCreateDto;
import kea.dpang.eventserver.dto.request.SellerEventCreateDto;
import kea.dpang.eventserver.dto.response.AllEventGetDto;
import kea.dpang.eventserver.dto.response.ItemEventGetDto;
import kea.dpang.eventserver.dto.response.SellerEventGetDto;
import kea.dpang.eventserver.service.EventServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Event", description = "Event 서비스 API")
@Controller
@RequiredArgsConstructor
@RestController("/events")
@Slf4j
public class EventController {

    private final EventServiceImpl eventService;

    // 사용자 - 상품 이벤트 목록 조회(get)
    @GetMapping("/user/item")
    @Operation(summary = "사용자 기능 : 상품 이벤트 목록 조회", description = "사용자가 자사몰의 예정/진행중/종료 상품 이벤트를 모두 조회할 수 있습니다.")
    public List<ItemEventGetDto> getItemEvents(){
        return eventService.getItemEvents();
    }
    // 사용자 - 판매처 이벤트 목록 조회(get)
    @GetMapping("/user/seller")
    @Operation(summary = "사용자 기능 : 판매처 이벤트 목록 조회", description = "사용자가 자사몰의 예정/진행중/종료 판매처 이벤트를 모두 조회할 수 있습니다.")
    public List<SellerEventGetDto> getSellerEvents(){
        return eventService.getSellerEvents();
    }
    // 관리자 - 이벤트 목록 조회(get)
    @GetMapping("/admin")
    @Operation(summary = "관리자 기능 : 이벤트 목록 조회", description = "관리자가 자사몰의 예정/진행중/종료 이벤트를 모두 조회할 수 있습니다.")
    public List<AllEventGetDto> getAllEvents(){
        return eventService.getAllEvent();
    }
    // 관리자 - 상품 이벤트 등록(post)
    @PostMapping("/admin/item")
    @Operation(summary = "관리자 기능 : 상품 이벤트 등록", description = "관리자가 자사몰에 상품 이벤트를 등록할 수 있습니다.")
    public void postItemEvent(@RequestBody ItemEventCreateDto request){
        eventService.createItemEvent(request);
    }
    // 관리자 - 판매처 이벤트 등록(post)
    @PostMapping("/admin/seller")
    @Operation(summary = "관리자 기능 : 판매처 이벤트 등록", description = "관리자가 자사몰에 판매처 이벤트를 등록할 수 있습니다.")
    public void postSellerEvent(@RequestBody SellerEventCreateDto request){
        eventService.createSellerEvent(request);
    }
    // 관리자 - 상품 이벤트 수정(put)
    @PutMapping("/admin/item/{eventId}")
    @Operation(summary = "관리자 기능 : 상품 이벤트 수정", description = "관리자가 자사몰에 등록된 상품 이벤트를 수정할 수 있습니다.")
    public void putItemEvent(@PathParam("eventId") Long id, @RequestBody ItemEventCreateDto request){
        eventService.updateItemEvent(id,request);
    }
    // 관리자 - 판매처 이벤트 수정(put)
    @PutMapping("/admin/seller/{eventId}")
    @Operation(summary = "관리자 기능 : 판매처 이벤트 수정", description = "관리자가 자사몰에 등록된 관리자 이벤트를 수정할 수 있습니다.")
    public void putSellerEvent(@PathParam("eventId") Long id, @RequestBody SellerEventCreateDto request){
        eventService.updateSellerEvent(id,request);
    }
    // 관리자 - 이벤트 삭제(delete)
    @DeleteMapping("/dlete")
    @Operation(summary = "관리자 기능 : 이벤트 삭제", description = "관리자가 자사몰에 등록된 모든 종류의 이벤트를 하나 혹은 다수로 삭제할 수 있습니다.")
    public void deleteEvents(@RequestParam List<Long> ids){
        eventService.deleteEvent(ids);
    }
}
