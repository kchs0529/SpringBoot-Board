package org.zerock.board.dto;

import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
public class PageResultDTO<DTO,EN> { // Guestbook -> GuestbookDTO
    private List<DTO> dtoList; // List<DTO> 제네릭타입을 지정해주는데 어떤 DTO인지는 생성할때 결정된다.
    private int totalPage;
    private int page; // 현재 페이지번호
    private int size;
    private int start,end;
    private boolean prev,next;
    private List<Integer> pageList;

    public PageResultDTO(Page<EN> result, Function<EN,DTO> fn){ //dtoList를 생성자로 초기화한다.
        dtoList = result.stream().map(fn).collect(Collectors.toList());
        totalPage = result.getTotalPages();
        makePageList(result.getPageable());
    }

    // 페이징에 필요한것들을 메소드로 따로 빼서 구함
    private void makePageList(Pageable pageable){
        this.page = pageable.getPageNumber()+1;
        this.size = pageable.getPageSize();

        //페이지 블럭의 끝 번호 구하기 공식
        int tempEnd = (int)(Math.ceil(page/10.0))*10;
        //페이지의 시작, 마지막 번호 구하기 공식
        start = tempEnd-9;
        end = totalPage > tempEnd ? tempEnd : totalPage;
        //이전가기, 다음가기
        prev = start>1;
        next = totalPage>tempEnd;

        //1,2,3,4...10 밑에 출력되는 번호를 출력할수있도록 담는 변수 pageList
        // boxed():  IntStream -> Integer -> List<Integer>  스트림을 원시타입인 Integer 로 바꾸고 List<Integer>로바꿈
        pageList = IntStream.rangeClosed(start,end).boxed().collect(Collectors.toList());
    }
}
