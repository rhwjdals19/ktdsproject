package com.ktds.KimGo_Project.service;

import com.ktds.KimGo_Project.dto.BoardDTO;
import com.ktds.KimGo_Project.entity.Board;
import com.ktds.KimGo_Project.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    public void save(BoardDTO boardDTO, MultipartFile file) {
        Board board = convertDTOToEntity(boardDTO);
        // 파일 처리 및 imagePath 설정 로직 생략

        boardRepository.save(board);
    }

    public List<BoardDTO> findAll() {
        return boardRepository.findAll().stream().map(this::convertEntityToDTO).collect(Collectors.toList());
    }

    public Page<BoardDTO> findPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Board> boardPage = boardRepository.findAll(pageable);
        List<BoardDTO> dtos = boardPage.getContent().stream().map(this::convertEntityToDTO).collect(Collectors.toList());
        return new PageImpl<>(dtos, pageable, boardPage.getTotalElements());
    }
    public Page<BoardDTO> findPaginatedByKeyword(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Board> boardPage = boardRepository.findByTitleContainingIgnoreCase(keyword, pageable);
        List<BoardDTO> dtos = boardPage.getContent().stream()
                .map(this::convertEntityToDTO)
                .collect(Collectors.toList());
        return new PageImpl<>(dtos, pageable, boardPage.getTotalElements());
    }






    private BoardDTO convertEntityToDTO(Board board) {
        BoardDTO dto = new BoardDTO();
        dto.setId(board.getId());
        dto.setTitle(board.getTitle());
        dto.setContent(board.getContent());
        dto.setLocation(board.getLocation());
        dto.setPhone(board.getPhone());
        dto.setRating(board.getRating());
        dto.setImagePath(board.getImagePath());
        dto.setDate(board.getDate());
        dto.setFoodType(board.getFoodType());
        dto.setRate(board.getRate());
        return dto;
    }

    private Board convertDTOToEntity(BoardDTO dto) {
        Board board = new Board();
        board.setTitle(dto.getTitle());
        board.setContent(dto.getContent());
        board.setLocation(dto.getLocation());
        board.setPhone(dto.getPhone());
        board.setRating(dto.getRating()); // String 타입 처리
        board.setFoodType(dto.getFoodType()); // String 타입 처리
        board.setRate(dto.getRate()); // String 타입 처리
        board.setImagePath(dto.getImagePath()); // String 타입 처리
        // imagePath 설정은 파일 처리 로직에 따라
        return board;
    }

    public List<BoardDTO> findByKeyword(String keyword) {
        // repository의 검색 메소드를 호출하여 결과를 가져온 후 DTO로 변환
        List<Board> boards = boardRepository.findByTitleContainingIgnoreCase(keyword);
        return boards.stream().map(this::convertEntityToDTO).collect(Collectors.toList());
    }



}