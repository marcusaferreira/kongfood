package br.com.fiap.techchallenge.kongfood.domain.customer

class CPF(
    val cpf: String
) {
    init {
        require(cpf.length != 11) { "CPF must have 11 digits" }
        require(!cpf.matches(Regex("[0-9]+"))) { "CPF must have only numbers" }
        require(verifyCPF()) {"CPF is invalid"}
    }
    private fun verifyCPF(): Boolean {
        if (cpf == "00000000000" ||
            cpf == "11111111111" ||
            cpf == "22222222222" || cpf == "33333333333" ||
            cpf == "44444444444" || cpf == "55555555555" ||
            cpf == "66666666666" || cpf == "77777777777" ||
            cpf == "88888888888" || cpf == "99999999999" ||
            cpf.length != 11
        ) return false

        var d1 = 0
        var d2 = 0
        val digito1: Int
        val digito2: Int
        var digitoCPF: Int


        for (nCount in 1 until cpf.length - 1) {
            digitoCPF = cpf.substring(nCount - 1, nCount).toInt()

            // multiplique a última casa por 2 a seguinte por 3 a seguinte por 4
            // e assim por diante.
            d1 += (11 - nCount) * digitoCPF

            // para o segundo digito repita o procedimento incluindo o primeiro
            // digito calculado no passo anterior.
            d2 += (12 - nCount) * digitoCPF
        }

        // Primeiro resto da divisão por 11.

        // Primeiro resto da divisão por 11.
        var resto: Int = d1 % 11

        // Se o resultado for 0 ou 1 o digito é 0 caso contrário o digito é 11
        // menos o resultado anterior.

        // Se o resultado for 0 ou 1 o digito é 0 caso contrário o digito é 11
        // menos o resultado anterior.
        digito1 = if (resto < 2) 0 else 11 - resto

        d2 += 2 * digito1

        // Segundo resto da divisão por 11.

        // Segundo resto da divisão por 11.
        resto = d2 % 11

        // Se o resultado for 0 ou 1 o digito é 0 caso contrário o digito é 11
        // menos o resultado anterior.

        // Se o resultado for 0 ou 1 o digito é 0 caso contrário o digito é 11
        // menos o resultado anterior.
        digito2 = if (resto < 2) 0 else 11 - resto

        // Digito verificador do CPF.
        val nDigVerific = cpf.substring(cpf.length - 2, cpf.length)

        // Concatenando o primeiro resto com o segundo.

        // Concatenando o primeiro resto com o segundo.
        val nDigResult: String = digito1.toString() + digito2.toString()

        // comparar o digito verificador do cpf com o primeiro resto + o segundo
        // resto.

        // comparar o digito verificador do cpf com o primeiro resto + o segundo
        // resto.
        return nDigVerific == nDigResult
    }

    override fun toString(): String {
        return cpf
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is CPF) return false

        if (cpf != other.cpf) return false

        return true
    }

    override fun hashCode(): Int {
        return cpf.hashCode()
    }
}