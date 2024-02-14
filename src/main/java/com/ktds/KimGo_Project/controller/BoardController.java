package com.ktds.KimGo_Project.controller;

import com.ktds.KimGo_Project.dto.BoardDTO;
import com.ktds.KimGo_Project.entity.Board;
import com.ktds.KimGo_Project.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;



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
        if (!file.isEmpty()) {
            String uploadDir = "C:\\Users\\KTDS\\Desktop\\KimGo_Project\\src\\main\\resources\\static\\upload";
            Path uploadPath = Paths.get(uploadDir);

            try {
                // 디렉토리가 존재하지 않으면 생성
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                // 파일 저장
                Path filePath = uploadPath.resolve(file.getOriginalFilename());
                Files.copy(file.getInputStream(), filePath);

                // 파일 경로 저장 (데이터베이스에 저장될 경로 설정)
                boardDTO.setImagePath("/upload/" + file.getOriginalFilename());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        boardService.save(boardDTO, file);
        redirectAttributes.addFlashAttribute("message", "맛집 정보가 성공적으로 등록되었습니다.");
        return "redirect:/board/list";
    }

    @GetMapping("/list")
    public String listBoards(Model model, @RequestParam(value = "search", required = false) String search) {
        List<BoardDTO> list = boardService.findAll();

        if (search != null && !search.trim().isEmpty()) {
            // 검색어가 있는 경우 검색 결과를 가져옵니다.
            list = boardService.findByKeyword(search);
        } else {
            // 검색어가 없는 경우 모든 목록을 가져옵니다.
            list = boardService.findAll();
        }

        list.sort(new Comparator<BoardDTO>() {
            @Override
            public int compare(BoardDTO o1, BoardDTO o2) {
                return o2.getRate() - o1.getRate();
            }
        });
//        model.addAttribute("boards", baordService.findAll());
        model.addAttribute("boards", list);
        return "list";
    }
}