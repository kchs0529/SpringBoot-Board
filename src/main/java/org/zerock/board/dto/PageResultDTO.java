package org.zerock.board.dto;

import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
public class PageResultDTO<DTO,EN> {   // Guestbook -> GuestbookDTO

     private List<DTO> dtoList;

     private int totalPage;

     private int page;

     private int size;  // getter/setter

     private int start,end;

     private boolean prev,next;  // isXXX()

     private List<Integer> pageList;

     public PageResultDTO(Page<EN> result, Function<EN,DTO> fn) {
         dtoList = result.stream().map(fn).collect(Collectors.toList());
         totalPage = result.getTotalPages();
         makePageList(result.getPageable());
     }

     private void makePageList(Pageable pageable){
        this.page = pageable.getPageNumber() + 1;
        this.size = pageable.getPageSize();

        //페이지 블럭의 마지막 끝번호 구하기
        int tempEnd = (int)(Math.ceil(page/10.0)) * 10;

        //페이지의 시작번호
        start = tempEnd - 9;

        prev = start > 1;

        end = totalPage > tempEnd ? tempEnd: totalPage;

        next = totalPage > tempEnd;

        // boxed() : IntStream -> Stream<Integer> -> List<Integer>
        pageList = IntStream.rangeClosed(start,end).boxed().collect(Collectors.toList());

     }

}
