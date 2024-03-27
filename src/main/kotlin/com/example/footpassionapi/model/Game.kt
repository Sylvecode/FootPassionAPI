package com.example.footpassionapi.model

import jakarta.persistence.*
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service
import java.sql.Date


@Entity
@Table(name = "game")
data class GameBean(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    var equipe1: String = "",
    var equipe2: String = "",
    var date: Date? = null,
    var scoreEquipe1: Int = 0,
    var scoreEquipe2: Int = 0,
    var fini: Boolean = false
)

@Repository
interface GameRepository : JpaRepository<GameBean, Long>

@Service
class GameService(val gameRep: GameRepository) {

    fun createGame(equipe1: String, equipe2: String, date: Date?): GameBean {
        if (date == null) {
            throw Exception("Veuillez rentrer la date")
        } else if (equipe1.isNullOrBlank()) {
            throw Exception("Veuillez rentrer le nom de l'équipe 1")
        } else if (equipe2.isNullOrBlank()) {
            throw Exception("Veuillez rentrer le nom de l'équipe 2")
        }
        val game = GameBean(null, equipe1, equipe2, date)
        gameRep.save(game)
        return game
    }


    fun updateGame(id: Long, action: String): GameBean {
        val game: GameBean = gameRep.findByIdOrNull(id) ?: throw Exception("Match introuvable")

        if (game.fini) {
            throw Exception("Le match est déjà terminé")
        }

        when (action) {
            "equipe1" -> game.scoreEquipe1++
            "equipe2" -> game.scoreEquipe2++
            "end" -> game.fini = true
            else -> throw Exception("Veuillez rentrer une action valide (equipe1, equipe2, end)")
        }

        return gameRep.save(game)
    }


    fun getRecentGames(): List<GameBean> {
        val allGames = gameRep.findAll(Sort.by(Sort.Direction.DESC, "date"))
        return allGames.filter { it.fini }
    }

    fun getAll() = gameRep.findAll()
}
