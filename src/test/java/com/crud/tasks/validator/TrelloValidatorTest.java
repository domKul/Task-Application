package com.crud.tasks.validator;

import com.crud.tasks.domain.TrelloBoard;
import com.crud.tasks.domain.TrelloCard;
import com.crud.tasks.trello.validator.TrelloValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TrelloValidatorTest {


    @Test
    public void testValidateTrelloBoards() {
        //Given
        TrelloBoard testBoard1 = new TrelloBoard("1", "test",new ArrayList<>());
        TrelloBoard testBoard2 = new TrelloBoard("2", "test",new ArrayList<>());
        TrelloBoard boardWithOutTest = new TrelloBoard("3", "board",new ArrayList<>());
        List<TrelloBoard> trelloBoards = List.of(testBoard1,testBoard2,boardWithOutTest);
        TrelloValidator trelloValidator = new TrelloValidator();

        //When
        List<TrelloBoard> filteredBoards = trelloValidator.validateTrelloBoards(trelloBoards);

        //Then
        assertEquals(1, filteredBoards.size());
        assertEquals(boardWithOutTest, filteredBoards.get(0));
    }

    @Test
    void testValidateTrelloCard(){
        //Given
        TrelloCard trelloCardWIthTestName = new TrelloCard("test","description","pos","1");
        TrelloCard trelloCardWithCardName = new TrelloCard("card","description","pos","2");

        TrelloValidator trelloValidator = new TrelloValidator();
        //When
        trelloValidator.validateCard(trelloCardWIthTestName);
        trelloValidator.validateCard(trelloCardWithCardName);

        //Then
        //nothing
    }

}
