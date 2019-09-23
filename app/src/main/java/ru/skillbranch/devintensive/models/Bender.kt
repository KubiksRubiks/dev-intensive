package ru.skillbranch.devintensive.models

class Bender(var status: Status = Status.NORMAL, var question: Question = Question.NAME) {

    var answerCount: Int = 0

    fun askQuestion(): String = when (question) {
        Question.NAME -> Question.NAME.question
        Question.PROFESSION -> Question.PROFESSION.question
        Question.MATERIAL -> Question.MATERIAL.question
        Question.BDAY -> Question.BDAY.question
        Question.SERIAL -> Question.SERIAL.question
        Question.IDLE -> Question.IDLE.question
    }

    fun listenAnswer(answer: String): Pair<String, Triple<Int, Int, Int>> {
        return when (question) {
            Question.IDLE -> question.question to status.color
            else -> {
                "${checkAnswer(answer)}\n${question.question}" to status.color
            }
        }
    }

    private fun checkAnswer(answer: String): String {
        val (isValid, answerString) = question.validate(answer)
        return if (isValid) {
            if (question.answers.contains(answerString)) {
                question = question.nextQuestion()
                "Отлично - ты справился"
            } else {
                answerCount++
                if (answerCount > 3) {
                    answerCount = 0
                    resetBenderState()
                    "Это неправильный ответ. Давай все по новой"
                } else {
                    status = status.nextStatus()
                    "Это неправильный ответ"
                }
            }
        } else {
            answerString
        }
    }

    private fun resetBenderState() {
        status = Status.NORMAL
        question = Question.NAME
    }

    enum class Status(val color: Triple<Int, Int, Int>) {
        NORMAL(Triple(255, 255, 255)),
        WARNING(Triple(255, 120, 0)),
        DANGER(Triple(255, 60, 60)),
        CRITICAL(Triple(255, 0, 0));

        fun nextStatus(): Status {
            return if (this.ordinal < values().lastIndex) {
                values()[this.ordinal + 1]
            } else {
                values()[0]
            }

        }
    }

    enum class Question(val question: String, val answers: List<String>) {
        NAME("Как меня зовут?", listOf("бендер", "bender")) {
            override fun nextQuestion(): Question = PROFESSION
            override fun validate(text: String): Pair<Boolean, String> {
                return if (text.isNotEmpty() && text[0].isUpperCase()) {
                    true to text.toLowerCase()
                } else {
                    false to "Имя должно начинаться с заглавной буквы"
                }
            }
        },
        PROFESSION("Назови мою профессию?", listOf("сгибальщик", "bender")) {
            override fun nextQuestion(): Question = MATERIAL
            override fun validate(text: String): Pair<Boolean, String> {
                return if (text.isNotEmpty() && text[0].isLowerCase()) {
                    true to text.toLowerCase()
                } else {
                    false to "Профессия должна начинаться со строчной буквы"
                }
            }
        },
        MATERIAL("Из чего я сделан?", listOf("металл", "дерево", "metal", "iron", "wood")) {
            override fun nextQuestion(): Question = BDAY
            override fun validate(text: String): Pair<Boolean, String> {
                val pattern = Regex("\\d+")
                return if (pattern.findAll(text).none()) {
                    true to text.toLowerCase()
                } else {
                    false to "Материал не должен содержать цифр"
                }
            }
        },
        BDAY("Когда меня создали?", listOf("2993")) {
            override fun nextQuestion(): Question = SERIAL
            override fun validate(text: String): Pair<Boolean, String> {
                val pattern = Regex("[^\\d]")
                return if (pattern.findAll(text).none()) {
                    true to text.toLowerCase()
                } else {
                    false to "Год моего рождения должен содержать только цифры"
                }
            }
        },
        SERIAL("Мой серийный номер?", listOf("2716057")) {
            override fun nextQuestion(): Question = IDLE
            override fun validate(text: String): Pair<Boolean, String> {
                val pattern = Regex("[^\\d]")
                return if (pattern.findAll(text).none() && text.length == 7) {
                    true to text.toLowerCase()
                } else {
                    false to "Серийный номер содержит только цифры, и их 7"
                }
            }
        },
        IDLE("На этом все, вопросов больше нет", listOf()) {
            override fun nextQuestion(): Question = IDLE
            override fun validate(text: String): Pair<Boolean, String> {
                return true to text
            }
        };

        abstract fun nextQuestion(): Question
        abstract fun validate(text: String): Pair<Boolean, String>
    }
}