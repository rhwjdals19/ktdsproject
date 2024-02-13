package com.ktds.KimGo_Project.repository;

import com.ktds.KimGo_Project.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BoardRepository extends JpaRepository<Board, Long> {
}
