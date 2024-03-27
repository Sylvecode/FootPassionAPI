package com.example.footpassionapi.apirest

import com.example.footpassionapi.model.GameBean
import com.example.footpassionapi.model.GameService


import org.springframework.web.bind.annotation.*
import java.sql.Date
import java.sql.Date.*


//Classe contenant des méthodes associées à des URL retournant du JSON (APIRest)
@RestController
class MyRestController(val gameService: GameService) {

    //http://localhost:8080/test
    @GetMapping("/test")
    fun testMethode(): String {
        println("/test")

        return "<b>helloWorld</b>"
    }

    //http://localhost:8080/addGame?equipe1=Lyon&equipe2=Marseille&date=2024-03-23
    //Créer un match en rentrant le nom de l'equipe 1, de l'équipe 2 et la date en paramètre
    @GetMapping("/addGame")
    fun addGame(equipe1: String, equipe2: String, date: Date) {
        println("/addGame equipe1=$equipe1 equipe2=$equipe2 date=$date" )

        gameService.createGame(equipe1, equipe2, date)
    }

    //http://localhost:8080/updateGame?id=6&action=equipe1
    //Prend l'id du match en paramètre et incrémente le score de l'équipe choisie ou déclare le match fini
    //action=equipe1 (incrémente le score de l'équipe1) ; action=equipe2 (incrémente le score de l'équipe1) ; action=end (déclare le match terminé)
    @GetMapping("/updateGame")
    fun updateGame(id: Long, action: String) : GameBean {
        println("/updateGame action=$action" )

        return gameService.updateGame(id, action)
    }

    //http://localhost:8080/getRecentGames
    //Récupère la liste des matchs du plus récent au plus ancien
    @GetMapping("/getRecentGames")
    fun getRecentGames() : List<GameBean> {
        return gameService.getRecentGames()
    }
}