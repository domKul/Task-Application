package com.crud.tasks.mapper;

import com.crud.tasks.domain.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TrelloMapperTests {

    private TrelloMapper trelloMapper = new TrelloMapper();


    @Test
    void testTrelloCardsMapper(){
        //Given
        TrelloCard trelloCard = new TrelloCard("trelloCard", "trelloCard", "123","123");
        TrelloCardDto trelloCardDto = new TrelloCardDto("trelloCardDto", "trelloCardDto", "123","123");

        //When
        TrelloCardDto resultToDto = trelloMapper.mapToCardDto(trelloCard);
        TrelloCard resultToCard = trelloMapper.mapToCard(trelloCardDto);

        //Then
        assertEquals(trelloCard.getName(),resultToDto.getName());
        assertEquals(trelloCard.getDescription(),resultToDto.getDescription());
        assertEquals(trelloCardDto.getName(),resultToCard.getName());
        assertEquals(trelloCardDto.getDescription(),resultToCard.getDescription());
    }

    @Test
    void testTrelloBoardMapper(){
        //Given
        List<TrelloBoardDto> trelloBoardsDtos  = List.of(new TrelloBoardDto("1","dto", new ArrayList<>()));
        List<TrelloBoard> trelloBoards  = List.of(new TrelloBoard("2","no dto", new ArrayList<>()));

        //When
        List<TrelloBoard> resultToBoard = trelloMapper.mapToBoard(trelloBoardsDtos);
        List<TrelloBoardDto> resultToBoardToDto = trelloMapper.mapToBoardsDto(trelloBoards);

        //Then
        assertEquals(1,resultToBoard.size());
        assertEquals(1,resultToBoardToDto.size());
        assertEquals(trelloBoards.get(0).getId(),resultToBoardToDto.get(0).getId());
        assertEquals(trelloBoards.get(0).getName(),resultToBoardToDto.get(0).getName());
        assertEquals(trelloBoardsDtos.get(0).getId(),resultToBoard.get(0).getId());
        assertEquals(trelloBoardsDtos.get(0).getName(),resultToBoard.get(0).getName());
    }

    @Test
    void testTrelloListMapper(){
        //Given
        TrelloList trelloList = new TrelloList("1","ONE",true);
        TrelloListDto trelloListDto = new TrelloListDto("2","TWO",false);
        List<TrelloList> listNoDto = List.of(trelloList);
        List<TrelloListDto> listDto = List.of(trelloListDto);

        //When
        List<TrelloList> toList = trelloMapper.mapToList(listDto);
        List<TrelloListDto> toDto = trelloMapper.mapToListDto(listNoDto);

        //Then
        assertEquals(1,toList.size());
        assertEquals(1,toDto.size());
        assertEquals(trelloList.getId(),toDto.get(0).getId());
        assertEquals(trelloList.getName(),toDto.get(0).getName());
        assertEquals(trelloList.isClosed(),toDto.get(0).isClosed());
        assertEquals(trelloListDto.getId(),toList.get(0).getId());
        assertEquals(trelloListDto.getName(),toList.get(0).getName());
        assertEquals(trelloListDto.isClosed(),toList.get(0).isClosed());
    }
}
