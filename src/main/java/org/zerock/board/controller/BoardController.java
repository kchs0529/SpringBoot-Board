package org.zerock.board.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.board.dto.BoardDTO;
import org.zerock.board.dto.PageRequestDTO;
import org.zerock.board.service.BoardService;

@Controller
@RequestMapping("/board/")
@Log4j2
@RequiredArgsConstructor
public class BoardController {

    private  final BoardService boardService;

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model){

        log.info("List........."+pageRequestDTO);

        model.addAttribute("result",boardService.getList(pageRequestDTO));

    }

    @GetMapping("/register")
    public void register(){
        log.info("register get....");
    }

    @PostMapping("/register")
    public String registerPost(BoardDTO dto, RedirectAttributes redirectAttributes){

        log.info("dto...." + dto);

        Long bno = boardService.register(dto);

        log.info(bno);

        redirectAttributes.addFlashAttribute("msg",bno);
        return "redirect:/board/list";
    }

    @GetMapping({"/read","/modify"})
    public void read(Long bno, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model){
        log.info("bno...." + bno);
        BoardDTO boardDTO = boardService.get(bno);
        log.info(boardDTO);
        model.addAttribute("dto",boardDTO);
    }

    @PostMapping("/remove")
    public String remove(Long bno,RedirectAttributes redirectAttributes){
        log.info("bno : "+bno);
        boardService.removeWithReplies(bno);
        redirectAttributes.addFlashAttribute("msg",bno);

        return "redirect:/board/list";
    }

    @PostMapping("/modify")
    public String modify(BoardDTO dto,@ModelAttribute("requestDTO") PageRequestDTO requestDTO,
                         RedirectAttributes redirectAttributes){
        log.info("post modify.....................");
        log.info("dto:"+dto);

        boardService.modify(dto);

        //다른 컨트롤러에 값을 넘겨주기 위해 사용
        redirectAttributes.addAttribute("bno",dto.getBno());
        redirectAttributes.addAttribute("page",requestDTO.getPage());


        return "redirect:/board/read";
    }
}
