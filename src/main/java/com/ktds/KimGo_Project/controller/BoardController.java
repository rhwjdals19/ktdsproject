package com.ktds.KimGo_Project.controller;

import com.ktds.KimGo_Project.dto.BoardDTO;
import com.ktds.KimGo_Project.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    @Autowired
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping("/save")
    public String savePage() {
        return "save";
    }

    @PostMapping("/save")
    public String saveBoard(@ModelAttribute BoardDTO boardDTO,
                            @RequestParam("chooseFile") MultipartFile file,
                            RedirectAttributes redirectAttributes) {
        boardService.save(boardDTO, file);
        redirectAttributes.addFlashAttribute("message", "맛집 정보가 성공적으로 등록되었습니다.");
        return "redirect:/board/list";
    }

    @GetMapping("/list")
    public String listBoards(Model model) {
        model.addAttribute("boards", boardService.findAll());
        return "list";
    }
}