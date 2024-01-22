package kea.dpang.eventserver.controller;

import jakarta.websocket.server.PathParam;
import kea.dpang.eventserver.dto.request.ItemEventCreateDto;
import kea.dpang.eventserver.dto.request.SellerEventCreateDto;
import kea.dpang.eventserver.dto.response.AllEventGetDto;
import kea.dpang.eventserver.dto.response.ItemEventGetDto;
import kea.dpang.eventserver.dto.response.SellerEventGetDto;
import kea.dpang.eventserver.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RestController("/events")
public class EventController {
    private final EventService eventService;

    // 사용자 - 상품 이벤트 목록 조회(get)
    @GetMapping("/user/item")
    public List<ItemEventGetDto> getItemEvents(){
        return eventService.getItemEvents();
    }
    // 사용자 - 판매처 이벤트 목록 조회(get)
    @GetMapping("/user/seller")
    public List<SellerEventGetDto> getSellerEvents(){
        return eventService.getSellerEvents();
    }
    // 관리자 - 이벤트 목록 조회(get)
    @GetMapping("/admin")
    public List<AllEventGetDto> getAllEvents(){
        return eventService.getAllEvent();
    }
    // 관리자 - 상품 이벤트 등록(post)
    @PostMapping("/admin/item")
    public void postItemEvent(@RequestBody ItemEventCreateDto request){
        eventService.createItemEvent(request);
    }
    // 관리자 - 판매처 이벤트 등록(post)
    @PostMapping("/admin/seller")
    public void postSellerEvent(@RequestBody SellerEventCreateDto request){
        eventService.createSellerEvent(request);
    }
    // 관리자 - 상품 이벤트 수정(put)
    @PutMapping("/admin/item/{eventId}")
    public void putItemEvent(@PathParam("eventId") Long id, @RequestBody ItemEventCreateDto request){
        eventService.updateItemEvent(id,request);
    }
    // 관리자 - 판매처 이벤트 수정(put)
    @PutMapping("/admin/seller/{eventId}")
    public void putSellerEvent(@PathParam("eventId") Long id, @RequestBody SellerEventCreateDto request){
        eventService.updateSellerEvent(id,request);
    }
    // 관리자 - 이벤트 삭제(delete)
    @DeleteMapping("/dlete")
    public void deleteEvents(@RequestParam List<Long> ids){
        eventService.deleteEvent(ids);
    }
}
