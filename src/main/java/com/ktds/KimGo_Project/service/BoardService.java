package com.ktds.KimGo_Project.service;

import com.ktds.KimGo_Project.dto.BoardDTO;
import com.ktds.KimGo_Project.entity.Board;
import com.ktds.KimGo_Project.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

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
        // imagePath 설정은 파일 처리 로직에 따라
        return board;
    }
}