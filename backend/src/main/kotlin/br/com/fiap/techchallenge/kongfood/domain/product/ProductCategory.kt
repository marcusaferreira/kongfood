package br.com.fiap.techchallenge.kongfood.domain.product

enum class ProductCategory (val type: String) {
    DRINKS("Drinks"),
    DESSERTS("Desserts"),
    MAIN_COURSES("Main courses"),
    SIDE_DISHES("Side dishes"),
    COMBOS("Combos");

    companion object{
        fun getEnumByString(type: String): ProductCategory? {
            var typeWithoutSpecialCharacters = type.replace("_".toRegex(), " ")
            typeWithoutSpecialCharacters = typeWithoutSpecialCharacters.replace("-".toRegex(), " ")
            typeWithoutSpecialCharacters = typeWithoutSpecialCharacters.replace("[^A-Za-z0-9 ]".toRegex(), "")
            for (e in entries) {
                if (e.type.contentEquals(typeWithoutSpecialCharacters,true)) return e
            }
            return null
        }
    }

}
