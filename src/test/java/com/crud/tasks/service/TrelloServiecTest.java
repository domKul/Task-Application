package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.*;
import com.crud.tasks.trello.client.TrelloClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
public class TrelloServiecTest {
    @Mock
    private TrelloClient trelloClient;

    @Mock
    private SimpleEmailService emailService;

   @Mock
   private AdminConfig adminConfig;

    @InjectMocks
    private TrelloService trelloService;
    @Mock
    private TrelloCardDto trelloCardDto;



    @Test
    public void testCreateTrelloCard() {
        //Given
        trelloService = new TrelloService(trelloClient, emailService, adminConfig);
        trelloCardDto = new TrelloCardDto("Test Card", "Test Description", "top", "test-list-id");
        when(adminConfig.getAdminMail()).thenReturn("${admin.mail}");
        Trello trello = new Trello();
        trello.setCard(1);
        trello.setBoard(1);
        AttachmentsByType attachmentsByType = new AttachmentsByType();
        attachmentsByType.setTrello(trello);
        Badges badges = new Badges();
        badges.setVote(1);
        badges.setAttachments(attachmentsByType);
        CreatedTrelloCardDto expectedCard = new CreatedTrelloCardDto();
        expectedCard.setId("1234");
        expectedCard.setName("Test Card");
        expectedCard.setShortUrl("test URL");
        expectedCard.setBadges(badges);
        when(trelloClient.createNewCard(trelloCardDto)).thenReturn(expectedCard);

        //When
        CreatedTrelloCardDto resultCard = trelloService.createTrelloCard(trelloCardDto);

        //Then
        assertEquals(expectedCard.getName(), resultCard.getName());
        verify(trelloClient, times(1)).createNewCard(trelloCardDto);
        verify(emailService, times(1)).send(any(Mail.class));
        Optional.of(resultCard).ifPresent(card ->
                assertEquals("Test Card", card.getName())
        );
    }


        @Test
        void testFetchTrelloBoards() {
            // given
            TrelloListDto trelloListDto1 = new TrelloListDto("1", "List 1", true);
            TrelloListDto trelloListDto2 = new TrelloListDto("2", "List 2", false);
            TrelloBoardDto board1 = new TrelloBoardDto();
            TrelloBoardDto board2 = new TrelloBoardDto("2", "Board 2", List.of(new TrelloListDto("3", "List 3", true)));
            board1.setLists(List.of(trelloListDto1,trelloListDto2));
            List<TrelloBoardDto> boards = List.of(board1, board2);
            when(trelloClient.getTrelloBoards()).thenReturn(boards);

            // when
            List<TrelloBoardDto> fetchedBoards = trelloService.fetchTrelloBoards();

            // then
            assertEquals(boards.size(), fetchedBoards.size());
            assertEquals(boards.get(0).getId(), fetchedBoards.get(0).getId());
            assertEquals(boards.get(1).getName(), fetchedBoards.get(1).getName());
        }

}
