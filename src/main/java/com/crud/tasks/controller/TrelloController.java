package com.crud.tasks.controller;

import com.crud.tasks.domain.CreatedTrelloCard;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.domain.service.TrelloService;
import com.crud.tasks.trello.client.TrelloClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("v1/trello")
@RequiredArgsConstructor
@CrossOrigin("*")
public class TrelloController {

    private final TrelloService trelloService;

    @GetMapping("boards")
    public ResponseEntity<List<TrelloBoardDto>> getTrelloBoards() {
        return ResponseEntity.ok(trelloService.fetchTrelloBoards());

    }

    @PostMapping("cards")
    public ResponseEntity<CreatedTrelloCard> createdTrelloCard (@RequestBody TrelloCardDto trelloCardDto){
        return ResponseEntity.ok(trelloService.createTrelloCard(trelloCardDto));
    }


 /*private void save(){
     List<TrelloBoardDto> trelloBoards = trelloClient.getTrelloBoards();
     trelloBoards.stream()
             .filter(trelloBoardDto -> trelloBoardDto.getId() !=null&&trelloBoardDto.getName() !=null)
             .filter(trelloBoardDto -> trelloBoardDto.getName().contains("Kodilla"))
             .forEach(trelloBoardDto -> System.out.println("ID: " +
                     trelloBoardDto.getId() + " Name: " + trelloBoardDto.getName() ));
     System.out.println("This board contains lists: ");
     trelloBoards.stream()
             .filter(trelloBoardDto -> trelloBoardDto.getName().contains("Kodilla"))
             .map(TrelloBoardDto::getLists)
             .flatMap(Collection::stream)
             .forEach(trelloListDto -> System.out.println(trelloListDto.getName()+" id - "+
                     trelloListDto.getId() +" "+ trelloListDto.isClosed()));
 }*/

}

