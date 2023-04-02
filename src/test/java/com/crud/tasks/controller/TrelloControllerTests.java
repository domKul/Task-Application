package com.crud.tasks.controller;

import com.crud.tasks.domain.CreatedTrelloCardDto;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.domain.TrelloListDto;
import com.crud.tasks.trello.facade.TrelloFacade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class TrelloControllerTests {
    @Mock
    private TrelloFacade trelloFacade;

    private TrelloController trelloController;



    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        trelloController = new TrelloController(trelloFacade);
    }

    @Test
    public void testGetTrelloBoardsReturnsOkResponseWithBoards() {
        //Given
        TrelloBoardDto board1 = new TrelloBoardDto("1", "Board 1",
                List.of(new TrelloListDto("1", "List 1", false)));
        TrelloBoardDto board2 = new TrelloBoardDto("2", "Board 2",
                List.of(new TrelloListDto("2", "List 2", false)));
        List<TrelloBoardDto> boards = List.of(board1, board2);
        when(trelloFacade.fetchTrelloBoards()).thenReturn(boards);

        //When
        ResponseEntity<List<TrelloBoardDto>> response = trelloController.getTrelloBoards();

        //Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(boards, response.getBody());
    }

    @Test
    public void testGetTrelloBoardsReturnsOkResponseWithEmptyList() {
        //Given
        List<TrelloBoardDto> boards = List.of();
        when(trelloFacade.fetchTrelloBoards()).thenReturn(boards);

        //When
        ResponseEntity<List<TrelloBoardDto>> response = trelloController.getTrelloBoards();

        //Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(boards, response.getBody());
    }

    @Test
    public void testCreateTrelloCard(){
        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto("name","description","pos","1");
        CreatedTrelloCardDto createdTrelloCardDto = new CreatedTrelloCardDto();
        when(trelloFacade.createCard(trelloCardDto)).thenReturn(createdTrelloCardDto);

        //When
        ResponseEntity<CreatedTrelloCardDto> result = trelloController.createdTrelloCard(trelloCardDto);

        //Then
        assertEquals(HttpStatus.OK,result.getStatusCode());
        assertEquals(createdTrelloCardDto,result.getBody());
    }
}
