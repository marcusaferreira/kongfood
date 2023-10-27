package br.com.fiap.techchallenge.kongfood.domain.product

enum class ProductCategory (val type: String) {
    DRINKS("Drinks"),
    DESSERTS("Desserts"),
    MAIN_COURSES("Main courses"),
    SIDE_DISHES("Side dishes"),
    COMBOS("Combos");

    companion object{
        fun getEnumByString(type: String): ProductCategory? {
            for (e in entries) {
                if (e.type.contentEquals(type,true)) return e
            }
            return null
        }
    }

}
